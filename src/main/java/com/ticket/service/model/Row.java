/**
 * 
 */
package com.ticket.service.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.ticket.service.exception.ReservationException;
import com.ticket.service.utils.Status;
import com.ticket.service.utils.TicketServiceConstant;
import com.ticket.service.utils.TicketServiceProperties;

/**
 * @author knimdi001c
 *
 */
public class Row {

	//It will also act as rowNumber. We can have seperate Row Number as well.
	//For simplicity taking rowId = rowNumber.
	private int rowId;
	
	private String rowNumber;
	private int rowOrder;
	
	//List of seats in a row.
	private List<Seat> seatsInRow;
	private AtomicInteger availableSeats;

	
	public Row(int rowId, String rowNumber, int rowOrder, List<Seat> seatsInRow){
		this.rowId = rowId;
		this.rowNumber = rowNumber;
		this.rowOrder = rowOrder;
		this.seatsInRow = seatsInRow;
		availableSeats = new AtomicInteger(this.seatsInRow.size());

	}
	
	
	public Row(int rowId, List<Seat> seatsInRow){
		this.rowId = rowId;
		this.seatsInRow = seatsInRow;
	}

	public int getSeatCount(){		
		return ( seatsInRow != null  && !seatsInRow.isEmpty() )? seatsInRow.size() : 0;
	}
	
	/**
	 * @return the rowId
	 */
	public int getRowId() {
		return rowId;
	}

	/**
	 * @return the seatsInRow
	 */
	public List<Seat> getSeatsInRow() {
		return seatsInRow;
	}

	/**
	 * @return the availableSeats
	 */
	public int getAvailableSeatCount() {
		return availableSeats.get();
	}

	/**
	 * @param availableSeats the availableSeats to set
	 */
	public void setAvailableSeatCount(AtomicInteger availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	public List<Seat> getAvailableSeatsInRow(){
		List<Seat> availableSeats = null;
		
		if(this.availableSeats.get() != 0){
			availableSeats = new CopyOnWriteArrayList<Seat>();
			int seatSize = seatsInRow.size();
			for(int i=0 ; i<seatSize; i++ ){
				if(seatsInRow.get(i).getSeatStatus() == Status.AVAILABLE){
					availableSeats.add(seatsInRow.get(i));
				}
			}
		}		
		return availableSeats;
	}

	public void unHoldHeldSeats(){
		int holdTimeInSec = Integer.parseInt(TicketServiceProperties.getInstance().getPropertyValue(TicketServiceConstant.SEATS_HOLD_TIME));
		int seatCount = getSeatsInRow().size();
		for(int k=0; k< seatCount ; k++ ) {
			Seat seat = getSeatsInRow().get(k); 
			if((seat.getSeatStatus() == Status.HELD || seat.getSeatStatus() == Status.RESERVED)&& ((System.currentTimeMillis() - seat.getHeldTime())/1000) >= holdTimeInSec){
				seat.setSeatStatus(Status.AVAILABLE);
				seat.setHeldTime(0L);
				this.availableSeats.getAndIncrement();
			}
			
		}
	}
	
	public List<Seat> holdAvailableSeats(int seatCountToHold){
		List<Seat> holdSeat = null ;
		if(getAvailableSeatCount() > 0){
			holdSeat = new CopyOnWriteArrayList<Seat>();
			int seatCount = getSeatsInRow().size();
			for(int k=0; k< seatCount && seatCountToHold > 0; k++ ){
				Seat seat = getSeatsInRow().get(k);
				if(seat.getSeatStatus() == Status.AVAILABLE){
					seat.setSeatStatus(Status.HELD);
					seat.setHeldTime(System.currentTimeMillis());
					this.availableSeats.decrementAndGet();
					holdSeat.add(seat);
					seatCountToHold--;
				}
				
			}
		}
		return holdSeat;
	}
	
	public void reserveSeat(List<Seat> seatToReserveList) throws ReservationException{
		int holdTimeInSec = Integer.parseInt(TicketServiceProperties.getInstance().getPropertyValue(TicketServiceConstant.SEATS_HOLD_TIME)); 
		for(Seat seat : seatToReserveList){
			Seat seatToReserve = getSeatToReserve(seat);
			if(seatToReserve != null){
				
				if(seatToReserve.getSeatStatus() != Status.RESERVED && seatToReserve.getSeatStatus() != Status.AVAILABLE 
						&& seatToReserve.getSeatStatus() == Status.HELD && (((System.currentTimeMillis() - seat.getHeldTime())/1000) < holdTimeInSec)){
					seatToReserve.setSeatStatus(Status.RESERVED );
				} else {
					throw new ReservationException(" SEAT NOT AVAILABLE FOR RESERVATION ");
				}
				
			} else {
				throw new ReservationException(" SEAT TO RESERVE NOT FOUND ");
			}
		}
	}
	
	private Seat getSeatToReserve(Seat seat){
		Seat seatInRow = null;
		int seatCount = getSeatsInRow().size();
		for(int k=0; k< seatCount ; k++ ){
			seatInRow = getSeatsInRow().get(k);
			if(seatInRow.getSeatId() == seat.getSeatId()){
				return seatInRow;
			}
		}
		return null;
	}
	
	/**
	 * @return the rowNumber
	 */
	public String getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the rowOrder
	 */
	public int getRowOrder() {
		return rowOrder;
	}

	/**
	 * @param rowOrder the rowOrder to set
	 */
	public void setRowOrder(int rowOrder) {
		this.rowOrder = rowOrder;
	}
	
}
