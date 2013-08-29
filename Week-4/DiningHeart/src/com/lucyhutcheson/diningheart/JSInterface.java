package com.lucyhutcheson.diningheart;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSInterface extends HeartSingleton {
    Context mContext;
	private ArrayList<HashMap<String, String>> _diningPlacesArray;

    /** Instantiate the interface and set the context */
    JSInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void addHeart(String name, String city, String category) {
        Log.i("JSINTERFACE", "SUBMITED: " + name + ", " + city + ", " + category);
        
        HashMap<String, String> newHeart = null;
        newHeart.put("name", name);
        newHeart.put("city", city);
        newHeart.put("category", category);
        HeartSingleton.getInstance().set_heart(newHeart);
        
		Log.i("JSINTERFACE", newHeart.toString());
		
        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
    }
}


