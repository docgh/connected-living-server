package com.connectedliving.closer.robots;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RobotService {

	public boolean handlesRegistration(HttpServletRequest request);

	public boolean handlesQuery(HttpServletRequest request);

	public boolean handlesCommand(HttpServletRequest request);

	public boolean handlesPicture(HttpServletRequest request);

	public boolean handlesPictureQuery(HttpServletRequest request);

	public boolean handlesStatus(HttpServletRequest request);

	public void handleRegistration(HttpServletRequest request, HttpServletResponse response);

	public void handleQuery(HttpServletRequest request, HttpServletResponse response);

	public void handleCommand(HttpServletRequest request, HttpServletResponse response);

	public void handlePicture(HttpServletRequest request, HttpServletResponse response);

	public void handlePictureQuery(HttpServletRequest request, HttpServletResponse response);

	public void handleStatus(HttpServletRequest request, HttpServletResponse response);

}
