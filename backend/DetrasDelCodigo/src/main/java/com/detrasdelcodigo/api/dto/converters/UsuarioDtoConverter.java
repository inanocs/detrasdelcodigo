package com.detrasdelcodigo.api.dto.converters;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.detrasdelcodigo.api.dto.UsuarioDto;
import com.detrasdelcodigo.api.model.Usuario;

@Component
public class UsuarioDtoConverter {

	@Autowired
	private PostDtoConverter postConverter;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Environment env;

	public UsuarioDto convertToDto(Usuario u, boolean wantPostInfo) {

		return UsuarioDto.builder().idusuario(u.getIdusuario()).username(u.getUsername()).apellidos(u.getApellidos())
				.nombre(u.getNombre()).email(u.getEmail()).posts(wantPostInfo ? u.getPosts().stream()
						.map(post -> postConverter.convertToDto(post, false)).collect(Collectors.toList()) : null)
				.avatar(u.getAvatar()!=null ? env.getProperty("app.baseurl")+u.getAvatar() : u.getAvatar())
				.rol(u.getRol()).build();

	}

	public Usuario convertToUser(Optional<?> user) {

		return modelMapper.map(user.get(), Usuario.class);
	}
}
