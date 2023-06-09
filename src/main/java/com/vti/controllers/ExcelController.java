package com.vti.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vti.entity.Product;
import com.vti.payload.response.MessageResponse;
import com.vti.security.service.ExcelHelper;
import com.vti.security.service.ExcelService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

	@Autowired
	ExcelService fileService;

	@PostMapping("/upload")
	public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		ExcelHelper excelHelper = new ExcelHelper();
		if (excelHelper.hasExcelFormat(file)) {
			try {
				fileService.save(file);
				message = "Uploaded xlsx file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
			} catch (Exception e) {
				message = "Could not upload xlsx file: " + file.getOriginalFilename() + "!";
				System.out.println("e " + e);
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
			}
		} else if (excelHelper.hasCSVFormat(file)) {
			try {
				fileService.save(file);
				message = "Upload file csv successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
			} catch (Exception e) {
				message = "Could not upload the csv file: " + file.getOriginalFilename() + "!";
				System.out.println("current " + e);
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
			}
		}
		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
	}

	@GetMapping("/downloadXLSX")
	public ResponseEntity<Resource> getXLSXFile() {
		String filename = "prods.xlsx";
		InputStreamResource file = new InputStreamResource(fileService.loadXLSX());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@GetMapping("/downloadCSV")
	public ResponseEntity<Resource> getCSVFile() {
		String filename = "prods.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadCSV());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = fileService.getAllProducts();

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}