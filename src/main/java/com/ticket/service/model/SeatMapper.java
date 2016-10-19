/**
 * 
 */
package com.ticket.service.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ticket.service.exception.ReservationException;
import com.ticket.service.utils.RowComparator;

/**
 * @author knimdi001c
 *
 */
public class SeatMapper {

	private Set<Row> rows; 
	
	public SeatMapper(){}

	/**
	 * @return the rows
	 */
	public Set<Row> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Set<Row> rows) {
		this.rows = rows;
	}
	
	public int getAvailableSeatCount(){
		int count=0;
		for (Row row : this.rows) {
			row.unHoldHeldSeats();
			count += row.getAvailableSeatCount();
		}
		return count;
	}

	public int getSeatCount(){
		int count=0;
		for (Row row : this.rows) {
			count += row.getSeatCount();
		}
		return count;
	}
	
	public Set<Row> findAndHoldSeat(int numSeats){
		Set<Row> rowMapper = new TreeSet<Row>(new RowComparator());
		Row currentRow = null;
		for (Row row : this.rows) {
			if( numSeats > 0){
				row.unHoldHeldSeats();
				if(row.getAvailableSeatCount() > 0) {
					int seatToHold =  numSeats > row.getAvailableSeatCount() ? row.getAvailableSeatCount() : numSeats;
					List<Seat> holdSeats = row.holdAvailableSeats(seatToHold);
					if(holdSeats != null && !holdSeats.isEmpty()){
						numSeats = numSeats - holdSeats.size();
					}
					currentRow = new Row(row.getRowId(), holdSeats);
					currentRow.setRowNumber(row.getRowNumber());
					currentRow.setRowOrder(row.getRowOrder());
					rowMapper.add(currentRow);
				}	
			}
		}
		return rowMapper;
	}
	
	public String reserveSeats(Set<Row> rowsHold, String customerEmail) throws ReservationException{
		for (Row row : this.rows) {
			row.unHoldHeldSeats();
			Row holdRow = getRow(rowsHold, row);
			if(holdRow != null){
				try{
					row.reserveSeat(holdRow.getSeatsInRow());
				}catch(ReservationException re){					
					throw new ReservationException(re); 
				}
			}
		}
		return null;
	}
	
	private Row getRow(Set<Row> rowsHold, Row row){
		for (Row rowHold : rowsHold) {
			if(rowHold.getRowId() == row.getRowId())
				return rowHold;
		}
		return null;
	}
	
	public void unHoldSeats(Set<Row> rowsToUnhold){
		for (Row row : this.rows) {
			Row held = getRow(rowsToUnhold, row);
			if(held != null){
				row.unHoldHeldSeats();
			}
		}
	}
}
