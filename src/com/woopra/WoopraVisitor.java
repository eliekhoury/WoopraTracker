package com.woopra;

import java.util.Properties;
import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraVisitor {

	private static final String APP_KEY = "Woopra_android";
	private String cookie;
	private Properties properties = null;

	private WoopraVisitor() {
		properties = new Properties();
	}

	public static WoopraVisitor getAnonymousVisitor() {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(getUUID());
		return visitor;
	}

	public static WoopraVisitor getVisitorByCookie(String cookie) {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(cookie);
		return visitor;
	}

	public static WoopraVisitor getVisitorByEmail(String email) {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(getUUID(APP_KEY, email));
		visitor.addProperty("email", email);
		return visitor;
	}

	public static WoopraVisitor getVisitorByString(String key) {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(getUUID(APP_KEY, key));
		visitor.addProperty("email", key);
		return visitor;
	}

	private static String getUUID(String fristKey, String secondKey) {
		long mostSigBits = fristKey.hashCode();
		long leastSigBits = secondKey.hashCode();
		UUID generateUUID = new UUID(mostSigBits, leastSigBits);
		String result = generateUUID.toString();
		result = result.substring(0, 8) + result.substring(9, 13)
				+ result.substring(14, 18) + result.substring(19, 23)
				+ result.substring(24);
		return result.toString();
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
