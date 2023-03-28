package com.vti.security.service;

import java.util.List;

import com.vti.entity.Category;
//import com.vti.form.CategoryFormForCreating;

public interface ICategoryService {
	public List<Category> getAllCategories();

	public Category getCategoryByID(int idInput);

//	void createCategory(CategoryFormForCreating CategoryFormCreate);

	public Category getCategoryByName(String name);

}
