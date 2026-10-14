
package disasternet;

import java.util.*;

public class NetworkFlow {

    // M4.1: Edmonds-Karp (Ford-Fulkerson using BFS)
    static int edmondsKarp(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residual = new int[n][n];

        for (int i = 0; i < n; i++)
            residual[i] = capacity[i].clone();

        int maxFlow = 0;
        int[] parent = new int[n];

        while (bfs(residual, source, sink, parent)) {
            int flow = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v])
                flow = Math.min(flow, residual[parent[v]][v]);

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residual[u][v] -= flow;
                residual[v][u] += flow;
            }

            maxFlow += flow;
        }

        return maxFlow;
    }

    static boolean bfs(int[][] residual, int source,
                       int sink, int[] parent) {
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        parent[source] = source;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < residual.length; v++) {
                if (parent[v] == -1 && residual[u][v] > 0) {
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        return parent[sink] != -1;
    }

    // M4.2: Bipartite Matching
    static boolean matchDfs(int team, boolean[][] graph,
                            boolean[] visited, int[] assigned) {
        for (int incident = 0; incident < graph[team].length;
             incident++) {
            if (graph[team][incident] && !visited[incident]) {
                visited[incident] = true;

                if (assigned[incident] == -1 ||
                    matchDfs(assigned[incident], graph,
                             visited, assigned)) {
                    assigned[incident] = team;
                    return true;
                }
            }
        }
        return false;
    }

    static int maximumMatching(boolean[][] graph) {
        int teams = graph.length;
        int incidents = graph[0].length;
        int[] assigned = new int[incidents];

        Arrays.fill(assigned, -1);
        int count = 0;

        for (int team = 0; team < teams; team++) {
            if (matchDfs(team, graph,
                         new boolean[incidents], assigned)) {
                count++;
            }
        }

        System.out.println("Team assignments:");
        for (int i = 0; i < incidents; i++) {
            if (assigned[i] != -1) {
                System.out.println("Incident " + (i + 1)
                        + " <- Team " + (assigned[i] + 1));
            }
        }

        return count;
    }

    public static void main(String[] args) {

        System.out.println("=== DisasterNet: M4 Network Flow ===");

        // Supply network: source -> junctions -> destination
        int[][] capacity = {
            {0, 10, 5, 0},
            {0, 0, 0, 10},
            {0, 0, 0, 5},
            {0, 0, 0, 0}
        };

        System.out.println("Maximum supply flow: "
                + edmondsKarp(capacity, 0, 3));

        // Team-to-incident compatibility
        boolean[][] graph = {
            {true, true},
            {true, false},
            {false, true}
        };

        System.out.println("Maximum team assignments: "
                + maximumMatching(graph));
    }
}
