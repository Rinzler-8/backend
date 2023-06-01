package com.vti.security.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vti.entity.Product;

public interface IExcelService {
	ByteArrayInputStream load();

	List<Product> getAllProducts();

	void save(MultipartFile file);
}
