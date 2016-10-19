package com.ticket.service.dao;

import com.ticket.service.model.SeatHold;
import com.ticket.service.model.SeatMapper;

public interface VenueSeatDao {
	public SeatMapper getVenueSeatArrangement();
	public void updateSeatHold(SeatHold seatHold);
	public SeatHold getSeatHold(int seatHoldId);
	public int generateSeatHoldId();
	public void removeSeatHold(int seatHoldId);
}
