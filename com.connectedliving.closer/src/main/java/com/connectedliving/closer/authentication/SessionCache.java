package com.connectedliving.closer.authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.configuration.CLConfig;
import com.connectedliving.closer.configuration.CLConfigProperty;

/**
 * Cache of user sessions
 * 
 * @author Greg Hill <ghill@connectedliving.com>
 *
 */
public class SessionCache {

	Map<String, UserSession> sessions;
	private int msValid;

	public SessionCache() {
		sessions = new HashMap<String, UserSession>();
		CLConfig config = Services.getInstance().getService(CLConfig.class);
		msValid = config.getIntProperty(CLConfigProperty.SESSION_IDLE_LIMIT) * 60 * 1000;
	}

	/**
	 * Create a new user session for the user
	 * 
	 * @param user
	 */
	public UserSession addNewSession(User user) {
		UserSession session = new UserSession(user);
		sessions.put(session.getSessionId(), session);
		return session;
	}

	/**
	 * Get user session from sessionId and confirmed against token
	 * 
	 * @param sessionId
	 * @param token
	 * @return Session if exists and valid
	 */
	public UserSession getUserFromSession(String sessionId, String token) {
		cleanup();
		UserSession session = sessions.get(sessionId);
		if (session == null) {
			// Session not found;
			return null;
		}
		if (!session.getToken().equals(token)) {
			// Invalid token
			return null;
		}
		session.update();
		return session;
	}

	/**
	 * Remove outdated sessions;
	 */
	private void cleanup() {
		Date cleanupDate = new Date(System.currentTimeMillis() - msValid);
		sessions.entrySet().removeIf(s -> s.getValue().getLastUsed().before(cleanupDate));
	}

}
