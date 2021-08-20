package com.connectedliving.closer.robots.testRobot;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;

public class TestRobotRegistration {

	private static String SERVERID = "TEST_SERVER";

	private JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("serverId", SERVERID);
		return json;
	}

	public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getParameter("token");
		if (token != null) {
			System.out.println("Received startup: " + token);
		}
		String facility = request.getParameter("facility");
		String name = request.getParameter("robot");
		if (token == null || facility == null || name == null) {
			// throw bad registration
		}
		Robot robot = new Robot(facility, name, token);
		Registry registry = Services.getInstance().getRegistry();
		registry.addRobot(robot);
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(getJSON().toString());
	}
}
