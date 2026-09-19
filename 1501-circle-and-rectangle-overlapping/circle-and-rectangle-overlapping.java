class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        int xi = Math.max(x1, Math.min(x2, xCenter));
        int yi = Math.max(y1, Math.min(y2, yCenter));
        long dx = xi - xCenter;
        long dy = yi - yCenter;
        return dx * dx + dy * dy <= (long) radius * radius;
    }
}