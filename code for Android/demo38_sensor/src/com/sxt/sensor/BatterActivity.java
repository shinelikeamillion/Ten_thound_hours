package com.sxt.sensor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class BatterActivity extends Activity {
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.textView1);
	}

	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);

	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			int level = intent.getIntExtra("level", 0);//电池当前水平
			int scale = intent.getIntExtra("scale", 0);//电池总刻度
			int status = intent.getIntExtra("status", 0);//电池当前状态
			int health = intent.getIntExtra("health", 0);//电池健康状况
			tv.setText("电池信息：" + level + "  " + scale);

			String statusString = "";

			switch (status) {
			case BatteryManager.BATTERY_STATUS_UNKNOWN:
				statusString = "unknown";
				break;
			case BatteryManager.BATTERY_STATUS_CHARGING:
				statusString = "charging";
				break;
			case BatteryManager.BATTERY_STATUS_DISCHARGING:
				statusString = "discharging";
				break;
			case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
				statusString = "not charging";
				break;
			case BatteryManager.BATTERY_STATUS_FULL:
				statusString = "full";
				break;
			}
			tv.append("\n"+statusString);
			
			String healthString = "";

			switch (health) {
			case BatteryManager.BATTERY_HEALTH_UNKNOWN:
				healthString = "unknown";
				break;
			case BatteryManager.BATTERY_HEALTH_GOOD:
				healthString = "good";
				break;
			case BatteryManager.BATTERY_HEALTH_OVERHEAT:
				healthString = "overheat";
				break;
			case BatteryManager.BATTERY_HEALTH_DEAD:
				healthString = "dead";
				break;
			case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
				healthString = "voltage";
				break;
			case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
				healthString = "unspecified failure";
				break;
			}
			tv.append("\n"+healthString);
		}
	};
}
