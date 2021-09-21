package com.connectedliving.closer.exceptions;

import org.json.JSONObject;

public class CLException extends Exception {

	private int code;
	private String message;
	private String exceptionClass = null;

	public enum GENERAL {
		NOT_IMPLEMENTED(1, "API not implemented"), GENERAL(2, "GENERAL ERROR");

		private int code;
		private String message;

		GENERAL(int code, String message) {
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

	public CLException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public CLException(GENERAL gen) {
		this.code = gen.getCode();
		this.message = gen.getMessage();
	}

	public void setClass(String messageClass) {
		this.exceptionClass = messageClass;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.putOpt("class", exceptionClass);
		json.put("code", code);
		json.put("message", message);
		return json;
	}

}
