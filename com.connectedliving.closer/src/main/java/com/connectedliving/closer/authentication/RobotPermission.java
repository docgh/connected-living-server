package com.connectedliving.closer.authentication;

import org.json.JSONObject;

/**
 * Class holder for permissions assigned for a robot
 * 
 * @author Greg Hill <ghill@connectedliving.com>
 *
 */
public class RobotPermission {

	private String name;
	private String facility;
	private long permission;

	public RobotPermission(String facility, String name, long permission) {
		this.facility = facility;
		this.name = name;
		this.permission = permission;
	}

	public RobotPermission(JSONObject json) {
		this.facility = json.getString("f");
		this.name = json.getString("n");
		this.permission = json.getLong("p");
	}

	public String getFacility() {
		return facility;
	}

	public String getName() {
		return name;
	}

	public long gerPermssion() {
		return permission;
	}

	/**
	 * Compare if the facility and robot name match
	 * 
	 * @param id
	 * @return
	 */
	public boolean equals(RobotPermission id) {
		return (this.name.equals(id.getName())) && (this.facility.equals(id.getFacility()));
	}

	/**
	 * Compare if the facility and robot name match
	 * 
	 * @param facility
	 * @param name
	 * @return
	 */
	public boolean equals(String facility, String name) {
		return (this.name.equals(name)) && (this.facility.equals(facility));
	}

	/**
	 * Return JSON representation of the object
	 * 
	 * @return
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("f", facility);
		json.put("n", name);
		json.put("p", permission);
		return json;
	}

}
