package com.connectedliving.closer.robots.testRobot;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TestRobotQueryService extends RobotQueryService {

	public TestRobotQueryService() {
		super();
	}

	JSONObject testJson() {
		JSONObject json = new JSONObject();
		json.put("command", command);
		JSONArray array = new JSONArray();
		array.put(1);
		array.put(2);
		json.put("arguments", array);
		return json;
	}

	public void handleQuery(HttpServletRequest request, HttpServletResponse response) {
		System.out.print(new Date().toLocaleString() + ": ");
		System.out.println("Recieved query");
		if (request.getHeader("Server") != null) {
			System.out.println(request.getHeader("Server"));
		}
		String robotName = request.getParameter("robot");
		Robot robot = Services.getInstance().getRegistry().getRobot("F", robotName);
		if (robot == null) {
			// Throw robot not found
		}
		robot.setHandler(this);
		try {
			synchronized (waitObject) {
				waitObject.wait();
				response.setContentType("application/json");
				response.setStatus(HttpServletResponse.SC_OK);
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
					writer.println(testJson().toString());
					response.flushBuffer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (writer != null) {
						writer.close();
					}
					robot.setHandler(null);
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
