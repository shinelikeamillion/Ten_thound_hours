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
	private AudioManager audioManager;// 音频管理器

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio);
		mess = (TextView) findViewById(R.id.mess);
		btnPause = (Button) findViewById(R.id.button_pause);
		//获取系统音频服务管理器
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		// 播放音乐
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
		// 停止音乐
		findViewById(R.id.button_stop).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						stopPlayer();
					}
				});
		// 暂停音乐
		findViewById(R.id.button_pause).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						pausePlay();
					}
				});
		// 继续音乐
		findViewById(R.id.button_continue).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						replayd();
					}
				});
		// 增加音量
		findViewById(R.id.button_vAdd).setOnClickListener(listen);
		// 降低音量
		findViewById(R.id.button_vSub).setOnClickListener(listen);
	}

	/**
	 * 播放资源库下音频文件
	 */
	private void playRaw() {
		try {
			mess.setText("正在缓冲...");
			// 这里的播放器创建方式和其他不同
			player = MediaPlayer.create(this, R.raw.tstart);

			// player.prepare();//缓冲 这里不需要也不能缓冲
			player.setVolume(0.2f, 0.2f);// 设置左右声道音量
			player.setLooping(false);
			player.start();// 开始或恢复播放
			mess.setText("开始播放...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放assets目录下的音频文件
	 * 
	 */
	private void playAsset() {
		if (player == null) {
			player = new MediaPlayer();
		}
		// 正在播放时重复点击开始无效
		if (player.isPlaying()) {
			return;
		}
		mess.setText("正在缓冲...");
		AssetManager am = getAssets();
		AssetFileDescriptor afd;
		try {
			player.reset();
			afd = am.openFd("audio/test.mp3");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			player.prepare();// 缓冲
			player.start();
			mess.setText("正在播放...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放SDCard下的音乐 突突兔
	 */
	private void playSDCard() {
		if (player == null) {
			player = new MediaPlayer();
		}
		try {
			// 这里处理 可以重复播放 ：先停止 再重置
			if (player.isPlaying()) {
				player.stop();
				player.reset();
			}

			player.setDataSource("/mnt/sdcard/aa.mp3");// 装载音频
			player.prepare();
			player.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放网络 在线播放
	 */
	private void playNet() {
		try {
			if (player == null) {
				player = new MediaPlayer();
			}
			// 这里处理 可以重复播放 ：先停止 再重置
			if (player.isPlaying()) {
				player.stop();
				player.reset();
			}
			player.setDataSource(this, Uri
					.parse("http://192.168.1.99:808/aa.mp3"));
			player.setLooping(true);// 循环播放
			player.prepare();
			player.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止播放
	 */
	private void stopPlayer() {
		if (player != null && player.isPlaying()) {
			player.stop();
			player.release();// 释放资源
			player = null;
		}
	}

	/**
	 * 暂停
	 */
	private void pausePlay() {
		if (player != null && player.isPlaying()) {
			player.pause();// 暂停播放
		}
	}

	/**
	 * 继续 重新播放
	 */
	private void replayd() {
		if (player != null && !player.isPlaying()) {
			player.start();// 开始或恢复播放
		}
	}

	// 音量调节
	private OnClickListener listen = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// AudioManager.STREAM_MUSIC 指定媒体音量 或其他类型音量如铃声，闹铃等
			switch (arg0.getId()) {
			case R.id.button_vAdd:// 增加音量ADJUST_RAISE
				audioManager.adjustStreamVolume(AudioManager.STREAM_RING,
						AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				// audioManager.adjustVolume(AudioManager.ADJUST_RAISE,
				// AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
				break;
			case R.id.button_vSub:// 降低音量ADJUST_LOWER
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