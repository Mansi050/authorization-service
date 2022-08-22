package com.cts.code.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class Controlleradvice {
	
	//Exception Method for User not found
	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error userNotFoundException(UsernameNotFoundException userNotFoundException) {
		return new Error(HttpStatus.FORBIDDEN,LocalDateTime.now(),userNotFoundException.getMessage());
	}
	
	//Exception for JWT Malfunction
	
	@ExceptionHandler(MalformedJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Error tokenMalformedException() {
		return new Error(HttpStatus.UNAUTHORIZED, LocalDateTime.now(),"Not Authorized");
	}

	//Exception for JWT Signature unauthorized  Error
	
	@ExceptionHandler(SignatureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Error tokenSignatureException() {
		return new Error(HttpStatus.UNAUTHORIZED,LocalDateTime.now(),"Not AUthorized");
	}
}
