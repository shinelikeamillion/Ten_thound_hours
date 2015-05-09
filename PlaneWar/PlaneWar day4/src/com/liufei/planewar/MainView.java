package com.liufei.planewar;

import java.util.Random;

import com.liufei.planewar.util.PublicData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	private boolean isRunning = true;
	private boolean isPause = false;
	private static boolean isPlanePressed;
	private float touchX;
	private float touchY;
	private int gameSpeed = PublicData.gameSpeed;
	private int bgSpeed = 5;
	private long gameCount = 0;
	private Random random = new Random();

	private float screenWidth; // ��Ļ����
	private float screenHeight; // ��Ļ�߶�
	private float initPlaneX; // ��ʼ������λ��
	private float initPlaneY;

	private float bgY1; // ����ͼƬ��y����
	private float bgY2;

	private float scalex; // ���ű���
	private float scaley;

	private Bitmap pauseOrPlayImg; // ��ͣ��ʼͼƬ
	private int pauseIndex = 0; // ��ͣ�Ϳ�ʼ��Ӧ��ͼƬ
	// private Bitmap[] pauseOrPlayBtn; // ��ͣ��ʼͼƬ
	private Bitmap backGround1; // ����ͼ
	private Bitmap backGround2; // ����ͼ

	private Bitmap myPlaneImg; // �ҵķɻ�ͼƬ
	private MyPlane myPlaneSprite; // �ҵķɻ�����
	private Bitmap bulletImg1; // �ӵ�ͼ

	private EnemySmallPlane[] smallPlanes;
	private int smallCount = 5; // �л����ڴ��е�����

	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;
	private Thread thread;

	private Context context;

	public MainView(Context context) {
		super(context);
		this.context = context;
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		init();
		thread.start();
	}
	
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		release();
	}
	/**
	 * ��ȡͼƬ
	 */
	private Bitmap getImgById(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}

	// ��ʼ��
	private void init() {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		backGround1 = getImgById(R.drawable.bg_01);
		backGround2 = getImgById(R.drawable.bg_02);

		scalex = screenWidth / backGround1.getWidth(); // ��������ŵı���
		scaley = screenHeight / backGround2.getHeight();

		bgY2 = -backGround1.getHeight(); // ���ڶ���ͼƬ��ʼ���ڵ�һ��ͼƬ���Ϸ�

		// �ҵķɻ�ͼƬ
		myPlaneImg = getImgById(R.drawable.myplane);
		myPlaneSprite = new MyPlane(getResources(), myPlaneImg, myPlaneImg
				.getWidth() / 2, myPlaneImg.getHeight());
		// �ҵķɻ�λ��
		initPlaneX = (PublicData.screenWidth - myPlaneSprite.getWidth()) / 2;
		initPlaneY = (PublicData.screenHeight - myPlaneSprite.getHeight());
		myPlaneSprite.setXY(initPlaneX, initPlaneY);

		// �ӵ�ͼ
		bulletImg1 = getImgById(R.drawable.bullet2);
		myPlaneSprite.setBulletImg(bulletImg1);

		// ��ʼ���ҵķɻ�
		myPlaneSprite.init();

		// ��ͣͼƬ�Ͱ�ť
		pauseOrPlayImg = getImgById(R.drawable.pause);
		// pauseOrPlayBtn = new Bitmap[2];
		// pauseOrPlayBtn[0] = Bitmap.createBitmap(pauseOrPlayImg, 0, 0,
		// pauseOrPlayImg.getWidth(), pauseOrPlayImg.getHeight() / 2);
		// pauseOrPlayBtn[1] = Bitmap.createBitmap(pauseOrPlayImg, 0,
		// pauseOrPlayImg.getHeight() / 2, pauseOrPlayImg.getWidth(),
		// pauseOrPlayImg.getHeight() / 2);

		// ��ʼ���л�
		smallPlanes = new EnemySmallPlane[smallCount];
		for (int i = 0; i < smallPlanes.length; i++) {
			smallPlanes[i] = new EnemySmallPlane(getResources());
			smallPlanes[i].setMyPlane(myPlaneSprite);
		}
	}

	/**
	 * һ���ɳ�һ�ܵл�
	 */
	private void initEnemy() {
		for (int i = 0; i < smallPlanes.length; i++) {
			if (smallPlanes[i].isVisible == false) {
				smallPlanes[i].reset();
				int n = (int) screenWidth - smallPlanes[i].getWidth();
				int r = random.nextInt(n);
				System.out.println(n + "=========�ų�һ��==========" + r);
				smallPlanes[i].setXY(r, -smallPlanes[i].getHeight());
				break;
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchX = event.getX();
			touchY = event.getY();
			if (myPlaneSprite.isCollWith(touchX, touchY)) {
				isPlanePressed = true;
			}
			if (touchX <= pauseOrPlayImg.getWidth() + 10
					&& touchY <= pauseOrPlayImg.getHeight() / 2 + 10) {
				if (isPause) {
					isPause = false;
					pauseIndex = 0;
					synchronized (thread) {
						thread.notify();
					}
				} else {
					isPause = true;
					pauseIndex = 1;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isPlanePressed) {
				float pX1 = event.getX();
				float pY1 = event.getY();
				float pX0 = pX1 - touchX;
				float pY0 = pY1 - touchY;

				myPlaneSprite.move(pX0, pY0);

				touchX = pX1;
				touchY = pY1;
			}
			break;
		case MotionEvent.ACTION_UP:
			isPlanePressed = false;
			break;
		default:
			break;
		}
		return true;
	}
	/*
	 * ��Ϸ��ͣ
	 */
	public void pause () {
		isPause = true;
		pauseIndex = 1;
	}

	/**
	 * ���ƽ���
	 */
	private void draw() {
		try {
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			// ���㱳��ͼƬ����Ļ�ı�����Ȼ����Ʊ���
			canvas.scale(scalex, scaley);
			canvas.drawBitmap(backGround1, 0, bgY1, paint);
			canvas.drawBitmap(backGround2, 0, bgY2, paint);
			canvas.restore();

			// ���Ʒ���
			paint.setColor(Color.DKGRAY);
			paint.setTextSize(20);
			String score = "Score:" + PublicData.myScore;
			float textWidth = paint.measureText(score); // ���ֿ���
			canvas.drawText(score, (PublicData.screenWidth - textWidth) / 2,
					20, paint);

			// ���Ʒɻ�
			myPlaneSprite.draw(canvas, paint);

			// ���Ƶл�
			for (int i = 0; i < smallPlanes.length; i++) {
				smallPlanes[i].draw(canvas, paint);
			}
			// ����Ѫ��
			drawHP();

			// ������ͣ��ť
			canvas.clipRect(0, 0, pauseOrPlayImg.getWidth(), pauseOrPlayImg
					.getHeight() / 2);
			canvas.drawBitmap(pauseOrPlayImg, 0, -pauseOrPlayImg.getHeight()
					/ 2 * pauseIndex, paint);
			canvas.restore();
		} catch (Exception e) {
			
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	/*
	 * �������Ѫֵ�仯
	 */
	private void drawHP() {
		float wid = screenWidth / 4; // Ѫ������
		float hei = 10; // Ѫ���߶�
		float startX = screenWidth - wid - 10;
		paint.setColor(Color.GRAY);
		canvas.drawRect(startX, screenHeight - hei - 10, startX + wid + 2,
				screenHeight - 10, paint);
		paint.setColor(Color.GREEN);
		if (myPlaneSprite.hp <= 600) {
			paint.setColor(Color.RED);
		}
		// ��Ѫֵ��������
		canvas.drawRect(startX + 1, screenHeight - hei - 10 + 1, startX + 1
				+ (wid * myPlaneSprite.hp * 1.0f / PublicData.myHp),
				screenHeight - 10 - 1, paint);
	}

	/**
	 * �����߼�
	 */
	private void logic() {
		// ʵ�ֹ���Ч��
		bgY1 += bgSpeed;
		bgY2 += bgSpeed;
		if (bgY1 >= backGround1.getHeight()) {
			bgY1 = -backGround1.getHeight();
		}
		if (bgY2 >= backGround2.getHeight()) {
			// ����ͼƬ�Ĵ�С��һ������˭��������ν��
			bgY2 = -backGround2.getHeight();
		}

		// �ҵķɻ�
		if (gameCount % 1 == 0) {
			myPlaneSprite.logic();
		}
		// �л�
		if (gameCount % 20 == 0) {
			initEnemy();
		}
		for (int i = 0; i < smallPlanes.length; i++) {
			smallPlanes[i].logic();
		}
	}

	/*
	 * �ͷ���Դ
	 */
	public void release() {
		isRunning = false;
//		if (myPlaneSprite != null) {
//			myPlaneSprite.release();
//			myPlaneSprite = null;
//		}

		if (backGround1 != null && !backGround1.isRecycled()) {
			backGround1.recycle();
			backGround1 = null;
		}
		if (backGround2 != null && !backGround2.isRecycled()) {
			backGround2.recycle();
			backGround2 = null;
		}
//		// �ͷŵл���Դ
//		if (smallPlanes != null) {
//			for (EnemySmallPlane plane : smallPlanes) {
//				plane.release();
//				plane = null;
//			}
//			smallPlanes = null;
//		}
	}
	/*
	 * ��ͣ�������Ϸ
	 */
	public void goContinue () {
		isPause = false;
		pauseIndex = 0;
		synchronized (thread) {
			thread.notify();
		}
	}
	@Override
	public void run() {
		while (isRunning) {
			gameCount++;
			long startTime = System.currentTimeMillis();

			if (!isPause) {
				logic();
			}
			draw();

			if (isPause) {
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			long endTime = System.currentTimeMillis();

			if (endTime - startTime < gameSpeed) {
				try {
					Thread.sleep(gameSpeed - (endTime - startTime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		release();
	}

}