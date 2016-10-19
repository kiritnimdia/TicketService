package com.ticket.service.model;

import com.ticket.service.utils.Status;

public class Seat {

	private Status seatStatus = Status.AVAILABLE;
	private int seatId;
	private String seatNumber;
	private long heldTime;
	
	public Seat(int id, String seatNumber){
		this.seatId = id;
	}
	
	/**
	 * @return the seatId
	 */
	public int getSeatId() {
		return seatId;
	}

	/**
	 * @return the seatStatus
	 */
	public Status getSeatStatus() {
		return seatStatus;
	}

	/**
	 * @param seatStatus the seatStatus to set
	 */
	public void setSeatStatus(Status seatStatus) {
		this.seatStatus = seatStatus;
	}

	/**
	 * @return the seatNumber
	 */
	public String getSeatNumber() {
		return seatNumber;
	}

	/**
	 * @param seatNumber the seatNumber to set
	 */
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * @return the heldTime
	 */
	public long getHeldTime() {
		return heldTime;
	}

	/**
	 * @param heldTime the heldTime to set
	 */
	public void setHeldTime(long heldTime) {
		this.heldTime = heldTime;
	}
	
	
}
