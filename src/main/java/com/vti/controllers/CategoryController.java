package com.vti.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.CategoryDto;
import com.vti.entity.Category;
import com.vti.security.service.ICategoryService;

@RestController
@RequestMapping(value = "api/v1/categories")
@CrossOrigin("*")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;

	@GetMapping()
	public ResponseEntity<?> getAllCategories() {
		List<Category> categoryListDB = categoryService.getAllCategories();
		List<CategoryDto> categoryListDto = new ArrayList<>();

		// convert categoryListDB --> categoryListDto
		for (Category categoryDB : categoryListDB) {
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setId(categoryDB.getId());
			categoryDto.setName(categoryDB.getName());

			categoryListDto.add(categoryDto);
		}

		return new ResponseEntity<>(categoryListDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCategoryByID(@PathVariable(name = "id") int id) {
		try {
			Category categoryDB = categoryService.getCategoryByID(id);

			// convert categoryDB --> CategoryDto

			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setId(categoryDB.getId());
			categoryDto.setName(categoryDB.getName());
			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}

	}

}
