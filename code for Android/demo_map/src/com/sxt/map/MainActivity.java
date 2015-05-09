package com.sxt.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(MainActivity.this, Demo_mapActivity.class);
				startActivity(it);
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(MainActivity.this, SateActivity.class);
				startActivity(it);
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(MainActivity.this, TrafficActivity.class);
				startActivity(it);
			}
		});
	}
}
