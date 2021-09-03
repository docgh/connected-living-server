package com.connectedliving.closer.robots.temi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TemiRobotQueryService extends RobotQueryService {

	HttpSession session = null;

	public TemiRobotQueryService() {
		super();
		setTimeout();
	}

	JSONObject testJson() {
		JSONObject json = new JSONObject();
		json.put("command", command);
		json.put("arguments", new JSONArray(arguments));
		return json;
	}

	@Override
	public boolean hasBeenUsed(HttpServletRequest request) {
		if (((Request) request).getSessionHandler().getSession(session.getId()) == null)
			return false;
		return used;
	}

	public void handleQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.print(new Date().toLocaleString() + ": ");
		System.out.println("Recieved query");
		if (request.getHeader("Cookie") != null) {
			System.out.println(request.getHeader("Cookie"));
		}
		session = request.getSession(true);
		String robotName = request.getParameter("robot");
		String facility = request.getParameter("facility");
		Robot robot = Services.getInstance().getRegistry().getRobot(facility, robotName);
		if (robot == null) {
			throw new Exception("Robot not found");
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
					session.invalidate();
				} finally {
					if (writer != null) {
						writer.close();
					}
					robot.setHandler(null);
					clearTimeout();
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}