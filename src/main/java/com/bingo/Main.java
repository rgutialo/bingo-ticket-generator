package com.bingo;

import com.bingo.model.Ticket;
import com.bingo.service.TicketGeneratorService;

public class Main {

    public static void main(String[] args) {
        final short[][] testArray = new short[3][9];
        short generator = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                testArray[i][j] = generator++;
            }
        }
        Ticket[] tickets = TicketGeneratorService.generateTicketStrip();
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

}
