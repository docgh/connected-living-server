package com.connectedliving.closer.robots.temi;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TemiRobotCommandService {

	public void handle(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Command received");
		Services services = Services.getInstance();
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			if (data != null) {
				JSONObject json = new JSONObject(data);
				String robotName = json.getString("robot");
				String command = json.getString("command");
				String facility = json.getString("facility");
				String arguments = json.has("arguments") ? json.getJSONArray("arguments").toString() : "";
				Robot robot = services.getRegistry().getRobot(facility, robotName);
				if (robot == null) {
					// Not registered
				} else {
					RobotQueryService handler = robot.getHandler();
					if (handler == null || handler.hasBeenUsed(request)) {
						FirebaseService fb = services.getFireBase();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
