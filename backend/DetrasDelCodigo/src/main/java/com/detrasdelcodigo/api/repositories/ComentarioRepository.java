package com.detrasdelcodigo.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detrasdelcodigo.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
