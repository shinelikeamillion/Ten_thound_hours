package com.sxt.map;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Demo_mapActivity extends Activity {
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.map1);
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		
		mBaiduMap = mMapView.getMap();
		
		//����Maker�����  
		LatLng point = new LatLng(39.963175, 116.400244);  
		//����Markerͼ��  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_gcoding);  
		//����MarkerOption�������ڵ�ͼ�����Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap);  
		//�ڵ�ͼ�����Marker������ʾ  
		mBaiduMap.addOverlay(option);
		myclick();
	}
	
	
	private void myclick(){

		findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText edtLat = (EditText) findViewById(R.id.editText1);
				EditText edtLong = (EditText) findViewById(R.id.editText2);
				
				//����Maker�����  
				LatLng point = new LatLng(Double.parseDouble(edtLat.getText().toString()), Double.parseDouble(edtLong.getText().toString()));  
				//����Markerͼ��  
				BitmapDescriptor bitmap = BitmapDescriptorFactory  
				    .fromResource(R.drawable.icon_gcoding);  
				//����MarkerOption�������ڵ�ͼ�����Marker  
				OverlayOptions option = new MarkerOptions()  
				    .position(point)  
				    .icon(bitmap);  
				//�ڵ�ͼ�����Marker������ʾ  
				mBaiduMap.addOverlay(option);
				
				
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}

}