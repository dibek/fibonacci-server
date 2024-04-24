package com.dibek.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciServerTest {

    @Test
    public void testGenerateFibonacci() {
        assertEquals("0, 1, 1, 2, 3, 5, 8, ", FibonacciServer.generateFibonacci(7));
        assertEquals("0, 1, 1, 2, 3, 5, 8, 13, ", FibonacciServer.generateFibonacci(8));
        assertEquals("0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ", FibonacciServer.generateFibonacci(10));
    }

}
