package com.detrasdelcodigo.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.detrasdelcodigo.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findUsuarioByUsername(String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Optional<Usuario> findUsuarioByUsernameOpt(String username);
	
	@Query("select u from Usuario u where u.email=?1")
	public Usuario findUsuarioByEmail(String email);

}
