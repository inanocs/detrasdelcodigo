package com.detrasdelcodigo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.dto.CrearComentarioDto;
import com.detrasdelcodigo.api.dto.converters.ComentarioDtoConverter;
import com.detrasdelcodigo.api.model.Comentario;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.services.ComentarioService;
import com.prueba.api.errors.UsuarioExisteException;

import lombok.extern.java.Log;

@Log
@RequestMapping("/comentarios/")
@RestController
public class ComentariosController {

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private ComentarioDtoConverter comentarioConverter;


	@GetMapping("/{idcomentario}")
	public ResponseEntity<?> getComentarioById(@PathVariable Long idcomentario) {

		return ResponseEntity.ok(comentarioConverter.convertToDto(comentarioService.findById(idcomentario)
				.orElseThrow(() -> new UsuarioExisteException("El post no existe"))));
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/")
	public ResponseEntity<?> publicarComentario(@AuthenticationPrincipal Usuario usuario,
			@RequestBody CrearComentarioDto comentarioDto) {

		
		log.info("Contenido: " + comentarioDto.getContenido());
		log.info("Post: " + comentarioDto.getIdpost());
		
		
		Comentario comentarioConvertido = comentarioConverter.convertToComentario(comentarioDto);
		comentarioConvertido.setUsuario(usuario);
		
		Comentario comentarioCreado = comentarioService
				.crearComentario(comentarioConvertido);

		return ResponseEntity.status(HttpStatus.CREATED).body(comentarioConverter.convertToDto(comentarioCreado));
	}
}
