package com.connectedliving.closer.authentication;

public class Permissions {

	public static final long CAMERA_PERMISSION = 0b1; // Get image/video
	public static final long MOVE_CAMERA = 0b10; // Manipulate camera position

	public static final long MIC_PERMISSION = 0b100; // Listen

	public static final long DIRECTIONAL_CONTROL = 0b1000; // Control direction/movement of robot
	public static final long LOCATION_CONTROL = 0b10000; // Move to specified locations

}
