package com.connectedliving.closer.robots.temi;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.authentication.User;
import com.connectedliving.closer.exceptions.AuthException;
import com.connectedliving.closer.exceptions.CLException;
import com.connectedliving.closer.network.CLRequest;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TemiRobotCommandService {

	private static final Logger LOG = LoggerFactory.getLogger(TemiRobotCommandService.class);
	private static final int statusTimeout = 10000;
	TemiRobotStatusCache cache;

	public TemiRobotCommandService(TemiRobotStatusCache cache) {
		this.cache = cache;
	}

	public void handle(User user, CLRequest request, HttpServletResponse response) throws CLException {
		LOG.info("Command received");

		try {
			// String data =
			// request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			String data = request.getBody();
			if (data != null) {
				JSONObject json = new JSONObject(data);
				String command = json.getString("command");
				if (command != null && command.equals("status")) {
					handleStatus(json, request, response);
				} else {
					sendCommand(user, json, request);
				}
			}
		} catch (IOException e) {
			LOG.error("Error processing command", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkPermission(User user, Robot robot, String command) throws CLException {
		if (user == null)
			return;
		long permissions = -1;

		for (TemiCommand com : TemiCommand.values()) {
			if (com.getCommand().equals(command)) {
				permissions = com.getPermission();
			}
		}
		if (permissions == -1)
			throw new AuthException(AuthException.ExceptionType.INSUFFICIENT_PERMISSION);

		if ((permissions & user.getPermission(robot.getFacility(), robot.getName())) == 0) {
			throw new AuthException(AuthException.ExceptionType.INSUFFICIENT_PERMISSION);
		}
	}

	private void handleStatus(JSONObject json, CLRequest request, HttpServletResponse response)
			throws InterruptedException, IOException, CLException {
		long time = new Date().getTime();
		boolean done = false;
		JSONObject status = null;
		while (!done && status == null) {
			sendCommand(null, json, request); // send the status command;
			String robotName = json.getString("robot");
			String facility = json.getString("facility");
			int count = 0;
			while (count++ < 4 && status == null) {
				status = cache.getStatus(facility, robotName);
				if (status == null) {
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

	private void sendCommand(User user, JSONObject json, CLRequest request) throws CLException {
		Services services = Services.getInstance();
		String robotName = json.getString("robot");
		String command = json.getString("command");
		String facility = json.getString("facility");
		String arguments = json.has("arguments") ? json.getJSONArray("arguments").toString() : "";
		Robot robot = services.getService(Registry.class).getRobot(facility, robotName);
		checkPermission(user, robot, command);
		if (robot == null) {
			// Not registered
			LOG.error("No ROBOT FOUND");
		} else {
			RobotQueryService handler = robot.getHandler();
			if (handler == null || handler.hasBeenUsed(request)) {
				FirebaseService fb = services.getService(FirebaseService.class);
				if (fb != null) {
					LOG.info("Sending firebase message");
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
