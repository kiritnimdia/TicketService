package com.ticket.service.utils;

import java.io.IOException;
import java.util.Properties;

public class TicketServiceProperties {
	private static volatile TicketServiceProperties ticketServiceProp = new TicketServiceProperties();
	private Properties ticketServiceProperties = new Properties();
	
	private TicketServiceProperties(){
		init();
	}
	
	public static TicketServiceProperties getInstance(){
		return ticketServiceProp;
	}
	
	private void init(){
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			ticketServiceProperties.load(classLoader.getResourceAsStream("ticketservice.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
	} 
	
	public String getPropertyValue(String key){
		return ticketServiceProperties.getProperty(key);
	}
}
