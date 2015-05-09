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
	//��ʱ�� ÿ���յ��㲥֪ͨʱ���ͻ����´����µĶ��� ����Timer��Ҫ����Ϊstatic ��֤��ͬһ�� �����޷�ֹͣ
	private static Timer timer;
	private String action = "com.sxt.zhp.MyWidget";//���嵥�е�actionһ�¼���
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
			timer.cancel();//ȡ����ʱ��
			Log.i("MyTag", "----cancel()----");
		}
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(final Context context) {
		System.out.println("onEnabled"+this);
		super.onEnabled(context);
		
		//��ʱ��
		timer = new Timer();
		
		//��ʱ����
		TimerTask task = new TimerTask() {			
			@Override
			public void run() {		//���͹㲥		
				Log.i("MyTag", "--------"+MyWidgetProvider.this);
				Intent it = new Intent(action);//�Լ�����
				context.sendBroadcast(it);
			}
		};
		//��ʱ����
		timer.schedule(task, 1000, 1000);
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println(intent.getAction()+"=onReceive="+this);
		
		if(action.equals(intent.getAction())){//�Լ����Ĺ㲥
			//RemoteViews��������һ��View�����ܹ���ʾ�����������У������ں�layout��Դ�ļ�ʵ�ֲ��֡�
			RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			rv.setTextViewText(R.id.update, format.format(new Date()));
			
			 //����������ʱ������������� WidgetTestActivity
	        Intent it = new Intent(context, Demo28Activity.class);
	        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, it, 0);
	        //��update TextView��ӵ���¼� �����ִ����ͼ
			rv.setOnClickPendingIntent(R.id.update, pendingIntent);
			
			//���ý�����ʾ�������
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
