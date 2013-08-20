/*
 * project		VideosOnLocation
 * 
 * package		com.lucyhutcheson.libs
 * 
 * @author		Lucy Hutcheson
 * 
 * date			Aug 11, 2013
 * 
 */
package com.lucyhutcheson.libs;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * The Class GPSTracker gets the user's GPS location.
 */
public class GPSTracker extends Service implements LocationListener {

	private final Context _context;
	boolean _isGPSEnabled = false;
	boolean _isNetworkEnabled = false;
	boolean _canGetLocation = false;
	Location _location;
	double _latitude;
	double _longitude;
	private static final long _MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10
																		// meters
	private static final long _MIN_TIME_BTW_UPDATES = 1000 * 60 * 1; // 1 minute
	protected LocationManager _locationManager;

	/**
	 * Gets the best network provider.
	 * 
	 * @return the location
	 */
	public boolean canGetLocation() {
		return this._canGetLocation;
	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(_context);

		alertDialog.setTitle("GPS Settings");
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu to enable it?");
		// AFTER PRESSING SETTINGS BUTTON
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						_context.startActivity(intent);
					}
				});
		// AFTER PRESSING CANCEL BUTTON
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		// SHOW DIALOG TO USER
		alertDialog.show();
	}

	/**
	 * Instantiates a new GPS tracker.
	 * 
	 * @param context
	 *            the context
	 */
	public GPSTracker(Context context) {
		this._context = context;
		getLocation();
	}

	/**
	 * Gets the location.
	 * 
	 * @return the location
	 */
	public Location getLocation() {
		try {
			_locationManager = (LocationManager) _context
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			_isGPSEnabled = _locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			_isNetworkEnabled = _locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!_isGPSEnabled && !_isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this._canGetLocation = true;
				// First get location from Network Provider
				if (_isNetworkEnabled) {
					_locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							_MIN_TIME_BTW_UPDATES,
							_MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (_locationManager != null) {
						_location = _locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (_location != null) {
							_latitude = _location.getLatitude();
							_longitude = _location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (_isGPSEnabled) {
					if (_location == null) {
						_locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								_MIN_TIME_BTW_UPDATES,
								_MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (_locationManager != null) {
							_location = _locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (_location != null) {
								_latitude = _location.getLatitude();
								_longitude = _location.getLongitude();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _location;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	public double getLatitude() {
		if (_location != null) {
			_latitude = _location.getLatitude();
		}
		return _latitude;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	public double getLongitude() {
		if (_location != null) {
			_longitude = _location.getLongitude();
		}
		return _longitude;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onLocationChanged(android.location.
	 * Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void stopUsingGPS() {
		if (_locationManager != null) {
			_locationManager.removeUpdates(GPSTracker.this);
		}
	}
	
}
