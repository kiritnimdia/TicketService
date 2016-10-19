/**
 * 
 */
package com.ticket.service;

import com.ticket.service.model.SeatHold;

/**
 * @author knimdi001c
 *
 */
public class AbstractTicketService implements TicketService {

	/* (non-Javadoc)
	 * @see com.ticket.service.TicketService#numSeatsAvailable()
	 */
	@Override
	public int numSeatsAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.ticket.service.TicketService#findAndHoldSeats(int, java.lang.String)
	 */
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ticket.service.TicketService#reserveSeats(int, java.lang.String)
	 */
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

}
