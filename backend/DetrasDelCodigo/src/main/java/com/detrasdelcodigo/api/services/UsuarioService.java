package com.detrasdelcodigo.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.detrasdelcodigo.api.dto.EditarPasswordUsuarioDto;
import com.detrasdelcodigo.api.dto.EditarUsuarioDataDto;
import com.detrasdelcodigo.api.model.Rol;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.repositories.UsuarioRepository;
import com.detrasdelcodigo.api.services.base.BaseService;
import com.detrasdelcodigo.api.upload.StorageService;
import com.detrasdelcodigo.api.util.AesEncryptor;
import com.prueba.api.errors.UsuarioExisteException;
import com.prueba.api.errors.UsuariosException;

@Service
public class UsuarioService extends BaseService<Usuario, Long, UsuarioRepository> {

	@Autowired
	private RolService rolService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StorageService storageService;

	public Optional<Usuario> findUsuarioByUsernameOpt(String username) {

		return repositorio.findUsuarioByUsernameOpt(username);
	}

	public Usuario nuevoUsuario(Usuario user) {
		if (repositorio.findUsuarioByUsername(user.getUsername()) != null) {
			throw new UsuarioExisteException("El usuario ya existe");
		}

		if (repositorio.findUsuarioByEmail(user.getEmail()) != null) {

			throw new UsuarioExisteException("El email ya está registrado");
		}

		String password = user.getPassword();

		if (password.length() == 0) {
			throw new UsuariosException("La contraseña no debe estar vacía");
		}

		Rol rol = rolService.findById(1L).orElseThrow(() -> new UsuariosException("El rol no existe"));

		user.setRol(rol);

		user.setPassword(passwordEncoder.encode(password));

		return save(user);
	}

	public Usuario edit(Usuario user) {

		Usuario userFound = null;

		userFound = repositorio.findUsuarioByUsername(user.getUsername());

		if (userFound != null && userFound.getIdusuario() != user.getIdusuario()) {
			throw new UsuarioExisteException(
					"El nombre de usuario ya existe " + userFound.getIdusuario() + ", " + user.getIdusuario());
		}

		userFound = repositorio.findUsuarioByEmail(user.getEmail());

		if (userFound != null && userFound.getIdusuario() != user.getIdusuario()) {

			throw new UsuarioExisteException("El email ya está registrado");
		}

		userFound = repositorio.findById(user.getIdusuario()).get();

		user.setComentarios(userFound.getComentarios());

		if (user.getPassword() == null) {
			user.setPassword(userFound.getPassword());
		} else {
			String password = user.getPassword();
			AesEncryptor encriptador = new AesEncryptor();
			user.setPassword(encriptador.getAESEncrypt(password));
		}

		user.setPosts(userFound.getPosts());

		return repositorio.save(user);
	}

	public Usuario findByUsername(String username) {

		return repositorio.findUsuarioByUsername(username);
	}

	public Usuario actualizarAvatar(MultipartFile img, Usuario u) {

		if (img != null && !img.isEmpty()) {

			String imagen = storageService.store(img);

			u.setAvatar("/files/" + imagen);
		}

		return repositorio.save(u);
	}

	public Usuario actualizarDatosPersonales(EditarUsuarioDataDto usuarioDto, Usuario usuario) {

		Usuario userFound = null;

		userFound = repositorio.findUsuarioByEmail(usuarioDto.getEmail());

		if (userFound != null && userFound.getIdusuario() != usuario.getIdusuario()) {

			throw new UsuarioExisteException("El email ya está registrado");
		}

		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellidos(usuarioDto.getApellidos());
		usuario.setEmail(usuarioDto.getEmail());

		return repositorio.save(usuario);
	}
	
	public boolean actualizarPassword(EditarPasswordUsuarioDto usuarioDto,Usuario u) {
		
		if(!passwordEncoder.matches(usuarioDto.getOldPassword(), u.getPassword())) {
			
			throw new UsuariosException("La contraseña es incorrecta");
		}
		
		if(!usuarioDto.getNewPassword().equals(usuarioDto.getRepeatNewPassword())) {
			throw new UsuariosException("Las contraseñas deben ser iguales.");
		}
		
		u.setPassword(passwordEncoder.encode(usuarioDto.getNewPassword()));
		
		repositorio.save(u);
		
		return passwordEncoder.matches(usuarioDto.getOldPassword(), u.getPassword());
	}

}
