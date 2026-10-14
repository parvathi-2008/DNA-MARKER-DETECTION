
package disasternet;

import java.util.*;

public class ConstraintAlgorithms {

    // M5.1: Check whether a Boolean assignment satisfies 3-SAT
    // Each row represents one clause of three literals.
    // Positive k means variable k; negative k means NOT variable k.
    static boolean check3SAT(int[][] clauses, boolean[] values) {
        for (int[] clause : clauses) {
            boolean satisfied = false;

            for (int literal : clause) {
                int variable = Math.abs(literal) - 1;
                boolean value = values[variable];

                if (literal < 0) value = !value;

                if (value) {
                    satisfied = true;
                    break;
                }
            }

            if (!satisfied) return false;
        }

        return true;
    }

    // M5.2: Find a clique of size k using backtracking
    static boolean hasClique(boolean[][] graph, int k) {
        return findClique(graph, k, 0, new ArrayList<>());
    }

    static boolean findClique(boolean[][] graph, int k, int start,
                              List<Integer> selected) {
        if (selected.size() == k) return true;

        for (int v = start; v < graph.length; v++) {
            boolean valid = true;

            for (int u : selected) {
                if (!graph[u][v]) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                selected.add(v);

                if (findClique(graph, k, v + 1, selected))
                    return true;

                selected.remove(selected.size() - 1);
            }
        }

        return false;
    }

    // M5.3: Vertex Cover 2-approximation
    // Select both endpoints of each uncovered edge.
    static List<Integer> vertexCover(boolean[][] graph) {
        int n = graph.length;
        boolean[][] covered = new boolean[n][n];
        List<Integer> cover = new ArrayList<>();

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (graph[u][v] && !covered[u][v]) {
                    cover.add(u);
                    cover.add(v);

                    for (int i = 0; i < n; i++) {
                        covered[u][i] = true;
                        covered[i][u] = true;
                        covered[v][i] = true;
                        covered[i][v] = true;
                    }
                }
            }
        }

        return cover;
    }

    public static void main(String[] args) {
        System.out.println(
                "=== DisasterNet: M5 Constraint Algorithms ===");

        // Variables: X1=true, X2=false, X3=true
        boolean[] values = {true, false, true};

        int[][] clauses = {
            {1, 2, 3},
            {-1, 2, 3},
            {1, -2, 3}
        };

        System.out.println("3-SAT satisfied: "
                + check3SAT(clauses, values));

        // Emergency activity conflict graph
        boolean[][] graph = {
            {false, true, true, false},
            {true, false, true, true},
            {true, true, false, true},
            {false, true, true, false}
        };

        System.out.println("Clique of size 3 exists: "
                + hasClique(graph, 3));

        System.out.println("Vertex Cover (0-based): "
                + vertexCover(graph));
    }
}
