package com.detrasdelcodigo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.services.CategoriaService;

@RequestMapping("/categorias/")
@RestController
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategorias(){
		
		return ResponseEntity.ok(categoriaService.findAll());
	}
}
