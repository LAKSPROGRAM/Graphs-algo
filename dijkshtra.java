import java.util.*;

public class DijkstraSetAdjList {

    static class Pair {
        int node, weight;
        Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public static int[] shortestPath(List<List<Integer>> edges, int n, int m, int src) {
        // Create adjacency list
        Map<Integer, List<Pair>> adjList = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int u = edges.get(i).get(0);
            int v = edges.get(i).get(1);
            int w = edges.get(i).get(2);
            adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(new Pair(v, w));
            adjList.computeIfAbsent(v, k -> new ArrayList<>()).add(new Pair(u, w));
        }

        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[src] = 0;

        // Using TreeSet to emulate set<pair<int,int>> in C++
        TreeSet<int[]> set = new TreeSet<>(
                (a, b) -> (a[0] == b[0]) ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])
        );
        set.add(new int[]{0, src});

        while (!set.isEmpty()) {
            int[] curr = set.pollFirst();
            int nodeDistance = curr[0];
            int topNode = curr[1];

            if (!adjList.containsKey(topNode)) continue;
            for (Pair neigh : adjList.get(topNode)) {
                if (nodeDistance + neigh.weight < distance[neigh.node]) {
                    // Remove old record if present
                    set.remove(new int[]{distance[neigh.node], neigh.node});
                    // Update distance
                    distance[neigh.node] = nodeDistance + neigh.weight;
                    // Insert new pair
                    set.add(new int[]{distance[neigh.node], neigh.node});
                }
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<List<Integer>> edges = new ArrayList<>();
        System.out.print("Enter the number of nodes : ");
        int n = sc.nextInt();

        System.out.print("Enter the number of edges : ");
        int m = sc.nextInt();
        System.out.println("Enter the edges : ");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            edges.add(Arrays.asList(u, v, w));
        }
        System.out.print("Enter the source : ");
        int src = sc.nextInt();

        int[] answer = shortestPath(edges, n, m, src);
        System.out.print("The shortest path : ");
        for (int x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
