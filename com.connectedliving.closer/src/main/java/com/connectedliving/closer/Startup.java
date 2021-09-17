package com.connectedliving.closer;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectedliving.closer.configuration.CLConfig;
import com.connectedliving.closer.network.CLServer;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.temi.TemiRobotService;
import com.connectedliving.closer.storage.DatabaseService;
import com.connectedliving.closer.storage.RobotStorageService;
import com.connectedliving.closer.storage.impl.DatabaseServiceImpl;
import com.connectedliving.closer.storage.impl.RobotStorageServiceImpl;

public class Startup {

	static final String testToken = "cnS1eKDITNCiol4vxlQdXS:APA91bE9BgxHrdoCJLHTCQXn9AWJ9--U2Z29xg86Ck637W68nAsVwK60jObI2vK-g4LBYr-IkqOe0yLi-NHRIZnMhiJekXm5zmcFEyC0MqkAUFWqSuH4nza13RPmPGZ2wJY_xXZfOWSO";

	static final Logger LOG = LoggerFactory.getLogger(Startup.class);

	public static void main(String[] args) {
		try {
			BasicConfigurator.configure();
			// Init servicees
			Services services = Services.getInstance();
			// Add configuration
			CLConfig config = new CLConfig();
			services.add(CLConfig.class, config);
			// Add Robot registry
			Registry registry = new Registry();
			services.add(Registry.class, registry);
			// Register Robot Service
			services.registerRobotService(new TemiRobotService());
			services.add(RobotStorageService.class, new RobotStorageServiceImpl(services));
			// Register Firebase
			services.add(FirebaseService.class, new FirebaseService());
			// Register Database
			DatabaseService dbService = new DatabaseServiceImpl(config);
			services.add(DatabaseService.class, dbService);
			new CLServer().start();

			LOG.info("Service started");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
