package com.dibek.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        System.out.println("Server fibonacci started on port  " + port);
    }

    protected static class FibonacciHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Main Handler ");
            if ("GET".equals(exchange.getRequestMethod())) {
                System.out.println("Request URI " + exchange.getRequestURI());
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
            System.out.println("Generated series ");
            return sb.toString();
        }

        private Map<String, String> queryToMap(String query) {
            if (query == null || query.isEmpty() ) return Collections.emptyMap();

            if (!query.contains("&")) {
                System.out.println("Query " + query);
                String[] values = query.split("=");
                Map<String,String> queryMap =  new HashMap<>();
                System.out.println("Number iteration from array " + values[1]);
                queryMap.put(values[0], values[1]);
                return queryMap;
            }
              return Stream.of(query.split("&"))
               .filter(s -> !s.isEmpty())
               .map(kv -> kv.split("=", 2)) 
               .collect(Collectors.toMap(x -> x[0], x-> x[1]));
        }
    }
}
