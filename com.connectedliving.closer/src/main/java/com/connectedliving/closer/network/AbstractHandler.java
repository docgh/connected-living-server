package com.connectedliving.closer.network;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.exceptions.CLException;

public abstract class AbstractHandler extends CLAuthenticatedHandler {

	AbstractHandler(Services services) {
		super(services);
	}

	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			do_doPut(request, response);
		} catch (CLException ex) {
			errorWriter(response, ex);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			do_doGet(request, response);
		} catch (CLException ex) {
			errorWriter(response, ex);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			do_doPost(request, response);
		} catch (CLException ex) {
			errorWriter(response, ex);
		}
	}

	private void errorWriter(HttpServletResponse response, CLException ex) throws IOException {
		response.setStatus(HttpStatus.OK_200);
		JSONObject json = new JSONObject();
		json.put("Error", ex.toJSON());
		response.getWriter().print(json.toString());
	}

	public void do_doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, CLException {
		throw new CLException(CLException.GENERAL.NOT_IMPLEMENTED);
	}

	public void do_doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, CLException {
		throw new CLException(CLException.GENERAL.NOT_IMPLEMENTED);
	}

	public void do_doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, CLException {
		throw new CLException(CLException.GENERAL.NOT_IMPLEMENTED);
	}
}
