package com.casestudy.AmazeCare.Exception;

public class BedNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 7598445893236428845L;

	private String message;

	public BedNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
