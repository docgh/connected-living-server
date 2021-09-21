package com.connectedliving.closer.network;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.authentication.UserSession;
import com.connectedliving.closer.exceptions.CLException;
import com.connectedliving.closer.robots.RobotService;

public class CommandHandler extends AbstractHandler {

	CommandHandler(Services services) {
		super(services);
	}

	@Override
	public void do_doPut(HttpServletRequest request, HttpServletResponse response) throws CLException {
		CLRequest req = new CLRequest(request);
		UserSession session = authenticateUser(req, response);
		List<RobotService> services = Services.getInstance().getRobotServices();
		for (RobotService service : services) {
			if (service.handlesCommand(req)) {
				service.handleCommand(session.getUser(), req, response);
			}
		}
	}

}
