package com.casestudy.AmazeCare.Exception;

public class NurseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -923762145834460412L;

	private String message;

	public NurseNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
