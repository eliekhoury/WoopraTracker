package com.woopra.samples;

import java.util.Properties;

import com.woopra.R;
import com.woopra.WoopraEvent;
import com.woopra.WoopraTracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Woopra on 1/26/2013
 * 
 */
public class SamplesActivity extends Activity {
	EditText host = null;
	CheckBox ping = null;
	EditText timeout = null;
	EditText visitorKey1 = null;
	EditText visitorValue1 = null;
	EditText visitorKey2 = null;
	EditText visitorValue2 = null;
	EditText eventKey1 = null;
	EditText eventValue1 = null;
	EditText eventKey2 = null;
	EditText eventValue2 = null;
	EditText eventKey3 = null;
	EditText eventValue3 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//
		host = (EditText) findViewById(R.id.host);
		ping = (CheckBox) findViewById(R.id.ping);
		timeout = (EditText) findViewById(R.id.timeout);
		visitorKey1 = (EditText) findViewById(R.id.visitor_key1);
		visitorValue1 = (EditText) findViewById(R.id.visitor_value1);
		visitorKey2 = (EditText) findViewById(R.id.visitor_key2);
		visitorValue2 = (EditText) findViewById(R.id.visitor_value2);

		eventKey1 = (EditText) findViewById(R.id.event_key1);
		eventValue1 = (EditText) findViewById(R.id.event_value1);
		eventKey2 = (EditText) findViewById(R.id.event_key2);
		eventValue2 = (EditText) findViewById(R.id.event_value2);
		eventKey3 = (EditText) findViewById(R.id.event_key3);
		eventValue3 = (EditText) findViewById(R.id.event_value3);
		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new RequestSender().execute();
				Toast.makeText(SamplesActivity.this, "Http request sending...",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	public class RequestSender extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {

			//
			WoopraTracker.getInstance().setup(host.getText().toString());

			// A new instance of the Woopra tracker will be created and we'll
			// set it up for mybusiness.com

			WoopraTracker.getInstance().resetVisitorByContext(
					SamplesActivity.this);
			//
			WoopraTracker.getInstance().setPingEnabled(ping.isChecked());
			WoopraTracker.getInstance().setIdleTimeout(
					Integer.valueOf(timeout.getText().toString())); // seconds

			// When instantiated, the SDK will try to find a store woopra user
			// identifier, if it's not found, it will be generated and saved to
			// be reused in the future. We call this identifier the cookie.
			// Then we should be able to add and remove properties to the
			// Visitor object.
			// method addVisitorProperty example
			WoopraTracker.getInstance().addVisitorProperty(
					visitorKey1.getText().toString(),
					visitorValue1.getText().toString());
			// method addVisitorProperties example
			Properties visitorProperties = new Properties();
			visitorProperties.setProperty(visitorKey2.getText().toString(),
					visitorValue2.getText().toString());
			WoopraTracker.getInstance().addVisitorProperties(visitorProperties); // Properties
																					// object.

			WoopraEvent event = new WoopraEvent("appview");
			// or
			// WoopraEvent event = new WoopraEvent("appview", properties);
			// method addVisitorProperty example
			event.addEventProperty(eventKey1.getText().toString(), eventValue1
					.getText().toString());
			// method addVisitorProperties example
			Properties eventProperties = new Properties();
			eventProperties.setProperty(eventKey2.getText().toString(),
					eventValue2.getText().toString());
			eventProperties.setProperty(eventKey3.getText().toString(),
					eventValue3.getText().toString());
			event.addEventProperties(eventProperties);

			return WoopraTracker.getInstance().trackEvent(event);

		}
	}
}
