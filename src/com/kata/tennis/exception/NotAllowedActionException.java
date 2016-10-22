package com.kata.tennis.exception;

public class NotAllowedActionException extends Exception {

	private static final long serialVersionUID = -5159879601451936936L;

	private String message;

	public NotAllowedActionException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}