package com.urlshortener.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;

class ResultUtil {

	static <T> ResponseEntity<T> notFound() {
		return new ResponseEntity<>(NOT_FOUND);
	}

	static <T> ResponseEntity<T> created(T t) {
		return new ResponseEntity<>(t, CREATED);
	}

	static <T> ResponseEntity<T> conflict() {
		return new ResponseEntity<>(CONFLICT);
	}

}
