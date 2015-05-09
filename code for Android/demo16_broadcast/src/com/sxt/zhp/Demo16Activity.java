package com.sxt.zhp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Android�Ĵ������Activity,Servcie,BroadCast,ContentProvider 
 * �㲥��
 * 1.��̬ע��(xml�嵥ע��)
 * 2.��̬ע��(������ע��)
 */
public class Demo16Activity extends Activity {
	private static final String BROADCAST_FLAG_STATIC = "com.sxt.zhp.MyBroadCast";// ��̬��action���
	private static final String BROADCAST_FLAG_DY = "com.sxt.zhp.MyBroadCastdy";// ��̬��action���
	private BroadCastDy broadReceiver ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ���Ծ�̬�㲥
		findViewById(R.id.btn_static).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(BROADCAST_FLAG_STATIC);
				sendBroadcast(it);//���͹㲥
			}
		});
		//���Զ�̬�㲥
		findViewById(R.id.btn_dy).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(BROADCAST_FLAG_DY);
				sendBroadcast(it);//���͹㲥				
			}
		});
	}

	@Override
    protected void onResume() {
    	super.onResume();
    	//������
    	broadReceiver = new BroadCastDy();  
    	//��ͼ������
    	IntentFilter filter = new IntentFilter(BROADCAST_FLAG_DY);
    	//ע��㲥������  �����߹�ϵ
    	registerReceiver(broadReceiver, filter);
    }

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadReceiver);
	}
}