package com.connectedliving.closer.robots.temi;

import com.connectedliving.closer.authentication.Permissions;

public enum TemiCommand {

	MOVE_FORWARD("move_forward", Permissions.DIRECTIONAL_CONTROL),
	MOVE_BACK("move_back", Permissions.DIRECTIONAL_CONTROL), MOVE_STOP("move_stop", Permissions.DIRECTIONAL_CONTROL),
	ROTATE_RIGHT("rotate_right", Permissions.DIRECTIONAL_CONTROL),
	ROTATE_LEFT("rotate_left", Permissions.DIRECTIONAL_CONTROL),

	GOTO_LOCATION("goto_location", Permissions.LOCATION_CONTROL),

	// Camera movement
	CAMERA_UP("camera_up", Permissions.MOVE_CAMERA), CAMERA_DOWN("camera_down", Permissions.MOVE_CAMERA),
	CAMERA_LEFT("camera_left", Permissions.MOVE_CAMERA), CAMERA_RIGHT("camera_right", Permissions.MOVE_CAMERA),

	// Camera
	CAMERA_PICTURE("camera_picture", Permissions.CAMERA_PERMISSION),

	// Admin / Status
	STATUS("status", 0),;

	private long permission;
	private String command;

	TemiCommand(String command, long permission) {
		this.command = command;
		this.permission = permission;
	}

	public String getCommand() {
		return command;
	}

	public long getPermission() {
		return permission;
	}

}
