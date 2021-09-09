package com.connectedliving.closer.network;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CLAuthenticatedHandler extends HttpServlet {

	// TODO Implement. Throw if not authenticated
	protected boolean isUserAuthenticated(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	// TODO Implement
	protected boolean isRobotAuthenticated(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	/**
	 * Send unauthorized
	 * 
	 * @param response
	 */
	protected void sendUnauthorized(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
