package com.vti.security.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService implements IStorageService {

	private final Path storageFolder = Paths.get("uploads");

	// constructor create uploads folder
	public ImageStorageService() {
		try {
			Files.createDirectories(storageFolder);

		} catch (IOException e) {
			throw new RuntimeException("Cannot initialize storage", e);
		}
	}

	public boolean isImageFile(MultipartFile file) {

		// FileNameUtils common-io
		String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
		return Arrays.asList(new String[] { "png", "jpeg" }).contains(fileExtension.trim().toLowerCase());
	}

	@Override
	public String storeFile(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file!");
			}

			// check file is image
			if (!isImageFile(file)) {
				throw new RuntimeException("You can only upload image file");
			}

			// file must be <= 20mb
			float fileSizeInMegaBytes = file.getSize() / 1_000_000.0f;
			if (fileSizeInMegaBytes > 8.0f) {
				throw new RuntimeException("File must be <= 8mb");
			}

			// rename file to avoid duplication
			String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
			String generatedFileName = UUID.randomUUID().toString().replace("-", "");

			generatedFileName = generatedFileName + "." + fileExtension;

			Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize()
					.toAbsolutePath();
			if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
				throw new RuntimeException("Cannot store file outside current directory");
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
			}
			return generatedFileName;

		} catch (IOException e) {
			throw new RuntimeException("Failed to store file!", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {

			// list all stored files
			return Files.walk(this.storageFolder, 1)
					.filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
					.map(this.storageFolder::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load stored files", e);
		}
	}

	@Override
	public byte[] readFileContent(String fileName) {
		try {
			Path file = storageFolder.resolve(fileName);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
				return bytes;
			} else {
				throw new RuntimeException("Could not read file: " + fileName);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not read file: " + fileName, e);
		}
	}

	@Override
	public void deleteAllFiles() {
		// TODO Auto-generated method stub
	}
}
