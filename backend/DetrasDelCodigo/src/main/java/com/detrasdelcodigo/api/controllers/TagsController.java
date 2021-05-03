package com.detrasdelcodigo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.services.TagService;

@RequestMapping("/tags/")
@RestController
public class TagsController {

	@Autowired
	private TagService tagService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllTags(){
		
		return ResponseEntity.ok(tagService.findAll());
	}
	
}
