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

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webview = new WebView(this);
		setContentView(webview);
		
		 // Let's display the progress in the activity title bar, like the
		 // browser app does.
		 getWindow().requestFeature(Window.FEATURE_PROGRESS);

		 webview.getSettings().setJavaScriptEnabled(true);

		 final Activity activity = this;
		 webview.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%
		     activity.setProgress(progress * 1000);
		   }
		 });
		 webview.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		   }
		 });

		 webview.loadUrl("http://slashdot.org/");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}

