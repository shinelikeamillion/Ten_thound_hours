package com.sxt.zhp;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.AlteredCharSequence;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 多个地鼠和多个等级
 */
public class GameActivity extends Activity implements OnClickListener {
	private static final int FLAG_MOUSE_SHOW = 1;
	private static final int FLAG_MOUSE_HIDDEN = 2;
	private ImageView[] ivs;
	private int score;// 当前得分
	private int showCount = 1;// 同时显示地鼠个数
	private int level;// 等级
	private int[] levelUp = { 5, 10, 15, 20, 25 };

	private int showTime = 1000;// 控制显示的时间长度
	private int speed = 600;// 控制游戏的速度

	private boolean isRunning = true;
	private boolean isPause = false;

	private TextView tvScore;// 显示得分
	private TextView tvLevel;// 显示等级

	private Random random = new Random();//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();

		tvScore = (TextView) findViewById(R.id.tv_score);
		tvLevel = (TextView) findViewById(R.id.tv_level);
		new GameThread().start();
	}

	/**
	 * 初始化资源
	 */
	private void init() {
		RelativeLayout ly = (RelativeLayout) findViewById(R.id.game_layout);
		int count = ly.getChildCount();
		ivs = new ImageView[9];
		for (int i = 0; i < count; i++) {
			View v0 = ly.getChildAt(i);
			if (v0 instanceof ImageView) {// 因为其中还有textView 其他组件
				ImageView v = (ImageView) ly.getChildAt(i);
				v.setVisibility(View.INVISIBLE);
				v.setOnClickListener(this);// 添加上监听
				ivs[i] = v;
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(isPause){//是暂停状态
			pauseDailog();//暂停提示		
		}else{
			MyMusic.getInstance(this).playBackMusic();
		}
	}
	@Override
	protected void onPause() {		
		super.onPause();
		isPause = true;
		MyMusic.getInstance(this).pauseBackMusic();//暂停音乐
		
	}
	@Override
	protected void onStop() {
		super.onStop();
		MyMusic.getInstance(this).stopBackMusic();
	}
	
	/**
	 * 升级
	 */
	private void levelUp() {
		if (score >= levelUp[level] && level < levelUp.length - 1) {
			level++;
			showCount++;// 显示地鼠个数
			speed -= 100;
			showTime -= 100;

			tvLevel.setText("当前等级：" + level);
		}
	}
	/**
	 * 游戏重置
	 */
	private void reset(){
		level = 0;
		showCount = 1;
		speed = 600;
		showTime = 1000;
		score = 0;
		isPause = false;
		tvLevel.setText("当前等级：" + level);
		tvScore.setText("当前得分："+score);
	}

	/**
	 * 点击地鼠
	 */
	public void onClick(View arg0) {
		if (arg0.isShown()) {
			score++;
			
			//播放音效
			MyMusic.getInstance(this).playSound(R.raw.birddie);
			
			arg0.setVisibility(View.GONE);
			tvScore.setText("当前得分：" + score);

			// 是否需要升级
			levelUp();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			isPause = true;//游戏暂停
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示信息");
			builder.setMessage("您选择您的操作：");
			builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
			});
			builder.setNeutralButton("继续", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					isPause = false;
				}
			});
			builder.setNegativeButton("重玩", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					reset();//重置
				}
			});
			builder.create().show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 暂停提示
	 */
	public void pauseDailog(){

		//
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示信息");
		builder.setMessage("游戏已暂停，是否继续？");
		builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//音乐和游戏继续
				MyMusic.getInstance(GameActivity.this).playBackMusic();
				isPause = false;
			}
		});
		builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		builder.create().show();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FLAG_MOUSE_SHOW:
				for (int i = 0; i < showCount; i++) {
					int index = random.nextInt(9);
					ivs[index].setVisibility(View.VISIBLE);// 显示地鼠
				}

				break;
			case FLAG_MOUSE_HIDDEN:
				for (int i = 0; i < 9; i++) {// 全部隐藏
					if (ivs[i].isShown()) {
						ivs[i].setVisibility(View.GONE);
					}
				}
				break;
			default:
				break;
			}
		};
	};

	class GameThread extends Thread {
		@Override
		public void run() {
			while (isRunning) {
				if(!isPause){
					// 通知显示
					handler.sendEmptyMessage(FLAG_MOUSE_SHOW);

					try {
						Thread.sleep(showTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// 通知隐藏
					handler.sendEmptyMessage(FLAG_MOUSE_HIDDEN);
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}