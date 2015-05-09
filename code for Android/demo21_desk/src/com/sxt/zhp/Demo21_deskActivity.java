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
 * 1.����Ȩ�� 2.�����ȡ��ֽ������WallpaperManager.getInstance(Demo21_deskActivity.this);
 * 3.����
 * 
 * @author Administrator
 * 
 */
/**
 * getWallpaper();��õ�ǰϵͳ��ֽ  ���úͻ�ñ�ֽ�����������ַ�ʽ
 *     <uses-permission android:name="android.permission.SET_WALLPAPER"/>
 *				//���ַ�ʽ
				//1.
//				Drawable wallImg = getWallpaper();
				//2.��ֽ������
				WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
				Drawable wallImg = wm.getDrawable();
				
				���ñ�ֽ����ñ�ֽ����ԭ��ֽ
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

		// ��һ��
		findViewById(R.id.btn_next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				index++;
				index = index % imgs.length;
				Log.i("MyTag", "=====" + index);
				iv.setImageResource(imgs[index]);

			}
		});

		// ���ñ�ֽ
		findViewById(R.id.btn_set).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��ȡ��ֽ������
				WallpaperManager wm = WallpaperManager
						.getInstance(Demo21_deskActivity.this);
				try {
					wm.setResource(imgs[index]);// ���ñ�ֽ

					Toast.makeText(getApplication(), "�������", Toast.LENGTH_LONG)
							.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		// ��ԭ��ֽ
		findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��ȡ��ֽ������
				WallpaperManager wm = WallpaperManager
						.getInstance(Demo21_deskActivity.this);
				try {
					wm.clear();

					Toast.makeText(getApplication(), "���", Toast.LENGTH_LONG)
							.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		
		//��ȡ��ֽ
		findViewById(R.id.btn_get).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��ȡ��ֽ������
				Drawable d = getWallpaper();
				iv.setImageDrawable(d);
			}
		});
	}
}