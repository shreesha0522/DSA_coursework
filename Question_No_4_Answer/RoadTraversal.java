package Question_No_4_Answer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoadTraversal {
    /**
     * The main method where the program starts. It takes two inputs: 
     * the locations of the packages and the roads connecting them.
     * The function calculates the minimum number of roads to be traversed to collect all packages.
     */
    public static void main(String[] args) {
        // Test Case 1: Locations where the packages are and the roads connecting them
        int[] packageLocations1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};

        // Test Case 2: Another set of package locations and roads
        int[] packageLocations2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};

        // Calculate and print the minimum number of roads to traverse for the first input
        int minRoads1 = calculateMinimumRoads(packageLocations1, roads1);
        System.out.println("Minimum roads to traverse for Input 1: " + minRoads1);

        // Calculate and print the minimum number of roads to traverse for the second input
        int minRoads2 = calculateMinimumRoads(packageLocations2, roads2);
        System.out.println("Minimum roads to traverse for Input 2: " + minRoads2);
    }

    /**
     * This method calculates the minimum number of roads needed to collect all packages.
     * It tries starting from every possible location and considers the number of roads traveled 
     * both for collecting the packages and for the return journey.
     *
     * @param packages Array representing whether a package is at a location (1 for present, 0 for absent).
     * @param roads Array of pairs where each pair represents a road connecting two locations.
     * @return The minimum number of roads needed to collect all the packages.
     */
    public static int calculateMinimumRoads(int[] packages, int[][] roads) {
        int locations = packages.length; // Total number of locations
        List<List<Integer>> adjacencyList = createGraph(locations, roads); // Build the graph from the roads

        int minimumRoads = Integer.MAX_VALUE; // Initialize the minimum roads with a very large number

        // Try starting from every possible location
        for (int startLocation = 0; startLocation < locations; startLocation++) {
            boolean[] visitedLocations = new boolean[locations]; // Tracks which locations we've already visited
            int roadsTraveled = 0; // Keeps count of the roads traveled

            // Traverse the roads to collect all the packages (limit to a distance of 2 roads)
            roadsTraveled += bfsToCollectPackages(startLocation, adjacencyList, packages, visitedLocations);

            // Backtrack to the starting location
            roadsTraveled += returnToStart(startLocation, adjacencyList, visitedLocations);

            // Update the minimum roads if fewer roads were traveled
            minimumRoads = Math.min(minimumRoads, roadsTraveled);
        }

        return minimumRoads; // Return the least number of roads required
    }

    /**
     * Builds a graph from the provided roads, where each node contains a list of its connected nodes.
     *
     * @param locations The total number of locations.
     * @param roads The roads connecting the locations.
     * @return A list where each index corresponds to a location and holds its neighbors.
     */
    private static List<List<Integer>> createGraph(int locations, int[][] roads) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < locations; i++) {
            graph.add(new ArrayList<>()); // Initialize an empty list for each location
        }

        // Add the roads to the adjacency list for both connected locations
        for (int[] road : roads) {
            int from = road[0];
            int to = road[1];
            graph.get(from).add(to);
            graph.get(to).add(from); // Since roads are bidirectional
        }

        return graph;
    }

    /**
     * Uses breadth-first search (BFS) to collect all packages within a distance of 2 roads from the starting location.
     * It also keeps track of the roads that have been traveled during the process.
     *
     * @param start The starting location.
     * @param graph The graph representing the roads between locations.
     * @param packages The array indicating which locations have packages.
     * @param visited Tracks the locations that have already been visited during the search.
     * @return The total number of roads traversed while collecting the packages.
     */
    private static int bfsToCollectPackages(int start, List<List<Integer>> graph, int[] packages, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start); // Start from the given location
        visited[start] = true; // Mark the start location as visited

        int roadsTraveled = 0;

        // Perform BFS up to a depth of 2 roads
        for (int depth = 0; depth < 2; depth++) {
            int currentLevelSize = queue.size(); // Get the number of locations at the current depth
            for (int i = 0; i < currentLevelSize; i++) {
                int currentLocation = queue.poll(); // Dequeue a location

                // If there's a package at this location, mark it as collected
                if (packages[currentLocation] == 1) {
                    packages[currentLocation] = 0; // Package is now collected
                }

                // Explore all neighbors (connected locations)
                for (int neighbor : graph.get(currentLocation)) {
                    if (!visited[neighbor]) { // If this location hasn't been visited yet
                        visited[neighbor] = true; // Mark it as visited
                        queue.add(neighbor); // Add it to the queue
                        roadsTraveled++; // Count this road as traveled
                    }
                }
            }
        }

        return roadsTraveled;
    }

    /**
     * Simulates the process of backtracking to the original start location, counting the roads traversed.
     *
     * @param start The starting location.
     * @param graph The graph representing the roads.
     * @param visited Locations that have been visited during backtracking.
     * @return The number of roads traveled during the backtracking process.
     */
    private static int returnToStart(int start, List<List<Integer>> graph, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start); // Start the backtrack from the original location
        visited[start] = true; // Mark it as visited

        int roadsTraveled = 0;

        // Backtrack until we reach the starting location again
        while (!queue.isEmpty()) {
            int currentLocation = queue.poll(); // Dequeue a location

            // If we reach the start location, we stop backtracking
            if (currentLocation == start) {
                break;
            }

            // Explore all neighbors of the current location
            for (int neighbor : graph.get(currentLocation)) {
                if (!visited[neighbor]) { // If a neighbor hasn't been visited yet
                    visited[neighbor] = true;
                    queue.add(neighbor); // Add it to the queue
                    roadsTraveled++; // Count the road as traveled
                }
            }
        }

        return roadsTraveled;
    }
}
