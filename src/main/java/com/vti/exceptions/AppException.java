package com.vti.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Setter
@ToString
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class AppException extends RuntimeException {
    private Instant timestamp;
    private int code;
    private String message;
    private String path;

    public AppException(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public AppException(int code, String message, String path) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
        this.path = path;
    }

    public AppException(ErrorResponseBase errorResponseBase) {
        this.code = errorResponseBase.getStatus().value();
        this.message = errorResponseBase.message;
        this.timestamp = Instant.now();
    }

    public AppException(Exception ex) {
        String mes = ex.getMessage();
        if (mes == null) {
            this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            this.message = "NullPointerException";
        } else if (mes.contains("Access Denied")) {
            this.code = ErrorResponseBase.UNAUTHORIZED.status.value();
            this.message = ErrorResponseBase.UNAUTHORIZED.getMessage();
        } else if (mes.contains("not found") || mes.contains("not exist") || mes.contains("NoSuchKey") || mes.contains("not_exist")) {
            this.code = ErrorResponseBase.NOT_FOUND.status.value();
            this.message = ErrorResponseBase.NOT_FOUND.getMessage();
        } else if (mes.contains("Bad credentials")) {
            this.code = ErrorResponseBase.UNAUTHORIZED.status.value();
            this.message = "Sai username hoặc password";
        }else {
            this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            this.message = ex.getMessage();
        }
        this.timestamp = Instant.now();
    }

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    
}
