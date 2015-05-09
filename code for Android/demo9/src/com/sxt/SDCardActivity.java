package com.sxt;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SDCardActivity extends Activity {
	private TextView tv;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.raw);
		
		tv = (TextView) findViewById(R.id.textView_raw);
		findViewById(R.id.button_raw).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				readSD();
				
			}
		});
	}
	private void readSDCard(){
		File dir = new File("/mnt/sdcard/");
//		File[] childFiles = file.listFiles();
//		for (File file2 : childFiles) {
//			System.out.println(file2.getAbsolutePath()+"===="+file2.getName());
//			tv.append(file2.getAbsolutePath()+"\n");
//		}
		File file = new File(dir,getPackageName());
		if(!file.exists()){
			file.mkdirs();
		}
		//创建文件
		for (int i = 0; i < 10; i++) {
			File fc = new File(file,System.currentTimeMillis()+".txt");
			try {
				fc.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readSD(){
		System.out.println("===="+Environment.getExternalStorageState());
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(this, "对不起，没有发现SDCard！", Toast.LENGTH_LONG).show();
			return;
		}
		
//		File dir = getExternalCacheDir();
//		tv.setText(dir.getAbsolutePath());
		File down = Environment.getDownloadCacheDirectory();
		tv.append("\ndownloadCacheDir:"+down.getAbsolutePath());
		
		down = Environment.getExternalStorageDirectory();
		tv.append("\nExternalStorage:"+down.getAbsolutePath());
		
		//Environment.getExternalStoragePublicDirectory(Environment.MEDIA_MOUNTED);
		down = Environment.getRootDirectory();
		tv.append("\nRootDir:"+down.getAbsolutePath());
		
		down = Environment.getDataDirectory();
		tv.append("\nDataDir:"+down.getAbsolutePath());
		
	}
	
}
