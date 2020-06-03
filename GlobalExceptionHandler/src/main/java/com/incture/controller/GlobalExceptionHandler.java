package com.incture.controller;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.incture.exception.GenericConditionalException;



/*
 * This class is used to to define exception 
 * @Order defines the sort order for an annotated component.
 * The value() is optional and represents an order value as defined in the Ordered interface.
 *  Lower values have higher priority. The default value is Ordered.LOWEST_PRECEDENCE,
 * indicating lowest priority (losing to any other specified order value).*/

/*ControllerAdvice is an annotation introduced in Spring 3.2, and as the name suggests,
 * is “Advice” for multiple controllers.
 * It is used to enable a single ExceptionHandler to be applied to multiple controllers.
*/

@Order(Ordered.HIGHEST_PRECEDENCE)
@CrossOrigin
@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GenericConditionalException.class)
	protected ResponseEntity<Object> handleGenericConditionalException(GenericConditionalException ex, WebRequest req) {
		ex.printStackTrace();
		return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.CONFLICT, req);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		return handleExceptionInternal(ex,
				new GenericConditionalException(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(SQLException.class)
	protected ResponseEntity<Object> handleSqlException(SQLException ex, WebRequest req) {
		ex.printStackTrace();
		return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, req);
	}

	@ExceptionHandler(HibernateException.class)
	protected ResponseEntity<Object> handleHibernateException(HibernateException ex, WebRequest req) {
		ex.printStackTrace();

		return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, req);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	protected ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex,
			WebRequest req) {
		ex.printStackTrace();
		return handleExceptionInternal(ex,
				new GenericConditionalException("Un-processable data. Please check all the required fields."),
				new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, req);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest req) {
		ex.printStackTrace();
		return handleExceptionInternal(ex,new GenericConditionalException("Un-processable data. Please check all the required fields."),new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
	}
	

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllException(Exception ex, WebRequest req) {
		ex.printStackTrace();
		return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, req);
	}

}
