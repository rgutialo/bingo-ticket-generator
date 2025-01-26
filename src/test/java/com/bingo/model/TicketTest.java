package com.bingo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void ticket_defaultConstructor() {
        final var result = new Ticket();
        for (short i = 0; i < Ticket.ROWS; i++) {
            for (short j = 0; j < Ticket.COLUMNS; j++) {
                assertEquals(0, result.getNumberInRowAndColumn(i,j));
            }
        }
    }

    @Test
    void ticket_when_ValidParametersAreReceived_then_TicketIsCreatedWith12GapsAnd15Numbers() {
        short[] gapsPerColumn = {2, 1, 2, 1, 1, 2, 1, 1, 1};
        short[] ticketNumbers = {1, 10, 14, 22, 32, 38, 44, 49, 56, 61, 68, 73, 77, 85, 89};
        final var result = new Ticket(gapsPerColumn, ticketNumbers);
        assertNotNull(result);

        int gapCount = 0;
        int numberCount = 0;
        for (short i = 0; i < Ticket.ROWS; i++) {
            for (short j = 0; j < Ticket.COLUMNS; j++) {
                if (result.isGap(i, j)) {
                    gapCount++;
                }
                else if (result.getNumberInRowAndColumn(i,j) != 0){
                    numberCount++;
                }
            }
        }
        assertEquals(12, gapCount);
        assertEquals(15, numberCount);
    }

    @Test
    void ticket_when_ValidParamsReceived_then_TicketGeneratedHas4GapsPerRow() {
        short[] gapsPerColumn = {2, 1, 2, 1, 1, 2, 1, 1, 1};
        short[] ticketNumbers = {1, 10, 14, 22, 32, 38, 44, 49, 56, 61, 68, 73, 77, 85, 89};
        final var result = new Ticket(gapsPerColumn, ticketNumbers);
        for (short i = 0; i < Ticket.ROWS; i++) {
            int rowGaps = 0;
            for (short j = 0; j < Ticket.COLUMNS; j++) {
                if (result.getNumberInRowAndColumn(i,j) == -1) {
                    rowGaps++;
                }
            }
            assertEquals(4, rowGaps);
        }
    }

    @Test
    void ticket_when_ValidParamsReceived_then_TicketNumbersAreOrderedPerColumn() {
        short[] gapsPerColumn = {2, 1, 2, 1, 1, 2, 1, 1, 1};
        short[] ticketNumbers = {1, 10, 14, 22, 32, 38, 44, 49, 56, 61, 68, 73, 77, 85, 89};
        final var result = new Ticket(gapsPerColumn, ticketNumbers);
            for (int col = 0; col < Ticket.COLUMNS; col++) {
                byte previousValue = -1;
                for (int row = 0; row < Ticket.ROWS; row++) {
                    if (!result.isGap(row, col)) {
                        byte currentValue = result.matrix[row][col];
                        assertTrue(previousValue == -1 || previousValue <= currentValue);
                        previousValue = currentValue;
                    }
                }
            }
    }

    @Test
    void isGap_when_ThereIsAGapInRowAndColumnReceived_then_TrueIsReturned() {
        final var result = new Ticket();
        result.setValueInTicket((short) 0,(short)0, (byte)-1);
        assertTrue(result.isGap(0, 0));
    }

    @Test
    void isGap_when_ThereIsNOGapInRowAndColumnReceived_then_FalseIsReturned() {
        final Ticket ticket = new Ticket();
        assertFalse(ticket.isGap(0, 0));
    }

    @Test
    void validateIncomingParams_when_InvalidGapsParamReceived_then_IllegalArgumentExceptionThrown() {
        short[] invalidGaps = {2, 1, 2, 1, 1, 2, 1, 2};
        short[] ticketNumbers = {3, 8, 14, 22, 26, 32, 38, 44, 49, 56, 61, 73, 77, 85, 89};
        assertThrows(IllegalArgumentException.class, () -> Ticket.validateIncomingParams(invalidGaps, ticketNumbers));
    }

    @Test
    void validateIncomingParams_when_InvalidNumbersParamReceived_then_IllegalArgumentExceptionThrown() {
        short[] gapsPerColumn = {2, 1, 2, 1, 1, 2, 1, 2, 1};
        short[] invalidNumbers = {3, 8, 14, 22}; // Too few numbers
        assertThrows(IllegalArgumentException.class, () -> Ticket.validateIncomingParams(gapsPerColumn, invalidNumbers));
    }
}
