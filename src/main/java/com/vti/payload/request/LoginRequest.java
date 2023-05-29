package com.vti.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.vti.entity.Status;

import lombok.Data;

@Data
public class LoginRequest {

	private String username;

	@NotBlank
	private String password;

	private String email;

	private List<String> role;

	private Status status;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LoginRequest() {
		super();
	}

}
