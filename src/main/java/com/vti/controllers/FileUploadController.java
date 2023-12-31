package com.vti.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.vti.entity.ResponseObject;
import com.vti.security.service.IStorageService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping(value = "api/v1/fileUpload")
public class FileUploadController {

	// Inject Storage Service here
	@Autowired
	private IStorageService storageService;

	@PostMapping()
	public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file) {
		try {

			// save file to a folder
			String generatedFileName = storageService.storeFile(file);

			return ResponseEntity.status(HttpStatus.OK).body(generatedFileName);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("ok", e.getMessage(), ""));
		}
	}

	@GetMapping("/files/{fileName:.+}")
	public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
		try {
			byte[] bytes = storageService.readFileContent(fileName);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}

	}

	@GetMapping()
	public ResponseEntity<?> getAllUploadedFiles() {
		try {
			List<String> urls = storageService.loadAll().map(path -> {
				String urlPath = MvcUriComponentsBuilder
						.fromMethodName(FileUploadController.class, "readDetailFile", path.getFileName().toString())
						.build().toUri().toString();
				return urlPath;
			}).collect(Collectors.toList());

			return ResponseEntity.ok(urls);
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseObject("failed", "List file failed", new String[] {}));
		}

	}
}
