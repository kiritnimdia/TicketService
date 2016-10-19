/**
 * 
 */
package com.ticket.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.ticket.service.exception.TicketServiceException;
import com.ticket.service.model.SeatHold;
import com.ticket.service.utils.TicketServiceUtils;

/**
 * @author knimdi001c
 *
 */
@PrepareForTest(TicketServiceImpl.class)
public class TicketServiceReserveTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testReserveSeats_HappyPath(){
		
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		
		assertEquals(95,ticketService.numSeatsAvailable());
		
		String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		
		assertEquals(95,ticketService.numSeatsAvailable());
		
		assertNotNull(reservationCode);
		System.out.println("testReserveSeats_HappyPath Passed");
	}

	
	@Test
	public void testReserveSeats_CustomerEmailNotMatching(){
		
		thrown.expect(TicketServiceException.class);
        thrown.expectMessage(containsString("CUSTOMER EMAIL NOT MATCHING"));
        
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		
		
		assertEquals(95,ticketService.numSeatsAvailable());
		
		String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kirit@gmail.com");
		System.out.println("testReserveSeats_CustomerEmailNotMatching Passed");
		assertNull(reservationCode);
		
	}
	
	
	@Test
	public void testReserveSeats_InvalidEmail(){
		thrown.expect(TicketServiceException.class);
        thrown.expectMessage(containsString("Customer Email is not Valid"));
        
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		
		assertEquals(95,ticketService.numSeatsAvailable());
				
		ticketService.reserveSeats(seatHold.getSeatHoldId(), "kirit@jfjf");
		System.out.println("testReserveSeats_InvalidEmail Passed");
	}
	
	
	@Test
	public void testReserveSeats_SeatExpires(){
		
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		
		assertEquals(95,ticketService.numSeatsAvailable());
		
		try{
			Thread.sleep(7000);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		}catch(TicketServiceException ex){
			
		}
		assertEquals(100,ticketService.numSeatsAvailable());
		System.out.println("testReserveSeats_SeatExpires Passed");
	}
	
	@Test
	public void testReserveSeats_SeatExpiresException(){
		thrown.expect(TicketServiceException.class);
        thrown.expectMessage(containsString("SEAT NOT AVAILABLE FOR RESERVATION"));
      
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		
		assertEquals(95,ticketService.numSeatsAvailable());
		
		try{
			Thread.sleep(7000);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		
		System.out.println("testReserveSeats_SeatExpiresException Passed");
	}
	
	
	@Test
	public void testReserveSeats_SeatHoldIdExpires(){
		thrown.expect(TicketServiceException.class);
        thrown.expectMessage(containsString("SEATS ARE NO LONGER HOLD"));
        
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		assertEquals(95,ticketService.numSeatsAvailable());
		
		try{
			Thread.sleep(7000);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		}catch(TicketServiceException ex){			
			assertEquals(100,ticketService.numSeatsAvailable());
		}
		
		ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		
		System.out.println("testReserveSeats_SeatHoldIdExpires Passed");
	}
	
	
	@Test
	public void testReserveSeats_EndToEnd(){
		
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = null;
		
		seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		assertEquals(95,ticketService.numSeatsAvailable());
		
		String reservationCode = null;
		reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		
		assertEquals(95,ticketService.numSeatsAvailable());
		
		assertNotNull(reservationCode);
		
		//-----------------------------------------------------------------------
		
		
		seatHold = ticketService.findAndHoldSeats(90, "kiritnimdia@gmail.com");		
		assertEquals(90, seatHold.getSeatMapper().getSeatCount());		
		assertEquals(5,ticketService.numSeatsAvailable());
		reservationCode = null;
		reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		
		assertEquals(5,ticketService.numSeatsAvailable());
		assertNotNull(reservationCode);
		
		//-----------------------------------------------------------------------
		
		
		seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		assertEquals(0,ticketService.numSeatsAvailable());
		reservationCode = null;
		reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), "kiritnimdia@gmail.com");
		
		assertEquals(0,ticketService.numSeatsAvailable());
		assertNotNull(reservationCode);
		System.out.println("testReserveSeats_EndToEnd Passed");
	}
	
}
