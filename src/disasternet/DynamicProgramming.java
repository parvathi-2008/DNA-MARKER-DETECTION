
package disasternet;

import java.util.Scanner;

public class DynamicProgramming {

    // M3.1: Levenshtein Edit Distance
    public static int editDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1)
                        ? 0 : 1;

                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1,
                             dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[m][n];
    }

    // M3.2: Weighted Edit Distance
    public static int weightedEditDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i * 2;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1)
                        ? 0 : 3;

                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 2,
                             dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[m][n];
    }

    // M3.3: Bitmask DP for resource selection
    // Maximize resource value within a capacity.
    public static int resourceSelection(int[] weights,
                                        int[] values,
                                        int capacity) {
        int n = weights.length;
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; i++) {
            for (int c = capacity; c >= weights[i]; c--) {
                dp[c] = Math.max(dp[c],
                        dp[c - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }

    // M3.4: Matrix Chain Multiplication
    public static int matrixChain(int[] p) {
        int n = p.length - 1;
        int[][] dp = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j]
                            + p[i] * p[k + 1] * p[j + 1];

                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== DisasterNet: M3 Dynamic Programming ===");

        System.out.print("Enter reported word: ");
        String a = sc.nextLine();

        System.out.print("Enter intended word: ");
        String b = sc.nextLine();

        System.out.println("Edit Distance: "
                + editDistance(a, b));

        System.out.println("Weighted Edit Distance: "
                + weightedEditDistance(a, b));

        int[] weights = {2, 3, 4};
        int[] values = {4, 5, 7};
        int capacity = 5;

        System.out.println("Maximum resource value: "
                + resourceSelection(weights, values, capacity));

        int[] dimensions = {10, 20, 30, 40};

        System.out.println("Minimum matrix multiplications: "
                + matrixChain(dimensions));
    }
}
