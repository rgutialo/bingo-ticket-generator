package com.bingo.service;

import com.bingo.model.StripTicket;
import com.bingo.model.Ticket;
import com.bingo.service.model.ShortDeque;

import java.util.*;

public class TicketGeneratorService {

    private static final short[] BINGO_NUMBERS = new short[90];
    private static final short MAX_NUMBER = 90;

    static {
        for (short i = 1; i <= MAX_NUMBER; i++) {
            BINGO_NUMBERS[i - 1] = i;
        }
    }

    /**
     * Generates a ticket strip with 6 tickets
     *
     * @return return a ticket strip with 6 tickets fully filled with gaps and numbers ordered as game requires
     */

    public static Ticket[] generateTicketStrip() {
        final StripTicket ticketStrip = new StripTicket();
        final short[][] bingoNumbers = initializeBingoNumbers();
        shuffleNumbers(bingoNumbers);
        final ShortDeque[] numbersGroupedByTens = mapMatrixBingoNumbersToDeques(bingoNumbers);
        fillTicketsWithNumbers(ticketStrip, numbersGroupedByTens);
        return ticketStrip.getTickets();
    }

    /**
     * Fills the tickets with the numbers grouped by tens
     *
     * @param ticketStrip          ticket strip to fill
     * @param numbersGroupedByTens deque array of shuffled numbers grouped by tens
     */
    private static void fillTicketsWithNumbers(StripTicket ticketStrip, ShortDeque[] numbersGroupedByTens) {
        for (int i = 0; i < ticketStrip.getTickets().length; i++) {
            final Ticket currentTicket = ticketStrip.getTickets()[i];
            for (int j = 0; j < Ticket.COLUMNS; j++) {
                for (int k = 0; k < Ticket.ROWS; k++) {
                    if (!currentTicket.isGap(k, j) && !numbersGroupedByTens[j].isEmpty()) {
                        currentTicket.getMatrix()[k][j] = (byte) numbersGroupedByTens[j].removeFirst();
                    }
                }
            }
        }
        orderNumbersPerColumn(ticketStrip);
    }

    /**
     * Orders the numbers in each column of the ticket strip
     *
     * @param ticketStrip ticketcks in strip to order
     */
    private static void orderNumbersPerColumn(StripTicket ticketStrip) {
        for (short i = 0; i < ticketStrip.getTickets().length; i++) {
            final Ticket currentTicket = ticketStrip.getTickets()[i];
            for (short j = 0; j < Ticket.COLUMNS; j++) {
                List<Byte> columnValues = new ArrayList<>();

                for (short k = 0; k < Ticket.ROWS; k++) {
                    if (!currentTicket.isGap(k, j)) {
                        columnValues.add(currentTicket.getMatrix()[k][j]);
                    }
                }

                Collections.sort(columnValues);

                short index = 0;
                for (short k = 0; k < Ticket.ROWS; k++) {
                    if (!currentTicket.isGap(k, j)) {
                        currentTicket.setValueInTicket(k, j, columnValues.get(index++));
                    }
                }
            }
        }
    }

    /**
     * Maps the matrix of bingo numbers to a deque of numbers grouped by tens
     *
     * @param bingoNumbers matrix of bingo numbers
     * @return a deque array of numbers grouped by tens
     */
    private static ShortDeque[] mapMatrixBingoNumbersToDeques(short[][] bingoNumbers) {
        final ShortDeque[] numbersGroupedByTens = new ShortDeque[9];
        for (short i = 0; i < Ticket.COLUMNS; i++) {
            numbersGroupedByTens[i] = ShortDeque.fromArray(bingoNumbers[i]);
        }
        return numbersGroupedByTens;
    }

    /**
     * Initializes the numbers matrix with the numbers from 1 to 90
     *
     * @return a matrix with the numbers from 1 to 90 ordered by row
     */
    static short[][] initializeBingoNumbers() {
        final short[][] bingoNumbers = new short[9][11];

        for (int i = 0; i < Ticket.COLUMNS; i++) {
            if (i == 0)
                bingoNumbers[i] = Arrays.copyOfRange(BINGO_NUMBERS, 0, 9);
            else if (i == 8)
                bingoNumbers[i] = Arrays.copyOfRange(BINGO_NUMBERS, (10 * i) - 1, (10 * (i + 1)));
            else
                bingoNumbers[i] = Arrays.copyOfRange(BINGO_NUMBERS, (10 * i) - 1, (10 * (i + 1)) - 1);
        }
        return bingoNumbers;
    }

    /**
     * Shuffles the numbers in the matrix received
     *
     * @param numbersMatrix matrix of numbers to shuffle by row
     */
    static void shuffleNumbers(short[][] numbersMatrix) {
        for (int i = 0; i < Ticket.COLUMNS; i++) {
            shuffleRowNumbers(numbersMatrix[i]);
        }
    }

    /**
     * Shuffles the numbers in the array received
     *
     * @param array array of numbers to shuffle
     */
    private static void shuffleRowNumbers(short[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            short temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


}
