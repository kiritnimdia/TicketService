/**
 * 
 */
package com.ticket.service.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.ticket.service.model.Row;
import com.ticket.service.model.Seat;
import com.ticket.service.model.SeatHold;
import com.ticket.service.model.SeatMapper;

/**
 * @author knimdi001c
 *
 */
public class TicketServiceUtils {
	
	private static volatile TicketServiceUtils utils = new TicketServiceUtils();
	private SeatMapper venueSeatMapper;
	private Set<Integer> seatHoldIdSet = new HashSet<Integer>();
	private AtomicInteger seatHoldId = new AtomicInteger(1);
	
	private Map<Integer, SeatHold> seatHoldMapper;
	
	private TicketServiceUtils(){
		intializeSeats();
	}
	
	public void reinitialize(){
		intializeSeats();
	}
	
	public static TicketServiceUtils getInstance(){
		return utils;
	}
	
	public void intializeSeats(){
		seatHoldMapper = new HashMap<Integer, SeatHold>();
		venueSeatMapper = new SeatMapper();
		// This can be configured. Can read from properties File
		int rows = Integer.parseInt(TicketServiceProperties.getInstance().getPropertyValue(TicketServiceConstant.NUMBER_OF_ROWS));
		int seatsPerRow = Integer.parseInt(TicketServiceProperties.getInstance().getPropertyValue(TicketServiceConstant.SEATS_PER_ROW));;
		Seat seat;
		Row row;
		List<Seat> seatsInRow;
		Set<Row> rowMapper = new TreeSet<Row>(new RowComparator());
		for(int i=0; i<rows; i++ ){
			seatsInRow = new CopyOnWriteArrayList<Seat>();
			for(int k=0; k<seatsPerRow; k++ ){
				seat = new Seat(k, "Seat Number "+k);
				seatsInRow.add(seat);
			}
			//SeatHold time Get it from properties file
			row = new Row(i, "Row "+i , i ,seatsInRow);
			rowMapper.add(row);
		}
		venueSeatMapper.setRows(rowMapper);
		
	}
	
	public SeatMapper getVenueSeatMapper(){
		if(venueSeatMapper == null){
			intializeSeats();
		}
		return venueSeatMapper;
	}
	
	public void updateSeatHoldMapper(SeatHold seatHold){
		seatHoldMapper.put(seatHold.getSeatHoldId(), seatHold);
	}
	
	
	public SeatHold getSeatHold(int seatHoldId){
		return seatHoldMapper.get(seatHoldId);
	}
	
	public int generateSeatHoldId(){
		int a = seatHoldId.getAndIncrement();
		if(!seatHoldIdSet.contains(a)){
			seatHoldIdSet.add(a);
			return a;
		} 
		return generateSeatHoldId();
	}
	
	public void removeSeatHold(int seatHoldId){
		seatHoldMapper.remove(new Integer(seatHoldId));		
	}
}
