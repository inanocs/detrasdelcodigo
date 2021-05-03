package com.detrasdelcodigo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditarUsuarioDataDto {
	
	private String email;
	private String nombre;
	private String apellidos;
}
