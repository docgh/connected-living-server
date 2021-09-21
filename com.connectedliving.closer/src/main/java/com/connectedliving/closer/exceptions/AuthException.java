package com.connectedliving.closer.exceptions;

public class AuthException extends CLException {

	public final static String EXCEPTIONCLASS = "AUTH";

	public enum ExceptionType {
		INVALID_AUTH(1, "Authentication is invalid"), SESSION_INVALID(2, "Session is ex"),
		INSUFFICIENT_PERMISSION(3, "Insufficient permission"),

		;

		private int code;
		private String message;

		ExceptionType(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	public AuthException(ExceptionType type) {
		super(type.code, type.getMessage());
		setClass(EXCEPTIONCLASS);
	}

}
