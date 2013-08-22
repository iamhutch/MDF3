package com.lucyhutcheson.ChristmastCountdownWidget;

import com.lucyhutcheson.christmascountdown.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

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
				//CalendarUtility cu = new CalendarUtility(this);
				
			}
		}
		
	}
	
	

}
