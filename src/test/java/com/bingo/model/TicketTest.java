package com.bingo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketTest {

    @Test
    void ticket_constructor() {
        final Ticket ticket = new Ticket();
        final var result = ticket.getMatrix();
        assertEquals(Ticket.ROWS, result.length);
        assertEquals(Ticket.COLUMNS, result[0].length);

        for (int i = 0; i < Ticket.ROWS; i++) {
            for (int j = 0; j < Ticket.COLUMNS; j++) {
                assertEquals(0, result[i][j]);
            }
        }
    }

    @Test
    void isGap_when_ThereIsAGapInRowAndColumnReceived_then_TrueIsReturned() {
        final Ticket ticket = new Ticket();
        ticket.setValueInTicket((short) 0,(short)0, (byte)-1);
        assertTrue(ticket.isGap(0, 0));
    }

    @Test
    void isGap_when_ThereIsNOGapInRowAndColumnReceived_then_FalseIsReturned() {
        final Ticket ticket = new Ticket(); //By default, all values are 0
        assertFalse(ticket.isGap(0, 0));
    }

    @Test
    void fillGaps_when_GapsArrowIsReceived_then_GapsInTicketAreCorrectlySet() {
        final Ticket ticket = new Ticket();
        final short[] gapsPerColumn = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        ticket.fillGaps(gapsPerColumn);

        int gapCount = 0;
        for (int i = 0; i < Ticket.ROWS; i++) {
            for (int j = 0; j < Ticket.COLUMNS; j++) {
                if (ticket.isGap(i, j)) {
                    gapCount++;
                }
            }
        }

        assertEquals(9, gapCount);
    }

    @Test
    void setValueInTicket_when_ValueReceivedForRowAndColumn_then_ValueIsCorrectlySet() {
        final short row = 2;
        final short column = 3;
        final byte value = 5;
        final Ticket ticket = new Ticket();

        ticket.setValueInTicket(row, column, value);

        assertEquals(value, ticket.getMatrix()[row][column]);
    }
}
