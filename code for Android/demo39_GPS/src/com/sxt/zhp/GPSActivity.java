package com.sxt.zhp;

import java.sql.Date;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * GPS位置 注意开启权限： <uses-permission
 * android:name="android.permission.ACCESS_FINE_LOCATION"/> <uses-permission
 * android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * 
 */
public class GPSActivity extends Activity {
	private TextView tv;
	private LocationManager lm;// 位置管理器服务
	private static int GPS_SETTING = 222;
	private String TAG = "MyTag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		System.out.println("==========GPSActivity=======");

		tv = (TextView) findViewById(R.id.showmess);
		
		

		// 获取位置管理器服务
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		initLocation();//获取初始信息

		locationUpdate();

		stateUpdate();

		isOpenGPS();

		// Cri
	}

	/**
	 * 获取初始信息
	 */
	private void initLocation(){
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
			return;
		}
		
//	    //为获取地理位置信息时设置查询条件
//        String bestProvider = lm.getBestProvider(getCriteria(), true);
//        System.out.println(">>>>>--"+bestProvider);
//        //获取位置信息
//        //如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
//        Location location= lm.getLastKnownLocation(bestProvider);  
//        tv.setText("当前信息：\n");
//        tv.append("时间：" + new Date(location.getTime()));
//		tv.append("经度：" + location.getLongitude());
//		tv.append("纬度：" + location.getLatitude());
//		tv.append("海拔：" + location.getAltitude());
	}
	/**
	 * 判断GPS是否正常开启
	 */
	private void isOpenGPS() {
		// 判断GPS是否正常启动
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();

			// 开启GPS导航设置界面
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

			startActivityForResult(intent, GPS_SETTING); // 设置完成后再回来
			// 自动调用本activity中的onActivityResult方法
			return;
		}
	}

	/**
	 * GPS设置导航界面完后 会回调这里
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == GPS_SETTING) {
			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Toast.makeText(this, "亲，您咋还没开GPS", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "GPS打开了", Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 状态变化监听
	 */
	private void stateUpdate() {
		lm.addGpsStatusListener(new Listener() {

			@Override
			public void onGpsStatusChanged(int event) {
				switch (event) {
				// 第一次定位
				case GpsStatus.GPS_EVENT_FIRST_FIX:
					Log.i(TAG, "第一次定位");
					break;
				// 卫星状态改变
				case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
					Log.i(TAG, "卫星状态改变");
					// 获取当前状态
					GpsStatus gpsStatus = lm.getGpsStatus(null);
					// 获取卫星颗数的默认最大值
					int maxSatellites = gpsStatus.getMaxSatellites();
					// 创建一个迭代器保存所有卫星
					Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
							.iterator();
					int count = 0;
					while (iters.hasNext() && count <= maxSatellites) {
						GpsSatellite s = iters.next();
						count++;
					}
					System.out.println("搜索到：" + count + "颗卫星");
					break;
				// 定位启动
				case GpsStatus.GPS_EVENT_STARTED:
					Log.i(TAG, "定位启动");
					break;
				// 定位结束
				case GpsStatus.GPS_EVENT_STOPPED:
					Log.i(TAG, "定位结束");
					break;

				}
			}
		});
	}

	/**
	 * 位置变化监听
	 */
	private void locationUpdate() {
		lm.requestLocationUpdates(3000, 10, getCriteria(), locListener, null);
//		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10,locListener);
	}

	/**
	 * 返回查询条件
	 * 
	 * @return
	 */
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 设置是否要求速度
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(true);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}
	
	/**
	 * 位置变化监听器
	 */
	LocationListener locListener = new LocationListener() {
		/**
		 * 状态发生改变时触发
		 */
		public void onStatusChanged(String arg0, int status,
				Bundle arg2) {
			tv.append("\n状态变化了：");
			switch (status) {
			// GPS状态为可见时
			case LocationProvider.AVAILABLE:
				Log.i(TAG, "当前GPS状态为可见状态");
				tv.append("可见状态");
				break;
			// GPS状态为服务区外时
			case LocationProvider.OUT_OF_SERVICE:
				Log.i(TAG, "当前GPS状态为服务区外状态");
				tv.append("服务区外状态");
				break;
			// GPS状态为暂停服务时
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.i(TAG, "当前GPS状态为暂停服务状态");
				tv.append("暂停服务状态");
				break;
			}
		}

		/**
		 * 开启GPS服务的时候触发
		 */
		public void onProviderEnabled(String arg0) {
			tv.append("\n开启了GPS");
		}

		@Override
		public void onProviderDisabled(String arg0) {

			tv.append("\n禁用了GPS");
		}

		/**
		 * 位置改变时
		 */
		public void onLocationChanged(Location location) {
			tv.append("\n位置发生改变了：");
			tv.append("时间：" + new Date(location.getTime()));
			tv.append("经度：" + location.getLongitude());
			tv.append("纬度：" + location.getLatitude());
			tv.append("海拔：" + location.getAltitude());
		}
	};
}
