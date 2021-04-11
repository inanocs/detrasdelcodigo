package com.prueba.api.errors;

public class UsuarioExisteException extends UsuariosException{

	public UsuarioExisteException() {
		super();
	}
	
	public UsuarioExisteException(String msj) {
		super(msj);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1852322669928089317L;

}
