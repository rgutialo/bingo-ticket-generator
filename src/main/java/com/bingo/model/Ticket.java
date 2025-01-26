package com.bingo.model;

import java.util.Arrays;

/**
 * Class which represents a ticket with 3 rows and 9 columns.
 */
public class Ticket {
    private static final String TICKET_HEADER = "****************T I C K E T ****************\n";
    private static final String TICKET_FOOTER = "********************************************\n";
    private static final short NUMBERS_PER_TICKET = 15;
    private static final short GAPS_PER_TICKET = 12;
    public static final short ROWS = 3;
    public static final short COLUMNS = 9;
    public static final int GAPS_PER_ROW = 4;
    private final byte[][] matrix = new byte[ROWS][COLUMNS];
    private final short[] gapsPerRow = {0, 0, 0};

    /**
     * Default constructor which initializes the matrix of the ticket with zeros.
     **/
    public Ticket() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                setValueInTicket((short) i, (short) j, (byte) 0);
            }
        }
    }

    /**
     * Constructor which initializes the ticket with the given gaps per column and ticket numbers.
     *
     * @param gapsPerColumn gaps per column
     * @param ticketNumbers numbers to fill the ticket
     * @throws IllegalArgumentException if the gaps per column or ticket numbers are invalid
     */
    public Ticket(short[] gapsPerColumn, short[] ticketNumbers) throws IllegalArgumentException {
        validateIncomingParams(gapsPerColumn, ticketNumbers);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                setValueInTicket((short) i, (short) j, (byte) 0);
            }
        }
        fillGaps(gapsPerColumn);
        fillNumbers(ticketNumbers);
    }

    short getNumberInRowAndColumn(short row, short column) {
        if (row < 0 || row > ROWS || column < 0 ||column > COLUMNS)
            throw new IllegalArgumentException("Cannot be obtained any value from ticket: Invalid row and column");
        return matrix[row][column];
    }

    /**
     * Prints the ticket in a human-readable format.
     *
     * @return built string with the ticket.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(TICKET_HEADER);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isGap(i, j)) {
                    sb.append("  * ");
                } else {
                    sb.append(String.format("%3d ", matrix[i][j]));
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append(TICKET_FOOTER);
        return sb.toString();
    }

    /**
     * Validates the incoming parameters for the ticket.
     *
     * @param gapsPerColumn gaps per column of the ticket
     * @param ticketNumbers numbers to fill the ticket
     */
    static void validateIncomingParams(short[] gapsPerColumn, short[] ticketNumbers) {
        if (gapsPerColumn == null || ticketNumbers == null) {
            throw new IllegalArgumentException("Ticket cannot be created: Received null parameters for gaps or numbers");
        }
        if (gapsPerColumn.length != COLUMNS) {
            throw new IllegalArgumentException("Ticket cannot be created: Invalid parameter for gaps: Received gaps array of size " + gapsPerColumn.length + " instead of " + COLUMNS);
        }
        short totalGaps = 0;
        for (short i = 0; i < gapsPerColumn.length; i++) {
            if (gapsPerColumn[i] < 0) {
                throw new IllegalArgumentException("Ticket cannot be created: Invalid parameter for gaps: Received negative value for gaps in column " + i);
            }
            totalGaps += gapsPerColumn[i];
        }
        if (totalGaps != GAPS_PER_TICKET) {
            throw new IllegalArgumentException("Ticket cannot be created: Invalid parameter for gaps: Received total of " + totalGaps + " gaps instead of " + GAPS_PER_TICKET);
        }
        if (ticketNumbers.length != NUMBERS_PER_TICKET) {
            throw new IllegalArgumentException("Ticket cannot be created: Invalid parameter for numbers: Received numbers array of size " + ticketNumbers.length + " instead of " + NUMBERS_PER_TICKET);
        }
    }

    /**
     * Fills the ticket with the given numbers.
     *
     * @param ticketNumbers numbers to fill the ticket
     */

    private void fillNumbers(short[] ticketNumbers) {
        int column;
        Arrays.sort(ticketNumbers);
        for (short i = 0; i < NUMBERS_PER_TICKET; i++) {
            if (ticketNumbers[i] == 90) {
                column = 8;
            } else if (ticketNumbers[i] % 10 == 0) {
                column = (ticketNumbers[i]) / 10;
            } else {
                column = (ticketNumbers[i] - 1) / 10;
            }
            for (short row = 0; row < Ticket.ROWS; row++) {
                if (!isGap(row, column) && !isFilled(row, column)) {
                    setValueInTicket(row, (short) column, (byte) ticketNumbers[i]);
                    break;
                }
            }
        }
    }

    /**
     * Sets the value in the given row and column of the ticket.
     *
     * @param row    row of the matrix
     * @param column column of the matrix
     * @param value  value to set in the given row and column.
     */
    void setValueInTicket(short row, short column, byte value) {
        this.matrix[row][column] = value;
    }

    /**
     * Checks if the given row and column is a gap.
     *
     * @param row    row of the matrix
     * @param column column of the matrix
     * @return true if the given row and column is a gap, false otherwise.
     */

    boolean isGap(int row, int column) {
        return matrix[row][column] == -1;
    }

    /**
     * Checks if the given row and column is filled.
     *
     * @param row    row
     * @param column column
     * @return true if the given row and column is filled, false otherwise.
     */

    private boolean isFilled(int row, int column) {
        if (isGap(row, column)) {
            return true;
        }
        return matrix[row][column] != 0;
    }

    /**
     * Checks which row has the least number of gaps.
     *
     * @return the row with the least number of gaps.
     */
    private short smallestRowWithGaps() {
        short smallestRow = 0;
        for (short i = 0; i < gapsPerRow.length; i++) {
            if (gapsPerRow[i] < gapsPerRow[smallestRow]) {
                smallestRow = i;
            }
        }
        return smallestRow;
    }

    /**
     * Fills the gaps in the ticket based on the given array of gaps per column.
     *
     * @param gapsPerColumn array of gaps per column.
     */
    private void fillGaps(short[] gapsPerColumn) {
        for (short column = 0; column < gapsPerColumn.length; column++) {
            short rowSelected;
            for (short j = 0; j < gapsPerColumn[column]; j++) {

                if (this.getGapsPerRow(0) == GAPS_PER_ROW && this.getGapsPerRow(1) == GAPS_PER_ROW) {
                    rowSelected = 2;
                } else if (this.getGapsPerRow(0) == GAPS_PER_ROW && this.getGapsPerRow(2) == GAPS_PER_ROW) {
                    rowSelected = 1;
                } else if (this.getGapsPerRow(1) == GAPS_PER_ROW && this.getGapsPerRow(2) == GAPS_PER_ROW) {
                    rowSelected = 0;
                } else {
                    rowSelected = smallestRowWithGaps();
                }
                if (!this.isGap(rowSelected, column) && this.getGapsPerRow(rowSelected) < GAPS_PER_ROW) {
                    setValueInTicket(rowSelected, column, (byte) -1);
                    incrementNumberOfGaps(rowSelected);

                } else {
                    j--;
                }
            }
        }
    }

    /**
     * Returns the number of gaps in the given row.
     *
     * @param row row of the matrix
     * @return the number of gaps in the given row.
     */

    private short getGapsPerRow(int row) {
        return gapsPerRow[row];
    }

    /**
     * Increments the number of gaps in the given row.
     *
     * @param row row of the matrix
     */

    private void incrementNumberOfGaps(final int row) {
        gapsPerRow[row]++;
    }
}




