package com.connectedliving.closer.storage;

import com.connectedliving.closer.robots.Robot;

public interface RobotStorageService {

	boolean storeRegistration(Robot robot);

	Robot getRobot(String facility, String id);

	boolean deleteRobot(Robot robot);

}
