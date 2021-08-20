package com.connectedliving.closer.robots;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RobotService {

	public boolean handlesRegistration(HttpServletRequest request);

	public boolean handlesQuery(HttpServletRequest request);

	public boolean handlesCommand(HttpServletRequest request);

	public void handleRegistration(HttpServletRequest request, HttpServletResponse response);

	public void handleQuery(HttpServletRequest request, HttpServletResponse response);

	public void handleCommand(HttpServletRequest request, HttpServletResponse response);

}
