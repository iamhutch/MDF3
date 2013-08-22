package com.lucyhutcheson.christmastcountdownwidget;

import java.util.Calendar;

import com.lucyhutcheson.christmascountdown.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RemoteViews;

public class WidgetConfigActivity extends Activity implements OnClickListener {
	
	final String URI = "http://www.christmas.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure);
		
		Button button = (Button) this.findViewById(R.id.buttonDone);
		button.setOnClickListener(this);
	}
	
	private void displayAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
		.setMessage(message)
		.setCancelable(true);
		builder.create().show();
	}

	@Override
	public void onClick(View v) {

		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			
			if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID)
			{
				DatePicker dp = (DatePicker) this.findViewById(R.id.datePicker1);
				CalendarUtility cu = new CalendarUtility(this);
				cu.Save(dp.getDayOfMonth(), dp.getMonth(), dp.getYear());
				
				Calendar now = cu.getCalendarNoHours();
				
				if (cu.getChristmasMsec() < now.getTimeInMillis())
				{
					this.displayAlert("Date must be after today's date");
					return;
				}
				RemoteViews rv = new RemoteViews(this.getPackageName(), R.layout.widget_layout);
				rv.setTextViewText(R.id.daysLeft, String.valueOf(cu.DaysToChristmas()));
				
				Intent buttonIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
				PendingIntent pi = PendingIntent.getActivity(this, 0, buttonIntent, 0);
				rv.setOnClickPendingIntent(R.id.button1, pi);
				AppWidgetManager.getInstance(this).updateAppWidget(widgetId, rv);
				
				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
				setResult(RESULT_OK, resultValue);
				finish();
			}
		}
		
	}
	
	

}
