package com.vti.dto;

import java.util.List;

import com.vti.entity.Role;
import com.vti.entity.Status;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

//chuyển đổi giữa dữ liệu lấy được từ DB đẩy lên Frontend.
public class AccountDTO {

	private int id;
	private String email;
	private String mobile;
	private String username;
	private String firstName;
	private String lastName;
	private String urlAvatar;
	private String address;
	private List<Role> role;
	private Status status;

	public AccountDTO(int id, String email, String mobile, String username, String firstName, String lastName,
			String urlAvatar, String address, List<Role> role, Status status) {
		super();
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.urlAvatar = urlAvatar;
		this.address = address;
		this.role = role;
		this.status = status;
	}

	public AccountDTO() {
		super();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
