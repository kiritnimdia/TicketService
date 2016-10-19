/**
 * 
 */
package com.ticket.service;

import java.util.Set;
import java.util.UUID;

import com.ticket.service.dao.VenueSeatDao;
import com.ticket.service.dao.VenueSeatDaoImpl;
import com.ticket.service.exception.ReservationException;
import com.ticket.service.exception.TicketServiceException;
import com.ticket.service.model.Row;
import com.ticket.service.model.SeatHold;
import com.ticket.service.model.SeatMapper;
import com.ticket.service.utils.EmailValidator;

/**
 * @author knimdi001c
 *
 */
public class TicketServiceImpl extends AbstractTicketService {
	
	private VenueSeatDao venueSeatDao;
	
	@Override
	public int numSeatsAvailable() {
		SeatMapper seatMapper = getVenueSeatDao().getVenueSeatArrangement();
		return seatMapper.getAvailableSeatCount();
	}
	
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		
		if(!EmailValidator.isValidEmail(customerEmail)){
			throw new TicketServiceException(" Customer Email is not Valid ");
		}
		//validate customerEmail
		
		SeatMapper seatMapper = getVenueSeatDao().getVenueSeatArrangement();
		// No Seats Available
		if(seatMapper.getAvailableSeatCount() == 0){
			throw new TicketServiceException(" NO SEATS AVAILABLE ");
		}
			
		// Find and Hold seats
		Set<Row> rowMapper = seatMapper.findAndHoldSeat(numSeats);
		SeatMapper holdSeatMapper = new SeatMapper();
		holdSeatMapper.setRows(rowMapper);
 		SeatHold bestSeatsHold = new SeatHold(generateSeatHoldId(), holdSeatMapper, customerEmail);
 		bestSeatsHold.setSeatRequested(numSeats);
		// generate UUID and assign to bestSeatsHold		
 		getVenueSeatDao().updateSeatHold(bestSeatsHold);
 		
		return bestSeatsHold;
	}
	

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		
		if(!EmailValidator.isValidEmail(customerEmail)){
			throw new TicketServiceException(" Customer Email is not Valid ");
		}
		
		SeatHold seatHold = getVenueSeatDao().getSeatHold(seatHoldId);
		
		if(seatHold == null){
			throw new TicketServiceException(" SEATS ARE NO LONGER HOLD ");
		}
		
		SeatMapper seatMapper = getVenueSeatDao().getVenueSeatArrangement();
		
		if(!seatHold.getCustomerEmail().equals(customerEmail) ){
			throw new TicketServiceException(" CUSTOMER EMAIL NOT MATCHING ");
		}
		
		try{
			seatMapper.reserveSeats(seatHold.getSeatMapper().getRows(), customerEmail);
		}catch(ReservationException re){
			seatMapper.unHoldSeats(seatHold.getSeatMapper().getRows());
			getVenueSeatDao().removeSeatHold(seatHold.getSeatHoldId());
			throw new TicketServiceException(re);
		}
		
		return getReservationConfirmationCode();
	}
	
	private String getReservationConfirmationCode() {
        return UUID.randomUUID().toString();
    }
	
    private int generateSeatHoldId() {
        return getVenueSeatDao().generateSeatHoldId();
    }
	
	/**
	 * @return the venueSeatDao
	 */
	public VenueSeatDao getVenueSeatDao() {
		return new VenueSeatDaoImpl();
	}

	/**
	 * @param venueSeatDao the venueSeatDao to set
	 */
	public void setVenueSeatDao(VenueSeatDao venueSeatDao) {
		this.venueSeatDao = venueSeatDao;
	}
}
