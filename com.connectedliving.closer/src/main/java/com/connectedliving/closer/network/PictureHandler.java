package com.connectedliving.closer.network;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.RobotService;

public class PictureHandler extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		List<RobotService> services = Services.getInstance().getRobotServices();
		for (RobotService service : services) {
			if (service.handlesPicture(request)) {
				service.handlePicture(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		List<RobotService> services = Services.getInstance().getRobotServices();
		for (RobotService service : services) {
			if (service.handlesPictureQuery(request)) {
				service.handlePictureQuery(request, response);
			}
		}
	}

}