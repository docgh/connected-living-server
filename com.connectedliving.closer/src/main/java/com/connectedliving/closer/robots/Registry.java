package com.connectedliving.closer.robots;

import java.util.HashMap;
import java.util.Map;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.storage.RobotStorageService;

public class Registry {

	////////// Temporary. Should be database implementation

	Map<String, Robot> robotsCache;

	public Registry() {
		robotsCache = new HashMap<String, Robot>();
	}

	private String createIndexId(Robot robot) {
		return createIndex(robot.getFacility(), robot.getName());
	}

	private String createIndex(String facility, String name) {
		return facility + "-" + name;
	}

	public void addRobot(Robot robot) {
		String index = createIndexId(robot);
		robotsCache.put(index, robot);
		Services.getInstance().getService(RobotStorageService.class).storeRegistration(robot);
	}

	public Robot getRobot(String facility, String name) {
		String index = createIndex(facility, name);
		Robot robot = robotsCache.get(index);
		if (robot != null) {
			return robot;
		}
		robot = Services.getInstance().getService(RobotStorageService.class).getRobot(facility, name);
		if (robot != null) {
			robotsCache.put(index, robot);
		}
		return robot;
	}

	public String getToken(String facility, String name) {
		Robot robot = getRobot(facility, name);
		if (robot == null) {
			return null;
		}
		return robot.getToken();
	}
}
