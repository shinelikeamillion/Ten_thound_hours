package com.sxt.zhp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	//定时器 每次收到广播通知时，就会重新创建新的对象 所以Timer需要定义为static 保证是同一个 否则无法停止
	private static Timer timer;
	private String action = "com.sxt.zhp.MyWidget";//和清单中的action一致即可
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		System.out.println("onDeleted"+this);
	}

	@Override
	public void onDisabled(Context context) {
		System.out.println(this+"onDisabled=="+timer);
		
		if(timer!=null){
			timer.cancel();//取消定时器
			Log.i("MyTag", "----cancel()----");
		}
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(final Context context) {
		System.out.println("onEnabled"+this);
		super.onEnabled(context);
		
		//定时器
		timer = new Timer();
		
		//定时任务
		TimerTask task = new TimerTask() {			
			@Override
			public void run() {		//发送广播		
				Log.i("MyTag", "--------"+MyWidgetProvider.this);
				Intent it = new Intent(action);//自己动作
				context.sendBroadcast(it);
			}
		};
		//定时调度
		timer.schedule(task, 1000, 1000);
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println(intent.getAction()+"=onReceive="+this);
		
		if(action.equals(intent.getAction())){//自己发的广播
			//RemoteViews类描述了一个View对象能够显示在其他进程中，可以融合layout资源文件实现布局。
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			rv.setTextViewText(R.id.update, format.format(new Date()));
			
			 //点击桌面组件时进入主程序入口 WidgetTestActivity
	        Intent it = new Intent(context, Demo28Activity.class);
	        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, it, 0);
	        //给update TextView添加点击事件 点击后执行意图
			rv.setOnClickPendingIntent(R.id.update, pendingIntent);
			
			//将该界面显示到插件中
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName componentName = new ComponentName(context,MyWidgetProvider.class);
			
			appWidgetManager.updateAppWidget(componentName, rv);
		}
		
		super.onReceive(context, intent);
	
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		System.out.println("onUpdate:"+this);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
}
