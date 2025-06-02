package com.casestudy.AmazeCare.Exception;

public class TestScheduleNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -6587999231139691264L;

	private String message;

	public TestScheduleNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
