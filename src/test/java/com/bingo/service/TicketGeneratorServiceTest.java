package com.bingo.service;

import com.bingo.model.Ticket;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TicketGeneratorServiceTest {
    @Test
    void generateTicketStrip_then_returnsSixTickets() {
        final var result = TicketGeneratorService.generateTicketStrip();
        assertNotNull(result);
        assertEquals(6, result.length);
    }

    @Test
    void generateTicketStrip_then_TicketsReturnedAreProperlyFilledAndEachTicketHasFifteenNumbers() {
        final var result = TicketGeneratorService.generateTicketStrip();
        for (Ticket ticket : result) {
            int totalNumbersPerTicket = 0;
            for (int row = 0; row < Ticket.ROWS; row++) {
                for (int col = 0; col < Ticket.COLUMNS; col++) {
                    if (!ticket.isGap(row, col)) {
                        totalNumbersPerTicket++;
                        assertTrue(ticket.getMatrix()[row][col] > 0);
                    }
                }
            }
            assertTrue(totalNumbersPerTicket >= 15);
        }
    }

    @Test
    void generateTicketStrip_then_TicketNumbersAreOrderedPerColumn() {
        final var result = TicketGeneratorService.generateTicketStrip();
        for (Ticket ticket : result) {
            for (int col = 0; col < Ticket.COLUMNS; col++) {
                byte previousValue = -1;
                for (int row = 0; row < Ticket.ROWS; row++) {
                    if (!ticket.isGap(row, col)) {
                        byte currentValue = ticket.getMatrix()[row][col];
                        assertTrue(previousValue == -1 || previousValue <= currentValue);
                        previousValue = currentValue;
                    }
                }
            }
        }
    }

    @Test
    void shuffleNumbers_thenShuffleBingoNumbersWithoutLoosingAnyNumber() {
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
