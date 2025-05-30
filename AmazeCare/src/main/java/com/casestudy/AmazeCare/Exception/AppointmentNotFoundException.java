package com.casestudy.AmazeCare.Exception;

public class AppointmentNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -233080753733792246L;

	public String message;

	public AppointmentNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
