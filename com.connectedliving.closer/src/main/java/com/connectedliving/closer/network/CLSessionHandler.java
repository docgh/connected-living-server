package com.connectedliving.closer.network;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CLSessionHandler implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Session created - " + event.toString());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Session destroyed - " + event.toString());
	}

}
