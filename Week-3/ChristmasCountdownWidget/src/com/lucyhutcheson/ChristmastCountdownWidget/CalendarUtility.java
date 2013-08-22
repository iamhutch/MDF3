package com.lucyhutcheson.christmastcountdownwidget;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CalendarUtility {
	
	Context myContext;
	Editor editor;
	long DaysToChristmas;
	final String YEAR = "year";
	final String MONTH = "month";
	final String DAY = "day";
	final String PREFS_NAME = "ChristmasCountdown";
	SharedPreferences settings;

	public CalendarUtility(Context C)
	{
		myContext = C;
	}
	
	public void Save(int day, int month, int year)
	{
		Calendar holiday = getCalendarNoHours();
		
		holiday.set(Calendar.DAY_OF_MONTH, day);
		holiday.set(Calendar.MONTH, month);
		holiday.set(Calendar.YEAR, year);
		
		settings = myContext.getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		editor.putInt(DAY, day);
		editor.putInt(MONTH, month);
		editor.putInt(YEAR, year);
		editor.apply();
	}
	
	public long getChristmasMsec()
	{
		Calendar holiday = getCalendarNoHours();
		settings = myContext.getSharedPreferences(PREFS_NAME, 0);
		holiday.set(Calendar.DAY_OF_MONTH, settings.getInt(DAY, Integer.parseInt("0")));
		holiday.set(Calendar.MONTH, settings.getInt(MONTH, Integer.parseInt("0")));
		holiday.set(Calendar.YEAR, settings.getInt(YEAR, Integer.parseInt("0")));
		this.DaysToChristmas  = holiday.getTimeInMillis();
		return holiday.getTimeInMillis();
	}
	
	public long testChristmasMsec() {
		return --DaysToChristmas;
	}
	
	public Calendar getCalendarNoHours() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	
	public long DaysToChristmas() {
		Calendar now = getCalendarNoHours();
		long diffMsec = getChristmasMsec() - now.getTimeInMillis();
		long diffDays = diffMsec / (24*60*60*1000);
		return diffDays;
	}
	
	public void deleteHoliday(Context context) {
		settings = myContext.getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		editor.clear();
		editor.commit();
	}
}
