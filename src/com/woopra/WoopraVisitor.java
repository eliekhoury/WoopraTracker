package com.woopra;

import java.util.Properties;
import java.util.UUID;

public class WoopraVisitor {

	private String cookie;
	private Properties properties = null;

	private WoopraVisitor() {
		properties = new Properties();
	}

	public static WoopraVisitor anonymousVisitor() {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(getUUID());
		return visitor;
	}

	private static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void addProperties(Properties newProperties) {
		properties.putAll(newProperties);
	}

	public void addProperty(String key, String value) {
		properties.setProperty(key, value);
	}
}
