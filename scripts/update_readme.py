#!/usr/bin/env python3
"""
update_readme.py  ─  Dual-platform DSA stats engine
═════════════════════════════════════════════════════
Supports: LeetCode (topics/ + {num}-{slug}/) + GeeksforGeeks (Difficulty: {level}/{name}/)

README markers patched:
  <!-- STATS:START -->        ... <!-- STATS:END -->
  <!-- PLATFORM:START -->     ... <!-- PLATFORM:END -->
  <!-- TOPICS:START -->       ... <!-- TOPICS:END -->
  <!-- RECENT:START -->       ... <!-- RECENT:END -->

Author: auto-generated for yadavxprakhar/DSA-Solutions
"""

import os
import re
import json
import time
import requests
from datetime import datetime
from pathlib import Path
from collections import defaultdict

# ══════════════════════════════════════════════════════════════════════════════
# CONFIG
# ══════════════════════════════════════════════════════════════════════════════

REPO_ROOT   = Path(__file__).parent.parent
README_PATH = REPO_ROOT / "README.md"
INDEX_FILE  = REPO_ROOT / "scripts" / "problem_index.json"

# ── LeetCode paths ──
LC_TOPICS_DIR = REPO_ROOT / "topics"          # organized problems
LC_ROOT_PATTERN = re.compile(r'^(\d+)-(.+)$') # raw LeetSync dumps in root

# ── GFG paths ──
# GFG-to-GitHub extension creates: Difficulty: {Basic|Easy|Medium|Hard}/{Problem Name}/
GFG_DIFFICULTY_RE = re.compile(
    r'^Difficulty:\s*(Basic|Easy|Medium|Hard)$',
    re.IGNORECASE
)

# ── Difficulty normalization ──
LC_DIFFICULTIES  = ("easy", "medium", "hard")
GFG_DIFFICULTIES = ("basic", "easy", "medium", "hard")

DIFFICULTY_EMOJI = {
    "easy":   "🟢 Easy",
    "medium": "🟡 Medium",
    "hard":   "🔴 Hard",
    "basic":  "🔵 Basic",
}

PLATFORM_BADGE = {
    "leetcode": "![LC](https://img.shields.io/badge/LC-FFA116?style=flat-square&logo=leetcode&logoColor=white)",
    "gfg":      "![GFG](https://img.shields.io/badge/GFG-2F8D46?style=flat-square&logo=geeksforgeeks&logoColor=white)",
}

TOPIC_ORDER = [
    "arrays", "strings", "linked-list", "trees", "graphs",
    "dynamic-programming", "sliding-window", "two-pointers",
    "binary-search", "stack-queue", "backtracking", "greedy",
    "heap", "math",
]

LEETCODE_API   = "https://leetcode.com/graphql"
LEETCODE_QUERY = """
query getProblem($titleSlug: String!) {
  question(titleSlug: $titleSlug) {
    title
    difficulty
  }
}
"""

# ══════════════════════════════════════════════════════════════════════════════
# INDEX  (problem_index.json)
# ══════════════════════════════════════════════════════════════════════════════

def load_index() -> dict:
    if INDEX_FILE.exists():
        with open(INDEX_FILE) as f:
            raw = json.load(f)
        return {k: v for k, v in raw.items() if not k.startswith("_")}
    return {}


def save_index(index: dict):
    with open(INDEX_FILE, "w") as f:
        json.dump(index, f, indent=2)
    print(f"   💾  Saved {len(index)} entries → problem_index.json")


# ══════════════════════════════════════════════════════════════════════════════
# LEETCODE API
# ══════════════════════════════════════════════════════════════════════════════

def fetch_lc_difficulty(slug: str) -> dict:
    """Hit LeetCode GraphQL. Returns {title, difficulty} or {}."""
    try:
        r = requests.post(
            LEETCODE_API,
            json={"query": LEETCODE_QUERY, "variables": {"titleSlug": slug}},
            headers={
                "Content-Type": "application/json",
                "User-Agent":   "Mozilla/5.0",
                "Referer":      "https://leetcode.com",
            },
            timeout=10,
        )
        if r.status_code != 200:
            print(f"   ⚠️   LC API {r.status_code} for '{slug}'")
            return {}
        q = r.json().get("data", {}).get("question")
        if not q:
            return {}
        return {"title": q["title"], "difficulty": q["difficulty"].lower()}
    except requests.exceptions.RequestException as e:
        print(f"   ⚠️   LC API error for '{slug}': {e}")
        return {}


def resolve_lc_meta(index: dict, key: str, slug: str) -> dict:
    """
    Return cached LC metadata, or fetch from API if missing/invalid.
    Mutates index in-place so caller can detect whether it changed.
    """
    cached = index.get(key, {})
    if cached.get("difficulty") in LC_DIFFICULTIES and cached.get("platform") == "leetcode":
        return cached

    print(f"   🌐  Fetching LC: {slug}")
    data = fetch_lc_difficulty(slug)
    time.sleep(0.3)

    if data:
        entry = {**cached, **data, "platform": "leetcode"}
        index[key] = entry
        print(f"   ✅  {data['title']} → {data['difficulty'].upper()}")
        return entry

    # API failed — keep existing or create fallback (will retry next run)
    fallback = {
        "title":      slug.replace("-", " ").title(),
        "difficulty": "medium",
        "platform":   "leetcode",
    }
    index[key] = {**fallback, **cached}  # cached fields win if present
    return index[key]


# ══════════════════════════════════════════════════════════════════════════════
# GFG PARSER
# ══════════════════════════════════════════════════════════════════════════════

def slugify(name: str) -> str:
    """'Find length of Loop' → 'find-length-of-loop'"""
    return re.sub(r'[^a-z0-9]+', '-', name.lower()).strip('-')


def resolve_gfg_meta(index: dict, key: str, problem_name: str, difficulty: str) -> dict:
    """
    GFG metadata comes directly from folder structure — no API needed.
    Upserts into index for caching purposes.
    """
    cached = index.get(key, {})
    if cached.get("platform") == "gfg" and cached.get("difficulty"):
        return cached

    entry = {
        "title":      problem_name,
        "difficulty": difficulty.lower(),  # basic / easy / medium / hard
        "platform":   "gfg",
    }
    index[key] = {**entry, **{k: v for k, v in cached.items() if v}}
    return index[key]


# ══════════════════════════════════════════════════════════════════════════════
# SCANNERS
# ══════════════════════════════════════════════════════════════════════════════

def scan_leetcode(index: dict) -> list[dict]:
    """
    Scan topics/{topic}/{num}-{slug}/ for organized LC problems.
    Falls back to scanning root {num}-{slug}/ for unorganized LeetSync dumps.
    De-duplicates by problem number — topics/ wins over root.
    """
    problems = {}  # num → problem dict

    # ── Pass 1: topics/ (authoritative, organized) ──
    if LC_TOPICS_DIR.exists():
        for topic_dir in sorted(LC_TOPICS_DIR.iterdir()):
            if not topic_dir.is_dir():
                continue
            topic = topic_dir.name
            for prob_dir in sorted(topic_dir.iterdir(), reverse=True):
                if not prob_dir.is_dir():
                    continue
                m = LC_ROOT_PATTERN.match(prob_dir.name)
                if not m:
                    continue
                num, slug = m.group(1), m.group(2)
                key = f"lc-{num}-{slug}"
                meta = resolve_lc_meta(index, key, slug)
                java = list(prob_dir.glob("*.java"))
                mtime = max(f.stat().st_mtime for f in java) if java else 0
                problems[num] = {
                    "platform":   "leetcode",
                    "num":        num,
                    "slug":       slug,
                    "key":        key,
                    "title":      meta.get("title", slug.replace("-", " ").title()),
                    "difficulty": meta.get("difficulty", "medium"),
                    "topic":      topic,
                    "path":       f"./topics/{topic}/{prob_dir.name}/",
                    "mtime":      mtime,
                    "source":     "topics",
                }

    # ── Pass 2: root {num}-{slug}/ (unorganized LeetSync) — only if not in topics ──
    for item in sorted(REPO_ROOT.iterdir()):
        if not item.is_dir():
            continue
        m = LC_ROOT_PATTERN.match(item.name)
        if not m:
            continue
        num, slug = m.group(1), m.group(2)
        if num in problems:
            continue  # already captured from topics/
        key = f"lc-{num}-{slug}"
        meta = resolve_lc_meta(index, key, slug)
        java = list(item.glob("*.java"))
        mtime = max(f.stat().st_mtime for f in java) if java else 0
        problems[num] = {
            "platform":   "leetcode",
            "num":        num,
            "slug":       slug,
            "key":        key,
            "title":      meta.get("title", slug.replace("-", " ").title()),
            "difficulty": meta.get("difficulty", "medium"),
            "topic":      "uncategorized",
            "path":       f"./{item.name}/",
            "mtime":      mtime,
            "source":     "root",
        }

    return list(problems.values())


def scan_gfg(index: dict) -> list[dict]:
    """
    Scan root for GFG-to-GitHub folder pattern:
      Difficulty: Basic/
      Difficulty: Easy/
      Difficulty: Medium/
      Difficulty: Hard/
    Each contains one subfolder per problem named after the problem.
    """
    problems = []
    seen_keys = set()

    for diff_dir in sorted(REPO_ROOT.iterdir()):
        if not diff_dir.is_dir():
            continue
        m = GFG_DIFFICULTY_RE.match(diff_dir.name)
        if not m:
            continue
        difficulty = m.group(1).lower()  # basic / easy / medium / hard

        for prob_dir in sorted(diff_dir.iterdir(), reverse=True):
            if not prob_dir.is_dir():
                continue
            problem_name = prob_dir.name  # "Find length of Loop"
            slug = slugify(problem_name)
            key  = f"gfg-{difficulty}-{slug}"

            if key in seen_keys:
                continue
            seen_keys.add(key)

            meta = resolve_gfg_meta(index, key, problem_name, difficulty)
            java = list(prob_dir.glob("*.java")) + list(prob_dir.glob("*.cpp"))
            mtime = max(f.stat().st_mtime for f in java) if java else 0

            # Best-guess topic from slug keywords
            topic = infer_topic(slug)

            problems.append({
                "platform":   "gfg",
                "num":        None,
                "slug":       slug,
                "key":        key,
                "title":      meta.get("title", problem_name),
                "difficulty": meta.get("difficulty", difficulty),
                "topic":      topic,
                "path":       f"./{diff_dir.name}/{prob_dir.name}/",
                "mtime":      mtime,
            })

    return problems


TOPIC_KEYWORDS = {
    "linked-list":           ["linked", "list", "node", "cycle", "loop", "lru", "flatten"],
    "trees":                 ["tree", "bst", "binary", "inorder", "preorder", "postorder", "height", "diameter", "lca"],
    "graphs":                ["graph", "bfs", "dfs", "island", "path", "connected", "topological", "cycle"],
    "dynamic-programming":   ["dp", "knapsack", "subsequence", "substring", "partition", "coin", "jump", "climb"],
    "arrays":                ["array", "subarray", "rotate", "sort", "search", "matrix", "spiral", "kadane"],
    "strings":               ["string", "palindrome", "anagram", "prefix", "suffix", "character", "reverse", "pattern"],
    "sliding-window":        ["window", "longest", "maximum subarray"],
    "two-pointers":          ["two pointer", "container", "trap", "three sum"],
    "binary-search":         ["binary search", "sorted", "rotated", "peak", "median"],
    "stack-queue":           ["stack", "queue", "monotonic", "bracket", "parenthes"],
    "backtracking":          ["subset", "permutation", "combination", "backtrack", "n-queen", "sudoku"],
    "heap":                  ["heap", "priority", "kth largest", "kth smallest", "merge k"],
    "greedy":                ["greedy", "activity", "job schedule", "minimum cost"],
    "math":                  ["prime", "gcd", "lcm", "factorial", "fibonacci", "power", "sqrt"],
}

def infer_topic(slug: str) -> str:
    """Guess topic from problem slug keywords."""
    slug_lower = slug.lower()
    for topic, keywords in TOPIC_KEYWORDS.items():
        if any(kw in slug_lower for kw in keywords):
            return topic
    return "uncategorized"


# ══════════════════════════════════════════════════════════════════════════════
# STATS BUILDER
# ══════════════════════════════════════════════════════════════════════════════

def build_stats(lc_problems: list, gfg_problems: list) -> dict:
    stats = {
        "lc":  {"total": 0, "easy": 0, "medium": 0, "hard": 0},
        "gfg": {"total": 0, "basic": 0, "easy": 0, "medium": 0, "hard": 0},
        "total": 0,
        "by_topic": defaultdict(lambda: {
            "total": 0, "easy": 0, "medium": 0, "hard": 0, "basic": 0
        }),
        "recent": [],
    }

    for p in lc_problems:
        d = p["difficulty"]
        stats["lc"]["total"] += 1
        stats["lc"][d]       = stats["lc"].get(d, 0) + 1
        stats["total"]       += 1
        stats["by_topic"][p["topic"]]["total"] += 1
        stats["by_topic"][p["topic"]][d]        = stats["by_topic"][p["topic"]].get(d, 0) + 1
        stats["recent"].append(p)

    for p in gfg_problems:
        d = p["difficulty"]
        stats["gfg"]["total"] += 1
        stats["gfg"][d]       = stats["gfg"].get(d, 0) + 1
        stats["total"]       += 1
        stats["by_topic"][p["topic"]]["total"] += 1
        stats["by_topic"][p["topic"]][d]        = stats["by_topic"][p["topic"]].get(d, 0) + 1
        stats["recent"].append(p)

    stats["recent"].sort(key=lambda x: x["mtime"], reverse=True)
    stats["recent"] = stats["recent"][:10]

    return stats


# ══════════════════════════════════════════════════════════════════════════════
# README SECTION GENERATORS
# ══════════════════════════════════════════════════════════════════════════════

def make_stats_table(stats: dict) -> str:
    lc  = stats["lc"]
    gfg = stats["gfg"]
    lc_total  = lc["total"]
    gfg_total = gfg["total"]
    grand     = stats["total"]

    return f"""\
| Platform | 🔵 Basic | 🟢 Easy | 🟡 Medium | 🔴 Hard | **Total** |
|----------|----------|---------|-----------|---------|-----------|
| ![LC](https://img.shields.io/badge/LeetCode-FFA116?style=flat-square&logo=leetcode&logoColor=white) LeetCode | — | {lc.get('easy',0)} | {lc.get('medium',0)} | {lc.get('hard',0)} | **{lc_total}** |
| ![GFG](https://img.shields.io/badge/GFG-2F8D46?style=flat-square&logo=geeksforgeeks&logoColor=white) GeeksForGeeks | {gfg.get('basic',0)} | {gfg.get('easy',0)} | {gfg.get('medium',0)} | {gfg.get('hard',0)} | **{gfg_total}** |
| **Combined** | — | {lc.get('easy',0)+gfg.get('easy',0)} | {lc.get('medium',0)+gfg.get('medium',0)} | {lc.get('hard',0)+gfg.get('hard',0)} | **{grand}** |"""


def make_platform_summary(stats: dict) -> str:
    lc  = stats["lc"]
    gfg = stats["gfg"]
    total = stats["total"]
    lc_pct  = round(lc["total"]  / total * 100) if total else 0
    gfg_pct = round(gfg["total"] / total * 100) if total else 0
    return f"""\
| | LeetCode | GeeksForGeeks |
|--|----------|---------------|
| Problems | {lc['total']} ({lc_pct}%) | {gfg['total']} ({gfg_pct}%) |
| Easy | {lc.get('easy',0)} | {gfg.get('easy',0)} |
| Medium | {lc.get('medium',0)} | {gfg.get('medium',0)} |
| Hard | {lc.get('hard',0)} | {gfg.get('hard',0)} |
| Basic (GFG) | — | {gfg.get('basic',0)} |

> 📦 **Total across both platforms: {total} problems solved**"""


def make_topics_table(stats: dict) -> str:
    by_topic = stats["by_topic"]
    rows = []

    for topic in TOPIC_ORDER:
        if topic not in by_topic:
            continue
        t    = by_topic[topic]
        name = topic.replace("-", " ").title()
        rows.append(
            f"| {name} | {t['total']} | {t.get('basic',0)} | {t.get('easy',0)} | {t.get('medium',0)} | {t.get('hard',0)} |"
        )

    for topic, t in by_topic.items():
        if topic not in TOPIC_ORDER:
            name = topic.replace("-", " ").title()
            rows.append(
                f"| {name} | {t['total']} | {t.get('basic',0)} | {t.get('easy',0)} | {t.get('medium',0)} | {t.get('hard',0)} |"
            )

    header = (
        "| Topic | Solved | 🔵 Basic | 🟢 Easy | 🟡 Medium | 🔴 Hard |\n"
        "|-------|--------|----------|---------|-----------|---------|"
    )
    return header + "\n" + "\n".join(rows) if rows else header


def make_recent_table(stats: dict) -> str:
    rows = []
    for p in stats["recent"]:
        diff_emoji    = DIFFICULTY_EMOJI.get(p["difficulty"], "🟡 Medium")
        topic_display = p["topic"].replace("-", " ").title()
        platform_tag  = PLATFORM_BADGE.get(p["platform"], "")
        title_cell    = f"{platform_tag} {p['title']}"
        rows.append(
            f"| {p.get('num') or '—'} | {title_cell} | {diff_emoji} | {topic_display} | [View →]({p['path']}) |"
        )

    header = (
        "| # | Problem | Difficulty | Topic | Solution |\n"
        "|---|---------|------------|-------|---------|"
    )
    return header + "\n" + "\n".join(rows) if rows else header + "\n| — | No problems yet | — | — | — |"


# ══════════════════════════════════════════════════════════════════════════════
# README PATCHER
# ══════════════════════════════════════════════════════════════════════════════

def patch_section(content: str, marker: str, new_body: str) -> str:
    pattern     = rf'(<!-- {marker}:START -->)(.*?)(<!-- {marker}:END -->)'
    replacement = rf'\g<1>\n{new_body}\n\g<3>'
    result, n   = re.subn(pattern, replacement, content, flags=re.DOTALL)
    if n == 0:
        print(f"   ⚠️   Marker '{marker}' not found in README.md — section skipped")
    return result


# ══════════════════════════════════════════════════════════════════════════════
# MAIN
# ══════════════════════════════════════════════════════════════════════════════

def main():
    print("=" * 60)
    print("  DSA-Solutions README Updater  —  LeetCode + GFG")
    print("=" * 60)

    index        = load_index()
    index_before = json.dumps(index, sort_keys=True)

    print("\n📂 Scanning LeetCode problems...")
    lc_problems = scan_leetcode(index)
    print(f"   Found {len(lc_problems)} LC problems")

    print("\n📂 Scanning GeeksForGeeks problems...")
    gfg_problems = scan_gfg(index)
    print(f"   Found {len(gfg_problems)} GFG problems")

    # Persist index if anything changed
    if json.dumps(index, sort_keys=True) != index_before:
        save_index(index)

    stats = build_stats(lc_problems, gfg_problems)

    print(f"\n📊 Stats:")
    print(f"   LeetCode  → easy:{stats['lc'].get('easy',0)}  medium:{stats['lc'].get('medium',0)}  hard:{stats['lc'].get('hard',0)}  total:{stats['lc']['total']}")
    print(f"   GFG       → basic:{stats['gfg'].get('basic',0)}  easy:{stats['gfg'].get('easy',0)}  medium:{stats['gfg'].get('medium',0)}  hard:{stats['gfg'].get('hard',0)}  total:{stats['gfg']['total']}")
    print(f"   Combined  → {stats['total']}")

    print("\n📝 Patching README.md...")
    with open(README_PATH) as f:
        content = f.read()

    content = patch_section(content, "STATS",    make_stats_table(stats))
    content = patch_section(content, "PLATFORM", make_platform_summary(stats))
    content = patch_section(content, "TOPICS",   make_topics_table(stats))
    content = patch_section(content, "RECENT",   make_recent_table(stats))

    with open(README_PATH, "w") as f:
        f.write(content)

    print(f"✅ Done — {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("=" * 60)


if __name__ == "__main__":
    main()
