/*
 * project		WebLaunch
 * 
 * package		com.lucyhutcheson.weblaunch
 * 
 * @author		Lucy Hutcheson
 * 
 * date			Aug 5, 2013
 * 
 */
package com.lucyhutcheson.weblaunch;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// VIEW WEBSITE BUTTON AND HANDLER FOR IMPLICIT INTENT
		Button webButton = (Button) findViewById(R.id.webButton);
		webButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				EditText editURI = (EditText) findViewById(R.id.urlField);
				Uri intentUri = Uri.parse(editURI.getText().toString());
				
				// SETUP IMPLICIT INTENT
				Intent webIntent = new Intent(Intent.ACTION_VIEW, intentUri);

				String title = "View this website with";
				// Create and start the chooser
				Intent chooser = Intent.createChooser(webIntent, title);
				startActivity(chooser);				

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
