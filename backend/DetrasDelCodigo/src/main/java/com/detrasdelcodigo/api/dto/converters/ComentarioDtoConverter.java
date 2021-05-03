package com.detrasdelcodigo.api.dto.converters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.detrasdelcodigo.api.dto.ComentarioDto;
import com.detrasdelcodigo.api.dto.CrearComentarioDto;
import com.detrasdelcodigo.api.model.Comentario;
import com.detrasdelcodigo.api.model.Post;

@Component
public class ComentarioDtoConverter {

	@Autowired
	private UsuarioDtoConverter userConverter;
	
	public ComentarioDto convertToDto(Comentario comentario) {
		
		return ComentarioDto.builder()
				.idcomentario(comentario.getIdcomentario())
				.createdAt(comentario.getCreatedAt())
				.idpost(comentario.getPost().getIdpost())
				.contenido(comentario.getContenido())
				.usuario(userConverter.convertToDto(comentario.getUsuario()))
				.build();
	}
	
	public Comentario convertToComentario(ComentarioDto comentarioDto) {
		
		
		return Comentario.builder()
				.contenido(comentarioDto.getContenido())
				.createdAt(comentarioDto.getCreatedAt())
				.idcomentario(comentarioDto.getIdcomentario())
				.post(new Post(comentarioDto.getIdpost().longValue()))
				.usuario(userConverter.convertToUser(Optional.of(comentarioDto.getUsuario())))
				.build();
	}
	
	public Comentario convertToComentario(CrearComentarioDto comentarioDto) {
		
		return Comentario.builder()
		.post(new Post(comentarioDto.getIdpost()))
		.contenido(comentarioDto.getContenido())
		.build();
	}
}
