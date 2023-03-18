package com.vti.controllers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.ProductDto;
import com.vti.entity.Product;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;
import com.vti.security.service.IProductService;

@RestController
@RequestMapping(value = "api/v1/products")
@CrossOrigin("*")
public class ProductController {
	@Autowired
	private IProductService productService;

	// Lấy All danh sách sản phẩm
	@GetMapping()
//	public ResponseEntity<?> getAllProducts() {
//		List<Product> productListDB = productService.getAllProducts();
//		List<ProductDto> productListDto = new ArrayList<>();
//
//		// convert productListDB --> productListDto
//		for (Product productDB : productListDB) {
//			ProductDto productDto = new ProductDto();
//			productDto.setId(productDB.getId());
//			productDto.setName(productDB.getName());
//			productDto.setPrice(productDB.getPrice());
//			productDto.setInfo(productDB.getInfo());
//			productDto.setDetail(productDB.getDetail());
//			productDto.setRatingStar(productDB.getRatingStar());
//			productDto.setImageName(productDB.getImageName());
//			productDto.setManufacturerName(productDB.getManufacturer().getName().toString());
//			productDto.setCategoryName(productDB.getCategory().getName());
//
//			productListDto.add(productDto);
//		}
//
//		return new ResponseEntity<>(productListDto, HttpStatus.OK);
//	}

	public ResponseEntity<?> getAllproduct(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Product> productPage_DB = productService.getAllProducts(pageable, search);
		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu
		// Chuyển đổi dữ liệu
		Page<ProductDto> productPage_Dtos = productPage_DB.map(new Function<Product, ProductDto>() {
			@Override
			public ProductDto apply(Product product) {
				ProductDto productDto = new ProductDto();
				productDto.setProduct_id(product.getId());
				productDto.setName(product.getName());
				productDto.setPrice(product.getPrice());
				productDto.setInfo(product.getInfo());
				productDto.setDetail(product.getDetail());
				productDto.setRatingStar(product.getRatingStar());
				productDto.setImageName(product.getImageName());
				productDto.setCategoryName(product.getCategory().getName());
				return productDto;
			}

		});

		return new ResponseEntity<>(productPage_Dtos, HttpStatus.OK);
	}

// Tìm sản phẩm theo id
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable(name = "id") int id) {
		try {
			Product productDB = productService.getProductById(id);

			ProductDto productDto = new ProductDto();
			productDto.setProduct_id(productDB.getId());
			productDto.setName(productDB.getName());
			productDto.setPrice(productDB.getPrice());
			productDto.setInfo(productDB.getInfo());
			productDto.setDetail(productDB.getDetail());
			productDto.setRatingStar(productDB.getRatingStar());
			productDto.setImageName(productDB.getImageName());
			productDto.setCategoryName(productDB.getCategory().getName());

			return new ResponseEntity<>(productDto, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Can not get product by ID", HttpStatus.NOT_FOUND);
		}

	}

//	Thêm mới sản phẩm
	@PostMapping()
	public ResponseEntity<?> createNewproduct(@RequestBody ProductFormForCreating productNewForm) {
		try {
//			Thêm mới Product
//			Sau khi thêm mới, trả về thông tin Product vừa thêm
			Product productNew = productService.createProduct(productNewForm);

//			Convert
			ProductDto productNewDto = new ProductDto();
			productNewDto.setProduct_id(productNew.getId());
			productNewDto.setName(productNew.getName());
			productNewDto.setPrice(productNew.getPrice());
			productNewDto.setInfo(productNew.getInfo());
			productNewDto.setDetail(productNew.getDetail());
			productNewDto.setRatingStar(productNew.getRatingStar());
			productNewDto.setImageName(productNew.getImageName());
			productNewDto.setCategoryName(productNew.getCategory().getName());

			return new ResponseEntity<>(productNewDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not create new product", HttpStatus.BAD_REQUEST);
		}

	}

// Update sản phẩm

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable(name = "id") int id,
			@RequestBody ProductFormForUpdating productUpdateForm) {
		try {
			productService.updateProduct(id, productUpdateForm);
			return new ResponseEntity<>("Update product ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not update product", HttpStatus.NOT_FOUND);
		}
	}

//	Xóa sản phẩm theo id
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") int id) {
		try {

			productService.deleteProductById(id);

			return new ResponseEntity<>("Delete product ok", HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Can not delete product", HttpStatus.NOT_FOUND);
		}
	}

}
