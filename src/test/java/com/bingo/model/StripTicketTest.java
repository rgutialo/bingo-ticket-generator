package com.bingo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StripTicketTest {

    private static final int ROWS = 3;
    private static final int COLUMNS = 9;


    @Test
    void StripTicket_constructor() {
        final var result = new StripTicket();
        assertNotNull(result);
    }

    @Test
    void WhenStripTicketIsCreated_then_SixTicketsAreGenerated() {
        final var result = new StripTicket();
        assertNotNull(result.getTickets());
        assertEquals(6, result.getTickets().length);
    }

    @Test
    void WhenStripTicketIsCreated_then_EachTicketHas3RowsAnd9Columns() {
        final var result = new StripTicket();
        for (Ticket ticket : result.getTickets()) {
            assertNotNull(ticket.getMatrix());
            assertEquals(ROWS, ticket.getMatrix().length);
            assertEquals(COLUMNS, ticket.getMatrix()[0].length);
        }
    }

    @Test
    void WhenStripTicketIsCreated_then_EachTicketHas12Gaps() {
        final var result = new StripTicket();
        final Ticket[] tickets = result.getTickets();
        for (Ticket ticket : tickets) {
            byte[][] matrix = ticket.getMatrix();
            int totalGaps = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == -1) {
                        totalGaps++;
                    }
                }
            }
            assertEquals(12, totalGaps);
        }
    }

    @Test
    void WhenStripTicketIsCreated_then_EachTicketHas4GapsPerRow() {
        final var result = new StripTicket();
        final Ticket[] tickets = result.getTickets();
        for (Ticket ticket : tickets) {
            byte[][] matrix = ticket.getMatrix();
            for (int i = 0; i < ROWS; i++) {
                int rowGaps = 0;
                for (int j = 0; j < COLUMNS; j++) {
                    if (matrix[i][j] == -1) {
                        rowGaps++;
                    }
                }
                assertEquals(4, rowGaps);
            }
        }
    }

    @Test
    void WhenStripTicketIsCreated_then_EachTicketHasNOT3GapsPerColumn() {
        final var result = new StripTicket();
        final Ticket[] tickets = result.getTickets();
        for (Ticket ticket : tickets) {
            byte[][] matrix = ticket.getMatrix();
            for (int i = 0; i < COLUMNS; i++) {
                int rowGaps = 0;
                for (int j = 0; j < ROWS; j++) {
                    if (matrix[j][i] == -1) {
                        rowGaps++;
                    }
                }
                assertNotEquals(3, rowGaps);
            }
        }
    }


}
