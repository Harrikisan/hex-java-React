package com.casestudy.AmazeCare.Exception;

public class DoctorScheduleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 4093227861471294631L;

	private String message;

	public DoctorScheduleNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
