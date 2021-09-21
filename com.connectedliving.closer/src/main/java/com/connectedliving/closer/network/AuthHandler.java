package com.connectedliving.closer.network;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.authentication.SessionCache;
import com.connectedliving.closer.authentication.User;
import com.connectedliving.closer.authentication.UserSession;
import com.connectedliving.closer.exceptions.AuthException;
import com.connectedliving.closer.storage.UserStorageService;

public class AuthHandler extends AbstractHandler {

	public AuthHandler(Services services) {
		super(services);
	}

	@Override
	public void do_doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, AuthException {

		String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		if (data != null && data.contains("{")) {
			JSONObject authData = new JSONObject(data);
			if (!authData.has("username") || !authData.has("password")) {
				throw new AuthException(AuthException.ExceptionType.INVALID_AUTH);
			}
			UserSession session = getSession(authData.getString("username"), authData.getString("password"));
			if (session == null) {
				throw new AuthException(AuthException.ExceptionType.INVALID_AUTH);
			}
			response.addCookie(new Cookie("Session-secret", session.getToken()));
			response.setStatus(HttpStatus.OK_200);
			response.setContentType("application/json");
			response.getWriter().write(createReturnJson(session).toString());
		}

	}

	private JSONObject createReturnJson(UserSession session) {
		JSONObject json = new JSONObject();
		json.put("sessionId", session.getSessionId());
		return json;
	}

	/**
	 * Check username and password. If valid, and user present, create session and
	 * add to session storage
	 * 
	 * @param username
	 * @param password
	 * @return UserSession if valid
	 */
	private UserSession getSession(String username, String password) {
		UserStorageService userService = services.getService(UserStorageService.class);
		User user = userService.getUser(username, password);
		if (user == null) {
			return null;
		}
		UserSession session = services.getService(SessionCache.class).addNewSession(user);
		return session;
	}
}
