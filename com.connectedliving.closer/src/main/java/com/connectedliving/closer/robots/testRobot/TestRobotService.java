package com.connectedliving.closer.robots.testRobot;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.robots.RobotService;

public class TestRobotService implements RobotService {

	public boolean handlesRegistration(HttpServletRequest request) {
		return true;
	}

	public boolean handlesQuery(HttpServletRequest request) {
		return true;
	}

	public boolean handlesCommand(HttpServletRequest request) {
		return true;
	}

	public void handleRegistration(HttpServletRequest request, HttpServletResponse response) {
		TestRobotRegistration registration = new TestRobotRegistration();
		try {
			registration.doRegister(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handleQuery(HttpServletRequest request, HttpServletResponse response) {
		TestRobotQueryService query = new TestRobotQueryService();
		query.handleQuery(request, response);
	}

	public void handleCommand(HttpServletRequest request, HttpServletResponse response) {
		TestRobotCommandService command = new TestRobotCommandService();
		command.handle(request, response);

	}

}
