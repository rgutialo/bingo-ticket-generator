package com.bingo.service;

import com.bingo.model.Ticket;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketGeneratorPerformanceTest {

    private static final int ITERATIONS = 50;
    private static final int TICKETS_PER_ITERATION = 10000;
    private static final long MAX_TIME_PER_ITERATION_MS = 1000; // 1 second

    @Test
    void testGenerateTicketStripPerformance() throws ClassNotFoundException {
        long startStripTicketTime = System.nanoTime();

        Class.forName("com.bingo.model.StripTicket");

        long startGenerationTime = System.nanoTime();
        final List<Long> executionTimes = new ArrayList<>();

        for (int i = 0; i < ITERATIONS; i++) {
            final long startTime = System.nanoTime();

            for (int j = 0; j < TICKETS_PER_ITERATION; j++) {
                TicketGeneratorService.generateTicketStrip();
            }

            final long endTime = System.nanoTime();
            final long durationMs = (endTime - startTime) / 1_000_000;
            executionTimes.add(durationMs);

            System.out.println("Iteration " + (i + 1) + " executed in: " + durationMs + " ms");
            assertTrue(durationMs < MAX_TIME_PER_ITERATION_MS);
        }

        final long totalTime = executionTimes.stream().mapToLong(Long::longValue).sum();
        final double averageTime = totalTime / (double) ITERATIONS;
        final double averageTimeWithInitialization = ((totalTime)+(startGenerationTime - startStripTicketTime)) / (double) ITERATIONS;

        System.out.println("Average execution time per iteration: " + averageTime + " ms");
        System.out.println("Average execution including static initialization time per iteration: " + averageTimeWithInitialization + " ms");
    }
}
