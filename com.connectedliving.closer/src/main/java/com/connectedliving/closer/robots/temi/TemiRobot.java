package com.connectedliving.closer.robots.temi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.connectedliving.closer.robots.Robot;

public class TemiRobot extends Robot {

	private List<String> locations;
	public static final int ROBOT_TYPE = 1;

	public TemiRobot(String facility, String robotName, String token, String data) {
		super(facility, robotName, token, ROBOT_TYPE);
		if (data != null) {
			JSONObject json = new JSONObject(data);
			initializeData(json);
		}
	}

	public TemiRobot(String facility, String robotName, String token, JSONObject data) {
		super(facility, robotName, token, ROBOT_TYPE);
		if (data == null)
			return;
		initializeData(data);
	}

	private void initializeData(JSONObject data) {
		locations = new ArrayList<String>();
		setLocations(data);
	}

	/**
	 * Return list of available locations
	 * 
	 * @return
	 */
	public List<String> getLocations() {
		return locations;
	}

	private void setLocations(JSONObject data) {
		if (data.has("Locations")) {
			JSONArray locArray = data.getJSONArray("Locations");
			locArray.forEach((l) -> locations.add(l.toString()));
		}
	}

	@Override
	public int getType() {
		return ROBOT_TYPE;
	}

	@Override
	public void setData(String data) {
		if (data == null)
			return;
		JSONObject json = new JSONObject(data);
		setLocations(json);
	}

	@Override
	public String getData() {
		JSONObject json = new JSONObject();
		if (locations.size() > 0) {
			final JSONArray locJson = new JSONArray();
			locations.stream().forEach((l) -> locJson.put(l));
			json.put("Locations", locJson);
		}
		return json.toString();
	}
}
