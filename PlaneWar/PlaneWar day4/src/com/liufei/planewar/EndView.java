package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

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

public class EndView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	private boolean isRunning = true;
	private boolean isPause = false;

	private float screenWidth;// ��Ļ���
	private float screenHeight;// ��Ļ�߶�
	private float initPlaneX;// ��ʼ������λ��
	private float initPlaneY;

	private float scalex; // ���ű���
	private float scaley;

	private Bitmap backGround; // ����ͼ
	private Bitmap menuBtnBack; // δѡ�в˵�����
	private Bitmap menuSelectBack; // ѡ��ʱ������ͼƬ

	float inverY = 30; // �˵���ļ��
	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;
	private Thread thread;
	private Rect tempRect = new Rect();
	private String[] texts = { "������Ϸ", "�˳���Ϸ" };

	private float menuBtnStartX; // ��һ���˵���ť��λ��
	private float menuBtnStartY;
	
	private int selMenu = -1; //ѡ�еĲ˵���
	private Context context;

	public EndView(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		thread = new Thread(this);
		this.context = context;
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
			
			//������ʾ����������
			paint.setTextSize(20);
			paint.setColor(Color.GRAY);
			paint.setShadowLayer(0, 0, 10, 10);
			String score = "SCORE:" + PublicData.myScore;
			float scoreWidth = paint.measureText(score);
			canvas.drawText(score, (PublicData.screenWidth - scoreWidth)/2, PublicData.screenHeight/3, paint);
			startY =  PublicData.screenHeight/3 + inverY;
				
			// ���Ʋ˵���
			paint.setTextSize(30);
			paint.setColor(Color.WHITE);

			// ���ذ�Χ�����ַ�������С��һ��Rect����
			paint.getTextBounds(texts[0], 0, texts[0].length(), tempRect);
			float strheight = tempRect.height();
			float strwidth = tempRect.width();

			// ���ֵ���ʼλ��Ϊ���ֵĵײ�(����ͼƬ��ֹ��Ϊ���Ͻ�)���������´���,�����뱳����̫��ֱ���У���ȥ4���Ե���
			if (selMenu == 0) {
				canvas.drawBitmap(menuSelectBack, (screenWidth - (menuBtnBack
						.getWidth())) / 2, startY + inverY, paint);
			} else {
				canvas.drawBitmap(menuBtnBack, (screenWidth - (menuBtnBack
						.getWidth())) / 2, startY + inverY, paint);
			}
			canvas.drawText(texts[0], (screenWidth - strwidth) / 2, startY
					+ inverY + menuBtnBack.getHeight()
					- (menuBtnBack.getHeight() - strheight) / 2 - 4, paint);
			// ��λ�ñ����������Դ������¼�
			menuBtnStartX = (screenWidth - menuBtnBack.getWidth()) / 2;
			menuBtnStartY = startY + inverY;
			startY = startY + inverY + menuBtnBack.getHeight();
			
			if (selMenu == 1) {
				canvas.drawBitmap(menuSelectBack, (screenWidth - (menuBtnBack
						.getWidth())) / 2, startY + inverY, paint);
			} else {
				canvas.drawBitmap(menuBtnBack, (screenWidth - (menuBtnBack
						.getWidth())) / 2, startY + inverY, paint);
			}
			canvas.drawText(texts[1], (screenWidth - strwidth) / 2, startY
					+ inverY + menuBtnBack.getHeight()
					- (menuBtnBack.getHeight() - strheight) / 2 - 4, paint);
			startY = startY + inverY + menuBtnBack.getHeight();

			canvas.restore();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
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
	//������ť�ĵ���¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (x > menuBtnStartX && y > menuBtnStartY
				&& x < menuBtnStartX + menuBtnBack.getWidth()
				&& y < menuBtnStartY + menuBtnBack.getHeight()) {
//			Toast.makeText(context, texts[0], 1).show();// ��ʼ��Ϸ
			selMenu = 0;
			//ҳ���л�
			PlaneWarActivity.instance.toMainView();
		} else if (x > menuBtnStartX
				&& y > menuBtnStartY + inverY + menuBtnBack.getHeight()
				&& x < menuBtnStartX + menuBtnBack.getWidth()
				&& y < menuBtnStartY + inverY + menuBtnBack.getHeight() * 2) {
			PlaneWarActivity.instance.handler.sendEmptyMessage(PlaneWarActivity.CURRENT_VIEW_END);
			selMenu = 1;
		}
//		return super.onTouchEvent(event);
		return false;
	}

	
	/**
	 * �ͷ���Դ
	 */
	public void release() {
		isRunning = false;
		if (!backGround.isRecycled()) {
			backGround.recycle();
			backGround = null;
		}
		if (!menuBtnBack.isRecycled()) {
			menuBtnBack.recycle();
			menuBtnBack = null;
		}
		if (!menuSelectBack.isRecycled()) {
			menuSelectBack.recycle();
			menuSelectBack = null;
		}
	}
	@Override
	public void run() {
		while (isRunning) {
			draw();

		}
	}
}
