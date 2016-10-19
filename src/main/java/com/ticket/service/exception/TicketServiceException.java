package com.ticket.service.exception;

public class TicketServiceException extends RuntimeException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TicketServiceException(String message) {
	    super(message);
	}
	
	public TicketServiceException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	public TicketServiceException(Throwable cause) {
		super(cause);
	}
}
