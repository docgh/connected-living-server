package com.connectedliving.closer;

import org.apache.log4j.BasicConfigurator;

import com.connectedliving.closer.network.CLServer;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.testRobot.TestRobotService;

public class Startup {

	static final String testToken = "fYY0aKQ2SpGWuIRLAk-Zrw:APA91bF8Xhxa6nDMYWvMFr37sTMALoJ2DEzRiGlHCPILggx51s-wJHOSHZqi5ZQ2825RKgS4oAUNKI7ktcC6f_SRmxnUu-wqYGBcbyugfXMk0PKDO4crvXDlqgZJjoWeJ84RgrbuYv0d";

	public static void main(String[] args) {
		try {
			BasicConfigurator.configure();
			Services services = Services.getInstance();
			services.setRegistry(new Registry());
			services.registerRobotService(new TestRobotService());
			services.setFirebaseService(new FirebaseService());
			new CLServer().start();

			// For testing
			Robot testRobot = new Robot("F", "1", testToken);
			Services.getInstance().getRegistry().addRobot(testRobot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
