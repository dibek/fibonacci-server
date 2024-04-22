package com.dibek.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class FibonacciServer {


    public static void main(String[] args) throws Exception {
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/fibonacci", new FibonacciHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port " + port);
    }

    protected static class FibonacciHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
                int maxIterations = Integer.parseInt(params.getOrDefault("maxIterations", "10"));
                System.out.println("Number of iterations " + maxIterations);
                String response = generateFibonacciSeries(maxIterations);
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }

        public String generateFibonacciSeries(int n) {
            StringBuilder sb = new StringBuilder();
            int a = 0, b = 1;
            for (int i = 0; i < n; i++) {
                sb.append(a).append(", ");
                int temp = a;
                a = b;
                b += temp;
            }
            return sb.toString();
        }

        private Map<String, String> queryToMap(String query) {
            String[] params = query.split("&");
            Map<String, String> map = new java.util.HashMap<>();
            for (String param : params) {
                String[] keyValue = param.split("=");
                map.put(keyValue[0], keyValue[1]);
            }
            return map;
        }
    }
}
