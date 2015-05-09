package com.sxt.zhp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * 
 * ֪ͨ 
 * DEFAULT_ALL ʹ������Ĭ��ֵ�������������𶯣������ȵ� 
 * DEFAULT_LIGHTS ʹ��Ĭ��������ʾ 
 * DEFAULT_SOUNDS ʹ��Ĭ����ʾ���� 
 * DEFAULT_VIBRATE ʹ��Ĭ���ֻ���
 * 
 * �����ֻ��𶯣�һ��Ҫ��manifest.xml�м���Ȩ��
 * 
 * FLAG_AUTO_CANCEL ��֪ͨ�ܱ�״̬���������ť�������
 * FLAG_NO_CLEAR ��֪ͨ�ܱ�״̬���������ť�������
 * FLAG_ONGOING_EVENT ֪ͨ�������������� 
 * FLAG_INSISTENT �Ƿ�һֱ���У���������һֱ���ţ�֪���û���Ӧ
 * 
 */

public class Demo22_notificationActivity extends Activity {
	private int index = 11;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        
        findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				addNotify();
			}
		});
        
        findViewById(R.id.btn_del).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				delNotify();
			}
		});
        findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				clearNotify();
			}
		});
    }
    
    /**
     * ���֪ͨ
     */
    private void addNotify(){
    	Notification notify = new Notification();

    	notify.icon = R.drawable.ic_launcher;
    	notify.tickerText = "����֪ͨ"+index;
    	notify.defaults = Notification.DEFAULT_ALL;// ʹ������Ĭ��ֵ�������������𶯣������ȵ�
//		notify.flags = Notification.FLAG_NO_CLEAR;//�����ť���������
//		notify.flags = Notification.FLAG_NO_CLEAR|Notification.FLAG_AUTO_CANCEL;//���ܰ�ť��� ���ǵ�����Զ����
    	notify.flags = Notification.FLAG_AUTO_CANCEL;   
		notify.ledARGB = Color.BLUE;  
		notify.ledOnMS =5000; //����ʱ�䣬����
		
		
		
		// ���֪ͨ����Ϣ�������Ǹ�activity
		Intent intent = new Intent(this,Demo22_notificationActivity.class);
//		Intent intent = new Intent(Intent.ACTION_VIEW); 
//		intent.setData(Uri.parse("http://www.baidu.com"));

		//δִ����ͼ��������ִ�е���ͼ�ࣩ
		PendingIntent contentIntent = PendingIntent.getActivity(
				this, 0, intent, 0);
		
		notify.setLatestEventInfo(this, "֪ͨ"+index, "�������Ϸ�����������鿴", contentIntent);
		
		///��ȡ֪ͨ������ 		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(index, notify);//����֪ͨ ����id(��һ�����������Ƿ�Ϊͬһ��֪ͨ)
		index++;
    }
    
    /**
     * ȡ��֪ͨ
     */
    private void delNotify(){
    	NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	index--;
    	nm.cancel(index);
    }
    /**
     * ȡ��ȫ��֪ͨ
     */
    private void clearNotify(){
    	NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	nm.cancelAll();
    }
}