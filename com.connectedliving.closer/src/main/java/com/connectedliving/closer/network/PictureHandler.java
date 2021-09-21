package com.connectedliving.closer.network;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.authentication.UserSession;
import com.connectedliving.closer.exceptions.CLException;
import com.connectedliving.closer.robots.RobotService;

public class PictureHandler extends AbstractHandler {

	PictureHandler(Services services) {
		super(services);
	}

	@Override
	public void do_doPost(HttpServletRequest request, HttpServletResponse response) {
		if (isRobotAuthenticated(request, response)) {
			List<RobotService> services = Services.getInstance().getRobotServices();
			for (RobotService service : services) {
				if (service.handlesPicture(request)) {
					service.handlePicture(request, response);
				}
			}
		}

	}

	@Override
	public void do_doGet(HttpServletRequest request, HttpServletResponse response) throws CLException {
		CLRequest req = new CLRequest(request);
		UserSession session = authenticateUser(req, response);
		List<RobotService> services = Services.getInstance().getRobotServices();
		for (RobotService service : services) {
			if (service.handlesPictureQuery(req)) {
				service.handlePictureQuery(req, response);
			}
		}

	}

}
