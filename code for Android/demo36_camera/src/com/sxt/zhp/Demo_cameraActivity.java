package com.sxt.zhp;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Demo_cameraActivity extends Activity {
	private Button btnCamera;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 拍照按钮
		btnCamera = (Button) findViewById(R.id.button_camera); 
		btnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 打开摄像头，获得Camera对象

				Camera camera = null;

				camera = Camera.open();

				camera.takePicture(shutterCallback, null, jpegCallback);

			}
		});
	}

	// 在图像被捕获时回调
	private ShutterCallback shutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			System.out.println("============捕获到图像============");
			// 发出提示用户的声音
			ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC,
					ToneGenerator.MAX_VOLUME);
			tone.startTone(ToneGenerator.TONE_PROP_BEEP2);
		}
	};

	// 返回照片的JPEG格式的数据
	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Parameters ps = camera.getParameters();
			if (ps.getPictureFormat() == PixelFormat.JPEG) {
				// 存储拍照获得的图片
				String path = save(data);
				// 将图片交给Image程序处理
				Uri uri = Uri.fromFile(new File(path));
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.setDataAndType(uri, "image/jpeg");
				startActivity(intent);

				// 显示到界面
				// Bitmap img = BitmapFactory.decodeByteArray(data, 0,
				// data.length);
				// photo.setImageBitmap(img);
			}

			// 一定要释放 否则不能重复拍照
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	};

	// 保存到磁盘
	private String save(byte[] data) {
		String path = "/sdcard/" + System.currentTimeMillis() + ".jpg";
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile(); // 创建文件
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return path;
	}
}