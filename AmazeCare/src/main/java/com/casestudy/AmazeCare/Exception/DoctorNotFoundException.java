package com.casestudy.AmazeCare.Exception;

public class DoctorNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7788963963175586082L;

	private String message;

	public DoctorNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
