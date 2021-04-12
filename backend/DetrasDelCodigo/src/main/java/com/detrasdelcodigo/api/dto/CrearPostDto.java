package com.detrasdelcodigo.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearPostDto {
	
	private String contenido;

	private String descripcion;

	private String portada;

	private String titulo;

	private long idcategoria;
	
	private List<Long> idTags;
	

}
