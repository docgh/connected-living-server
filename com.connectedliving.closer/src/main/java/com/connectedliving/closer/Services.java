package com.connectedliving.closer;

import java.util.ArrayList;
import java.util.List;

import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.RobotService;

// DO WE WANT OSGI OR OTHER SERVICE REGISTRY????

public class Services {

	private Registry registry;
	private ArrayList<RobotService> robotServices;
	private FirebaseService firebase;
	static Services instance;

	public Services() {
		this.robotServices = new ArrayList<RobotService>();
	}

	public static Services getInstance() {
		if (instance == null) {
			instance = new Services();
		}
		return instance;
	}

	public void setRegistry(Registry handler) {
		this.registry = handler;
	}

	public Registry getRegistry() {
		if (registry == null) {
			// THROW NO SERVICE
		}
		return registry;
	}

	public List getRobotServices() {
		return robotServices;
	}

	public void registerRobotService(RobotService service) {
		robotServices.add(service);
	}

	public void setFirebaseService(FirebaseService firebase) {
		this.firebase = firebase;
	}

	public FirebaseService getFireBase() {
		return firebase;
	}

}
