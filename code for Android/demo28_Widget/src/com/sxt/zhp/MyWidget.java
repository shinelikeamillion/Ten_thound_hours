package com.sxt.zhp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyWidget extends AppWidgetProvider{
	private static String MYTAG = "MyTag";
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.i(MYTAG, "=====onDeleted======");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		Log.i(MYTAG, "=====onDisabled======");
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		Log.i(MYTAG, "=====onEnabled======");
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(MYTAG, "=====onReceive======");
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i(MYTAG, "=====onUpdate======");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
}
