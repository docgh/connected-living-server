package com.connectedliving.closer.robots.testRobot;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.network.firebase.FirebaseService;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TestRobotCommandService {

	public void handle(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Command received");
		Services services = Services.getInstance();
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			if (data != null) {
				JSONObject json = new JSONObject(data);
				String robotName = json.getString("robot");
				String command = json.getString("command");
				Robot robot = services.getRegistry().getRobot("F", robotName);
				if (robot == null) {
					// Not registered
				} else {
					RobotQueryService handler = robot.getHandler();
					if (handler == null || handler.hasBeenUsed()) {
						FirebaseService fb = services.getFireBase();
						if (fb != null) {
							if (fb.sendMessage(json.toString(), robot.getToken())) {
								// OK
							} else {
								// Fail
							}
						}
					} else {
						handler.perform(command);
					}
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
