package com.prueba.api.errors;

public class CategoriaExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public CategoriaExistsException(String msj) {
		
		super(msj);
	}

	
	
}
