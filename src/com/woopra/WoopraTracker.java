package com.woopra;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.util.Property;

/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraTracker {

	public static String LOG_TAG = "WoopraTracker";
	private static final String W_EVENT_ENDPOINT = "http://www.woopra.com/track/ce/";
	private static WoopraTracker gSingleton = null;
	private String domain = null;
	// default timeout value for Woopra service
	private int idleTimeout = 30;
	private boolean pingEnabled = false;
	//
	private String referer = null;

	private WoopraVisitor visitor = null;

	private WoopraTracker() {
	}

	public static WoopraTracker getInstance() {
		if (gSingleton == null) {
			gSingleton = new WoopraTracker();
			gSingleton.setVisitor(WoopraVisitor.anonymousVisitor());
		}
		return gSingleton;
	}

	public boolean trackEvent(WoopraEvent event) {
		if (getDomain() == null) {
			Log.i(LOG_TAG,
					"WTracker.domain property must be set before [WTracker trackEvent:] invocation. Ex.: tracker.domain = mywebsite.com");
			return false;
		}
		if (getVisitor() == null) {
			Log.i(LOG_TAG,
					"WTracker.visitor property must be set before [WTracker trackEvent:] invocation");
			return false;
		}
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(W_EVENT_ENDPOINT).append("?host=")
				.append(getDomain()).append("&cookie=")
				.append(getVisitor().getCookie())
				.append("&response=xml&timeout=").append(idleTimeout);
		if (referer != null) {
			urlBuilder.append("&referer=").append(referer);
		}
		// Add visitors properties
		for (Entry<Object, Object> entry : visitor.getProperties().entrySet()) {
			urlBuilder.append("&cv_").append(entry.getKey()).append("=")
					.append(entry.getValue());
		}
		// Add Event properties
		for (Entry<Object, Object> entry : event.getProperties().entrySet()) {
			urlBuilder.append("&ce_").append(entry.getKey()).append("=")
					.append(entry.getValue());
		}
		Log.i(LOG_TAG, "Final url:" + urlBuilder.toString());
		//
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlBuilder.toString());
		try {
			HttpResponse response = httpClient.execute(httpGet);
			Log.i(LOG_TAG,
					"Response:" + EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			Log.e(LOG_TAG, "Got error!", e);
			return false;
		}
		return true;
	}

	public String getDomain() {
		return domain;
	}

	public void setup(String domain) {
		this.domain = domain;
	}

	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public boolean isPingEnabled() {
		return pingEnabled;
	}

	public void setPingEnabled(boolean pingEnabled) {
		this.pingEnabled = pingEnabled;
	}

	public WoopraVisitor getVisitor() {
		return visitor;
	}

	public void setVisitor(WoopraVisitor visitor) {
		this.visitor = visitor;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public void addVisitorProperty(String key, String value) {
		getVisitor().addProperty(key, value);
	}

	public void addVisitorProperties(Properties newProperties) {
		getVisitor().addProperties(newProperties);
	}
}
