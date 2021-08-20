package com.connectedliving.closer.storage;

import com.connectedliving.closer.robots.Robot;

public interface StorageService {

	boolean storeRegistration(Robot robot);

	boolean getRobot(String facility, String id);

	boolean deleteRobot(Robot robot);

}
