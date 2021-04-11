package com.detrasdelcodigo.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detrasdelcodigo.api.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
