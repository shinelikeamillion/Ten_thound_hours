package com.sxt.app;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 应用程序的安装
 *
 */
public class InstallActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		Button btn = new Button(this);
		setContentView(btn);
		btn.setText("开始安装");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				install();
			}
		});
		
	}
	
	private void install(){
			File cardDir = Environment.getExternalStorageDirectory();
		
			File file = new File(cardDir,"SensorSimulatorSettings-2.0-rc1.apk");
		
		  	Uri uri = Uri.fromFile(file);
	        Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.setDataAndType(uri, "application/vnd.android.package-archive");
	        startActivity(intent);
	}
}
