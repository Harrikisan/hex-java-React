package com.casestudy.AmazeCare.Exception;

public class WardNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -8738897151706680295L;
	
	private String message;

	public WardNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	

}
