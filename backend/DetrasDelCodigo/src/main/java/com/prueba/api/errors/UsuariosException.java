package com.prueba.api.errors;

public class UsuariosException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public UsuariosException(String msj) {
		super(msj);
	}
	
	public UsuariosException() {
		super();
	}
}
