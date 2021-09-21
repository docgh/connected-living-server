package com.connectedliving.closer.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CLRequest {

	HttpServletRequest delegate;
	Map<String, String[]> parameters;
	String body;

	public CLRequest(HttpServletRequest request) {
		this.delegate = request;
		InputStream input;
		try {
			input = request.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c = 0;
			while ((c = input.read()) > -1) {
				out.write(c);
			}
			body = out.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parameters = request.getParameterMap();
	}

	public String getParameter(String param) {
		String[] result = parameters.get(param);
		if (result != null && result.length >= 0) {
			return result[0];
		}
		return null;
	}

	public String getBody() {
		return body;
	}

	public HttpServletRequest getDelegate() {
		return delegate;
	}

}
