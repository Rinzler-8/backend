package com.vti.payload.response;

import java.util.List;

import com.vti.entity.Status;

public class UserInfoResponse {
	private String type = "Bearer";
	private int id;
	private String username;
	private String email;
	private List<String> roles;
	private Status status;

	public UserInfoResponse(int id, String username, String email, List<String> roles, Status status) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.status = status;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}