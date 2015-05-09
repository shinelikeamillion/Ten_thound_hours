package com.sxt.zhp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.sxt.zhp.util.TimeUtils;

/**
 * 音乐播放器
 *
 */
public class MusicPlayerActivity extends Activity implements OnClickListener {
	private MediaPlayer player;
	private SeekBar seekBar; // 进度
	private Button btnStart; // 开始播放
	private Button btnPause; // 暂停
	private Button btnForward; // 快进
	private Button btnBack; // 快退
	private Timer timer;

	private TextView tvMess;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:// 更新进度
				if(player==null){
					return;
				}
				// 先获取音乐播放器进度
				int cur = player.getCurrentPosition();
				seekBar.setProgress(cur);
				if(cur==player.getDuration()){
					stopPlayer();
				}
				break;

			default:
				break;
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music);

		initView();
		init();
	}

	/**
	 * 初始视图组件
	 */
	private void initView() {
		tvMess = (TextView) findViewById(R.id.tv_mess);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		btnStart = (Button) findViewById(R.id.btn_play);
		btnPause = (Button) findViewById(R.id.btn_pause);
		btnForward = (Button) findViewById(R.id.btn_fastforward);
		btnBack = (Button) findViewById(R.id.btn_fastback);

		btnStart.setOnClickListener(this);
		btnPause.setOnClickListener(this);
		btnForward.setOnClickListener(this);
		btnBack.setOnClickListener(this);

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar arg0) {
				if (player != null && player.isPlaying()) {
					player.seekTo(arg0.getProgress());
//					updateProcess();
				}
			}

			public void onStartTrackingTouch(SeekBar arg0) {
			}

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				updateProcess();
			}
		});
	}

	private void init() {
		player = new MediaPlayer();
		timer = new Timer();
		//播放结束监听器
		player.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer player) {
				System.out.println("==============end============");
				stopPlayer();
			}
		});
	}

	private void reset(){
		tvMess.setText("暂无播放内容");
		seekBar.setProgress(0);
		btnStart.setText("播放");
	}
	/**
	 * 播放
	 */
	private void startPlay() {
		if (player == null) {
			player = new MediaPlayer();
			player.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer player) {
					System.out.println("==============end============");
					stopPlayer();
				}
			});
		}
		try {
			// 重复点击无效
			if (player.isPlaying()) {
				return;
			}
			tvMess.setText("正在加载...");
			player.setDataSource("/mnt/sdcard/aa.mp3");// 装载音频
			tvMess.setText("正在缓冲...");
			player.prepare();
			player.start();

			if(timer==null){
				timer = new Timer();
			}
			timer.schedule(new TimerTask() { // 定时任务 每一秒执行一次
						@Override
						public void run() {
							handler.sendEmptyMessage(1);
						}
					}, 0, 1000);

			// 获取播放时长
			int length = player.getDuration();
			updateProcess();

			// 设置拖动条 最大值
			seekBar.setMax(length);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实时更新进度信息
	 */
	private void updateProcess() {
		if(player==null){
			return;
		}
		// 获取播放时长
		int length = player.getDuration();
		int curLen = player.getCurrentPosition();
//		System.out.println("=========" + curLen);
		tvMess.setText("播放进度：" + TimeUtils.secToTime(curLen / 1000) + "/"
				+ TimeUtils.secToTime(length / 1000));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_play: // 开始播放
			Button btn = (Button) v;
			if (btn.getText().equals("播放")) {
				startPlay();
				btn.setText("停止");
			} else {
				stopPlayer();
				btn.setText("播放");
			}
			break;
		case R.id.btn_pause:// 暂停
			btn = (Button) v;
			if (btn.getText().equals("暂停")) {
				pausePlay();
				btn.setText("继续");
			} else {
				rPlay();
				btn.setText("暂停");
			}
			
			break;
		case R.id.btn_fastforward:// 快速前进
			fastForward();
			break;
		case R.id.btn_fastback:// 快速后退
			fastBack();
			break;
		default:
			break;
		}
	}

	/**
	 * 停止播放
	 */
	private void stopPlayer() {
		if (player != null) {
			player.stop();
			player.release();// 释放资源
			player = null;
		}
		if(timer!=null){//同时停止任务
			timer.cancel();
			timer = null;
		}
		reset();//重置界面参数
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
	 * 继续
	 */
	private void rPlay(){
		if (player != null && !player.isPlaying()) {
			player.start();
		}
	}

	/**
	 * 快进
	 */
	private void fastForward() {
		if (player != null && player.isPlaying()) {
			int cur = player.getCurrentPosition();
			if (cur + 5000 < player.getDuration()) {
				player.seekTo(player.getCurrentPosition() + 5000);
			}else{
				player.seekTo(player.getDuration());
			}			
		}
	}
	/**
	 * 快退
	 */
	private void fastBack(){
		if (player != null && player.isPlaying()) {
			int cur = player.getCurrentPosition();
			if (cur - 5000 > 0) {
				player.seekTo(player.getCurrentPosition() - 5000);
			}else{
				player.seekTo(0);
			}			
		}
	}
	//退出时停止播放
	@Override
	protected void onStop() {
		stopPlayer();
		super.onStop();
	}
}
