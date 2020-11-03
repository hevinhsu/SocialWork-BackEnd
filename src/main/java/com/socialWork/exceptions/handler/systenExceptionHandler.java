package com.socialWork.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.socialWork.exceptions.LoginException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class systenExceptionHandler {

	@ExceptionHandler(LoginException.class)
	public Object loginExceptionHandler(LoginException e) {
		log.error("login fail");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
	
	@ExceptionHandler(Exception.class)
	public Object uncatchExceptionHandler() {
		log.error("found uncatch exception throwing");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("System Error");
	}
	
}
