package com.prueba.api.errors;

public class PostNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PostNotFoundException(String msj) {
		
		super(msj);
	}
	
	public PostNotFoundException() {
		super();
	}
}
