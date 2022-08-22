package com.cts.code.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class Error {
	private HttpStatus status;
	private LocalDateTime timestamp;
	private String message;
	
	public Error(HttpStatus status1, LocalDateTime timestamp1, String message1) {
		super();
		this.setStatus(status1);
		this.setTimestamp(timestamp1);
		this.setMessage(message1);
	}
	public Error() {
		super();
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
