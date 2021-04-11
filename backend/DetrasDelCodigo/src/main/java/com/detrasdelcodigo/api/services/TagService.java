package com.detrasdelcodigo.api.services;

import org.springframework.stereotype.Service;

import com.detrasdelcodigo.api.model.Tag;
import com.detrasdelcodigo.api.repositories.TagRepository;
import com.detrasdelcodigo.api.services.base.BaseService;

@Service
public class TagService extends BaseService<Tag, Long, TagRepository> {

}
