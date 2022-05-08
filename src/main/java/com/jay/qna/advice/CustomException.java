package com.jay.qna.advice;

@SuppressWarnings("serial")
public class CustomException extends RuntimeException {
	public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

}
