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

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	ImageButton viewButton;
	ImageButton addButton;
	private HashMap<String, String> map;
	private ArrayList<HashMap<String, String>> _diningPlaces;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewButtonListener();
		addButtonListener();
		
		_diningPlaces = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		map.put("name", "Chik-fil-A");
		map.put("city", "Killeen");
		map.put("category", "American");
		_diningPlaces.add(map);

		map = new HashMap<String, String>();
		map.put("name", "Cracker Barrel");
		map.put("city", "Harker Heights");
		map.put("category", "Country");
		_diningPlaces.add(map);
		
		FileFunctions.storeObjectFile(getApplicationContext(), "favorites", _diningPlaces, false);


	}

	public void viewButtonListener() {
		// VIEW BUTTON AND HANDLER
		viewButton = (ImageButton) findViewById(R.id.viewBtn);
		viewButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("VIEW BUTTON", "VIEW BUTTON CLICKED");
				
				// INTENT TO START VIEW ACTIVITY
				Intent intent = new Intent(MainActivity.this,ViewActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}
	
	public void addButtonListener() {
		// ADD BUTTON AND HANDLER
		addButton = (ImageButton) findViewById(R.id.addBtn);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("ADD BUTTON", "ADD BUTTON CLICKED");
				
				// INTENT TO START ADD ACTIVITY
				Intent intent = new Intent(MainActivity.this,AddActivity.class);
				MainActivity.this.startActivity(intent);
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
