package com.detrasdelcodigo.api.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.prueba.api.errors.ApiError;
import com.prueba.api.errors.CategoriaExistsException;
import com.prueba.api.errors.CategoriaNotFoundException;
import com.prueba.api.errors.PostNotFoundException;
import com.prueba.api.errors.ProductoNotFoundException;
import com.prueba.api.errors.UsuarioExisteException;
import com.prueba.api.errors.UsuariosException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProductoNotFoundException.class)
	public ResponseEntity<ApiError> handleProductoNoEncontrado(ProductoNotFoundException e) {

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, e.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ApiError> handlePostNoEncontrado(PostNotFoundException e) {

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, e.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(UsuarioExisteException.class)
	public ResponseEntity<ApiError> handleUsuarioExisteException(UsuarioExisteException e) {

		ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT, e.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
	
	@ExceptionHandler(UsuariosException.class)
	public ResponseEntity<ApiError> handleUsuariosException(UsuariosException e) {

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, e.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

//	Ya no nos haría falta puesto que heredamos de la clase abstracta ResponseEntityExceptionHandler
	/*
	 * @ExceptionHandler(JsonMappingException.class) public ResponseEntity<ApiError>
	 * handleJsonMappingException(JsonMappingException ex) {
	 * 
	 * ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
	 * 
	 * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError); }
	 */
	@ExceptionHandler(CategoriaNotFoundException.class)
	public ResponseEntity<ApiError> handleCategoriaNotFoundException(CategoriaNotFoundException e) {

		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, e.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(CategoriaExistsException.class)
	public ResponseEntity<ApiError> handleCategoriaExistsException(CategoriaExistsException e) {

		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, e.getMessage());

		return ResponseEntity.badRequest().body(error);
	}
	
	
//	Util para errores comunes
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ApiError error = new ApiError(status,ex.getMessage());
		
		return ResponseEntity.status(status).headers(headers).body(error);
	}

}
