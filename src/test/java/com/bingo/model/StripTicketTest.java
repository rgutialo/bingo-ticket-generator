package com.bingo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the StripTicket class.
 */
class StripTicketTest {

    @Test
    void stripTicket_constructor() {
        final var result = new StripTicket();
        assertNotNull(result);
    }

    @Test
    void getTicketGapsPerColumn_whenValidIndexReceived_thenGapsArrayReturnedWith12GapsInTotal() {
        short index = 0;
        short[] gaps = StripTicket.getTicketGapsPerColumn(index);
        int sumGaps = 0;
        for (short gap : gaps) {
            sumGaps += gap;
        }

        assertNotNull(gaps);
        assertEquals(9, gaps.length);
        assertEquals(12, sumGaps);
    }

    @Test
    void getTicketMissingNumbersPerColumn_whenValidIndexReceived_ThanMissingNumbersArrayPerColumnReturnedWith15NumbersIntotal() {
        short index = 0;
        short[] missingNumbersPerColumn = StripTicket.getTicketMissingNumbersPerColumn(index);
        int sumRequiredNumers = 0;
        for (short numbers : missingNumbersPerColumn) {
            sumRequiredNumers += numbers;
        }
        assertNotNull(missingNumbersPerColumn);
        assertEquals(9, missingNumbersPerColumn.length);
        assertEquals(15, sumRequiredNumers);
    }

    @Test
    void initializeMatrixGapsAndNumbers_shouldInitializeGapsAndMissingNumberMatrix() {
        StripTicket.initializeMatrixGapsAndNumbers((short) 0, (short) 0);
        short[][] gapsMatrix = new short[6][9];
        short[][] numbersMatrix = new short[6][9];

        //matrix gaps and numbers verification per rows
        for (short row = 0; row < 6; row++) {
            int gapsPerTicket = 0;
            int numbersPerTicket = 0;
            for (short col = 0; col < 9; col++) {
                gapsMatrix[row][col] = StripTicket.getTicketGapsPerColumn(row)[col];
                gapsPerTicket += gapsMatrix[row][col];
                numbersMatrix[row][col] = StripTicket.getTicketMissingNumbersPerColumn(row)[col];
                numbersPerTicket += numbersMatrix[row][col];
                assertEquals(3, gapsMatrix[row][col] + numbersMatrix[row][col]);
            }
            assertEquals(12, gapsPerTicket);
            assertEquals(15, numbersPerTicket);
        }

        //matrix gaps verification per column
        for (int i = 0; i < 9; i++) {
            int gapsPerColumnStrip = 0;
            for (int j = 0; j < 6; j++) {
                gapsPerColumnStrip += gapsMatrix[j][i];
            }
            if (i == 0)
                assertEquals(9, gapsPerColumnStrip);
            else if (i > 0 && i < 8)
                assertEquals(8, gapsPerColumnStrip);
            else if (i == 8)
                assertEquals(7, gapsPerColumnStrip);
        }
    }
}
