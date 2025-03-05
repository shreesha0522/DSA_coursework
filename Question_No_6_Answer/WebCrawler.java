package Question_No_6_Answer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class WebCrawler {
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();  // Thread-safe set
    private final ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executorService;
    private final int maxThreads;
    private final int maxPagesToCrawl;

    /**
     * The WebCrawler constructor initializes the web crawler with a specified number of threads and maximum pages to crawl.
     * 
     * @param maxThreads The maximum number of threads to use for crawling.
     * @param maxPagesToCrawl The maximum number of pages to crawl before stopping.
     */
    public WebCrawler(int maxThreads, int maxPagesToCrawl) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
        this.maxThreads = maxThreads;
        this.maxPagesToCrawl = maxPagesToCrawl;
    }

    /**
     * Starts the web crawling process by adding the start URL to the queue and submitting tasks for crawling.
     * 
     * @param startUrl The initial URL to begin crawling from.
     */
    public void startCrawling(String startUrl) {
        urlQueue.add(startUrl);

        for (int i = 0; i < maxThreads; i++) {
            executorService.submit(this::processUrls);
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Processes URLs by fetching their content and extracting new URLs to crawl.
     * This method is executed by multiple threads concurrently.
     */
    private void processUrls() {
        while (!urlQueue.isEmpty() && visitedUrls.size() < maxPagesToCrawl) {
            String url = urlQueue.poll();
            if (url == null || visitedUrls.contains(url)) {
                continue;
            }

            visitedUrls.add(url);
            System.out.println("Crawling: " + url);

            try {
                String content = fetchContent(url);
                Set<String> extractedUrls = extractUrls(content);
                urlQueue.addAll(extractedUrls);
            } catch (Exception e) {
                System.err.println("Failed to fetch: " + url);
            }
        }
    }

    /**
     * Fetches the content of a given URL.
     * 
     * @param urlString The URL to fetch content from.
     * @return The content of the URL as a string.
     * @throws Exception If there is an issue fetching the content.
     */
    private String fetchContent(String urlString) throws Exception {
        StringBuilder content = new StringBuilder();
        URL url = new URI(urlString).toURL();  // Fixes deprecation warning
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

    /**
     * Extracts URLs from the content of a web page using a simple regex.
     * 
     * @param content The content of the web page.
     * @return A set of extracted URLs.
     */
    private Set<String> extractUrls(String content) {
        Set<String> urls = new HashSet<>();
        // Simple regex to find URLs (this can be improved)
        String regex = "http[s]?://\\S+";
        content.lines().forEach(line -> {
            if (line.matches(regex)) {
                urls.add(line);
            }
        });
        return urls;
    }

    /**
     * The main method initializes the WebCrawler and starts the crawling process.
     * 
     * @param args Command-line arguments (not used in this case).
     */
    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler(5, 50);  // 5 threads, crawl up to 50 pages
        crawler.startCrawling("https://example.com");
    }
}
