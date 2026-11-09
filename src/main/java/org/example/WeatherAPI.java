package org.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WeatherAPI {
    public static void main(String[] args) {
        System.out.println("Weather Data");
        System.out.println("---------------------");
        System.out.println("Location: Fond Du Lac");

        String apiUrl = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=43.7730&longitude=-88.4471"
                + "&current=temperature_2m,wind_speed_10m"
                + "&temperature_unit=fahrenheit"
                + "&wind_speed_unit=mph";

        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream(),
                                StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(
                                connection.getErrorStream(),
                                StandardCharsets.UTF_8));

                System.out.println("HTTP Status: " + responseCode);
            }

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);
                response.append(System.lineSeparator());
            }

            reader.close();

            System.out.println();
            System.out.println("API Response:");
            System.out.println(response);
            if (responseCode >= 200 && responseCode < 300) {
                System.out.println(
                        "Weather data retrieved successfully!");
            } else {
                System.out.println(
                        "Unable to retrieve weather data");
            }
        } catch (Exception e) {
            System.out.println("Weather API request failed");
            System.out.println(
                    "Error details: " + e.getMessage());
            System.out.println(
                    "Check your internet connection and try again");

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
