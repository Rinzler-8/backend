package com.vti.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Category;
import com.vti.repository.ICategoryRepository;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryByID(int idInput) {
		return categoryRepository.findById(idInput).get();
	}

//	@Override
//	public void createCategory(CategoryFormForCreating CategoryFormCreate) {
//		Category Category = new Category();
//		Category.setName(CategoryFormCreate.getName());
//		CategoryRepository.save(Category);
//	}

	@Override
	public Category getCategoryByName(String nameDep) {
		return categoryRepository.findByName(nameDep);
	}
}
