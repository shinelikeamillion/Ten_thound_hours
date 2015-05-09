package com.sxt;

import com.sxt.zhp.R;

import android.app.Activity;
import android.os.Bundle;

public class Activity1 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout1);
		
		System.out.println("====Activity1====");
	}
}
