package com.detrasdelcodigo.api.services;

import org.springframework.stereotype.Service;

import com.detrasdelcodigo.api.model.Rol;
import com.detrasdelcodigo.api.repositories.RolRepository;
import com.detrasdelcodigo.api.services.base.BaseService;

@Service
public class RolService extends BaseService<Rol, Long, RolRepository> {

}
