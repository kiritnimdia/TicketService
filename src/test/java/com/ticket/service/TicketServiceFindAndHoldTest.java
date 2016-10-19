package com.ticket.service;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.ticket.service.exception.TicketServiceException;
import com.ticket.service.model.Row;
import com.ticket.service.model.SeatHold;
import com.ticket.service.model.SeatMapper;
import com.ticket.service.utils.TicketServiceUtils;

import static org.hamcrest.core.StringContains.containsString;

@PrepareForTest(TicketServiceImpl.class)
public class TicketServiceFindAndHoldTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testNumSeatsAvailable() {
		TicketServiceUtils.getInstance().reinitialize();
		SeatMapper seatMapper = TicketServiceUtils.getInstance().getVenueSeatMapper();
		TicketService ticketService = new TicketServiceImpl(); 
		
		//Number of rows
		assertEquals(10, seatMapper.getRows().size());
		
		//Number of seats per row
		for(Row row : seatMapper.getRows()){
			assertEquals(10,row.getSeatsInRow().size());
		}
		
		//Total Seats Available. Base condition test
		assertEquals(100,seatMapper.getAvailableSeatCount());
		
		assertEquals(seatMapper.getAvailableSeatCount(),ticketService.numSeatsAvailable());
		
	}

	@Test
	public void testFindAndHoldSeatHappyPath(){
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(5, "kiritnimdia@gmail.com");
		assertEquals(5, seatHold.getSeatMapper().getSeatCount());
		assertEquals(95,ticketService.numSeatsAvailable());
		System.out.println("testFindAndHoldSeatHappyPath Passed");		
	}
	
	@Test
	public void testFindAndHoldSeatBoundayCondition(){
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(100, "kiritnimdia@gmail.com");
		assertEquals(100, seatHold.getSeatMapper().getSeatCount());
		assertEquals(0,ticketService.numSeatsAvailable());
		System.out.println("testFindAndHoldSeatBoundayCondition Passed");
	}
	
	@Test
	public void testFindAndHoldSeat_NoSeatAvailable(){
		
		thrown.expect(TicketServiceException.class);
        thrown.expectMessage(containsString("NO SEATS AVAILABLE"));
		
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(100, "kiritnimdia@gmail.com");
		assertEquals(100, seatHold.getSeatMapper().getSeatCount());
		assertEquals(0,ticketService.numSeatsAvailable());
		ticketService.findAndHoldSeats(1, "kiritnimdia@gmail.com");
		System.out.println("testFindAndHoldSeat_NoSeatAvailable FAILED");
	}
	
	@Test
	public void testFindAndHoldSeat_MoreSeatsToHold(){
		
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(90, "kiritnimdia@gmail.com");
		assertEquals(90, seatHold.getSeatMapper().getSeatCount());
		assertEquals(10,ticketService.numSeatsAvailable());
		SeatHold seatHold1 = ticketService.findAndHoldSeats(20, "kiritnimdia@gmail.com");
		assertEquals(10, seatHold1.getSeatMapper().getSeatCount());
		assertEquals(0,ticketService.numSeatsAvailable());
		System.out.println("testFindAndHoldSeat_MoreSeatsToHold Passed");
	}
	
	@Test
	public void testFindAndHoldSeat_SeatTimeOut(){
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(100, "kiritnimdia@gmail.com");
		assertEquals(100, seatHold.getSeatMapper().getSeatCount());
		try{
			Thread.sleep(7000);
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(100,ticketService.numSeatsAvailable());
		System.out.println("testFindAndHoldSeat_SeatTimeOut Passed");
	}
	
	@Test
	public void testFindAndHoldSeat_InvalidEmail(){
		thrown.expect(TicketServiceException.class);
        thrown.expectMessage(containsString("Customer Email is not Valid"));
        
		TicketServiceUtils.getInstance().reinitialize();
		TicketService ticketService = new TicketServiceImpl(); 
		SeatHold seatHold = ticketService.findAndHoldSeats(100, "xcbd@ggg");
		
		assertEquals(100, seatHold.getSeatMapper().getSeatCount());
		System.out.println("testFindAndHoldSeat_SeatTimeOut FAILED");
		
	}
	
}
