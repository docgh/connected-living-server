package com.connectedliving.closer.robots;

import java.util.Timer;
import java.util.TimerTask;

import com.connectedliving.closer.network.CLRequest;

public abstract class RobotQueryService {

	protected Object waitObject;
	protected String command;
	protected String arguments;
	protected boolean used;
	private Timer t;
	protected int TIMEOUT = 30 * 60 * 1000; // 30 minutes

	public RobotQueryService() {
		waitObject = new Object();
		t = new Timer();
		command = "";
		arguments = "";
		used = false;
	}

	/**
	 * If request has been used and completed
	 * 
	 * @return
	 */
	public boolean hasBeenUsed(CLRequest request) {
		return used;
	}

	/**
	 * Perform the command
	 * 
	 * @param command
	 */
	public void perform(String command, String arguments) {
		this.command = command;
		this.arguments = arguments;
		used = true;
		synchronized (waitObject) {
			waitObject.notify();
			t.cancel(); // void the timeout
		}
	}

	/**
	 * Set a timeout where connection will be closed
	 */
	protected void setTimeout() {
		Timeout timeout = new Timeout();
		t.schedule(timeout, TIMEOUT);
	}

	protected void clearTimeout() {
		t.cancel();
	}

	/**
	 * Simple timer class
	 * 
	 * @author Greg Hill <ghill@connectedliving.com>
	 *
	 */
	class Timeout extends TimerTask {

		@Override
		public void run() {
			perform("refresh", "");
		}
	}

}
