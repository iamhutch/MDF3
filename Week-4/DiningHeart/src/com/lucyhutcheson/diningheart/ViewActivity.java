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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewActivity extends Activity {
	
	private ArrayList<String> _movies;
	private ArrayAdapter<String> _listAdapter;
	Context _context;
	private ListView _listView;

	static final String[] FRUITS = new String[] {"Chik-fil-A", "Cracker Barrel", "Lonestar"};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		_context = this;
		
		// ATTACH LIST ADAPTER
		_listView = (ListView) findViewById(R.id.listview);
		_listAdapter = new ArrayAdapter<String>(_context,	android.R.layout.simple_list_item_1, FRUITS);
		_listView.setAdapter(_listAdapter);

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
