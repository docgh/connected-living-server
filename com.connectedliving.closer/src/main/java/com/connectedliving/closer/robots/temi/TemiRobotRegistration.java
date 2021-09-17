package com.connectedliving.closer.robots.temi;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;

public class TemiRobotRegistration {

	private static final Logger LOG = LoggerFactory.getLogger(TemiRobotRegistration.class);
	private static String SERVERID = "TEST_SERVER";

	private JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("serverId", SERVERID);
		return json;
	}

	public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getParameter("token");
		if (token != null) {
			LOG.info("Received startup: " + token);
		}
		request.getSession(true);
		String facility = request.getParameter("facility");
		String name = request.getParameter("robot");
		LOG.debug("Facility: " + facility);
		LOG.debug("Robot:" + name);
		if (token == null || facility == null || name == null) {
			// throw bad registration
		}
		BufferedReader reader = request.getReader();
		String json = reader.readLine();
		JSONObject jsonData = null;
		if (json != null) {
			jsonData = new JSONObject(json);
			LOG.debug(jsonData.toString());
		}
		Robot robot = new TemiRobot(facility, name, token, jsonData);
		Registry registry = Services.getInstance().getService(Registry.class);
		registry.addRobot(robot);
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(getJSON().toString());
	}
}
