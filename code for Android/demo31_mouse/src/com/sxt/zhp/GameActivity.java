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
 * �������Ͷ���ȼ�
 */
public class GameActivity extends Activity implements OnClickListener {
	private static final int FLAG_MOUSE_SHOW = 1;
	private static final int FLAG_MOUSE_HIDDEN = 2;
	private ImageView[] ivs;
	private int score;// ��ǰ�÷�
	private int showCount = 1;// ͬʱ��ʾ�������
	private int level;// �ȼ�
	private int[] levelUp = { 5, 10, 15, 20, 25 };

	private int showTime = 1000;// ������ʾ��ʱ�䳤��
	private int speed = 600;// ������Ϸ���ٶ�

	private boolean isRunning = true;
	private boolean isPause = false;

	private TextView tvScore;// ��ʾ�÷�
	private TextView tvLevel;// ��ʾ�ȼ�

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
	 * ��ʼ����Դ
	 */
	private void init() {
		RelativeLayout ly = (RelativeLayout) findViewById(R.id.game_layout);
		int count = ly.getChildCount();
		ivs = new ImageView[9];
		for (int i = 0; i < count; i++) {
			View v0 = ly.getChildAt(i);
			if (v0 instanceof ImageView) {// ��Ϊ���л���textView �������
				ImageView v = (ImageView) ly.getChildAt(i);
				v.setVisibility(View.INVISIBLE);
				v.setOnClickListener(this);// ����ϼ���
				ivs[i] = v;
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(isPause){//����ͣ״̬
			pauseDailog();//��ͣ��ʾ		
		}else{
			MyMusic.getInstance(this).playBackMusic();
		}
	}
	@Override
	protected void onPause() {		
		super.onPause();
		isPause = true;
		MyMusic.getInstance(this).pauseBackMusic();//��ͣ����
		
	}
	@Override
	protected void onStop() {
		super.onStop();
		MyMusic.getInstance(this).stopBackMusic();
	}
	
	/**
	 * ����
	 */
	private void levelUp() {
		if (score >= levelUp[level] && level < levelUp.length - 1) {
			level++;
			showCount++;// ��ʾ�������
			speed -= 100;
			showTime -= 100;

			tvLevel.setText("��ǰ�ȼ���" + level);
		}
	}
	/**
	 * ��Ϸ����
	 */
	private void reset(){
		level = 0;
		showCount = 1;
		speed = 600;
		showTime = 1000;
		score = 0;
		isPause = false;
		tvLevel.setText("��ǰ�ȼ���" + level);
		tvScore.setText("��ǰ�÷֣�"+score);
	}

	/**
	 * �������
	 */
	public void onClick(View arg0) {
		if (arg0.isShown()) {
			score++;
			
			//������Ч
			MyMusic.getInstance(this).playSound(R.raw.birddie);
			
			arg0.setVisibility(View.GONE);
			tvScore.setText("��ǰ�÷֣�" + score);

			// �Ƿ���Ҫ����
			levelUp();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			isPause = true;//��Ϸ��ͣ
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("��ʾ��Ϣ");
			builder.setMessage("��ѡ�����Ĳ�����");
			builder.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
			});
			builder.setNeutralButton("����", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					isPause = false;
				}
			});
			builder.setNegativeButton("����", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					reset();//����
				}
			});
			builder.create().show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * ��ͣ��ʾ
	 */
	public void pauseDailog(){

		//
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ��Ϣ");
		builder.setMessage("��Ϸ����ͣ���Ƿ������");
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//���ֺ���Ϸ����
				MyMusic.getInstance(GameActivity.this).playBackMusic();
				isPause = false;
			}
		});
		builder.setNegativeButton("�˳�", new DialogInterface.OnClickListener() {			
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
					ivs[index].setVisibility(View.VISIBLE);// ��ʾ����
				}

				break;
			case FLAG_MOUSE_HIDDEN:
				for (int i = 0; i < 9; i++) {// ȫ������
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
					// ֪ͨ��ʾ
					handler.sendEmptyMessage(FLAG_MOUSE_SHOW);

					try {
						Thread.sleep(showTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// ֪ͨ����
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