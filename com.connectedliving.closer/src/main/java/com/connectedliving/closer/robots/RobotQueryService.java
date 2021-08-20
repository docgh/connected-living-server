package com.connectedliving.closer.robots;

public abstract class RobotQueryService {

	protected Object waitObject;
	protected String command;
	private boolean used;

	public RobotQueryService() {
		waitObject = new Object();
		command = "";
		used = false;
	}

	public boolean hasBeenUsed() {
		return used;
	}

	public void perform(String command) {
		this.command = command;
		used = true;
		synchronized (waitObject) {
			waitObject.notify();
		}
	}

}
