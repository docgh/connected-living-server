package com.connectedliving.closer.robots.temi;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemiStatusService {

	private static final Logger LOG = LoggerFactory.getLogger(TemiStatusService.class);
	TemiRobotStatusCache cache;

	public TemiStatusService(TemiRobotStatusCache cache) {
		this.cache = cache;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getParameter("token");
		if (token != null) {
			LOG.info("Received status");
		}
		String facility = request.getParameter("facility");
		String name = request.getParameter("robot");
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
		cache.addStatus(facility, name, jsonData);
	}
}
