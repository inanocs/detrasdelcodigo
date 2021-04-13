package com.detrasdelcodigo.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.detrasdelcodigo.api.dto.CrearPostDto;
import com.detrasdelcodigo.api.dto.CrearUsuarioDto;
import com.detrasdelcodigo.api.dto.PostDto;
import com.detrasdelcodigo.api.dto.UpdatePostDto;
import com.detrasdelcodigo.api.dto.UsuarioDto;
import com.detrasdelcodigo.api.dto.converters.PostDtoConverter;
import com.detrasdelcodigo.api.dto.converters.UsuarioDtoConverter;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.services.CategoriaService;
import com.detrasdelcodigo.api.services.PostService;
import com.detrasdelcodigo.api.services.UsuarioService;
import com.detrasdelcodigo.api.upload.StorageService;
import com.prueba.api.errors.PostNotFoundException;
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

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private PostService postService;

	@GetMapping("/")
	public ResponseEntity<?> getAllUsers() {

		List<UsuarioDto> usuarios = userService.findAll().stream().map(user -> userConverter.convertToDto(user))
				.collect(Collectors.toList());

		if (usuarios == null || usuarios.isEmpty())
			throw new UsuariosException("No hay usuarios disponibles");

		return ResponseEntity.ok(usuarios);

	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getByUsername(@PathVariable String username) {

		UsuarioDto usuario = userConverter.convertToDto(userService.findUsuarioByUsernameOpt(username)
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario " + username)));
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

		Usuario usuarioACrear = userService.nuevoUsuario(userConverter.convertToUser(Optional.of(nuevoUsuario)));

		return ResponseEntity.status(HttpStatus.CREATED).body(userConverter.convertToDto(usuarioACrear));

	}

	@PutMapping("/{username}")
	public ResponseEntity<?> EditUser(@PathVariable String username, @RequestBody CrearUsuarioDto usuario) {

		if (usuario.getIdusuario() == null) {
			throw new UsuariosException("El objeto debe llevar el id del usuario");
		}

		userService.edit(userConverter.convertToUser(Optional.of(usuario)));

		Usuario user = userService.findByUsername(username);

		return ResponseEntity.ok().body(userConverter.convertToDto(user));

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/me")
	public UsuarioDto me(@AuthenticationPrincipal Usuario usuario) {

		return userConverter.convertToDto(usuario);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/me/posts")
	public ResponseEntity<?> getPostsByUsernameLoged(@AuthenticationPrincipal Usuario usuario,
			@RequestParam(name = "titulo", defaultValue = "%%") String titulo,
			@RequestParam(name = "categoria", required = false) List<Long> idCategorias,
			@RequestParam(name = "tag", required = false) List<Long> tags,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		if (idCategorias == null || idCategorias.isEmpty()) {

			idCategorias = categoriaService.findAll().stream().map(c -> c.getIdcategoria())
					.collect(Collectors.toList());
		}
		Page<PostDto> posts = postService.findAllFilterDto("%" + titulo + "%", usuario.getUsername(), idCategorias,
				tags, pageable);

		return ResponseEntity.ok(posts);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/me/posts/{idpost}")
	public ResponseEntity<?> getPostsByIdPostUsernameLoged(@AuthenticationPrincipal Usuario usuario,
			@PathVariable Long idpost) {

		PostDto post = postConverter.convertToDto(postService.findByIdAndUsuario(idpost, usuario.getUsername())
				.orElseThrow(() -> new PostNotFoundException("No se ha encontrado el post con id: " + idpost)), true);

		return ResponseEntity.ok(post);
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/me/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> altaPost(@AuthenticationPrincipal Usuario usuario, 
			@RequestPart(name = "img", required = false) MultipartFile img,
			@RequestPart CrearPostDto post) {
		
		

		String urlImagen = null;

		if (img != null && !img.isEmpty()) {

			String imagen = storageService.store(img);

			urlImagen = MvcUriComponentsBuilder.fromMethodName(FicherosController.class, "serveFile", imagen, null)
					.build().toUriString();
			post.setPortada("/files/" + imagen);
		}

		
		PostDto postDto = postConverter.convertToDto(postService.createPost(post, usuario),true);

		return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping(value = "/me/posts/{idpost}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> ActualizacionPost(@AuthenticationPrincipal Usuario usuario, 
			@RequestPart(name = "img",required = false) MultipartFile img,
			@RequestPart UpdatePostDto post,
			@PathVariable Long idpost) {
		
		

		String urlImagen = null;

		if (img != null && !img.isEmpty()) {

			String imagen = storageService.store(img);

			urlImagen = MvcUriComponentsBuilder.fromMethodName(FicherosController.class, "serveFile", imagen, null)
					.build().toUriString();
			post.setPortada("/files/" + imagen);
		}

		post.setIdpost(idpost);
		PostDto postDto = postConverter.convertToDto(postService.updatePost(post, usuario),true);

		return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
	}
	

}
