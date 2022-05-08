package com.jay.qna.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class QnAControllerAdvice {
	
	@ExceptionHandler(CustomException.class)
	//@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response> TestException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(new Response("600", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
	
	//Response DTO
    @Data
    @AllArgsConstructor
    static class Response {
        private String code;
        private String msg;
    }
}
