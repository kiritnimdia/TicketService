/**
 * 
 */
package com.ticket.service.exception;

/**
 * @author knimdi001c
 *
 */
public class ReservationException extends Exception {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ReservationException(String message) {
	    super(message);
	}
	
	public ReservationException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	public ReservationException(Throwable cause) {
		super(cause);
	}
}
