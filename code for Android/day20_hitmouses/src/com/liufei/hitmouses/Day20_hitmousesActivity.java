package com.liufei.hitmouses;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Day20_hitmousesActivity extends Activity implements OnClickListener{
	private static final int FLAG_MOUSE_SHOW = 1;
	private static final int FLAG_MOUSE_HIDE = 2;
	private ImageView[] iv_mouses;
	private TextView tv_level, tv_score;//等级和分数
	private int count;//hole的数量
	private int score = 0;
	private int showCount = 1;//当前地鼠数
	private int level;//等级
	private int[] levelUp = {5, 10, 20 ,35, 60};
	
	
	private int showTime = 1000;//控制显示时间
	private int speed = 1500;//显示速度
	
	private boolean isRunning = true;

	private Random random = new Random();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv_level = (TextView) findViewById(R.id.tv_grade);
		tv_score = (TextView) findViewById(R.id.tv_score);
		
		//初始化地鼠
		init();
		new GameThread().start();
		
	}

	private void init() {
		RelativeLayout ly = (RelativeLayout) findViewById(R.id.game_layout);
		count = ly.getChildCount()-2;//要减掉两个textView
		iv_mouses = new ImageView[count];
		for (int i = 0; i < count; i++) {
			View view = ly.getChildAt(i);
			if (view instanceof ImageView) {
				ImageView imageView = (ImageView) ly.getChildAt(i);
				imageView.setVisibility(View.INVISIBLE);
//				imageView.setOnClickListener(this);
				iv_mouses[i] = imageView;
				iv_mouses[i].setOnClickListener(this);
			}
		}
	}
	
	//升级
	private void levelUp() {
		level ++;
		showCount ++;
		showTime -= 100;
		speed -= 100;
		
		tv_level.setText("等级："+level);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FLAG_MOUSE_SHOW:
				for (int i = 0; i < showCount; i++) {
					int index = random.nextInt(count);
					iv_mouses[index].setVisibility(View.VISIBLE);
				}
				break;
			case FLAG_MOUSE_HIDE:
				init();
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
				
				//通知显示
				handler.sendEmptyMessage(FLAG_MOUSE_SHOW);
				
				try {
					Thread.sleep(showTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//通知隐藏
				handler.sendEmptyMessage(FLAG_MOUSE_HIDE);
				
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (view.isShown()) {
			score++;
//			view.setVisibility(View.GONE);
			tv_score.setText("当前分数："+score);
			
			if (score >= levelUp[level] && level < levelUp.length - 1) {
				levelUp();
			}
		}
	}
}