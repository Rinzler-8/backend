package com.vti.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseObject {

	private String status;
	private String message;
	private Object data;

	public ResponseObject(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

}
