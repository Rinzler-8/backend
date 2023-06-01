package com.vti.security.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vti.entity.Product;
import com.vti.exceptions.ExcelHelper;
import com.vti.repository.IProductRepository;

@Service
public class ExcelService implements IExcelService {

	@Autowired
	IProductRepository prodRepository;
	@Autowired
	private ICategoryService categoryService;

	public void save(MultipartFile file) {
		try {
			ExcelHelper excelHelper = new ExcelHelper();
			List<Product> products = excelHelper.excelToProducts(file.getInputStream(), categoryService);
			prodRepository.saveAll(products);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		List<Product> products = prodRepository.findAll();
		ExcelHelper excelHelper = new ExcelHelper();
		ByteArrayInputStream in = excelHelper.productsToExcel(products);
		return in;
	}

	public List<Product> getAllProducts() {
		return prodRepository.findAll();
	}
}
