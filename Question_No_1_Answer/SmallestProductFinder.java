package Question_No_1_Answer;

import java.util.Arrays;

public class SmallestProductFinder {

    /**
     * Finds the kth smallest product of two sorted arrays.
     *
     * @param arr1 The first sorted array.
     * @param arr2 The second sorted array.
     * @param k The position of the smallest product to find.
     * @return The kth smallest product.
     */
    public static long findKthSmallestProduct(int[] arr1, int[] arr2, long k) {
        long left = Math.min(
            Math.min((long) arr1[0] * arr2[0], (long) arr1[0] * arr2[arr2.length - 1]),
            Math.min((long) arr1[arr1.length - 1] * arr2[0], (long) arr1[arr1.length - 1] * arr2[arr2.length - 1])
        );
        long right = Math.max(
            Math.max((long) arr1[0] * arr2[0], (long) arr1[0] * arr2[arr2.length - 1]),
            Math.max((long) arr1[arr1.length - 1] * arr2[0], (long) arr1[arr1.length - 1] * arr2[arr2.length - 1])
        );
        
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (countProducts(arr1, arr2, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Counts how many products are less than or equal to a given value.
     *
     * @param arr1 The first sorted array.
     * @param arr2 The second sorted array.
     * @param target The value to compare against.
     * @return The count of products <= target.
     */
    private static long countProducts(int[] arr1, int[] arr2, long target) {
        long count = 0;
        int n = arr2.length;
        for (int num : arr1) {
            if (num == 0) {
                if (target >= 0) count += n;
            } else if (num > 0) {
                count += findUpperBound(arr2, (double) target / num);
            } else {
                count += n - findUpperBound(arr2, (double) target / num);
            }
        }
        return count;
    }

    /**
     * Finds the index where an element would be inserted in a sorted array.
     *
     * @param arr The sorted array.
     * @param target The target value.
     * @return The upper bound index.
     */
    private static int findUpperBound(int[] arr, double target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        runTest(new int[]{2, 5}, new int[]{3, 4}, 2, 8);
        runTest(new int[]{-4, -2, 0, 3}, new int[]{2, 4}, 6, 0);
    }

    /**
     * Runs a test case for the function.
     *
     * @param arr1 The first sorted array.
     * @param arr2 The second sorted array.
     * @param k The target index.
     * @param expected The expected result.
     */
    private static void runTest(int[] arr1, int[] arr2, long k, long expected) {
        long result = findKthSmallestProduct(arr1, arr2, k);
        System.out.printf("Arrays: %s, %s | k=%d\n", Arrays.toString(arr1), Arrays.toString(arr2), k);
        System.out.printf("Expected: %d, Got: %d\n", expected, result);
        System.out.println(result == expected ? "Test PASSED" : "Test FAILED");
        System.out.println();
    }
}
