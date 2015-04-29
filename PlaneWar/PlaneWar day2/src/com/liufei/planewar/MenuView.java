package com.liufei.planewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MenuView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	private boolean isRunning = true;
	private boolean isPause = false;
	private int gameSpeed = 500;
	private int gameCount = 0;

	private float screenWidth;// ��Ļ���
	private float screenHeight;// ��Ļ�߶�
	private float initPlaneX;// ��ʼ������λ��
	private float initPlaneY;

	private float scalex; // ���ű���
	private float scaley;

	private Bitmap backGround; // ����ͼ
	private Bitmap titleImg; // ����ͼƬ
	private Bitmap menuBtnBack; // �˵�����
	private Bitmap menuSelectBack; // ѡ��ʱ������ͼƬ

	private Bitmap menuImgPlane; // �˵��еķɻ�����
	private Sprite menuPlaneSprite; // �˵��еĶ����ɻ�
	float inverY = 30; // �˵���ļ��
	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;
	private Thread thread;
	private Rect tempRect = new Rect();
	private String[] texts = { "��ʼ��Ϸ", "�˳���Ϸ", "������Ϣ" };

	private float menuBtnStartX; // ��һ���˵���ť��λ��
	private float menuBtnStartY;

	private Context context;

	public MenuView(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		thread = new Thread(this);
	}

	/**
	 * ��ʼ����Դ
	 */
	private void init() {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();

		backGround = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg_01);

		scalex = screenWidth / backGround.getWidth(); // �������ű���
		scaley = screenHeight / backGround.getHeight();

		menuBtnBack = getImgByRid(R.drawable.button);
		menuSelectBack = getImgByRid(R.drawable.button2);
		menuImgPlane = getImgByRid(R.drawable.fly);
		titleImg = getImgByRid(R.drawable.text);

		// �ɻ�����
		menuPlaneSprite = new Sprite(menuImgPlane, menuImgPlane.getWidth(),
				menuImgPlane.getHeight() / 3);

		initPlaneX = (screenWidth - menuImgPlane.getWidth()) / 2;
		initPlaneY = screenHeight / 5;
	}

	/*
	 * ��ȡͼƬ
	 */
	private Bitmap getImgByRid(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}

	/*
	 * ����
	 */
	private void draw() {
		try {
			float startY = initPlaneY;

			canvas = holder.lockCanvas();
			canvas.save();
			// ���㱳��ͼƬ����Ļ�ı���
			canvas.scale(scalex, scaley);
			// ���Ʊ���
			canvas.drawBitmap(backGround, 0, 0, paint);
			canvas.restore(); // ��ԭ

			// ���Ʒɻ�
			menuPlaneSprite.setXY(initPlaneX, initPlaneY);
			menuPlaneSprite.draw(canvas, paint);
			startY = startY + menuImgPlane.getHeight() / 3;

			// ���Ʊ���
			canvas.drawBitmap(titleImg,
					(screenWidth - titleImg.getWidth()) / 2, initPlaneY
							+ menuImgPlane.getHeight() / 3 + 10, paint);
			startY = startY + titleImg.getHeight();

			// ���Ʋ˵���
			paint.setTextSize(30);
			paint.setColor(Color.WHITE);

			// ���ذ�Χ�����ַ�������С��һ��Rect����
			paint.getTextBounds(texts[0], 0, texts[0].length(), tempRect);
			float strheight = tempRect.height();
			float strwidth = tempRect.width();

			// ���ֵ���ʼλ��Ϊ���ֵĵײ�(����ͼƬ��ֹ��Ϊ���Ͻ�)���������´���,�����뱳����̫��ֱ���У���ȥ4���Ե���
			canvas.drawBitmap(menuBtnBack, (screenWidth - (menuBtnBack
					.getWidth())) / 2, startY + inverY, paint);
			canvas.drawText(texts[0], (screenWidth - strwidth) / 2, startY
					+ inverY + menuBtnBack.getHeight()
					- (menuBtnBack.getHeight() - strheight) / 2 - 4, paint);
			// ��λ�ñ����������Դ������¼�
			menuBtnStartX = (screenWidth - menuBtnBack.getWidth()) / 2;
			menuBtnStartY = startY + inverY;
			startY = startY + inverY + menuBtnBack.getHeight();

			canvas.drawBitmap(menuBtnBack, (screenWidth - (menuBtnBack
					.getWidth())) / 2, startY + inverY, paint);
			canvas.drawText(texts[1], (screenWidth - strwidth) / 2, startY
					+ inverY + menuBtnBack.getHeight()
					- (menuBtnBack.getHeight() - strheight) / 2 - 4, paint);
			startY = startY + inverY + menuBtnBack.getHeight();

			canvas.drawBitmap(menuBtnBack, (screenWidth - (menuBtnBack
					.getWidth())) / 2, startY + inverY, paint);
			canvas.drawText(texts[2], (screenWidth - strwidth) / 2, startY
					+ inverY + menuBtnBack.getHeight()
					- (menuBtnBack.getHeight() - strheight) / 2 - 4, paint);

			canvas.restore();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	private void logic() {
		if (gameCount % 1 == 0) {
			menuPlaneSprite.next();
		}
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
		isRunning = false;
		release();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (x > menuBtnStartX && y > menuBtnStartY
				&& x < menuBtnStartX + menuBtnBack.getWidth()
				&& y < menuBtnStartY + menuBtnBack.getHeight()) {
			Toast.makeText(context, texts[0], 1);// ��ʼ��Ϸ
		} else if (x > menuBtnStartX
				&& y > menuBtnStartY + inverY + menuBtnBack.getHeight()
				&& x < menuBtnStartX + menuBtnBack.getWidth()
				&& y < menuBtnStartY + inverY + menuBtnBack.getHeight() * 2) {
			Toast.makeText(context, texts[0], 1);// ��ʼ��Ϸ
		} else if (x > menuBtnStartX
				&& y > menuBtnStartY + (inverY + menuBtnBack.getHeight()) * 2
				&& x < menuBtnStartX + menuBtnBack.getWidth()
				&& y < menuBtnStartY + menuBtnBack.getHeight()
						+ (inverY + menuBtnBack.getHeight()) * 2) {
			Toast.makeText(context, texts[0], 1);// ��ʼ��Ϸ
		}

		return super.onTouchEvent(event);
	}

	@Override
	public void run() {
		while (isRunning) {
			System.out.println("========run=========");
			gameCount++;
			long startTime = System.currentTimeMillis();
			draw();

			if (!isPause) {
				logic();
			}
			long endTime = System.currentTimeMillis();
			if (endTime - startTime < gameSpeed) {
				try {
					Thread.sleep(gameSpeed - endTime + startTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �ͷ���Դ
	 */
	public void release() {
		if (!backGround.isRecycled()) {
			backGround.recycle();
			backGround = null;
		}
		if (!menuBtnBack.isRecycled()) {
			menuBtnBack.recycle();
			menuBtnBack = null;
		}
		if (!menuImgPlane.isRecycled()) {
			menuImgPlane.recycle();
			menuImgPlane = null;
		}
		if (!menuSelectBack.isRecycled()) {
			menuSelectBack.recycle();
			menuSelectBack = null;
		}
		if (!titleImg.isRecycled()) {
			titleImg.recycle();
			titleImg = null;
		}
		if (menuPlaneSprite != null) {
			menuPlaneSprite.release();
			menuPlaneSprite = null;
		}
	}

}
