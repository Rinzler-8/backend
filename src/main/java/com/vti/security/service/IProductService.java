package com.vti.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Product;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;

public interface IProductService {
	public Page<Product> getAllProducts(Pageable pageable, String search);

	public Product getProductById(int id);

	public Product createProduct(ProductFormForCreating productNewForm);

	public Product updateProduct(int id, ProductFormForUpdating productUpdateForm);

	public void deleteProductById(int id);
}
