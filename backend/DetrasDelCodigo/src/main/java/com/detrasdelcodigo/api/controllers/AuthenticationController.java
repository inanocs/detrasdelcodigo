package com.detrasdelcodigo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.dto.converters.UsuarioDtoConverter;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.security.jwt.JwtProvider;
import com.detrasdelcodigo.api.security.jwt.model.JwtUserResponse;
import com.detrasdelcodigo.api.security.jwt.model.LoginRequest;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private UsuarioDtoConverter usuarioConverter;
	
	@PostMapping("/auth/login")
	public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		String jwtToken = tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(usuarioConverter.convertUserEntityAndTokenToJwtUserResponse(usuario,jwtToken));
	}

	
	
	
}
