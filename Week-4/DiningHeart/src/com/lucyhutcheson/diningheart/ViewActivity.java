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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ViewActivity extends Activity {

	static Context _context;
	private ListView _listView;
	HashMap<String, String> _favorites;
	HashMap<String, String> favList = new HashMap<String, String>();
	ArrayList<HashMap<String, String>> _diningPlacesArray;
	static final String[] FROM = new String[] { "name", "city", "category" };
	static final int[] TO = new int[] { R.id.title, R.id.city, R.id.category };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		_context = this;

		_diningPlacesArray = getFavorites();

		// ATTACH LIST ADAPTER
		_listView = (ListView) findViewById(R.id.listview);
		SimpleAdapter _myAdapter = new SimpleAdapter(_context, _diningPlacesArray, R.layout.view_row, FROM, TO);
		_listView.setAdapter(_myAdapter);

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

	/**
	 * Function to get read the favorites file which contains any dining data
	 * that was saved as a favorite.
	 * 
	 * @return hashmap of our favorites data
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<HashMap<String, String>> getFavorites() {
		Object stored = FileFunctions.readObjectFile(_context, "favorites",
				false);
		Log.i("FAVORITES", stored.toString());

		ArrayList<HashMap<String, String>> favorites = null;

		// CAST ARRAYLIST
		try {
			favorites = (ArrayList<HashMap<String, String>>) stored;
			Log.i("FAVORITES CAST", favorites.toString());
		} catch (Exception e) {
			Log.e("FAVORITES FOUND","ERROR");
			e.printStackTrace();
		}
		return favorites;
	}

}
