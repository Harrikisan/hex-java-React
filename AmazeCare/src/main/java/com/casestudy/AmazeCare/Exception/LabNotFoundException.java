package com.casestudy.AmazeCare.Exception;

public class LabNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String message;

	public LabNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
