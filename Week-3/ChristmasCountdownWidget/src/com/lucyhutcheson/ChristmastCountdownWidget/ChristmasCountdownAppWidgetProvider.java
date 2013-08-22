package com.lucyhutcheson.christmastcountdownwidget;

import com.lucyhutcheson.christmascountdown.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class ChristmasCountdownAppWidgetProvider extends AppWidgetProvider {

	public final String MERRYCHRISTMAS = "Merry\nChristmas";
	public final String DTC = "\nDays To \nChristmas!";
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		CalendarUtility cu = new CalendarUtility(context);
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		long days = cu.DaysToChristmas();
		
		if (days <= 0 )
		{
			rv.setTextViewText(R.id.daysLeft, MERRYCHRISTMAS);
			
		}
		else
		{
			rv.setTextViewText(R.id.daysLeft, DTC);
		}
		appWidgetManager.updateAppWidget(appWidgetIds, rv);
	}
	
	public void onDeleted(Context context, int[] appWidgetIds) {
		CalendarUtility cu = new CalendarUtility(context);
		cu.deleteHoliday(context);
	}

}
