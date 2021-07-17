package com.marlonfrazao.desafiozup.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.marlonfrazao.desafiozup.service.exceptions.ForbiddenException;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;
import com.marlonfrazao.desafiozup.service.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		return ResponseEntity.status(status).body(getStandardError(e, request, status, "Resource not found!"));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		return ResponseEntity.status(status).body(getStandardError(e, request, status, "Validation Exception!"));
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getOAuthCustomError("Forbidden", e.getMessage()));
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getOAuthCustomError("Unauthorized", e.getMessage()));
	}
	
	private StandardError getStandardError(Exception e, HttpServletRequest request, HttpStatus status, String error) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());	
		
		if (e instanceof MethodArgumentNotValidException) {
			ValidationError vError = new ValidationError(err);
			((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().forEach(f -> vError.addError(f.getField(), f.getDefaultMessage()));
			return vError;
		}
		
		err.setError(error);
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return err;
	}
	
	private OAuthCustomError getOAuthCustomError(String error, String errorDescription) {
		return new OAuthCustomError(error, errorDescription);
	}
}