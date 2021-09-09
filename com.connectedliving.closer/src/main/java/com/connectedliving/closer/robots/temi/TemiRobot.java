package com.connectedliving.closer.robots.temi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.connectedliving.closer.robots.Robot;

public class TemiRobot extends Robot {

	private String battery;
	private List<String> locations;

	public TemiRobot(String facility, String robotName, String token, JSONObject data) {
		super(facility, robotName, token);
		battery = "";
		locations = new ArrayList<String>();
		if (data == null)
			return;
		if (data.has("Locations")) {
			JSONArray locArray = data.getJSONArray("Locations");
			Iterator<String> it = locations.iterator();
			while (it.hasNext()) {
				locArray.put(it.next());
			}
		}
	}

	/**
	 * Return list of available locations
	 * 
	 * @return
	 */
	public List<String> getLocations() {
		return locations;
	}
}
