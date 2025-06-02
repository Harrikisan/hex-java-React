package com.casestudy.AmazeCare.Exception;

public class TestNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3458693227745937249L;
	
	private String message;

	public TestNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
