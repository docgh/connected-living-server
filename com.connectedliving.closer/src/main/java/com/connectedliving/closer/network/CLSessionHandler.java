package com.connectedliving.closer.network;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLSessionHandler implements HttpSessionListener {

	private static final Logger LOG = LoggerFactory.getLogger(CLSessionHandler.class);

	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		LOG.info("Session created - " + event.toString());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		LOG.info("Session destroyed - " + event.toString());
	}

}
