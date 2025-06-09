package com.hex.CodingChallenge.Exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -4887738562002560848L;

	private String message;

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	
}
