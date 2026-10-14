
package disasternet;

import java.util.*;
import java.util.concurrent.*;

public class AdvancedAlgorithms {

    static Random random = new Random();

    // M6.1: Randomized QuickSort
    static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int p = partition(a, low, high);
            quickSort(a, low, p - 1);
            quickSort(a, p + 1, high);
        }
    }

    static int partition(int[] a, int low, int high) {
        int pivotIndex = low + random.nextInt(high - low + 1);

        int temp = a[pivotIndex];
        a[pivotIndex] = a[high];
        a[high] = temp;

        int pivot = a[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                i++;
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        return i + 1;
    }

    // M6.2: Reservoir Sampling
    static List<Integer> reservoirSampling(
            List<Integer> observations, int k) {

        List<Integer> reservoir = new ArrayList<>();
        if (k <= 0) return reservoir;

        for (int i = 0; i < observations.size(); i++) {
            if (i < k) {
                reservoir.add(observations.get(i));
            } else {
                int j = random.nextInt(i + 1);
                if (j < k) {
                    reservoir.set(j, observations.get(i));
                }
            }
        }

        return reservoir;
    }

    // M6.3: Miller-Rabin primality test
    static boolean isPrime(long n) {
        if (n < 2) return false;

        for (int p : new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            if (n % p == 0) return n == p;
        }

        long d = n - 1;
        int s = 0;

        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        for (int a : new int[]{2, 3, 5, 7, 11}) {
            if (a >= n) continue;

            long x = modPow(a, d, n);

            if (x == 1 || x == n - 1) continue;

            boolean passed = false;

            for (int r = 1; r < s; r++) {
                x = x * x % n;

                if (x == n - 1) {
                    passed = true;
                    break;
                }
            }

            if (!passed) return false;
        }

        return true;
    }

    static long modPow(long a, long b, long mod) {
        long result = 1;
        a %= mod;

        while (b > 0) {
            if ((b & 1) == 1) result = result * a % mod;
            a = a * a % mod;
            b >>= 1;
        }

        return result;
    }

    // M6.4: Parallel Reduce
    static long parallelReduce(int[] observations) {
        return Arrays.stream(observations)
                     .parallel()
                     .asLongStream()
                     .sum();
    }

    // M6.5: Prefix Scan (sequential demonstration)
    static int[] prefixScan(int[] values) {
        int[] result = new int[values.length];
        int sum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];
            result[i] = sum;
        }

        return result;
    }

    public static void main(String[] args) {

        System.out.println(
                "=== DisasterNet: M6 Advanced Algorithms ===");

        // Request priority scores
        int[] priorities = {45, 10, 80, 25, 60};
        quickSort(priorities, 0, priorities.length - 1);

        System.out.println("Sorted priorities: "
                + Arrays.toString(priorities));

        // Sample continuously arriving field observations
        List<Integer> observations =
                Arrays.asList(12, 25, 30, 18, 45, 50);

        System.out.println("Sampled observations: "
                + reservoirSampling(observations, 3));

        System.out.println("Is 101 prime? " + isPrime(101));

        int[] data = {10, 20, 30, 40, 50};

        System.out.println("Parallel total: "
                + parallelReduce(data));

        System.out.println("Prefix scan: "
                + Arrays.toString(prefixScan(data)));

        System.out.println("M6 completed!");
    }
}
