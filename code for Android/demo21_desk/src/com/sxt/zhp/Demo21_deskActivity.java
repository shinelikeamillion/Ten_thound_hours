package com.sxt.zhp;

import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 1.配置权限 2.代码获取壁纸管理器WallpaperManager.getInstance(Demo21_deskActivity.this);
 * 3.设置
 * 
 * @author Administrator
 * 
 */
/**
 * getWallpaper();获得当前系统壁纸  设置和获得壁纸都可以用两种方式
 *     <uses-permission android:name="android.permission.SET_WALLPAPER"/>
 *				//两种方式
				//1.
//				Drawable wallImg = getWallpaper();
				//2.壁纸管理器
				WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
				Drawable wallImg = wm.getDrawable();
				
				设置壁纸，获得壁纸，还原壁纸
 */
public class Demo21_deskActivity extends Activity {
	private int[] imgs = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d };
	private int index;
	private ImageView iv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		iv = (ImageView) findViewById(R.id.imageView1);

		// 下一张
		findViewById(R.id.btn_next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				index++;
				index = index % imgs.length;
				Log.i("MyTag", "=====" + index);
				iv.setImageResource(imgs[index]);

			}
		});

		// 设置壁纸
		findViewById(R.id.btn_set).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取壁纸管理器
				WallpaperManager wm = WallpaperManager
						.getInstance(Demo21_deskActivity.this);
				try {
					wm.setResource(imgs[index]);// 设置壁纸

					Toast.makeText(getApplication(), "设置完成", Toast.LENGTH_LONG)
							.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		// 还原壁纸
		findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取壁纸管理器
				WallpaperManager wm = WallpaperManager
						.getInstance(Demo21_deskActivity.this);
				try {
					wm.clear();

					Toast.makeText(getApplication(), "完成", Toast.LENGTH_LONG)
							.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		
		//获取壁纸
		findViewById(R.id.btn_get).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取壁纸管理器
				Drawable d = getWallpaper();
				iv.setImageDrawable(d);
			}
		});
	}
}