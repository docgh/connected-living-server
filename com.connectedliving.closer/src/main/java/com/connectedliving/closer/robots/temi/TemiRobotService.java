package com.connectedliving.closer.robots.temi;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotService;

public class TemiRobotService implements RobotService {

	TemiRobotImageCache imageCache;
	TemiRobotStatusCache statusCache;

	public TemiRobotService() {
		imageCache = new TemiRobotImageCache();
		statusCache = new TemiRobotStatusCache();
	}

	public boolean handlesRegistration(HttpServletRequest request) {
		return true;
	}

	public boolean handlesQuery(HttpServletRequest request) {
		return true;
	}

	public boolean handlesCommand(HttpServletRequest request) {
		return true;
	}

	public boolean handlesPicture(HttpServletRequest request) {
		return true;
	}

	public boolean handlesPictureQuery(HttpServletRequest request) {
		return true;
	}

	public boolean handlesStatus(HttpServletRequest request) {
		return true;
	}

	public void handleRegistration(HttpServletRequest request, HttpServletResponse response) {
		TemiRobotRegistration registration = new TemiRobotRegistration();
		try {
			registration.doRegister(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handleQuery(HttpServletRequest request, HttpServletResponse response) {
		TemiRobotQueryService query = new TemiRobotQueryService();
		try {
			query.handleQuery(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleCommand(HttpServletRequest request, HttpServletResponse response) {
		TemiRobotCommandService command = new TemiRobotCommandService(statusCache);
		command.handle(request, response);

	}

	public void handlePicture(HttpServletRequest request, HttpServletResponse response) {
		try {
			imageCache.handle(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handlePictureQuery(HttpServletRequest request, HttpServletResponse response) {
		TemiRobotImageService imageService = new TemiRobotImageService(imageCache);
		try {
			imageService.handle(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handleStatus(HttpServletRequest request, HttpServletResponse response) {
		TemiStatusService service = new TemiStatusService(statusCache);
		try {
			service.handle(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean handlesRobotType(int robotType) {
		return robotType == TemiRobot.ROBOT_TYPE;
	}

	@Override
	public Robot createRobot(String facility, String name, String token, Long time, String data) {
		return new TemiRobot(facility, name, token, data);
	}

}
