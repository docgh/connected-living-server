package com.connectedliving.closer;

import org.apache.log4j.BasicConfigurator;

import com.connectedliving.closer.network.CLServer;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.temi.TemiRobotService;

public class Startup {

	static final String testToken = "cnS1eKDITNCiol4vxlQdXS:APA91bE9BgxHrdoCJLHTCQXn9AWJ9--U2Z29xg86Ck637W68nAsVwK60jObI2vK-g4LBYr-IkqOe0yLi-NHRIZnMhiJekXm5zmcFEyC0MqkAUFWqSuH4nza13RPmPGZ2wJY_xXZfOWSO";

	public static void main(String[] args) {
		try {
			BasicConfigurator.configure();
			Services services = Services.getInstance();
			services.setRegistry(new Registry());
			services.registerRobotService(new TemiRobotService());
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
