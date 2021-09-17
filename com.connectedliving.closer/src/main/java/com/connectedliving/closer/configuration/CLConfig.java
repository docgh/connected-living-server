package com.connectedliving.closer.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLConfig {

	private static final Logger LOG = LoggerFactory.getLogger(CLConfig.class);

	Properties prop;

	public CLConfig() {
		prop = new Properties();
		String fileLocation = System.getProperty("configuration");
		if (fileLocation == null) {
			fileLocation = "/etc/ConnectedLiving/cl.properties"; // Change this to default

		}
		try (FileInputStream fis = new FileInputStream(fileLocation)) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {
			LOG.error("Configuration not found", ex);
			throw new RuntimeException("Config not found", ex);
		} catch (IOException ex) {
			LOG.error("Configuration error", ex);
			throw new RuntimeException("Config error", ex);
		}
	}

	/**
	 * Get String property
	 * 
	 * @param name
	 * @return Property value or null if missing
	 */
	public String getProperty(CLConfigProperty property) {
		String ret = prop.getProperty(property.getValue());
		return ret == null ? (String) property.getDefault() : ret;
	}

	/**
	 * Get integer property
	 * 
	 * @param name
	 * @return Property value cast to int
	 */
	public int getIntProperty(CLConfigProperty property) {
		String p = prop.getProperty(property.getValue());
		return p == null ? (Integer) property.getDefault() : Integer.parseInt(p);
	}

}
