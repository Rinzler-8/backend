package com.vti.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.ERole;

@RestController
@RequestMapping(value = "api/v1/role")
@CrossOrigin("*")
public class RoleController {

	@GetMapping()
	public ResponseEntity<?> getAllRoles() {
		ERole[] values = ERole.values();
		return new ResponseEntity<>(values, HttpStatus.OK);
	}

}
