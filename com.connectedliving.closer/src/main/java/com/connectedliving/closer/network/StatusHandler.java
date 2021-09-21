package com.connectedliving.closer.network;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.RobotService;

public class StatusHandler extends AbstractHandler {

	StatusHandler(Services services) {
		super(services);
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<RobotService> services = Services.getInstance().getRobotServices();
		for (RobotService service : services) {
			if (service.handlesStatus(request)) {
				service.handleStatus(request, response);
			}
		}
	}
}
