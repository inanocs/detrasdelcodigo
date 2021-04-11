package com.detrasdelcodigo.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detrasdelcodigo.api.model.Rol;
import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.repositories.UsuarioRepository;
import com.detrasdelcodigo.api.services.base.BaseService;
import com.detrasdelcodigo.api.util.AesEncryptor;
import com.prueba.api.errors.UsuarioExisteException;
import com.prueba.api.errors.UsuariosException;

@Service
public class UsuarioService extends BaseService<Usuario,Long,UsuarioRepository> {
	
	@Autowired
	private RolService rolService;
	
	public Usuario save(Usuario user) {
		
		if(repositorio.findUsuarioByUsername(user.getUsername())!=null) {
			throw new UsuarioExisteException("El usuario ya existe");
		}
		
		if(repositorio.findUsuarioByEmail(user.getEmail())!=null) {
			
			throw new UsuarioExisteException("El email ya está registrado");
		}
		
		
		String password = user.getPassword();
		
		if(password.length()==0) {
			throw new UsuariosException("La contraseña no debe estar vacía");
		}
		
		Optional<Rol> rol = rolService.findById(1L);
		
		user.setRol(rol.get());
		
		AesEncryptor encriptador = new AesEncryptor();
		user.setPassword(encriptador.getAESEncrypt(password));
		
		return repositorio.save(user);
	}
	
	public Usuario edit(Usuario user) {
		
		Usuario userFound = null;
		
		userFound = repositorio.findUsuarioByUsername(user.getUsername());
		
		if(userFound!=null && userFound.getIdusuario()!=user.getIdusuario()) {
			throw new UsuarioExisteException("El nombre de usuario ya existe "+userFound.getIdusuario()+", "+user.getIdusuario());
		}
		
		userFound = repositorio.findUsuarioByEmail(user.getEmail());
		
		if(userFound != null && userFound.getIdusuario()!=user.getIdusuario()) {
			
			throw new UsuarioExisteException("El email ya está registrado");
		}	
		
		userFound = repositorio.findById(user.getIdusuario()).get();
		
		user.setComentarios(userFound.getComentarios());
		
		if(user.getPassword()==null) {
			user.setPassword(userFound.getPassword());
		}else {
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

}
