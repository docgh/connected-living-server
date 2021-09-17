package com.connectedliving.closer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.connectedliving.closer.robots.RobotService;

// DO WE WANT OSGI OR OTHER SERVICE REGISTRY????

public class Services {

	private ArrayList<RobotService> robotServices;
	static Services instance;
	private final ConcurrentMap<Class<?>, Object> services;

	public Services() {
		services = new ConcurrentHashMap<Class<?>, Object>();
		this.robotServices = new ArrayList<RobotService>();
	}

	public static Services getInstance() {
		if (instance == null) {
			instance = new Services();
		}
		return instance;
	}

	public <S> S getService(Class<? extends S> clazz) {
		@SuppressWarnings("unchecked")
		S service = (S) services.get(clazz);
		if (null == service) {
			throw new IllegalStateException("Missing service " + clazz.getName());
		}
		return service;
	}

	public <S> void add(Class<? extends S> clazz, S service) {
		services.put(clazz, service);
	}

	public List getRobotServices() {
		return robotServices;
	}

	public void registerRobotService(RobotService service) {
		robotServices.add(service);
	}

}
