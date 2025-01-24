package com.bingo.model;

/**
 * Class which represents a ticket with 3 rows and 9 columns.
 */
public class Ticket {
    private static final String TICKET_HEADER = "****************T I C K E T ****************\n";
    private static final String TICKET_FOOTER = "********************************************\n";
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
     * Returns the matrix of the ticket.
     *
     * @return the matrix of the ticket.
     */
    public byte[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Sets the value in the given row and column of the ticket.
     * @param row row of the matrix
     * @param column column of the matrix
     * @param value value to set in the given row and column.
     */
    public void setValueInTicket(short row, short column, byte value) {
        this.matrix[row][column] = value;
    }

    /**
     * Checks if the given row and column is a gap.
     *
     * @param row    row of the matrix
     * @param column column of the matrix
     * @return true if the given row and column is a gap, false otherwise.
     */

    public boolean isGap(int row, int column) {
        return matrix[row][column] == -1;
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
    public void fillGaps(short[] gapsPerColumn) {
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




