package com.detrasdelcodigo.api.dto;


import com.detrasdelcodigo.api.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDto {
	
	private Long idusuario;
	private String username;
	private String email;
	private String password;
	private String nombre;
	private String apellidos;
	private Rol rol;

}
