package com.connectedliving.closer.storage.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.authentication.User;
import com.connectedliving.closer.storage.DatabaseService;
import com.connectedliving.closer.storage.UserStorageService;

public class UserStorageServiceImpl implements UserStorageService {

	Logger LOG = LoggerFactory.getLogger(UserStorageServiceImpl.class);

	private static final String SELECT_USER_WITH_AUTH = "SELECT permissions from users WHERE username = ? AND password = ?";

	Services services;

	public UserStorageServiceImpl(Services services) {
		this.services = services;
	}

	/**
	 * Hash a password with salt
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private String getPassHash(String password, String salt) throws NoSuchAlgorithmException {
		String toHash = password + salt;
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(toHash.getBytes());
		String hash = new String(Base64.getEncoder().encode(messageDigest.digest()));
		LOG.debug("Hash: " + hash);
		return hash;

	}

	@Override
	public User getUser(String username, String password) {
		DatabaseService dbService = services.getService(DatabaseService.class);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = dbService.getReadOnlyCon();
			statement = con.prepareStatement(SELECT_USER_WITH_AUTH);
			int i = 1;
			statement.setObject(i++, username);
			statement.setObject(i++, getPassHash(password, username));
			rs = statement.executeQuery();
			if (rs.next()) {
				return new User(username, rs.getString("permissions"));
			}
			return null;
		} catch (SQLException | NoSuchAlgorithmException e) {
			LOG.error("Error getting user", e);
			return null;
		} finally {
			dbService.closeAll(rs, statement, con);
		}
	}

}
