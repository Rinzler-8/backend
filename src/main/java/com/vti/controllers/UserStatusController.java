package com.vti.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.Status;

@RestController
@RequestMapping(value = "api/v1/userStatus")
@CrossOrigin("*")
public class UserStatusController {

	private Status status;

	@GetMapping()
	public ResponseEntity<?> getAllStatus() {
		Status[] values = Status.values();
		return new ResponseEntity<>(values, HttpStatus.OK);
	}

}
