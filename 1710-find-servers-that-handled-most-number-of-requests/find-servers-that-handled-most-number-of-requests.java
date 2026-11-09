import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> availableServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            availableServers.add(i); 
        }
        PriorityQueue<int[]> busyServers = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0])
                                   : Integer.compare(a[1], b[1])
        );

        int[] countRequests = new int[k];
        for (int i = 0; i < arrival.length; i++) {
            int time = arrival[i];

            while (!busyServers.isEmpty() && busyServers.peek()[0] <= time) {
                int server = busyServers.poll()[1];
                availableServers.add(server);
            }

            if (availableServers.isEmpty()) {
                continue; 
            }

            int target = i % k;
            Integer targetServer = availableServers.ceiling(target);

            if (targetServer == null) { 
                targetServer = availableServers.first();
            }

            availableServers.remove(targetServer); 

            busyServers.offer(new int[]{time + load[i], targetServer});

            countRequests[targetServer]++;
        }

        int maxNumberOfRequests = 0;
        for (int c : countRequests) {
            maxNumberOfRequests = Math.max(maxNumberOfRequests, c); 
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (countRequests[i] == maxNumberOfRequests) {
                result.add(i);
            }
        }

        return result;
    }
}