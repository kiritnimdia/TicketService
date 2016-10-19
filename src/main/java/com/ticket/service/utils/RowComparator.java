package com.ticket.service.utils;

import java.util.Comparator;

import com.ticket.service.model.Row;

public class RowComparator implements Comparator<Row> {

	@Override
	public int compare(Row o1, Row o2) {
		// TODO Auto-generated method stub
		return Integer.compare(o1.getRowOrder(), o2.getRowOrder());
	}

}
