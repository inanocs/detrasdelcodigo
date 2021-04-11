package com.detrasdelcodigo.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.detrasdelcodigo.api.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	
	@Query("select p from Post p where upper (p.titulo) like upper(?1) and upper(p.usuario.username) like upper(?2) and p.categoria.idcategoria in (?3)")
	public List<Post> findAllFilter(String titulo, String username,List<Long> idcategoria);
	
	@Query("select p from Post p where upper (p.titulo) like upper(?1) and upper(p.usuario.username) like upper(?2) and p.categoria.idcategoria in (?3)")
	public Page<Post> findAllFilter(String titulo, String username,List<Long> idcategoria,Pageable p);
	
	@Query("select p from Post p where p.usuario.username=?1")
	public List<Post> findPostsByUsername(String username);
}
