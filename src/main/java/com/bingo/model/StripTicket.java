package com.bingo.model;

import com.bingo.CustomCollections;

/**
 * Class responsible for generating a strip of 6 tickets.
 */
public class StripTicket {
    private final Ticket[] tickets = new Ticket[6];

    private static final short ROWS = 6;
    private static final short COLS = 9;
    private static final short[][] MATRIX_GAPS = new short[ROWS][COLS];
    private static final short[] ACCUMULATED_SUM_PER_ROW = new short[ROWS];
    private static final short[] ACCUMULATED_SUM_PER_COLUMN = new short[COLS];
    private static final short[] COLUMN_COUNT_WITH_TWOs = new short[COLS];
    private static final short[] TOTAL_GAPS_PER_COLUMN = {9, 8, 8, 8, 8, 8, 8, 8, 7};
    private static final short[] TOTAL_TWOs_PER_COLUMN = {3, 2, 2, 2, 2, 2, 2, 2, 1};
    private static final short TOTAL_GAPS_PER_ROW = 12;
    private static final short[] GAPS_VALUES = {2, 1};

    /**
     * Default constructor which initializes the tickets and generates the gaps for each ticket.
     */
    public StripTicket() {
        for (short i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket();
        }
        generateGapsPerStrip((short) 0, (short)0);
        initializeGapsPerTicket();
    }

    /**
     * Returns the tickets generated.
     *
     * @return the tickets generated.
     */
    public Ticket[] getTickets() {
        return tickets;
    }

    /**
     * Fills the gaps in each ticket based on the generated matrix.
     */
    private void initializeGapsPerTicket() {
        for (short i = 0; i < tickets.length; i++) {
            tickets[i].fillGaps(MATRIX_GAPS[i]);
        }
    }


    /**
     * Generates a matrix with the gaps for each ticket following the rules of the game.
     *
     * @param row
     * @param col
     * @return
     */
    private static boolean generateGapsPerStrip(short row, short col) {
        if (row == ROWS) {
            return validateColumns();
        }

        if (col == 0 || col == 8) {
            CustomCollections.reverse(GAPS_VALUES);
        } else {
            CustomCollections.shuffle(GAPS_VALUES);
        }

        for (short value : GAPS_VALUES) {
            if (isValid(row, col, value)) {
                placeValue(row, col, value);

                int nextRow = (col == COLS - 1) ? row + 1 : row;
                int nextCol = (col == COLS - 1) ? 0 : col + 1;

                if (generateGapsPerStrip((short)nextRow, (short)nextCol)) {
                    return true;
                }

                removeValue(row, col, value);
            }
        }
        return false;
    }

    /**
     * Validates if the value can be placed in the given row and column following the requested pattern for strip of 6 tickets.
     *
     * @param row   row index in the matrix
     * @param col   column index in the matrix
     * @param value value to be placed (1 or 2)
     * @return true if the value can be placed, false otherwise.
     */
    private static boolean isValid(short row, short col, short value) {
        short newRowSum = (short) (ACCUMULATED_SUM_PER_ROW[row] + value);
        short newColSum = (short) (ACCUMULATED_SUM_PER_COLUMN[col] + value);
        short newCol2s = (short) (COLUMN_COUNT_WITH_TWOs[col] + (value == 2 ? 1 : 0));

        if (newRowSum > TOTAL_GAPS_PER_ROW || newColSum > TOTAL_GAPS_PER_COLUMN[col] || newCol2s > TOTAL_TWOs_PER_COLUMN[col]) {
            return false;
        }

        if (row == ROWS - 1) {
            return newColSum == TOTAL_GAPS_PER_COLUMN[col] && newCol2s == TOTAL_TWOs_PER_COLUMN[col];
        }

        return true;
    }

    /**
     * Places the value in the given row and column and updates the row, column sums and the number of 2's placed (if placed).
     *
     * @param row   row index in the matrix
     * @param col   column index in the matrix
     * @param value value to be placed (1 or 2)
     */

    private static void placeValue(short row, short col, short value) {
        MATRIX_GAPS[row][col] = value;
        ACCUMULATED_SUM_PER_ROW[row] += value;
        ACCUMULATED_SUM_PER_COLUMN[col] += value;
        if (value == 2) COLUMN_COUNT_WITH_TWOs[col]++;
    }

    /**
     * Removes the value from the given row and column and updates the row, column sums and the number of 2's placed (if placed).
     *
     * @param row   row index in the matrix
     * @param col   column index in the matrix
     * @param value value to be removed
     */
    private static void removeValue(short row, short col, short value) {
        MATRIX_GAPS[row][col] = 0;
        ACCUMULATED_SUM_PER_ROW[row] -= value;
        ACCUMULATED_SUM_PER_COLUMN[col] -= value;
        if (value == 2) COLUMN_COUNT_WITH_TWOs[col]--;
    }

    /**
     * Validates if the columns are filled correctly.
     *
     * @return true if the columns are filled correctly, false otherwise.
     */

    private static boolean validateColumns() {
        for (short col = 0; col < COLS; col++) {
            if (ACCUMULATED_SUM_PER_COLUMN[col] != TOTAL_GAPS_PER_COLUMN[col] || COLUMN_COUNT_WITH_TWOs[col] != TOTAL_TWOs_PER_COLUMN[col]) {
                return false;
            }
        }
        return true;
    }
}
