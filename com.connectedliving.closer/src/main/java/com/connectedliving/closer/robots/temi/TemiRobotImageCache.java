package com.connectedliving.closer.robots.temi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class TemiRobotImageCache {

	private static int MAX_SIZE = 200000;

	public class Image {
		private long time;
		private byte[] image;

		public Image(byte[] image) {
			this.time = new Date().getTime();
			this.image = image;
		}

		public byte[] getImage() {
			return image;
		}

		public long getTime() {
			return time;
		}
	}

	Map<String, Image> imageMap;

	public TemiRobotImageCache() {
		this.imageMap = new HashMap<String, Image>();
	}

	public Image getImage(String facility, String robot) {
		return imageMap.get(createId(facility, robot));
	}

	public void deleteImage(String facility, String robot) {
		imageMap.remove(createId(facility, robot));
	}

	private String createId(String facility, String robot) {
		return facility + ":" + robot;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String facility = request.getParameter("facility");
		String robot = request.getParameter("robot");
		if (facility == null || robot == null) {
			// throw error
		}
		Part filePart = request.getPart("file");
		InputStream inputStream = filePart.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		int totalSize = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, length);
			totalSize += length;
			if (totalSize > MAX_SIZE) {
				// throw new Exception("Size exceeded");
			}
		}
		outStream.close();
		inputStream.close();
		imageMap.put(createId(facility, robot), new Image(outStream.toByteArray()));
	}

}
