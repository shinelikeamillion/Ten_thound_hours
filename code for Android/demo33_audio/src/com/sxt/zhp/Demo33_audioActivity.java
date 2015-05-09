package com.sxt.zhp;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Demo33_audioActivity extends Activity {
	private MediaPlayer player;
	private TextView mess;
	private Button btnPause;
	private AudioManager audioManager;// ��Ƶ������

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);
		mess = (TextView) findViewById(R.id.mess);
		btnPause = (Button) findViewById(R.id.button_pause);
		//��ȡϵͳ��Ƶ���������
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		// ��������
		findViewById(R.id.button_play).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						playAsset();
						// playSDCard();
						// playNet();
						// playRaw();
					}
				});
		// ֹͣ����
		findViewById(R.id.button_stop).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						stopPlayer();
					}
				});
		// ��ͣ����
		findViewById(R.id.button_pause).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						pausePlay();
					}
				});
		// ��������
		findViewById(R.id.button_continue).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						replayd();
					}
				});
		// ��������
		findViewById(R.id.button_vAdd).setOnClickListener(listen);
		// ��������
		findViewById(R.id.button_vSub).setOnClickListener(listen);
	}

	/**
	 * ������Դ������Ƶ�ļ�
	 */
	private void playRaw() {
		try {
			mess.setText("���ڻ���...");
			// ����Ĳ�����������ʽ��������ͬ
			player = MediaPlayer.create(this, R.raw.tstart);

			// player.prepare();//���� ���ﲻ��ҪҲ���ܻ���
			player.setVolume(0.2f, 0.2f);// ����������������
			player.setLooping(false);
			player.start();// ��ʼ��ָ�����
			mess.setText("��ʼ����...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����assetsĿ¼�µ���Ƶ�ļ�
	 * 
	 */
	private void playAsset() {
		if (player == null) {
			player = new MediaPlayer();
		}
		// ���ڲ���ʱ�ظ������ʼ��Ч
		if (player.isPlaying()) {
			return;
		}
		mess.setText("���ڻ���...");
		AssetManager am = getAssets();
		AssetFileDescriptor afd;
		try {
			player.reset();
			afd = am.openFd("audio/test.mp3");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			player.prepare();// ����
			player.start();
			mess.setText("���ڲ���...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����SDCard�µ����� ͻͻ��
	 */
	private void playSDCard() {
		if (player == null) {
			player = new MediaPlayer();
		}
		try {
			// ���ﴦ�� �����ظ����� ����ֹͣ ������
			if (player.isPlaying()) {
				player.stop();
				player.reset();
			}

			player.setDataSource("/mnt/sdcard/aa.mp3");// װ����Ƶ
			player.prepare();
			player.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������� ���߲���
	 */
	private void playNet() {
		try {
			if (player == null) {
				player = new MediaPlayer();
			}
			// ���ﴦ�� �����ظ����� ����ֹͣ ������
			if (player.isPlaying()) {
				player.stop();
				player.reset();
			}
			player.setDataSource(this, Uri
					.parse("http://192.168.1.99:808/aa.mp3"));
			player.setLooping(true);// ѭ������
			player.prepare();
			player.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ֹͣ����
	 */
	private void stopPlayer() {
		if (player != null && player.isPlaying()) {
			player.stop();
			player.release();// �ͷ���Դ
			player = null;
		}
	}

	/**
	 * ��ͣ
	 */
	private void pausePlay() {
		if (player != null && player.isPlaying()) {
			player.pause();// ��ͣ����
		}
	}

	/**
	 * ���� ���²���
	 */
	private void replayd() {
		if (player != null && !player.isPlaying()) {
			player.start();// ��ʼ��ָ�����
		}
	}

	// ��������
	private OnClickListener listen = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// AudioManager.STREAM_MUSIC ָ��ý������ ���������������������������
			switch (arg0.getId()) {
			case R.id.button_vAdd:// ��������ADJUST_RAISE
				audioManager.adjustStreamVolume(AudioManager.STREAM_RING,
						AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				// audioManager.adjustVolume(AudioManager.ADJUST_RAISE,
				// AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
				break;
			case R.id.button_vSub:// ��������ADJUST_LOWER
				audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
						AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
				// audioManager.adjustVolume(AudioManager.ADJUST_LOWER,
				// AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
				break;
			default:
				break;
			}

		}
	};
}