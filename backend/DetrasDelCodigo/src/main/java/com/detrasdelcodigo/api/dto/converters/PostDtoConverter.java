package com.detrasdelcodigo.api.dto.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.model.Post;

@Component
public class PostDtoConverter {
	
//	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private UsuarioDtoConverter userDtoConverter;
	
	public PostDto convertToDto(Post p,boolean wantUserInfo) {
		
		return PostDto.builder()
				.categoria(p.getCategoria())
				.descripcion(p.getDescripcion())
				.createdAt(p.getCreatedAt())
				.idpost(p.getIdpost())
				.contenido(p.getContenido())
				.titulo(p.getTitulo())
				.portada(p.getPortada())
				.usuario(wantUserInfo ? userDtoConverter.convertToDto(p.getUsuario(),false): null)
				.build();
	}

}
