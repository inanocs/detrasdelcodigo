package com.detrasdelcodigo.api.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UsuarioService userEntityService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userEntityService.findUsuarioByUsernameOpt(username)
					.orElseThrow(()-> new UsernameNotFoundException(username + " no encontrado"));
	}
	
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		return userEntityService.findById(id)
				.orElseThrow(()-> new UsernameNotFoundException("Usuario con ID: " + id + " no encontrado"));
		
	}

}