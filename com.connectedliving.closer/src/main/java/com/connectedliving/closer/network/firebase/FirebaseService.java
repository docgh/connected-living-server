package com.connectedliving.closer.network.firebase;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

public class FirebaseService {

	private static final Logger LOG = LoggerFactory.getLogger(FirebaseService.class);

	/**
	 * Initializes Firebase Expects credentials to be in
	 * GOOGLE_APPLICATION_CREDENTIALS env variable Example: export
	 * GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json"
	 * 
	 * @throws IOException
	 */
	public FirebaseService() throws IOException {
		FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault())
				.build();

		FirebaseApp.initializeApp(options);
	}

	public boolean sendMessage(String msg, String registrationToken) {
		Message message = Message.builder().putData("msg", msg).setToken(registrationToken).build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			LOG.error("Problem sending firebase message", e);
			return false;
		}
		// Response is a message ID string.
		LOG.info("Successfully sent message: " + response);
		return true;

	}
}
