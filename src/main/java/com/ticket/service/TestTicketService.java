package com.ticket.service;

public class TestTicketService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicketService ts = new TicketServiceImpl();
		System.out.println(ts.numSeatsAvailable());
	}

}
