package com.connectedliving.closer.network;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.RobotService;

public class RegistryHandler extends HttpServlet {

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<RobotService> services = Services.getInstance().getRobotServices();
		for (RobotService service : services) {
			if (service.handlesRegistration(request)) {
				service.handleRegistration(request, response);
			}
		}
	}
}
