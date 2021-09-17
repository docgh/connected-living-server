package com.connectedliving.closer.robots.temi;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TemiRobotCommandService {

	private static final int statusTimeout = 10000;
	TemiRobotStatusCache cache;

	public TemiRobotCommandService(TemiRobotStatusCache cache) {
		this.cache = cache;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Command received");

		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			if (data != null) {
				JSONObject json = new JSONObject(data);
				String command = json.getString("command");
				if (command != null && command.equals("status")) {
					handleStatus(json, request, response);
				} else {
					sendCommand(json, request);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleStatus(JSONObject json, HttpServletRequest request, HttpServletResponse response)
			throws InterruptedException, IOException {
		long time = new Date().getTime();
		boolean done = false;
		JSONObject status = null;
		while (!done && status == null) {
			sendCommand(json, request); // send the status command;
			String robotName = json.getString("robot");
			String facility = json.getString("facility");
			int count = 0;
			while (count++ < 4 && status == null) {
				status = cache.getStatus(facility, robotName);
				if (status == null) {
					System.out.println("Waiting");
					Thread.sleep(500);
				}
			}
			if (new Date().getTime() - time > statusTimeout) {
				done = true;
			}
		}
		if (status != null) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
			response.getWriter().write(status.toString());
		} else {
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
	}

	private void sendCommand(JSONObject json, HttpServletRequest request) {
		Services services = Services.getInstance();
		String robotName = json.getString("robot");
		String command = json.getString("command");
		String facility = json.getString("facility");
		String arguments = json.has("arguments") ? json.getJSONArray("arguments").toString() : "";
		Robot robot = services.getService(Registry.class).getRobot(facility, robotName);
		if (robot == null) {
			// Not registered
			System.out.println("ERROR, NO ROBOT");
		} else {
			RobotQueryService handler = robot.getHandler();
			if (handler == null || handler.hasBeenUsed(request)) {
				FirebaseService fb = services.getService(FirebaseService.class);
				if (fb != null) {
					System.out.println("Firebase");
					if (fb.sendMessage(json.toString(), robot.getToken())) {

					} else {
						// Fail
					}

				}
			} else {
				handler.perform(command, arguments);
			}
		}
	}
}
