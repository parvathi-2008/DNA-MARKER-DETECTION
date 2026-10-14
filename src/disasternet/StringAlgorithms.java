
package disasternet;

import java.util.*;

public class StringAlgorithms {

    // KMP Algorithm
    public static boolean kmp(String text, String pattern) {
        if (pattern.isEmpty()) return true;

        int n = text.length(), m = pattern.length();
        int[] lps = new int[m];

        for (int i = 1, len = 0; i < m;) {
            if (pattern.charAt(i) == pattern.charAt(len))
                lps[i++] = ++len;
            else if (len > 0)
                len = lps[len - 1];
            else
                lps[i++] = 0;
        }

        int i = 0, j = 0;
        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            if (j == m) return true;
            if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j > 0) j = lps[j - 1];
                else i++;
            }
        }
        return false;
    }

    // Z-Function
    public static int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);

            while (i + z[i] < n &&
                   s.charAt(z[i]) == s.charAt(i + z[i]))
                z[i]++;

            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    // Rabin-Karp Algorithm
    public static boolean rabinKarp(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        if (m == 0) return true;
        if (m > n) return false;

        long base = 256, mod = 1000000007L;
        long patternHash = 0, textHash = 0, power = 1;

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * base + pattern.charAt(i)) % mod;
            textHash = (textHash * base + text.charAt(i)) % mod;
            if (i < m - 1) power = (power * base) % mod;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash &&
                text.regionMatches(i, pattern, 0, m))
                return true;

            if (i < n - m) {
                textHash = (textHash
                    - text.charAt(i) * power % mod + mod) % mod;
                textHash = (textHash * base + text.charAt(i + m)) % mod;
            }
        }
        return false;
    }

    // Aho-Corasick: simple multi-keyword search
    public static List<String> ahoCorasick(
            String text, String[] keywords) {
        List<String> found = new ArrayList<>();

        for (String word : keywords) {
            if (!word.isEmpty() &&
                text.toLowerCase().contains(word.toLowerCase())) {
                found.add(word);
            }
        }
        return found;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== DisasterNet: M1 String Algorithms ===");
        System.out.print("Enter emergency report: ");
        String text = sc.nextLine();

        System.out.print("Enter keyword: ");
        String pattern = sc.nextLine();

        System.out.println("KMP Match: " + kmp(
                text.toLowerCase(), pattern.toLowerCase()));
        System.out.println("Rabin-Karp Match: " + rabinKarp(
                text.toLowerCase(), pattern.toLowerCase()));

        System.out.println("Z-Function: " +
                Arrays.toString(zFunction(pattern + "$" + text)));

        String[] keywords = {
            "flood", "fire", "medical", "rescue"
        };

        System.out.println("Emergency keywords found: " +
                ahoCorasick(text, keywords));
    }
}
