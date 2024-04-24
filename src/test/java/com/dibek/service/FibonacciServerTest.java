package com.dibek.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciServerTest {

    @Test
    public void testExtractNSeries() {
        assertEquals("8", FibonacciServer.extractNSeries(7));
        assertEquals("13", FibonacciServer.extractNSeries(8));
        assertEquals("34", FibonacciServer.extractNSeries(10));
    }

}
