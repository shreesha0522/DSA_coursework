package Question_No_2_Answer;

import java.util.Arrays;

public class NearestPointsFinder {
    /**
     * Finds the closest pair of points based on Manhattan distance.
     *
     * @param xVals Array of x-coordinates.
     * @param yVals Array of y-coordinates.
     * @return Indices of the closest pair.
     */
    public static int[] getClosestPair(int[] xVals, int[] yVals) {
        int n = xVals.length;
        int[] closestPair = {0, 1};
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int distance = Math.abs(xVals[i] - xVals[j]) + Math.abs(yVals[i] - yVals[j]);
                if (distance < minDist || (distance == minDist && (i < closestPair[0] || (i == closestPair[0] && j < closestPair[1])))) {
                    minDist = distance;
                    closestPair[0] = i;
                    closestPair[1] = j;
                }
            }
        }
        return closestPair;
    }

    public static void main(String[] args) {
        runTest(new int[]{1, 2, 3, 2, 4}, new int[]{2, 3, 1, 2, 3}, new int[]{0, 3});
        runTest(new int[]{1, 1, 1}, new int[]{1, 1, 1}, new int[]{0, 1});
        runTest(new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{0, 1});
    }

    /**
     * Runs a test case for getClosestPair function.
     *
     * @param xVals X-coordinates.
     * @param yVals Y-coordinates.
     * @param expected Expected result.
     */
    private static void runTest(int[] xVals, int[] yVals, int[] expected) {
        int[] result = getClosestPair(xVals, yVals);
        System.out.printf("X: %s, Y: %s\n", Arrays.toString(xVals), Arrays.toString(yVals));
        System.out.printf("Expected: %s, Got: %s\n", Arrays.toString(expected), Arrays.toString(result));
        System.out.println(Arrays.equals(result, expected) ? "Test PASSED" : "Test FAILED");
        System.out.println();
    }
}
