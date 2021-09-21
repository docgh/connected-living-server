package com.connectedliving.closer.network;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.authentication.SessionCache;
import com.connectedliving.closer.authentication.UserSession;
import com.connectedliving.closer.exceptions.AuthException;
import com.connectedliving.closer.exceptions.CLException;

public abstract class CLAuthenticatedHandler extends HttpServlet {

	protected Services services;

	CLAuthenticatedHandler(Services services) {
		this.services = services;
	}

	// TODO Implement. Throw if not authenticated
	protected UserSession authenticateUser(CLRequest request, HttpServletResponse response) throws CLException {
		// String sessionId = request.getParameter("sessionId");
		String sessionId = request.getParameter("sessionId");
		if (sessionId == null) {
			throw new AuthException(AuthException.ExceptionType.INVALID_AUTH);
		}
		Cookie[] cookies = request.getDelegate().getCookies();
		String secret = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("Session-secret")) {
				secret = cookie.getValue();
			}
		}
		SessionCache cache = services.getService(SessionCache.class);
		UserSession session = cache.getUserFromSession(sessionId, secret);
		if (session == null)
			throw new AuthException(AuthException.ExceptionType.INVALID_AUTH);
		return session;

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
