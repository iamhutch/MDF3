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
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	URI uri = null;
	URL url = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("WEBCHECK", "STARTING");
		WebView webview = new WebView(this);
		setContentView(webview);
		
		Log.i("WEBCHECK", "PAST CONTENT VIEW");
		
		Intent intent = getIntent();
		Log.i("WEBCHECK", "PAST GET INTENT");
		String dataString = intent.getDataString();
		
		try {
			Log.i("WEBCHECK", "TRYING");
			url = new URL(dataString);
			Log.i("WEBCHECK", "URL");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.e("ERROR", "ERROR TRYING");
		}
		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(url.toString()+"/");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
