package com.detrasdelcodigo.api.services;

import org.springframework.stereotype.Service;

import com.detrasdelcodigo.api.model.Categoria;
import com.detrasdelcodigo.api.repositories.CategoriaRepository;
import com.detrasdelcodigo.api.services.base.BaseService;

@Service
public class CategoriaService extends BaseService<Categoria, Long, CategoriaRepository> {

}
