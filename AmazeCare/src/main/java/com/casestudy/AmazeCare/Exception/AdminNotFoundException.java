package com.casestudy.AmazeCare.Exception;

public class AdminNotFoundException extends RuntimeException{


	private static final long serialVersionUID = -8232638614251378490L;

	private String message;

	public AdminNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
