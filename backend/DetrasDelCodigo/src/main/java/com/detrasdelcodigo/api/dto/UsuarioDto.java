package com.detrasdelcodigo.api.dto;

import java.util.List;

import com.detrasdelcodigo.api.model.Rol;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

	private long idusuario;
	private String username;
	private String email;
	private String avatar;
	private String nombre;
	private String apellidos;
	private Rol rol;
	
	@JsonBackReference
	private List<PostDto> posts;
	
	
}
