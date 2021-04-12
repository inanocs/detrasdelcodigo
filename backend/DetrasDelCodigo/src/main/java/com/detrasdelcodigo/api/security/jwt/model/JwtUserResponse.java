package com.detrasdelcodigo.api.security.jwt.model;

import java.util.List;

import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.dto.UsuarioDto;
import com.detrasdelcodigo.api.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserResponse extends UsuarioDto {

	
	private String token;

	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(long idusuario, String username, String email, String avatar, String nombre,
			String apellidos, Rol rol, List<PostDto> posts, String token) {
		super(idusuario, username, email, avatar, nombre, apellidos, rol, posts);
		this.token = token;
	}
	
	
}
