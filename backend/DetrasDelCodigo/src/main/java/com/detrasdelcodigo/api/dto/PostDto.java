package com.detrasdelcodigo.api.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.detrasdelcodigo.api.model.Categoria;
import com.detrasdelcodigo.api.model.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private long idpost;

	private String contenido;

	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime createdAt;

	private String descripcion;

	private String portada;

	private String titulo;

	private Categoria categoria;
	
	@JsonManagedReference
	private UsuarioDto usuario;
	
	private List<Tag> tags;

	private List<ComentarioDto> comentarios;
}
