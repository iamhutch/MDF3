/*
 * project		WeatherCheck
 * 
 * package		com.lucyhutcheson.weathercheck
 * 
 * @author		Lucy Hutcheson
 * 
 * date			Aug 13, 2013
 * 
 */
package com.lucyhutcheson.weathercheck;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.lucyhutcheson.libs.GPSTracker;
import com.lucyhutcheson.libs.GetDataService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity allows users to see current weather as well as take a picture
 * with the camera which gets saved to the device.
 */
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	private ImageView _ImageView;
	private Bitmap _ImageBitmap;

	private String _CurrentPhotoPath;
	private GPSTracker gps;
	double latitude;
	double longitude;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory _AlbumStorageDirFactory = null;

	// Handle communication between this activity and
	// GetDataService class
	Handler searchServiceHandler = new Handler() {

		public void handleMessage(Message mymessage) {

			Log.i("WEATHER CHECK APP", "HANDLER STARTED");

			if (mymessage.arg1 == RESULT_OK	&& mymessage.obj != null) {
		        Log.i("RESPONSE", mymessage.obj.toString());
				JSONObject json = null;
				try {
					json = new JSONObject(mymessage.obj.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.i("UPDATE WITH JSON", json.toString());
				updateData(json);
				
			} else if (mymessage.arg1 == RESULT_CANCELED && mymessage.obj != null){
				Toast.makeText(MainActivity.this,mymessage.obj.toString(), Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(MainActivity.this,"Download failed.", Toast.LENGTH_LONG).show();
			}
			
		}

	};
	
	/**
	 * Updates all textviews with received JSON data.
	 * 
	 * @param data
	 *            the data
	 */
	public void updateData(JSONObject data) {
		Log.i("UPDATE DATA", data.toString());
		try {
			((TextView) findViewById(R.id._temp)).setText(data
					.getString("temp_f"));
			
			String stringURL = data.getString("icon_url");
			
			// Create an object for subclass of AsyncTask
	        GetXMLTask task = new GetXMLTask();
	        // Execute the task
	        task.execute(new String[] { stringURL });


		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("JSON ERROR", e.toString());
		}
	}

	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap thumb = null;
            for (String url : urls) {
            	thumb = downloadImage(url);
            }
            return thumb;
        }
 
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
        	ImageView _imageView = (ImageView) findViewById(R.id._imageWeather);
            _imageView.setImageBitmap(result);
        }
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }	
	
	/**
	 * Gets the album name for this application.
	 *
	 * @return the album name
	 */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	/**
	 * Gets the album directory to save to.
	 *
	 * @return the album dir
	 */
	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = _AlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	/**
	 * Creates the image file.
	 *
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	/**
	 * Sets the up photo file.
	 *
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private File setUpPhotoFile() throws IOException {
		File _f = createImageFile();
		_CurrentPhotoPath = _f.getAbsolutePath();
		return _f;
	}

	/**
	 * Sets the picture size so it isn't so large.
	 */
	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = _ImageView.getWidth();
		int targetH = _ImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options _bmOptions = new BitmapFactory.Options();
		_bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(_CurrentPhotoPath, _bmOptions);
		int _photoW = _bmOptions.outWidth;
		int _photoH = _bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(_photoW / targetW, _photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		_bmOptions.inJustDecodeBounds = false;
		_bmOptions.inSampleSize = scaleFactor;
		_bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap _bitmap = BitmapFactory.decodeFile(_CurrentPhotoPath, _bmOptions);

		/* Associate the Bitmap to the ImageView */
		_ImageView.setImageBitmap(_bitmap);
		_ImageView.setVisibility(View.VISIBLE);
	}

	/**
	 * Gallery add pic.
	 */
	private void galleryAddPic() {
		Intent _mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File _f = new File(_CurrentPhotoPath);
		Uri _contentUri = Uri.fromFile(_f);
		_mediaScanIntent.setData(_contentUri);
		this.sendBroadcast(_mediaScanIntent);
	}

	/**
	 * Dispatch take picture intent.
	 *
	 * @param actionCode the action code
	 */
	private void dispatchTakePictureIntent() {

		Intent _takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File _f = null;

		try {
			_f = setUpPhotoFile();
			_CurrentPhotoPath = _f.getAbsolutePath();
			_takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(_f));
		} catch (IOException e) {
			e.printStackTrace();
			_f = null;
			_CurrentPhotoPath = null;
		}
		startActivityForResult(_takePictureIntent, 1);
	}


	/**
	 * Handle camera photo.
	 */
	private void handleCameraPhoto() {
		if (_CurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			_CurrentPhotoPath = null;
		}
	}

	/**
	 * Button to take a photo.
	 *
	 */
	Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent();
		}
	};


	/**
	 * Called when the activity is first created.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("WEATHER CHECK APP", "MAIN ACTIVITY STARTED");
		_ImageView = (ImageView) findViewById(R.id._imageView);
		_ImageBitmap = null;

		Button _picBtn = (Button) findViewById(R.id._btnBigPic);
		setBtnListenerOrDisable(_picBtn, mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			_AlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			_AlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
		
		Log.i("WEATHER CHECK APP", "ABOUT TO START GPS");
		gps = new GPSTracker(MainActivity.this);
		if (gps.canGetLocation()) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
		} else {
			gps.showSettingsAlert();
		}

		Log.i("WEATHER CHECK APP", "ABOUT TO START MESSENGER");
		// GET SEARCHED FOR MOVIE INFORMATION
		Messenger messenger = new Messenger(searchServiceHandler);
		Intent startServiceIntent = new Intent(getApplicationContext(), GetDataService.class);
		startServiceIntent.putExtra(GetDataService.MESSENGER_KEY,messenger);
		startServiceIntent.setData(Uri.parse("http://api.wunderground.com/api/c6dc8ff98c36bc6c/geolookup/conditions/q/"+Uri.encode(String.valueOf(latitude))+","+ Uri.encode(String.valueOf(longitude))+".json"));
		//startServiceIntent.setData(Uri.parse("http://api.wunderground.com/api/c6dc8ff98c36bc6c/geolookup/q/0,0.json"));
		startService(startServiceIntent);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			handleCameraPhoto();
		}
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, _ImageBitmap);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,
				(_ImageBitmap != null));
		super.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		_ImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		_ImageView.setImageBitmap(_ImageBitmap);
		_ImageView.setVisibility(savedInstanceState
						.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE
						: ImageView.INVISIBLE);
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * Sets the btn listener or disable.
	 *
	 * @param btn the btn
	 * @param onClickListener the on click listener
	 * @param intentName the intent name
	 */
	private void setBtnListenerOrDisable(Button btn,
			Button.OnClickListener onClickListener, String intentName) {
		if (isIntentAvailable(this, intentName)) {
			btn.setOnClickListener(onClickListener);
		} else {
			btn.setText(getText(R.string.cannot).toString() + " "
					+ btn.getText());
			btn.setClickable(false);
		}
	}

}
