package com.dibek.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class FibonacciServerTest {


    public void testGenerateSingleFibonacciSeries() {
        FibonacciServer.FibonacciHandler handler = new FibonacciServer.FibonacciHandler();
        String result = handler.generateFibonacciSeries(10);
        assertEquals("0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ", result);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15}) // Test with different values of n
    public void testGenerateMultipleFibonacciSeries(int n) {
        FibonacciServer.FibonacciHandler handler = new FibonacciServer.FibonacciHandler();
        String result = handler.generateFibonacciSeries(n);
        String[] parts = result.split(", ");
        assertEquals(n, parts.length); // Check if the length of series is correct
        // You can add more assertions to check the generated series here
    }

}
