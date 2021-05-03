package com.detrasdelcodigo.api.services;

import org.springframework.stereotype.Service;

import com.detrasdelcodigo.api.model.Comentario;
import com.detrasdelcodigo.api.repositories.ComentarioRepository;
import com.detrasdelcodigo.api.services.base.BaseService;
import com.prueba.api.errors.PostNotFoundException;
import com.prueba.api.errors.UsuariosException;

@Service
public class ComentarioService extends BaseService<Comentario, Long, ComentarioRepository> {
	
	public Comentario crearComentario(Comentario comentario) {
		
		
		if(comentario.getUsuario() == null) {
			throw new UsuariosException("No hay usuario");
		}
		
		if(comentario.getPost() == null){
			throw new PostNotFoundException("El post no está registrado");
		}
		
		return repositorio.save(comentario);
	}

}
