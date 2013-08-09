/*
 * project		WebCheck
 * 
 * package		com.lucyhutcheson.webcheck
 * 
 * @author		Lucy Hutcheson
 * 
 * date			Aug 5, 2013
 * 
 */
package com.lucyhutcheson.webcheck;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Class created to receive an intent from another app and display the website
 * from the url received or provide an option to type in your own url if the app
 * was opened independently.
 */
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	// SETUP VARIABLES
	URI uri = null;
	URL url = null;
	EditText urlField;
	WebView webview;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("WEBCHECK", "STARTING MAIN ACTIVITY");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		// SETUP OUR INTENT
		Intent intent = getIntent();
		String dataString = intent.getDataString();

		// CHECK IF WE HAVE ANY URLS TO DISPLAY
		if (dataString != null) {

			webview = (WebView) findViewById(R.id.myWebView);

			// DISMISS THE KEYBOARD SO WE CAN SEE
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(
					((EditText) findViewById(R.id.urlField)).getWindowToken(),
					0);

			// SETUP OUR URL
			try {
				url = new URL(dataString);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e("ERROR", "ERROR TRYING");
			}

			// LOAD UP OUR WEBSITE
			webview.getSettings().setJavaScriptEnabled(true);
			webview.loadUrl(url.toString() + "/");

		}
		// OTHERWISE, LET'S SETUP A UI FOR THE USER

		webview = (WebView) findViewById(R.id.myWebView);
		urlField = (EditText) findViewById(R.id.urlField);

		// GO BUTTON AND HANDLER
		Button goBtn = (Button) findViewById(R.id.goBtn);
		goBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				webview = (WebView) findViewById(R.id.myWebView);
				// DISMISS THE KEYBOARD SO WE CAN SEE
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						((EditText) findViewById(R.id.urlField))
								.getWindowToken(), 0);
				// SETUP OUR URL
				try {
					url = new URL(urlField.getText().toString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Log.e("ERROR", "ERROR TRYING");
				}

				// LOAD UP OUR WEBSITE
				webview.getSettings().setJavaScriptEnabled(true);
				webview.loadUrl(url.toString() + "/");
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
