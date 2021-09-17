package com.connectedliving.closer.robots.temi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Registry;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotQueryService;

public class TemiRobotQueryService extends RobotQueryService {

	private static final Logger LOG = LoggerFactory.getLogger(TemiRobotQueryService.class);
	HttpSession session = null;

	public TemiRobotQueryService() {
		super();
		setTimeout();
	}

	JSONObject createJson() {
		JSONObject json = new JSONObject();
		json.put("command", command);
		if (arguments != null && !arguments.isEmpty()) {
			try {
				json.put("arguments", new JSONArray(arguments));
			} catch (JSONException ex) {
				LOG.error("Error parsing command arguments", ex);
			}
		}
		return json;
	}

	@Override
	public boolean hasBeenUsed(HttpServletRequest request) {
		if (((Request) request).getSessionHandler().getSession(session.getId()) == null)
			return false;
		return used;
	}

	public void handleQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOG.debug("Recieved query");
		if (request.getHeader("Cookie") != null) {
			LOG.debug("Cookie: " + request.getHeader("Cookie"));
		}
		session = request.getSession(true);
		String robotName = request.getParameter("robot");
		String facility = request.getParameter("facility");
		Robot robot = Services.getInstance().getService(Registry.class).getRobot(facility, robotName);
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
					writer.println(createJson().toString());
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
