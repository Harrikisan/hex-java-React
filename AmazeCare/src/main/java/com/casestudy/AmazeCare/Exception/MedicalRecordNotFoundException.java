package com.casestudy.AmazeCare.Exception;

public class MedicalRecordNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 8970457702874200680L;

	private String message;

	public MedicalRecordNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
