package com.numbeo.api.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.numbeo.api.model.CityPricesResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Client for interacting with the Numbeo API
 */
public class NumbeoApiClient {

    private static final Logger logger = LoggerFactory.getLogger(NumbeoApiClient.class);
    private static final String BASE_URL = "https://www.numbeo.com/api";
    private static final String CITY_PRICES_ENDPOINT = "city_prices";

    private final OkHttpClient httpClient;
    private final Gson gson;
    private final String apiKey;

    /**
     * Creates a new Numbeo API client
     *
     * @param apiKey The API key for authentication
     */
    public NumbeoApiClient(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Fetches city prices from the Numbeo API
     *
     * @param city    The city name (e.g., "San Francisco, CA")
     * @param country The country name (e.g., "United States")
     * @return CityPricesResponse containing the price data
     * @throws IOException if the request fails
     */
    public CityPricesResponse getCityPrices(String city, String country) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment(CITY_PRICES_ENDPOINT)
                .addQueryParameter("city", city)
                .addQueryParameter("country", country)
                .addQueryParameter("api_key", apiKey)
                .build();

        logger.info("Fetching prices for {} in {}", city, country);
        logger.debug("Request URL: {}", url.toString().replace(apiKey, "***"));

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error body";
                logger.error("API request failed with status {}: {}", response.code(), errorBody);
                throw new IOException("Unexpected response code: " + response.code() + " - " + errorBody);
            }

            String responseBody = response.body().string();
            logger.debug("Response received: {} bytes", responseBody.length());

            CityPricesResponse pricesResponse = gson.fromJson(responseBody, CityPricesResponse.class);

            if (pricesResponse == null) {
                throw new IOException("Failed to parse API response");
            }

            logger.info("Successfully fetched {} price items",
                    pricesResponse.getPrices() != null ? pricesResponse.getPrices().size() : 0);

            return pricesResponse;
        } catch (IOException e) {
            logger.error("Error fetching city prices: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Closes the HTTP client and releases resources
     */
    public void close() {
        if (httpClient != null) {
            httpClient.dispatcher().executorService().shutdown();
            httpClient.connectionPool().evictAll();
        }
    }
}
