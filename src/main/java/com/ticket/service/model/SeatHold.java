package com.ticket.service.model;

public class SeatHold {
	
	private int seatHoldId;
	private SeatMapper seatMapper;
	private String customerEmail;
	private int seatRequested;

	public SeatHold(int seatHoldId, SeatMapper seatMapper, String customerEmail){
		this.seatHoldId = seatHoldId;
		this.seatMapper = seatMapper;
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the seatHoldId
	 */
	public int getSeatHoldId() {
		return seatHoldId;
	}

	/**
	 * @param seatHoldId the seatHoldId to set
	 */
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	/**
	 * @return the seatMapper
	 */
	public SeatMapper getSeatMapper() {
		return seatMapper;
	}

	/**
	 * @param seatMapper the seatMapper to set
	 */
	public void setSeatMapper(SeatMapper seatMapper) {
		this.seatMapper = seatMapper;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the seatRequested
	 */
	public int getSeatRequested() {
		return seatRequested;
	}

	/**
	 * @param seatRequested the seatRequested to set
	 */
	public void setSeatRequested(int seatRequested) {
		this.seatRequested = seatRequested;
	}
	
	
}
