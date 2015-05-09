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
 * 通知 
 * DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等 
 * DEFAULT_LIGHTS 使用默认闪光提示 
 * DEFAULT_SOUNDS 使用默认提示声音 
 * DEFAULT_VIBRATE 使用默认手机震动
 * 
 * 加入手机震动，一定要在manifest.xml中加入权限
 * 
 * FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
 * FLAG_NO_CLEAR 该通知能被状态栏的清除按钮给清除掉
 * FLAG_ONGOING_EVENT 通知放置在正在运行 
 * FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
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
     * 添加通知
     */
    private void addNotify(){
    	Notification notify = new Notification();

    	notify.icon = R.drawable.ic_launcher;
    	notify.tickerText = "测试通知"+index;
    	notify.defaults = Notification.DEFAULT_ALL;// 使用所有默认值，比如声音，震动，闪屏等等
//		notify.flags = Notification.FLAG_NO_CLEAR;//清除按钮不能清除、
//		notify.flags = Notification.FLAG_NO_CLEAR|Notification.FLAG_AUTO_CANCEL;//不能按钮清除 但是点击后自动清除
    	notify.flags = Notification.FLAG_AUTO_CANCEL;   
		notify.ledARGB = Color.BLUE;  
		notify.ledOnMS =5000; //闪光时间，毫秒
		
		
		
		// 点击通知栏消息是启动那个activity
		Intent intent = new Intent(this,Demo22_notificationActivity.class);
//		Intent intent = new Intent(Intent.ACTION_VIEW); 
//		intent.setData(Uri.parse("http://www.baidu.com"));

		//未执行意图（触发后执行的意图类）
		PendingIntent contentIntent = PendingIntent.getActivity(
				this, 0, intent, 0);
		
		notify.setLatestEventInfo(this, "通知"+index, "好玩的游戏就在这里，点击查看", contentIntent);
		
		///获取通知管理器 		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(index, notify);//发送通知 但是id(第一个参数决定是否为同一个通知)
		index++;
    }
    
    /**
     * 取消通知
     */
    private void delNotify(){
    	NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	index--;
    	nm.cancel(index);
    }
    /**
     * 取消全部通知
     */
    private void clearNotify(){
    	NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	nm.cancelAll();
    }
}