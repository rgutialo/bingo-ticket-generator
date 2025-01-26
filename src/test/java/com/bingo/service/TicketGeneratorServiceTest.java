package com.bingo.service;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TicketGeneratorServiceTest {

    @Test
    void generateTicketStrip_whenGenerateTicketStrip_then_SixTicketsAreGenerated() {
        final var result = TicketGeneratorService.generateTicketStrip();
        assertNotNull(result);
        assertEquals(6, result.length);
    }

    @Test
    void initializeBingoNumbers_then_BingoNumbersAreGenerated() {
        Set<Short> originalNumbers = new HashSet<>();
        for (short i = 1; i <= 90; i++) {
            originalNumbers.add(i);
        }

        short[][] generatedMatrix = TicketGeneratorService.initializeBingoNumbers();

        Set<Short> generatedNumbers = new HashSet<>();
        for (short[] row : generatedMatrix) for (short num : row) generatedNumbers.add(num);
        assertEquals(originalNumbers, generatedNumbers);

    }

    @Test
    void shuffleNumbers_then_ShuffleBingoNumbersWithoutLoosingAnyNumber() {
        short[][] originalMatrix = TicketGeneratorService.initializeBingoNumbers();
        short[][] shuffledMatrix = TicketGeneratorService.initializeBingoNumbers();
        TicketGeneratorService.shuffleNumbers(shuffledMatrix);

        Set<Short> originalNumbers = new HashSet<>();
        Set<Short> shuffledNumbers = new HashSet<>();

        for (short[] row : originalMatrix) for (short num : row) originalNumbers.add(num);
        for (short[] row : shuffledMatrix) for (short num : row) shuffledNumbers.add(num);

        assertEquals(originalNumbers, shuffledNumbers);
    }
}
