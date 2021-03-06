/*
 * project		DiningHeart
 * 
 * package		com.lucyhutcheson.diningheart
 * 
 * @author		Lucy Hutcheson
 * 
 * date			Aug 26, 2013
 * 
 */
package com.lucyhutcheson.diningheart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AddActivity extends Activity {
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		final JSInterface jsInterface = new JSInterface(this);
		WebView myWebView = (WebView) findViewById(R.id.addWebView);
		myWebView.setWebViewClient(new WebViewClient());
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(jsInterface, "Native");
		myWebView.loadUrl("http://www.purelightdesigns.com/diningheart/index.html");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			AddActivity.this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}


