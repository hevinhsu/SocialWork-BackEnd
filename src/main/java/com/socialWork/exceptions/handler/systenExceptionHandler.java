package com.socialWork.exceptions.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.socialWork.exceptions.LoginException;
import com.socialWork.exceptions.RegisterException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class systenExceptionHandler {

	@ExceptionHandler(LoginException.class)
	public Object loginExceptionHandler(LoginException e) {
		log.error("login fail");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(RegisterException.class)
	public Object registerExceptionHandler(RegisterException e) {
		log.error("register fail");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object invalidParameterHandler(MethodArgumentNotValidException e) {
		BindingResult exceptions = e.getBindingResult();
		if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldError.getDefaultMessage());
            }
        }
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(BindException.class)
	public Object invalidParameterHandler(BindException e) {
		BindingResult exceptions = e.getBindingResult();
		if (exceptions.hasErrors()) {
			List<ObjectError> errors = exceptions.getAllErrors();
			if (!errors.isEmpty()) {
				FieldError fieldError = (FieldError) errors.get(0);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldError.getDefaultMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	
	@ExceptionHandler(Exception.class)
	public Object uncatchExceptionHandler() {
		log.error("found uncatch exception throwing");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("System Error");
	}
	
}