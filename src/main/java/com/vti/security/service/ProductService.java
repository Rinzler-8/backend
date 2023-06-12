package com.vti.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entity.Category;
import com.vti.entity.Product;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForRating;
import com.vti.form.ProductFormForUpdating;
import com.vti.repository.ICategoryRepository;
import com.vti.repository.IProductRepository;
import com.vti.specification.ProductSpecification;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Override
	public Page<Product> getAllProducts(Pageable pageable, String search) {
		Specification<Product> whereProduct = null;
		if (!StringUtils.isEmpty(search)) {
			ProductSpecification nameSpecification = new ProductSpecification("name", "LIKE", search);
			ProductSpecification categorySpecification = new ProductSpecification("category", "LIKE", search);
			whereProduct = Specification.where(nameSpecification).or(categorySpecification);
		}

		return productRepository.findAll(whereProduct, pageable); // findAll - phuong thuc co san cua JPA da duoc xay
																	// dung san khi extends ben repository
	}

	@Override
	public Product getProductById(int id) {
		return productRepository.getById(id);
	}

	@Override
	public void deleteProductById(int id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product createProduct(ProductFormForCreating productNewForm) {
		// Tìm category theo id
		Category category = categoryRepository.getById(productNewForm.getCategoryId());
		Product product = new Product();
		product.setName(productNewForm.getName());
		product.setPrice(productNewForm.getPrice());
		product.setInfo(productNewForm.getInfo());
		product.setDetail(productNewForm.getDetail());
		product.setRatingStar(productNewForm.getRatingStar());
		product.setImageName(productNewForm.getImageName());
		product.setCategory(category);
		Product productNew = productRepository.save(product);
		return productNew;

	}

	@Override
	public Product updateProduct(int id, ProductFormForUpdating productUpdateForm) {
		Product product = productRepository.getById(id);

		// Tìm manufacturer theo id
		Category category = categoryRepository.getById(productUpdateForm.getCategoryId());

		product.setName(productUpdateForm.getName());
//		product.setPrice(productUpdateForm.getPrice());
//		product.setInfo(productUpdateForm.getInfo());
//		product.setDetail(productUpdateForm.getDetail());
//		product.setRatingStar(productUpdateForm.getRatingStar());
//		product.setImageName(productUpdateForm.getImageName());
		product.setCategory(category);

		Product productUpdate = productRepository.save(product);
		return productUpdate;
	}

	@Override
	public Product rateProduct(int id, ProductFormForRating productRateForm) {
		Product product = productRepository.getById(id);

		product.setRatingStar(productRateForm.getRatingStar());

		Product productUpdate = productRepository.save(product);
		return productUpdate;
	}

}
