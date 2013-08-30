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

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSInterface extends HeartSingleton {
    Context mContext;
	private ArrayList<HashMap<String, String>> _diningPlaces;

    /** Instantiate the interface and set the context */
    JSInterface(Context c) {
        mContext = c;
    }

   
    /** ADD DATA FROM WEBVIEW TO SINGLETON AND FILE STORAGE */
    @JavascriptInterface
    public void addHeart(String name, String city, String category) {
        Log.i("JSINTERFACE", "SUBMITED: " + name + ", " + city + ", " + category);
		_diningPlaces = new ArrayList<HashMap<String, String>>();

		/*
		 * ADDED TO PULL SAVED DATA FIRST AND THEN ADD OUR NEW DATA
		 * 
		 */
        try {
    		_diningPlaces.addAll(ViewActivity.getFavorites());
        } catch (Exception e) {
        	Log.e("JSINTERFACE", "No saved data found.");
        	e.printStackTrace();
        }
        
        HashMap<String, String> newHeart = new HashMap<String, String>();
        newHeart.put("name", name);
        newHeart.put("city", city);
        newHeart.put("category", category);
		Log.i("JSINTERFACE", newHeart.toString());

		// Store data in singleton and file storage
		HeartSingleton.getInstance().set_heart(newHeart);
		_diningPlaces.add(newHeart);
		FileFunctions.storeObjectFile(mContext, "favorites", _diningPlaces, false);
        
        Toast.makeText(mContext, "New dining heart successfully added.", Toast.LENGTH_SHORT).show();

        // Finish
        ((AddActivity)mContext).finish();
    }
    
    
    @JavascriptInterface
    public void cancel(String cancel) {
		Log.i("JSINTERFACE", "CANCELLING");
        ((AddActivity)mContext).finish();
    }

}


