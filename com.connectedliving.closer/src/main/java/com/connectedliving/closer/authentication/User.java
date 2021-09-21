package com.connectedliving.closer.authentication;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User with associated permissions
 * 
 * @author Greg Hill <ghill@connectedliving.com>
 *
 */
public class User {

	private String username;
	private List<RobotPermission> robots;

	public User(String username, String robotPermissions) {
		this.username = username;
		robots = new ArrayList();
		if (robotPermissions != null && robotPermissions.contains("{")) {
			JSONArray robotArray = new JSONArray(robotPermissions);
			if (robotArray.length() > 0) {
				robotArray.forEach((r) -> {
					robots.add(new RobotPermission((JSONObject) r));
				});
			}
		}
	}

	/**
	 * Return the permssion that the user has for a given robot
	 * 
	 * @param facility
	 * @param name
	 * @return permission mask
	 */
	public long getPermission(String facility, String name) {
		long perm = 0;
		for (int i = 0; i < robots.size(); i++) {
			if (robots.get(i).equals(facility, name)) {
				perm = robots.get(i).gerPermssion();
			}
		}
		return perm;
	}

	/**
	 * Get JSONArray representation of the permissions
	 * 
	 * @return
	 */
	public String getPermissionJsonArray() {
		JSONArray perms = new JSONArray();
		robots.forEach((r) -> {
			perms.put(r.toJSON());
		});
		return perms.toString();
	}

}
