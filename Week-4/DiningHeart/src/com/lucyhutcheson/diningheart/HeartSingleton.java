/*
 * project		DiningHeart
 * 
 * package		com.lucyhutcheson.diningheart
 * 
 * @author		Lucy Hutcheson
 * 
 * date			Aug 29, 2013
 * 
 */
package com.lucyhutcheson.diningheart;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Singleton to hold the searched-for movie JSON data as well as the latest movies
 * JSON data when initiated by the user in the main activity.
 */
public class HeartSingleton {

	/**********************
	 * Private members
	 **********************/
	// Will hold single instance of this class
	private static HeartSingleton _instance = null;
	// Will hold data for dining heart
	private ArrayList<HashMap<String, String>> _heartArrayList = new ArrayList<HashMap<String, String>>();
	
	
	/**
	 * ********************
	 * Private Methods
	 * ********************.
	 */
	// Used to hide constructor and prevent other classes from instantiating
	protected HeartSingleton() {
	}
	
	/**
	 * ********************
	 * Public method
	 * ********************.
	 *
	 * @return single instance of MoviesSingletonClass
	 */
	public static HeartSingleton getInstance() {
		// Create a new instantiation if there isn't one
		if(_instance == null){
			_instance = new HeartSingleton();
		}
		// Otherwise, just return the one already created
		return _instance;
	}
	
	/**
	 * *************************
	 * Public setters / getters
	 * *************************.
	 *
	 */
	public ArrayList<HashMap<String, String>> get_heart(){
		return _heartArrayList;
	}
	
	/**
	 * Set_movies.
	 *
	 * @param string the movie
	 */

	public void set_heart(HashMap<String, String> hashMap) {
		_heartArrayList.add(hashMap);
	}
	
}