package com.bingo;

import com.bingo.model.Ticket;
import com.bingo.service.TicketGeneratorService;

public class Main {

    public static void main(String[] args) {
        Ticket[] tickets = TicketGeneratorService.generateTicketStrip();
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

}
