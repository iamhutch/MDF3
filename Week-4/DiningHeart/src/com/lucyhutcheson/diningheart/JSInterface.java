package com.lucyhutcheson.diningheart;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    JSInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void addHeart(String heart) {
        Toast.makeText(mContext, heart, Toast.LENGTH_SHORT).show();
        Log.i("JSINTERFACE", "SUBMIT PRESSED");
    }
}


