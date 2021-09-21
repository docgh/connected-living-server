package com.connectedliving.closer.robots;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.authentication.User;
import com.connectedliving.closer.exceptions.CLException;
import com.connectedliving.closer.network.CLRequest;

public interface RobotService {

	public boolean handlesRegistration(HttpServletRequest request);

	public boolean handlesQuery(HttpServletRequest request);

	public boolean handlesCommand(CLRequest request);

	public boolean handlesPicture(HttpServletRequest request);

	public boolean handlesPictureQuery(CLRequest request);

	public boolean handlesStatus(HttpServletRequest request);

	public void handleRegistration(HttpServletRequest request, HttpServletResponse response);

	public void handleQuery(HttpServletRequest request, HttpServletResponse response);

	public void handleCommand(User user, CLRequest request, HttpServletResponse response) throws CLException;

	public void handlePicture(HttpServletRequest request, HttpServletResponse response);

	public void handlePictureQuery(CLRequest request, HttpServletResponse response);

	public void handleStatus(HttpServletRequest request, HttpServletResponse response);

	public boolean handlesRobotType(int robotType);

	public Robot createRobot(String facility, String name, String token, Long time, String data);

}
