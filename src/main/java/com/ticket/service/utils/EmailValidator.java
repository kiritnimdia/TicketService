package com.ticket.service.utils;

import java.util.regex.Pattern;

public class EmailValidator {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	public static boolean isValidEmail(String email){
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			return false;
		}
		return true;
	}
	
}
