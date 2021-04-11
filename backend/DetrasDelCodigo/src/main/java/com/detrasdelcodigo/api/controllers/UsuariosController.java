package com.detrasdelcodigo.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.dto.CrearUsuarioDto;
import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.dto.UsuarioDto;
import com.detrasdelcodigo.api.dto.converters.PostDtoConverter;
import com.detrasdelcodigo.api.dto.converters.UsuarioDtoConverter;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.services.UsuarioService;
import com.prueba.api.errors.UsuariosException;

@RequestMapping("/users/")
@RestController
public class UsuariosController {

	@Autowired
	private UsuarioService userService;
	@Autowired
	private UsuarioDtoConverter userConverter;

	@Autowired
	private PostDtoConverter postConverter;

	@GetMapping("/")
	public ResponseEntity<?> getAllUsers() {

		List<UsuarioDto> usuarios = userService.findAll().stream().map(user -> userConverter.convertToDto(user, true))
				.collect(Collectors.toList());

		if (usuarios == null || usuarios.isEmpty())
			throw new UsuariosException("No hay usuarios disponibles");

		return ResponseEntity.ok(usuarios);

	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getByUsername(@PathVariable String username) {

		UsuarioDto usuario = userConverter.convertToDto(userService.findByUsername(username), true);
		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/{username}/posts")
	public ResponseEntity<?> getPostsByUsername(@PathVariable String username) {

		List<PostDto> posts = userService.findByUsername(username).getPosts().stream()
				.map(post -> postConverter.convertToDto(post, true)).collect(Collectors.toList());

		
		return ResponseEntity.ok(posts);
	}

	@PostMapping("/")
	public ResponseEntity<?> createUser(@RequestBody CrearUsuarioDto nuevoUsuario) {

		Usuario usuarioACrear = userService.save(userConverter.convertToUser(Optional.of(nuevoUsuario)));

		return ResponseEntity.status(HttpStatus.CREATED).body(userConverter.convertToDto(usuarioACrear, false));

	}

	@PutMapping("/{username}")
	public ResponseEntity<?> EditUser(@PathVariable String username, @RequestBody CrearUsuarioDto usuario) {

		if (usuario.getIdusuario() == null) {
			throw new UsuariosException("El objeto debe llevar el id del usuario");
		}

		userService.edit(userConverter.convertToUser(Optional.of(usuario)));

		Usuario user = userService.findByUsername(username);

		return ResponseEntity.ok().body(userConverter.convertToDto(user, true));

	}

}
