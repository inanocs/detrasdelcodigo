package com.detrasdelcodigo.api.dto.converters;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.detrasdelcodigo.api.dto.CrearPostDto;
import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.dto.UpdatePostDto;
import com.detrasdelcodigo.api.model.Categoria;
import com.detrasdelcodigo.api.model.Post;
import com.detrasdelcodigo.api.model.Tag;

@Component
public class PostDtoConverter {
	
//	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private UsuarioDtoConverter userDtoConverter;
	
	@Autowired 
	private Environment env;
	
	public PostDto convertToDto(Post p,boolean wantUserInfo) {
		
		return PostDto.builder()
				.categoria(p.getCategoria())
				.descripcion(p.getDescripcion())
				.createdAt(p.getCreatedAt())
				.idpost(p.getIdpost())
				.contenido(p.getContenido())
				.titulo(p.getTitulo())
				.portada(env.getProperty("app.baseurl")+p.getPortada())
				.tags(p.getTags())
				.usuario(wantUserInfo ? userDtoConverter.convertToDto(p.getUsuario()): null)
				.build();
	}
	
	public Post convertDtoToPost(CrearPostDto postDto) {
		
		return Post.builder()
				.titulo(postDto.getTitulo())
				.categoria(new Categoria(postDto.getIdcategoria()))
				.portada(postDto.getPortada())
				.descripcion(postDto.getDescripcion())
				.tags(
					postDto.getIdTags().stream().map(tag->new Tag(tag)).collect(Collectors.toList()) )
				.contenido(postDto.getContenido())
				.build();
	}
	
	public Post convertDtoToPost(UpdatePostDto postDto) {
		
		return Post.builder()
				.idpost(postDto.getIdpost())
				.titulo(postDto.getTitulo())
				.categoria(new Categoria(postDto.getIdcategoria()))
				.portada(postDto.getPortada())
				.descripcion(postDto.getDescripcion())
				.tags(
					postDto.getIdTags().stream().map(tag->new Tag(tag)).collect(Collectors.toList()) )
				.contenido(postDto.getContenido())
				.build();
	}

}
