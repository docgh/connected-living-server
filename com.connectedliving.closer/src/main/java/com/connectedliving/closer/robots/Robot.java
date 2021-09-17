package com.connectedliving.closer.robots;

import java.util.Date;

public abstract class Robot {

	private String fireBaseToken;
	private String robotName;
	private String facility;
	private String data;
	private Date registrationTime;
	private RobotQueryService handler;
	private int robotType;

	/**
	 * Create a robot storage class
	 * 
	 * @param facility
	 * @param robotName
	 * @param token
	 */
	public Robot(String facility, String robotName, String token, int robotType) {
		this.facility = facility;
		this.robotName = robotName;
		this.registrationTime = new Date();
		this.fireBaseToken = token;
		this.data = null;
		this.robotType = robotType;
	}

	/**
	 * Create a robot storage class
	 * 
	 * @param facility
	 * @param robotName
	 * @param token
	 */
	public Robot(String facility, String robotName, String token, Long time, int robotType) {
		this.facility = facility;
		this.robotName = robotName;
		this.registrationTime = new Date(time);
		this.fireBaseToken = token;
		this.data = null;
		this.robotType = robotType;
	}

	/**
	 * Return the firebase token
	 * 
	 * @return
	 */
	public String getToken() {
		return this.fireBaseToken;
	}

	/**
	 * Return the robot name
	 * 
	 * @return
	 */
	public String getName() {
		return this.robotName;
	}

	/**
	 * Return the name of the facility
	 * 
	 * @return
	 */
	public String getFacility() {
		return this.facility;
	}

	public Date getRegisteredDate() {
		return this.registrationTime;
	}

	public void setHandler(RobotQueryService handler) {
		this.handler = handler;
	}

	public RobotQueryService getHandler() {
		return handler;
	}

	// Should have override
	public int getType() {
		return 0;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}
}
