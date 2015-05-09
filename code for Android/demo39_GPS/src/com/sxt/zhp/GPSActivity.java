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
 * GPSλ�� ע�⿪��Ȩ�ޣ� <uses-permission
 * android:name="android.permission.ACCESS_FINE_LOCATION"/> <uses-permission
 * android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * 
 */
public class GPSActivity extends Activity {
	private TextView tv;
	private LocationManager lm;// λ�ù���������
	private static int GPS_SETTING = 222;
	private String TAG = "MyTag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		System.out.println("==========GPSActivity=======");

		tv = (TextView) findViewById(R.id.showmess);
		
		

		// ��ȡλ�ù���������
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		initLocation();//��ȡ��ʼ��Ϣ

		locationUpdate();

		stateUpdate();

		isOpenGPS();

		// Cri
	}

	/**
	 * ��ȡ��ʼ��Ϣ
	 */
	private void initLocation(){
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "�뿪��GPS����...", Toast.LENGTH_SHORT).show();
			return;
		}
		
//	    //Ϊ��ȡ����λ����Ϣʱ���ò�ѯ����
//        String bestProvider = lm.getBestProvider(getCriteria(), true);
//        System.out.println(">>>>>--"+bestProvider);
//        //��ȡλ����Ϣ
//        //��������ò�ѯҪ��getLastKnownLocation�������˵Ĳ���ΪLocationManager.GPS_PROVIDER
//        Location location= lm.getLastKnownLocation(bestProvider);  
//        tv.setText("��ǰ��Ϣ��\n");
//        tv.append("ʱ�䣺" + new Date(location.getTime()));
//		tv.append("���ȣ�" + location.getLongitude());
//		tv.append("γ�ȣ�" + location.getLatitude());
//		tv.append("���Σ�" + location.getAltitude());
	}
	/**
	 * �ж�GPS�Ƿ���������
	 */
	private void isOpenGPS() {
		// �ж�GPS�Ƿ���������
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "�뿪��GPS����...", Toast.LENGTH_SHORT).show();

			// ����GPS�������ý���
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

			startActivityForResult(intent, GPS_SETTING); // ������ɺ��ٻ���
			// �Զ����ñ�activity�е�onActivityResult����
			return;
		}
	}

	/**
	 * GPS���õ���������� ��ص�����
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == GPS_SETTING) {
			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Toast.makeText(this, "�ף���զ��û��GPS", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "GPS����", Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ״̬�仯����
	 */
	private void stateUpdate() {
		lm.addGpsStatusListener(new Listener() {

			@Override
			public void onGpsStatusChanged(int event) {
				switch (event) {
				// ��һ�ζ�λ
				case GpsStatus.GPS_EVENT_FIRST_FIX:
					Log.i(TAG, "��һ�ζ�λ");
					break;
				// ����״̬�ı�
				case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
					Log.i(TAG, "����״̬�ı�");
					// ��ȡ��ǰ״̬
					GpsStatus gpsStatus = lm.getGpsStatus(null);
					// ��ȡ���ǿ�����Ĭ�����ֵ
					int maxSatellites = gpsStatus.getMaxSatellites();
					// ����һ��������������������
					Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
							.iterator();
					int count = 0;
					while (iters.hasNext() && count <= maxSatellites) {
						GpsSatellite s = iters.next();
						count++;
					}
					System.out.println("��������" + count + "������");
					break;
				// ��λ����
				case GpsStatus.GPS_EVENT_STARTED:
					Log.i(TAG, "��λ����");
					break;
				// ��λ����
				case GpsStatus.GPS_EVENT_STOPPED:
					Log.i(TAG, "��λ����");
					break;

				}
			}
		});
	}

	/**
	 * λ�ñ仯����
	 */
	private void locationUpdate() {
		lm.requestLocationUpdates(3000, 10, getCriteria(), locListener, null);
//		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10,locListener);
	}

	/**
	 * ���ز�ѯ����
	 * 
	 * @return
	 */
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// ���ö�λ��ȷ�� Criteria.ACCURACY_COARSE�Ƚϴ��ԣ�Criteria.ACCURACY_FINE��ȽϾ�ϸ
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// �����Ƿ�Ҫ���ٶ�
		criteria.setSpeedRequired(false);
		// �����Ƿ�������Ӫ���շ�
		criteria.setCostAllowed(false);
		// �����Ƿ���Ҫ��λ��Ϣ
		criteria.setBearingRequired(false);
		// �����Ƿ���Ҫ������Ϣ
		criteria.setAltitudeRequired(true);
		// ���öԵ�Դ������
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}
	
	/**
	 * λ�ñ仯������
	 */
	LocationListener locListener = new LocationListener() {
		/**
		 * ״̬�����ı�ʱ����
		 */
		public void onStatusChanged(String arg0, int status,
				Bundle arg2) {
			tv.append("\n״̬�仯�ˣ�");
			switch (status) {
			// GPS״̬Ϊ�ɼ�ʱ
			case LocationProvider.AVAILABLE:
				Log.i(TAG, "��ǰGPS״̬Ϊ�ɼ�״̬");
				tv.append("�ɼ�״̬");
				break;
			// GPS״̬Ϊ��������ʱ
			case LocationProvider.OUT_OF_SERVICE:
				Log.i(TAG, "��ǰGPS״̬Ϊ��������״̬");
				tv.append("��������״̬");
				break;
			// GPS״̬Ϊ��ͣ����ʱ
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.i(TAG, "��ǰGPS״̬Ϊ��ͣ����״̬");
				tv.append("��ͣ����״̬");
				break;
			}
		}

		/**
		 * ����GPS�����ʱ�򴥷�
		 */
		public void onProviderEnabled(String arg0) {
			tv.append("\n������GPS");
		}

		@Override
		public void onProviderDisabled(String arg0) {

			tv.append("\n������GPS");
		}

		/**
		 * λ�øı�ʱ
		 */
		public void onLocationChanged(Location location) {
			tv.append("\nλ�÷����ı��ˣ�");
			tv.append("ʱ�䣺" + new Date(location.getTime()));
			tv.append("���ȣ�" + location.getLongitude());
			tv.append("γ�ȣ�" + location.getLatitude());
			tv.append("���Σ�" + location.getAltitude());
		}
	};
}
