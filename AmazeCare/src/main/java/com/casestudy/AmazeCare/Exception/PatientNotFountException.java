package com.casestudy.AmazeCare.Exception;

public class PatientNotFountException extends RuntimeException{

	private static final long serialVersionUID = -7066256570641696380L;

	private String message;

	public PatientNotFountException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
