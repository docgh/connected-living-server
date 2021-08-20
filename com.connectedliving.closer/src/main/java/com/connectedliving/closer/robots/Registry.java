package com.connectedliving.closer.robots;

import java.util.HashMap;
import java.util.Map;

public class Registry {

	////////// Temporary. Should be database implementation

	Map<String, Robot> robots;

	public Registry() {
		robots = new HashMap<String, Robot>();
	}

	private String createIndexId(Robot robot) {
		return createIndex(robot.getFacility(), robot.getName());
	}

	private String createIndex(String facility, String name) {
		return facility + "-" + name;
	}

	public void addRobot(Robot robot) {
		String index = createIndexId(robot);
		robots.put(index, robot);
	}

	public Robot getRobot(String facility, String name) {
		String index = createIndex(facility, name);
		return robots.get(index);
	}

	public String getToken(String facility, String name) {
		String index = createIndex(facility, name);
		if (robots.containsKey(index)) {
			return robots.get(index).getToken();
		}
		return null;
	}
}
