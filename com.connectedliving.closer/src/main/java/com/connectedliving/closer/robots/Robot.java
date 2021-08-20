package com.connectedliving.closer.robots;

import java.util.Date;

public class Robot {

	private String fireBaseToken;
	private String robotName;
	private String facility;
	private Date registrationTime;
	private RobotQueryService handler;

	/**
	 * Create a robot storage class
	 * 
	 * @param facility
	 * @param robotName
	 * @param token
	 */
	public Robot(String facility, String robotName, String token) {
		this.facility = facility;
		this.robotName = robotName;
		this.registrationTime = new Date();
		this.fireBaseToken = token;
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
}
