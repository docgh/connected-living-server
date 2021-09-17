package com.connectedliving.closer.robots.temi;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;

public class TemiRobotRegistration {

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
		request.getSession(true);
		String facility = request.getParameter("facility");
		String name = request.getParameter("robot");
		System.out.println("Facility: " + facility);
		System.out.println("Robot:" + name);
		if (token == null || facility == null || name == null) {
			// throw bad registration
		}
		BufferedReader reader = request.getReader();
		String json = reader.readLine();
		JSONObject jsonData = null;
		if (json != null) {
			jsonData = new JSONObject(json);
			System.out.println(jsonData.toString());
		}
		Robot robot = new TemiRobot(facility, name, token, jsonData);
		Registry registry = Services.getInstance().getService(Registry.class);
		registry.addRobot(robot);
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(getJSON().toString());
	}
}
