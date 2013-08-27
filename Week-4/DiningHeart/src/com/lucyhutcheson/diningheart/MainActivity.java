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

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// VIEW BUTTON AND HANDLER
		Button viewButton = (Button) findViewById(R.id.viewBtn);
		viewButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("VIEW BUTTON", "VIEW BUTTON CLICKED");
			}
		});

		// ADD BUTTON AND HANDLER
		Button addButton = (Button) findViewById(R.id.addBtn);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("ADD BUTTON", "ADD BUTTON CLICKED");
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
