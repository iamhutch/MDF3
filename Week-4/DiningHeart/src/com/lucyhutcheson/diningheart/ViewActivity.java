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


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			ViewActivity.this.finish();
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
