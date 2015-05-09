package com.sxt.zhp;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class NetImageActivity extends Activity{
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				
				netImage();
			}
		});
	}
	
	private void netImage(){
		//第一种方式
//		Bitmap bitMap = BitmapFactory.decodeStream(ins);			
//		img.setImageBitmap(bitMap);
		//第二种方式
//		Drawable drawable = new BitmapDrawable(ins);
		
		ImageThread it = new ImageThread("http://192.168.1.99:808/img/simulasion.gif");
//		iv.post(it);//发布线程
//		iv.postDelayed(it, 1000);
		runOnUiThread(it);
	}
	
	class ImageThread extends Thread{
		private String urlStr;
		public ImageThread(String url){
			this.urlStr = url;
		}
		@Override
		public void run() {
			try {
				URL url = new URL(urlStr);
				InputStream is = url.openStream();
				Bitmap img = BitmapFactory.decodeStream(is);
				iv.setImageBitmap(img);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
