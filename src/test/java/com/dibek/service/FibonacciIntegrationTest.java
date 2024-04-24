package com.dibek.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciIntegrationTest {

    @Test
    public void testNoParameters() throws IOException {
        URL url = new URL("http://localhost:9000/fibonacci");
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
}
