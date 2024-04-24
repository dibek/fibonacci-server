package com.dibek.service;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Scanner;

public class FibonacciIntegrationTest {


    private String ipAddress = System.getenv("FIBONACCI_SERVER_IP");

    @Test
    public void testNoParameters() throws IOException {
        URL url = new URL("http://" + ipAddress + ":9001/fibonacci");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        connection.disconnect();

        // Default number of iterations is 20
        assertEquals(FibonacciServer.generateFibonacci(20), response.toString());
    }


    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15}) // Provide different values for maxIteration
    public void testFibonacciEndpointWithDifferentValues(int maxIteration) {
        try {
            // Create URL
            URL url = new URL("http://" + ipAddress + ":9001/fibonacci?max=" + maxIteration);

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();

            // Verify response code
            assertEquals(HttpURLConnection.HTTP_OK, responseCode);

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
