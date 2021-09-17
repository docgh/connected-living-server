package com.connectedliving.closer.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.connectedliving.closer.Services;
import com.connectedliving.closer.robots.Robot;
import com.connectedliving.closer.robots.RobotService;
import com.connectedliving.closer.storage.DatabaseService;
import com.connectedliving.closer.storage.RobotStorageService;

public class RobotStorageServiceImpl implements RobotStorageService {

	private static final String SELECT_ROBOT = "SELECT Facility, Name, Token, Registration, Type, Data from robots WHERE Facility = ? AND NAME = ?";
	private static final String INSERT_ROBOT = "INSERT INTO robots (Facility, Name, Token, Registration, Type, Data) VALUES ( ?, ? ,?, ?, ?, ?) ON DUPLICATE KEY UPDATE Token = ?, Registration = ?, Data = ?";

	Services services;

	public RobotStorageServiceImpl(Services services) {
		this.services = services;
	}

	@Override
	public boolean storeRegistration(Robot robot) {
		DatabaseService dbService = services.getService(DatabaseService.class);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = dbService.getReadOnlyCon();
			statement = con.prepareStatement(INSERT_ROBOT);
			int i = 1;
			statement.setObject(i++, robot.getFacility());
			statement.setObject(i++, robot.getName());
			statement.setObject(i++, robot.getToken());
			statement.setObject(i++, robot.getRegisteredDate().getTime());
			statement.setObject(i++, robot.getType());
			statement.setObject(i++, robot.getData());
			statement.setObject(i++, robot.getToken());
			statement.setObject(i++, robot.getRegisteredDate().getTime());
			statement.setObject(i++, robot.getData());
			return statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			dbService.closeAll(statement, con);
		}
	}

	@Override
	public Robot getRobot(String facility, String id) {
		DatabaseService dbService = services.getService(DatabaseService.class);
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = dbService.getReadOnlyCon();
			statement = con.prepareStatement(SELECT_ROBOT);
			int i = 1;
			statement.setObject(i++, facility);
			statement.setObject(i++, id);
			rs = statement.executeQuery();
			if (rs.next()) {
				List<RobotService> robotServices = Services.getInstance().getRobotServices();
				Robot robot = null;
				for (RobotService s : robotServices) {
					if (s.handlesRobotType(rs.getInt("Type"))) {
						robot = s.createRobot(rs.getString("Facility"), rs.getString("Name"), rs.getString("Token"),
								rs.getLong("Registration"), rs.getString("Data"));
					}
				}
				return robot;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbService.closeAll(rs, statement, con);
		}
		return null;
	}

	@Override
	public boolean deleteRobot(Robot robot) {
		// TODO Auto-generated method stub
		return false;
	}
}
