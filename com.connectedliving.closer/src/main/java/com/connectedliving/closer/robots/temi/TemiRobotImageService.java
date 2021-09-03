package com.connectedliving.closer.robots.temi;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connectedliving.closer.robots.temi.TemiRobotImageCache.Image;
import com.google.api.client.util.IOUtils;

public class TemiRobotImageService {

	TemiRobotImageCache imageCache;

	public TemiRobotImageService(TemiRobotImageCache cache) {
		this.imageCache = cache;
	}

	private Image getImage(String facility, String robot) {
		Image image;
		boolean timedOut = false;
		int count = 0;
		while (((image = imageCache.getImage(facility, robot)) == null) && !timedOut) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				return null;
			}
			count++;
			if (count > 300) {
				timedOut = true;
			}
		}
		imageCache.deleteImage(facility, robot); // One use
		return image;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String facility = request.getParameter("facility");
		String robot = request.getParameter("robot");
		if (facility == null || robot == null) {
			// throw error
		}
		Image image = getImage(facility, robot);
		if (image != null) {
			response.setContentType("image/jpeg");
			response.setStatus(HttpServletResponse.SC_OK);
			ByteArrayInputStream stream = new ByteArrayInputStream(image.getImage());
			OutputStream out = response.getOutputStream();
			IOUtils.copy(stream, out);
			out.close();
			stream.close();

		}
	}
}
