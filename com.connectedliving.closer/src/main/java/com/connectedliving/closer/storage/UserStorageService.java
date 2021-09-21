package com.connectedliving.closer.storage;

import com.connectedliving.closer.authentication.User;

public interface UserStorageService {

	public User getUser(String username, String password);

}
