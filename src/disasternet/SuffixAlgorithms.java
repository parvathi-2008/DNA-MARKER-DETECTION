
package disasternet;

import java.util.Arrays;
import java.util.Scanner;

public class SuffixAlgorithms {

    // M2.1: Suffix Array
    public static int[] suffixArray(String s) {
        int n = s.length();
        Integer[] sa = new Integer[n];

        for (int i = 0; i < n; i++)
            sa[i] = i;

        Arrays.sort(sa, (a, b) ->
            s.substring(a).compareTo(s.substring(b)));

        int[] result = new int[n];
        for (int i = 0; i < n; i++)
            result[i] = sa[i];

        return result;
    }

    // M2.2: LCP Array using Kasai Algorithm
    public static int[] lcpArray(String s, int[] sa) {
        int n = s.length();
        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++)
            rank[sa[i]] = i;

        int common = 0;

        for (int i = 0; i < n; i++) {
            if (rank[i] == 0) continue;

            int j = sa[rank[i] - 1];

            while (i + common < n &&
                   j + common < n &&
                   s.charAt(i + common) ==
                   s.charAt(j + common)) {
                common++;
            }

            lcp[rank[i]] = common;

            if (common > 0) common--;
        }

        return lcp;
    }

    // M2.3: Search a pattern using Suffix Array
    public static boolean search(String text, int[] sa,
                                 String pattern) {
        int left = 0, right = sa.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int start = sa[mid];
            String suffix = text.substring(start);

            int cmp = suffix.compareTo(pattern);

            if (suffix.startsWith(pattern))
                return true;
            else if (cmp < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return false;
    }

    // M2.4: Demonstration of repeated descriptions
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== DisasterNet: M2 Suffix Structures ===");
        System.out.print("Enter emergency report: ");
        String text = sc.nextLine();

        int[] sa = suffixArray(text);
        int[] lcp = lcpArray(text, sa);

        System.out.println("Suffix Array: " +
                           Arrays.toString(sa));
        System.out.println("LCP Array: " +
                           Arrays.toString(lcp));

        System.out.print("Enter keyword to search: ");
        String pattern = sc.nextLine();

        System.out.println("Keyword found: " +
                           search(text, sa, pattern));
    }
}
