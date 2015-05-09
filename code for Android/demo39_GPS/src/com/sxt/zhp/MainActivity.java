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
 * LocationMangager��λ�ù�����
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
					 * ״̬�仯
					 */
					public void onStatusChanged(String arg0, int status,
							Bundle arg2) {
						switch (status) {
						// GPS״̬Ϊ�ɼ�ʱ
						case LocationProvider.AVAILABLE:
							Log.i(TAG, "��ǰGPS״̬Ϊ�ɼ�״̬");
							break;
						// GPS״̬Ϊ��������ʱ
						case LocationProvider.OUT_OF_SERVICE:
							Log.i(TAG, "��ǰGPS״̬Ϊ��������״̬");
							break;
						// GPS״̬Ϊ��ͣ����ʱ
						case LocationProvider.TEMPORARILY_UNAVAILABLE:
							Log.i(TAG, "��ǰGPS״̬Ϊ��ͣ����״̬");
							break;
						}

					}

					/**
					 * GPS����ʱ����
					 */
					public void onProviderEnabled(String arg0) {
						Log.i(TAG, "GPS������" + arg0);
						
					}

					/**
					 * GPS����ʱ����
					 */
					public void onProviderDisabled(String arg0) {
						Log.i(TAG, "GPS����ʱ��" + arg0);

					}

					/**
					 * λ�ñ仯
					 */
					public void onLocationChanged(Location location) {
						Log.i(TAG, "ʱ�䣺" + location.getTime());
						Log.i(TAG, "���ȣ�" + location.getLongitude());
						Log.i(TAG, "γ�ȣ�" + location.getLatitude());
						Log.i(TAG, "���Σ�" + location.getAltitude());

					}
				});
	}
}
