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

//	@GetMapping(value = "/{id}")
//	public ResponseEntity<?> getCategoryByID(@PathVariable(name = "id") int id) {
//		try {
//			Category categoryDB = categoryService.getCategoryByID(id);
//
//			// convert categoryDB --> CategoryDto
//
//			CategoryDto categoryDto = new CategoryDto();
//			categoryDto.setId(categoryDB.getId());
//			categoryDto.setName(categoryDB.getName());
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
//		}
//
//	}

}
