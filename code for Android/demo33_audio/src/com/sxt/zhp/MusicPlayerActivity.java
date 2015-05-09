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
 * ���ֲ�����
 *
 */
public class MusicPlayerActivity extends Activity implements OnClickListener {
	private MediaPlayer player;
	private SeekBar seekBar; // ����
	private Button btnStart; // ��ʼ����
	private Button btnPause; // ��ͣ
	private Button btnForward; // ���
	private Button btnBack; // ����
	private Timer timer;

	private TextView tvMess;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:// ���½���
				if(player==null){
					return;
				}
				// �Ȼ�ȡ���ֲ���������
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
	 * ��ʼ��ͼ���
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
		//���Ž���������
		player.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer player) {
				System.out.println("==============end============");
				stopPlayer();
			}
		});
	}

	private void reset(){
		tvMess.setText("���޲�������");
		seekBar.setProgress(0);
		btnStart.setText("����");
	}
	/**
	 * ����
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
			// �ظ������Ч
			if (player.isPlaying()) {
				return;
			}
			tvMess.setText("���ڼ���...");
			player.setDataSource("/mnt/sdcard/aa.mp3");// װ����Ƶ
			tvMess.setText("���ڻ���...");
			player.prepare();
			player.start();

			if(timer==null){
				timer = new Timer();
			}
			timer.schedule(new TimerTask() { // ��ʱ���� ÿһ��ִ��һ��
						@Override
						public void run() {
							handler.sendEmptyMessage(1);
						}
					}, 0, 1000);

			// ��ȡ����ʱ��
			int length = player.getDuration();
			updateProcess();

			// �����϶��� ���ֵ
			seekBar.setMax(length);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ʵʱ���½�����Ϣ
	 */
	private void updateProcess() {
		if(player==null){
			return;
		}
		// ��ȡ����ʱ��
		int length = player.getDuration();
		int curLen = player.getCurrentPosition();
//		System.out.println("=========" + curLen);
		tvMess.setText("���Ž��ȣ�" + TimeUtils.secToTime(curLen / 1000) + "/"
				+ TimeUtils.secToTime(length / 1000));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_play: // ��ʼ����
			Button btn = (Button) v;
			if (btn.getText().equals("����")) {
				startPlay();
				btn.setText("ֹͣ");
			} else {
				stopPlayer();
				btn.setText("����");
			}
			break;
		case R.id.btn_pause:// ��ͣ
			btn = (Button) v;
			if (btn.getText().equals("��ͣ")) {
				pausePlay();
				btn.setText("����");
			} else {
				rPlay();
				btn.setText("��ͣ");
			}
			
			break;
		case R.id.btn_fastforward:// ����ǰ��
			fastForward();
			break;
		case R.id.btn_fastback:// ���ٺ���
			fastBack();
			break;
		default:
			break;
		}
	}

	/**
	 * ֹͣ����
	 */
	private void stopPlayer() {
		if (player != null) {
			player.stop();
			player.release();// �ͷ���Դ
			player = null;
		}
		if(timer!=null){//ͬʱֹͣ����
			timer.cancel();
			timer = null;
		}
		reset();//���ý������
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
	 * ����
	 */
	private void rPlay(){
		if (player != null && !player.isPlaying()) {
			player.start();
		}
	}

	/**
	 * ���
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
	 * ����
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
	//�˳�ʱֹͣ����
	@Override
	protected void onStop() {
		stopPlayer();
		super.onStop();
	}
}
