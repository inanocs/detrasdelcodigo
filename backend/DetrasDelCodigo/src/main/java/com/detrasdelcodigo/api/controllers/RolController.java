package com.detrasdelcodigo.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detrasdelcodigo.api.model.Rol;
import com.detrasdelcodigo.api.services.RolService;

@RestController
@RequestMapping("/roles/")
public class RolController {

	@Autowired
	private RolService rolService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllRoles(){
		
		List<Rol> roles = rolService.findAll();
		
		return ResponseEntity.ok(roles);
	}
}
