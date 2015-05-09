package com.sxt.zhp;

import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

/**
 * 
 * LocationMangager，位置管理器
 * 
 */
public class MainActivity extends Activity {
	private static String TAG = "MyTag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i(TAG, "Oncreate");
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 10, new LocationListener() {

					/**
					 * 状态变化
					 */
					public void onStatusChanged(String arg0, int status,
							Bundle arg2) {
						switch (status) {
						// GPS状态为可见时
						case LocationProvider.AVAILABLE:
							Log.i(TAG, "当前GPS状态为可见状态");
							break;
						// GPS状态为服务区外时
						case LocationProvider.OUT_OF_SERVICE:
							Log.i(TAG, "当前GPS状态为服务区外状态");
							break;
						// GPS状态为暂停服务时
						case LocationProvider.TEMPORARILY_UNAVAILABLE:
							Log.i(TAG, "当前GPS状态为暂停服务状态");
							break;
						}

					}

					/**
					 * GPS开启时触发
					 */
					public void onProviderEnabled(String arg0) {
						Log.i(TAG, "GPS开启：" + arg0);
						
					}

					/**
					 * GPS禁用时触发
					 */
					public void onProviderDisabled(String arg0) {
						Log.i(TAG, "GPS禁用时：" + arg0);

					}

					/**
					 * 位置变化
					 */
					public void onLocationChanged(Location location) {
						Log.i(TAG, "时间：" + location.getTime());
						Log.i(TAG, "经度：" + location.getLongitude());
						Log.i(TAG, "纬度：" + location.getLatitude());
						Log.i(TAG, "海拔：" + location.getAltitude());

					}
				});
	}
}
