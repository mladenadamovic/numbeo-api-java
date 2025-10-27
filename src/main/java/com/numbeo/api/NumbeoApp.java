package com.numbeo.api;

import com.numbeo.api.client.NumbeoApiClient;
import com.numbeo.api.model.CityPricesResponse;
import com.numbeo.api.model.PriceItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Main application class for fetching and displaying Numbeo city prices
 */
public class NumbeoApp {

    private static final Logger logger = LoggerFactory.getLogger(NumbeoApp.class);

    public static void main(String[] args) {
        String city = "San Francisco, CA";
        String country = "United States";

        // Override with command line arguments if provided
        if (args.length >= 2) {
            city = args[0];
            country = args[1];
        }

        // Load API key from properties file or environment variable
        String apiKey = getApiKey();

        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_API_KEY_HERE")) {
            System.err.println("ERROR: API key not configured!");
            System.err.println("Please set your Numbeo API key in one of the following ways:");
            System.err.println("1. Set the NUMBEO_API_KEY environment variable");
            System.err.println("2. Add api.key property to src/main/resources/application.properties");
            System.err.println("3. Pass it as a system property: -Dapi.key=YOUR_KEY");
            System.exit(1);
        }

        NumbeoApiClient client = null;
        try {
            client = new NumbeoApiClient(apiKey);

            System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         NUMBEO PRICE INFORMATION                             ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();

            CityPricesResponse response = client.getCityPrices(city, country);

            displayPriceInformation(response);

        } catch (IOException e) {
            logger.error("Failed to fetch city prices", e);
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * Displays the price information in a formatted manner
     *
     * @param response The city prices response
     */
    private static void displayPriceInformation(CityPricesResponse response) {
        System.out.println("City:        " + response.getCityName());
        System.out.println("Country:     " + response.getCountry());
        System.out.println("Currency:    " + response.getCurrency());
        if (response.getYearLastUpdate() != null && response.getMonthLastUpdate() != null) {
            System.out.println("Last Update: " + response.getMonthLastUpdate() + "/" + response.getYearLastUpdate());
        }
        if (response.getContributors12Months() != null) {
            System.out.println("Contributors (12 months): " + response.getContributors12Months());
        }
        System.out.println();

        List<PriceItem> prices = response.getPrices();

        if (prices == null || prices.isEmpty()) {
            System.out.println("No price data available for this city.");
            return;
        }

        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                 PRICES                                                               ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-50s │ %-12s │ %-22s │ %-12s ║%n", "Item", "Average", "Range (Low - High)", "Data Points");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");

        String currentCategory = null;

        for (PriceItem item : prices) {
            // Print category header if it changes
            if (item.getCategory() != null && !item.getCategory().equals(currentCategory)) {
                currentCategory = item.getCategory();
                System.out.println("╟──────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢");
                System.out.printf("║ %-116s ║%n", "  " + currentCategory.toUpperCase());
                System.out.println("╟──────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢");
            }

            String itemName = truncate(item.getItemName() != null ? item.getItemName() : "N/A", 50);
            String avgPrice = item.getAveragePrice() != null ?
                String.format("%s %.2f", response.getCurrency(), item.getAveragePrice()) : "N/A";
            String range = "";
            if (item.getLowestPrice() != null && item.getHighestPrice() != null) {
                range = String.format("%.2f - %.2f", item.getLowestPrice(), item.getHighestPrice());
            } else {
                range = "N/A";
            }
            String dataPoints = item.getDataPoints() != null ? item.getDataPoints().toString() : "N/A";

            System.out.printf("║ %-50s │ %-12s │ %-22s │ %12s ║%n",
                itemName, avgPrice, range, dataPoints);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Total items: " + prices.size());
    }

    /**
     * Retrieves the API key from various sources
     *
     * @return The API key or null if not found
     */
    private static String getApiKey() {
        // Try system property first
        String apiKey = System.getProperty("api.key");
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }

        // Try environment variable
        apiKey = System.getenv("NUMBEO_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            return apiKey;
        }

        // Try loading from properties file
        try (InputStream input = NumbeoApp.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                apiKey = prop.getProperty("api.key");
                if (apiKey != null && !apiKey.isEmpty()) {
                    return apiKey;
                }
            }
        } catch (IOException e) {
            logger.warn("Could not load application.properties", e);
        }

        return null;
    }

    /**
     * Truncates a string to the specified length
     *
     * @param str The string to truncate
     * @param length The maximum length
     * @return The truncated string
     */
    private static String truncate(String str, int length) {
        if (str == null) {
            return "";
        }
        return str.length() <= length ? str : str.substring(0, length - 3) + "...";
    }
}
