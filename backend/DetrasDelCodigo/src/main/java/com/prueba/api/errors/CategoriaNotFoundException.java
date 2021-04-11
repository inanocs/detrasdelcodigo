package com.prueba.api.errors;

public class CategoriaNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaNotFoundException(long id) {
		
		super("La categoria con el id "+id+" no se ha encontrado");
	}

}
