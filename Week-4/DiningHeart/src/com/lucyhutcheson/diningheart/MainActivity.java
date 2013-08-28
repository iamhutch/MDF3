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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewButtonListener();
		addButtonListener();
		
		//_diningPlaces = new ArrayList<HashMap<String, String>>();
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		JSONObject object2 = new JSONObject();
		try {
			object.put("name", "Chik-fil-A");
			object.put("city", "Killeen");
			object.put("category", "American");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		array.put(object);

		try {
			object2.put("name", "Cracker Barrel");
			object2.put("city", "Harker Heights");
			object2.put("category", "Country");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		array.put(object2);

		FileFunctions.storeStringFile(getApplicationContext(), "dining", array.toString(), true);
		


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
