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
	private static boolean isPause = false;
	private static boolean isPlanePressed;
	private Sprite score = new Sprite();
	private float touchX;
	private float touchY;
	private int gameSpeed = 200;
	private int bgSpeed = 5;
	private long gameCount = 0;
	private Random random = new Random();

	private float screenWidth; // 屏幕宽度
	private float screenHeight; // 屏幕高度
	private float initPlaneX; // 初始的坐标位置
	private float initPlaneY;

	private float bgY1; // 背景图片的y坐标
	private float bgY2;

	private float scalex; // 缩放比例
	private float scaley;

	private Bitmap pauseOrPlayImg; // 暂停或开始图片
	private Bitmap[] pauseOrPlayBtn; // 暂停或开始图片
	private Bitmap backGround1; // 背景图
	private Bitmap backGround2; // 背景图

	private Bitmap myPlaneImg; // 我的飞机图片
	private MyPlane myPlaneSprite; // 我的飞机动画
	private Bitmap bulletImg1; // 子弹图
	
	private EnemySmallPlane[] smallPlanes;
	private int smallCount = 5; // 敌机在内存中的数量

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

	/**
	 * 获取图片
	 */
	private Bitmap getImgById(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}

	// 初始化
	private void init() {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		backGround1 = getImgById(R.drawable.bg_01);
		backGround2 = getImgById(R.drawable.bg_02);

		scalex = screenWidth / backGround1.getWidth(); // 计算出缩放的比例
		scaley = screenHeight / backGround2.getHeight();

		bgY2 = -backGround1.getHeight(); // 将第二张图片初始化在第一张图片的上方

		// 我的飞机图片
		myPlaneImg = getImgById(R.drawable.myplane);
		myPlaneSprite = new MyPlane(getResources(), myPlaneImg, myPlaneImg.getWidth() / 2,
				myPlaneImg.getHeight());
		// 我的飞机位置
		initPlaneX = (PublicData.screenWidth - myPlaneSprite.getWidth()) / 2;
		initPlaneY = (PublicData.screenHeight - myPlaneSprite.getHeight());
		myPlaneSprite.setXY(initPlaneX, initPlaneY);

		// 子弹图
		bulletImg1 = getImgById(R.drawable.bullet2);
		myPlaneSprite.setBulletImg(bulletImg1);

		// 初始化我的飞机
		myPlaneSprite.init();

		// 暂停图片和按钮
		pauseOrPlayImg = getImgById(R.drawable.pause);
		pauseOrPlayBtn = new Bitmap[2];
		pauseOrPlayBtn[0] = Bitmap.createBitmap(pauseOrPlayImg, 0, 0,
				pauseOrPlayImg.getWidth(), pauseOrPlayImg.getHeight() / 2);
		pauseOrPlayBtn[1] = Bitmap.createBitmap(pauseOrPlayImg, 0,
				pauseOrPlayImg.getHeight() / 2, pauseOrPlayImg.getWidth(),
				pauseOrPlayImg.getHeight() / 2);

		// 初始化敌机
		smallPlanes = new EnemySmallPlane[smallCount];
		for (int i = 0; i < smallPlanes.length; i++) {
			smallPlanes[i] = new EnemySmallPlane(getResources());
			smallPlanes[i].setMyPlane(myPlaneSprite);
		}
	}

	/**
	 * 一次派出一架敌机
	 */
	private void initEnemy() {
		for (int i = 0; i < smallPlanes.length; i++) {
			if (smallPlanes[i].isVisible == false) {
				smallPlanes[i].reset();
				int n = (int) screenWidth - smallPlanes[i].getWidth();
				int r = random.nextInt(n);
				System.out.println(n + "=========放出一架==========" + r);
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

	/**
	 * 绘制界面
	 */
	private void draw() {
		try {
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			// 计算背景图片与屏幕的比例，然后绘制背景
			canvas.scale(scalex, scaley);
			canvas.drawBitmap(backGround1, 0, bgY1, paint);
			canvas.drawBitmap(backGround2, 0, bgY2, paint);
			canvas.restore();
			// 绘制暂停按钮
//			canvas.drawBitmap(pauseOrPlayBtn[0], 0, 0, paint);
			paint.setColor(Color.DKGRAY);
			paint.setTextSize(20);
			// 绘制飞机
			myPlaneSprite.draw(canvas, paint);

			// 绘制敌机
			for (int i = 0; i < smallPlanes.length; i++) {
				smallPlanes[i].draw(canvas, paint);
			}

			canvas.restore();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	/**
	 * 动画逻辑
	 */
	private void logic() {
		// 实现滚屏效果
		bgY1 += bgSpeed;
		bgY2 += bgSpeed;
		if (bgY1 >= backGround1.getHeight()) {
			bgY1 = -backGround1.getHeight();
		}
		if (bgY2 >= backGround2.getHeight()) {
			// 两张图片的大小都一样等于谁都是无所谓的
			bgY2 = -backGround2.getHeight();
		}

		// 我的飞机
		if (gameCount % 1 == 0) {
			myPlaneSprite.logic();
		}
		 //敌机
		 if (gameCount % 20 == 0) {
		 initEnemy();
		 }
		 for (int i = 0; i < smallPlanes.length; i++) {
		 smallPlanes[i].logic();
		 }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		release();
	}

	/*
	 * 释放资源
	 */
	private void release() {
		isRunning = false;
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
			long endTime = System.currentTimeMillis();

			if (endTime - startTime < gameSpeed) {
				try {
					Thread.sleep(gameSpeed - (endTime - startTime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
