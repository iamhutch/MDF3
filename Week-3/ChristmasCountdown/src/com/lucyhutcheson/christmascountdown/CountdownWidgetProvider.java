package com.lucyhutcheson.christmascountdown;

import java.util.Calendar;

import com.lucyhutcheson.christmascountdown.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RemoteViews;

public class CountdownWidgetProvider extends AppWidgetProvider {
	public static final String DEBUG_TAG = "TutWidgetProvider";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		try {
			updateWidgetContent(context, appWidgetManager);
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Failed: " + e.toString());
		}
	}

	public static void updateWidgetContent(Context context,
			AppWidgetManager appWidgetManager) {

		// Initialize Calendar Utility Class
		CalendarUtility cu = new CalendarUtility(context);
		cu.Save(25, 12, 2013);
		
		RemoteViews remoteView = new RemoteViews(context.getPackageName(),
				R.layout.appwidget_layout);
		remoteView.setTextViewText(R.id.daysLeft, String.valueOf(cu.DaysToChristmas()));

		// Setup our url for christmas website
		Uri intentUri = Uri.parse("http://www.christmas.com");
		
		// SETUP IMPLICIT INTENT
		Intent webIntent = new Intent(Intent.ACTION_VIEW, intentUri);

		// Intent launchAppIntent = new Intent(context, TutListActivity.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(
				context, 0, webIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.full_widget,
				launchAppPendingIntent);
		ComponentName tutListWidget = new ComponentName(context,
				CountdownWidgetProvider.class);
		appWidgetManager.updateAppWidget(tutListWidget, remoteView);
	}
}
