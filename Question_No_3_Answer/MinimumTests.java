package Question_No_3_Answer;



public class MinimumTests {
    /**
     * Finds the minimum number of tests needed to determine the critical temperature.
     *
     * @param samples Number of available samples.
     * @param levels Number of temperature levels.
     * @return Minimum number of tests required.
     */
    public static int findMinimumTests(int samples, int levels) {
        int[][] dp = new int[samples + 1][levels + 1];
        int tests = 0;

        while (dp[samples][tests] < levels) {
            tests++;
            for (int i = 1; i <= samples; i++) {
                dp[i][tests] = dp[i - 1][tests - 1] + dp[i][tests - 1] + 1;
            }
        }
        return tests;
    }

    public static void main(String[] args) {
        runTest(1, 2, 2);
        runTest(2, 6, 3);
        runTest(3, 14, 4);
        runTest(4, 20, 5);
        runTest(2, 10, 4);
        runTest(3, 25, 5);
    }

    /**
     * Runs a test case to check if the function works correctly.
     *
     * @param samples Number of samples.
     * @param levels Number of temperature levels.
     * @param expected Expected number of tests.
     */
    private static void runTest(int samples, int levels, int expected) {
        int result = findMinimumTests(samples, levels);
        System.out.printf("Samples: %d, Levels: %d\n", samples, levels);
        System.out.printf("Expected: %d, Got: %d\n", expected, result);
        System.out.println(result == expected ? "PASSED" : "FAILED");
        System.out.println();
    }
}
