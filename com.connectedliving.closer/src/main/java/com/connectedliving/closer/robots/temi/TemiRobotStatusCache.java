package com.connectedliving.closer.robots.temi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class TemiRobotStatusCache {

	protected class Status {

		private JSONObject json;
		private long time;

		public Status(JSONObject json) {
			this.json = json;
			this.time = new Date().getTime();
		}

		public long getTime() {
			return time;
		}

		public JSONObject getJson() {
			return json;
		}

	}

	Map<String, Status> map;

	public TemiRobotStatusCache() {
		this.map = new HashMap<String, Status>();
	}

	private String createKey(String facility, String robot) {
		return facility + "-" + robot;
	}

	public void addStatus(String facility, String robot, JSONObject json) {
		if (json == null)
			return;
		map.put(createKey(facility, robot), new Status(json));
	}

	public JSONObject getStatus(String facility, String robot) {
		Status status = map.get(createKey(facility, robot));
		if (status == null)
			return null;
		map.remove(createKey(facility, robot));
		return status.getJson();
	}

}
