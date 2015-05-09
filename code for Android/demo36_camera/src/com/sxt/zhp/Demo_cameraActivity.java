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
		// ���հ�ť
		btnCamera = (Button) findViewById(R.id.button_camera); 
		btnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ������ͷ�����Camera����

				Camera camera = null;

				camera = Camera.open();

				camera.takePicture(shutterCallback, null, jpegCallback);

			}
		});
	}

	// ��ͼ�񱻲���ʱ�ص�
	private ShutterCallback shutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			System.out.println("============����ͼ��============");
			// ������ʾ�û�������
			ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC,
					ToneGenerator.MAX_VOLUME);
			tone.startTone(ToneGenerator.TONE_PROP_BEEP2);
		}
	};

	// ������Ƭ��JPEG��ʽ������
	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Parameters ps = camera.getParameters();
			if (ps.getPictureFormat() == PixelFormat.JPEG) {
				// �洢���ջ�õ�ͼƬ
				String path = save(data);
				// ��ͼƬ����Image������
				Uri uri = Uri.fromFile(new File(path));
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.setDataAndType(uri, "image/jpeg");
				startActivity(intent);

				// ��ʾ������
				// Bitmap img = BitmapFactory.decodeByteArray(data, 0,
				// data.length);
				// photo.setImageBitmap(img);
			}

			// һ��Ҫ�ͷ� �������ظ�����
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	};

	// ���浽����
	private String save(byte[] data) {
		String path = "/sdcard/" + System.currentTimeMillis() + ".jpg";
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile(); // �����ļ�
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