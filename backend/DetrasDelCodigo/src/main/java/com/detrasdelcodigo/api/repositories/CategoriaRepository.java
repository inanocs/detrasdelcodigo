package com.detrasdelcodigo.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detrasdelcodigo.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
