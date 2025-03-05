package Question_No_2_Answer;

import java.util.Arrays;

public class RewardsDistribution {
    /**
     * Calculates the minimum rewards needed for employees based on performance.
     *
     * @param performance Employee performance ratings.
     * @return Minimum rewards required.
     */
    public static int calculateRewards(int[] performance) {
        int n = performance.length;
        int[] rewards = new int[n];
        Arrays.fill(rewards, 1);

        // Left to right: Ensure higher-rated employees get more rewards than previous ones.
        for (int i = 1; i < n; i++) {
            if (performance[i] > performance[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Right to left: Ensure fairness for employees with higher ratings than the next one.
        for (int i = n - 2; i >= 0; i--) {
            if (performance[i] > performance[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        return Arrays.stream(rewards).sum();
    }

    public static void main(String[] args) {
        runTest(new int[]{1, 0, 2}, 5);
        runTest(new int[]{1, 2, 2}, 4);
        runTest(new int[]{1, 2, 3, 4, 5}, 15);
    }

    /**
     * Runs a test case to verify the reward calculation.
     *
     * @param performance Employee performance ratings.
     * @param expected Expected minimum rewards.
     */
    private static void runTest(int[] performance, int expected) {
        int result = calculateRewards(performance);
        System.out.printf("Performance: %s\n", Arrays.toString(performance));
        System.out.printf("Expected: %d, Got: %d\n", expected, result);
        System.out.println(result == expected ? "Test PASSED" : "Test FAILED");
        System.out.println();
    }
}
