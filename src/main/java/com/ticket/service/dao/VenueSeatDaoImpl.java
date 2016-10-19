/**
 * 
 */
package com.ticket.service.dao;

import com.ticket.service.model.SeatHold;
import com.ticket.service.model.SeatMapper;
import com.ticket.service.utils.TicketServiceUtils;

/**
 * @author knimdi001c
 *
 */
public class VenueSeatDaoImpl implements VenueSeatDao {

	/* (non-Javadoc)
	 * @see com.ticket.service.dao.VenueSeatDao#getVenueSeatArrangement()
	 */
	@Override
	public SeatMapper getVenueSeatArrangement() {
		// TODO Auto-generated method stub
		return TicketServiceUtils.getInstance().getVenueSeatMapper();
	}

	@Override
	public void updateSeatHold(SeatHold seatHold) {
		// TODO Auto-generated method stub
		TicketServiceUtils.getInstance().updateSeatHoldMapper(seatHold);
	}

	@Override
	public SeatHold getSeatHold(int seatHoldId) {
		// TODO Auto-generated method stub
		return TicketServiceUtils.getInstance().getSeatHold(seatHoldId);
	}

	@Override
	public int generateSeatHoldId() {
		// TODO Auto-generated method stub
		return TicketServiceUtils.getInstance().generateSeatHoldId();
	}
	
	public void removeSeatHold(int seatHoldId){
		TicketServiceUtils.getInstance().removeSeatHold(seatHoldId);
	}

}
