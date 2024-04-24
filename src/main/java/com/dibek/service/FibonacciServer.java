package com.dibek.service;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FibonacciServer {

    private static final int DEFAULT_ITERATIONS = 20;
    private static final int PORT = 9000;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/fibonacci", new FibonacciHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is running on port " + PORT + "...");
    }

    static class FibonacciHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            int maxIterations = DEFAULT_ITERATIONS;
            if (query != null) {
                Map<String, String> queryParams = parseQueryParams(query);
                if (queryParams.containsKey("maxIterations")) {
                    maxIterations = Integer.parseInt(queryParams.get("maxIterations"));
                }
            }
            String response = generateFibonacci(maxIterations);

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Method to generate Fibonacci series
    static String generateFibonacci(int maxIterations) {
        StringBuilder result = new StringBuilder();
        int a = 0, b = 1;
        for (int i = 0; i < maxIterations; i++) {
            result.append(a).append(", ");
            int temp = a;
            a = b;
            b = temp + b;
        }
        return result.toString();
    }

    // Method to parse query parameters
    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        Arrays.stream(query.split("&"))
                .map(param -> param.split("="))
                .forEach(pair -> params.put(pair[0], pair[1]));
        return params;
    }
}
