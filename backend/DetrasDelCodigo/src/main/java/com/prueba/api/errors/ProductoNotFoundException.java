package com.prueba.api.errors;

public class ProductoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductoNotFoundException(long id) {
		
		super("El producto con el id "+id+" no se ha encontrado");
	}
	
}
