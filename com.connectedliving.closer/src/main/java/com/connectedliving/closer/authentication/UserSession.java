package com.connectedliving.closer.authentication;

import java.util.Date;
import java.util.UUID;

public class UserSession {

	private User user;
	private String token;
	private String sessionId;
	private Date lastUsed;

	public UserSession(User user) {
		this.user = user;
		this.token = UUID.randomUUID().toString();
		this.sessionId = UUID.randomUUID().toString();
		this.lastUsed = new Date();
	}

	public String getToken() {
		return token;
	}

	public String getSessionId() {
		return sessionId;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Get the last time the token was used
	 * 
	 * @return
	 */
	public Date getLastUsed() {
		return lastUsed;
	}

	/**
	 * Update the last Used time to current
	 */
	public void update() {
		this.lastUsed = new Date();
	}

}
