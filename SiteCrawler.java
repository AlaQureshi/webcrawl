
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SiteCrawler {
    private static final Logger LOGGER = Logger.getLogger(SiteCrawler.class.getName());

    public static void main(String[] args) {
        String startUrl = "https://webscraper.io/test-sites/e-commerce/allinone";
        exploreWebsite(1, startUrl, new HashSet<>());
    }

    private static void exploreWebsite(int depth, String currentUrl, HashSet<String> seenUrls) {
        if (depth <= 5) {
            Document webpage = fetchWebpage(currentUrl, seenUrls);
            if (webpage != null) {
                processPage(webpage);
            }
        }
    }

    private static Document fetchWebpage(String pageUrl, HashSet<String> history) {
        if (!history.contains(pageUrl)) {
            try {
                Connection.Response response = Jsoup.connect(pageUrl).execute();
                Document document = response.parse();
                history.add(pageUrl);
                return document;
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Failed to retrieve content from " + pageUrl, ex);
            }
        }
        return null;
    }

    private static void processPage(Document document) {
        for (Element link : document.select("a[href]")) {
            LOGGER.info("Found a link: " + link.attr("abs:href"));
        }
    }
}
