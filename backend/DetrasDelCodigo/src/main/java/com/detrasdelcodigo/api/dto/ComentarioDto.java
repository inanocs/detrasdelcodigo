package com.detrasdelcodigo.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDto {
	
	private Long idcomentario;

	private String contenido;
	
	private LocalDateTime createdAt;
	
	private Long post;
	
	private UsuarioDto usuario;

}
