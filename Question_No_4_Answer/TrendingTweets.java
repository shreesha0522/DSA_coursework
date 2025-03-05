package Question_No_4_Answer;

import java.text.*;
import java.util.*;

public class TrendingTweets {
    /**
     * The main entry point of the program. This method processes a list of tweets, filters out the ones 
     * that are not from February 2024, counts the occurrences of hashtags, sorts them, and displays 
     * the top 3 trending hashtags.
     */
    public static void main(String[] args) {
        // Sample data: user_id, tweet_id, tweet_content, tweet_date
        List<Map<String, String>> tweets = new ArrayList<>();

        // Add sample tweets to the list using the helper function createTweet
        tweets.add(createTweet("201", "21", "Loving the vibes today! #GoodVibes #ChillMode", "2024-02-02"));
        tweets.add(createTweet("202", "22", "Work hustle never stops! #Grind #Hustle", "2024-02-03"));
        tweets.add(createTweet("203", "23", "Exploring new AI trends! #AI #TechWorld", "2024-02-04"));
        tweets.add(createTweet("204", "24", "Sunny days ahead! #GoodVibes #Sunshine", "2025-02-05"));
        tweets.add(createTweet("205", "25", "AI revolution is here! #AI #FutureTech", "2024-02-06"));
        tweets.add(createTweet("205", "25", "AI revolution is here! #AI #FutureTech", "2025-03-07"));
        tweets.add(createTweet("206", "26", "Success comes with persistence! #Hustle #Motivation", "2024-02-07"));
        tweets.add(createTweet("207", "27", "Nature therapy always works. #Peaceful #NatureLover", "2024-02-08"));

        // Define the date format (YYYY-MM-DD) to check the tweet dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;

        try {
            // Parse the start and end dates for February 2024
            startDate = dateFormat.parse("2024-02-01");
            endDate = dateFormat.parse("2024-02-29");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Map to store hashtag frequencies (hashtag -> count)
        Map<String, Integer> hashtagCount = new HashMap<>();

        // Iterate through each tweet in the list
        for (Map<String, String> tweet : tweets) {
            // Get the date of the tweet and check if it's within February 2024
            String tweetDateStr = tweet.get("tweet_date");

            try {
                Date tweetDate = dateFormat.parse(tweetDateStr);
                
                // Process only tweets from February 2024
                if (!tweetDate.before(startDate) && !tweetDate.after(endDate)) {
                    // Get the content of the tweet
                    String tweetContent = tweet.get("tweet");

                    // Split the tweet content into individual words
                    String[] words = tweetContent.split(" ");
                    
                    // Loop through each word and check if it's a hashtag (starts with #)
                    for (String word : words) {
                        if (word.startsWith("#")) {
                            // Convert hashtag to lowercase to make the counting case-insensitive
                            String hashtag = word.toLowerCase();
                            
                            // Increment the frequency count of the hashtag in the map
                            hashtagCount.put(hashtag, hashtagCount.getOrDefault(hashtag, 0) + 1);
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Convert the hashtag count map into a list of entries for sorting
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCount.entrySet());
        
        // Sort the hashtags first by frequency in descending order, then alphabetically
        sortedHashtags.sort((a, b) -> {
            // Compare frequencies in descending order
            int frequencyComparison = b.getValue().compareTo(a.getValue());
            if (frequencyComparison != 0) return frequencyComparison;
            
            // If frequencies are the same, compare alphabetically (ascending order)
            return a.getKey().compareTo(b.getKey());
        });

        // Print the header of the table
        System.out.println("+-------------+---------+");
        System.out.println("|   HASHTAG   |  COUNT  |");
        System.out.println("+-------------+---------+");
        
        // Display the top 3 hashtags (or fewer if there are not enough hashtags)
        for (int i = 0; i < Math.min(3, sortedHashtags.size()); i++) {
            // Get the current entry (hashtag and its frequency)
            Map.Entry<String, Integer> entry = sortedHashtags.get(i);
            
            // Print the hashtag and its count in a formatted way
            System.out.printf("| %-11s | %-7d |%n", entry.getKey(), entry.getValue());
        }
        
        // Print the footer of the table
        System.out.println("+-------------+---------+");
    }

    /**
     * A helper method to create a tweet's data.
     * @param userId The ID of the user who posted the tweet.
     * @param tweetId The unique ID of the tweet.
     * @param tweet The content of the tweet.
     * @param tweetDate The date when the tweet was posted.
     * @return A map representing the tweet with user_id, tweet_id, tweet content, and tweet_date.
     */
    private static Map<String, String> createTweet(String userId, String tweetId, String tweet, String tweetDate) {
        // Create a map to store tweet information
        Map<String, String> tweetData = new HashMap<>();
        
        // Store the user_id, tweet_id, tweet content, and tweet_date in the map
        tweetData.put("user_id", userId);
        tweetData.put("tweet_id", tweetId);
        tweetData.put("tweet", tweet);
        tweetData.put("tweet_date", tweetDate);
        
        // Return the map containing the tweet's details
        return tweetData;
    }
}
