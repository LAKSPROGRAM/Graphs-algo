import java.util.*;

public class Solution {
    static class Edge {
        int from, to, weight;
        Edge(int from, int to, int w) {
            this.from = from;
            this.to = to;
            this.weight = w;
        }
    }

    public static List<Edge> calculatePrimsMST(List<List<Integer>> edges, int n, int m) {
        // Build adjacency list
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int u = edges.get(i).get(0);
            int v = edges.get(i).get(1);
            int w = edges.get(i).get(2);
            adjList.putIfAbsent(u, new ArrayList<>());
            adjList.putIfAbsent(v, new ArrayList<>());
            adjList.get(u).add(new int[] {v, w});
            adjList.get(v).add(new int[] {u, w});
        }

        int[] key = new int[n];
        boolean[] mst = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        key = 0;

        for (int count = 0; count < n; count++) {
            int u = -1, min = Integer.MAX_VALUE;
            for (int v = 0; v < n; v++) {
                if (!mst[v] && key[v] < min) {
                    min = key[v];
                    u = v;
                }
            }
            if (u == -1) break;
            mst[u] = true;

            if (adjList.containsKey(u)) {
                for (int[] neighbor : adjList.get(u)) {
                    int v = neighbor;
                    int weight = neighbor;
                    if (!mst[v] && weight < key[v]) {
                        key[v] = weight;
                        parent[v] = u;
                    }
                }
            }
        }

        List<Edge> answer = new ArrayList<>();
        for (int v = 1; v < n; v++) {
            if (parent[v] != -1) {
                answer.add(new Edge(parent[v], v, key[v]));
            }
        }
        return answer;
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
        List<Edge> mst = calculatePrimsMST(edges, n, m);
        for (Edge e : mst) {
            System.out.println(e.from + "-" + e.to + " : " + e.weight);
        }
    }
}
