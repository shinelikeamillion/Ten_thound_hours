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

	private float screenWidth;// 屏幕宽度
	private float screenHeight;// 屏幕高度
	private float initPlaneX;// 初始的坐标位置
	private float initPlaneY;

	private float scalex; // 缩放比例
	private float scaley;

	private Bitmap backGround; // 背景图
	private Bitmap menuBtnBack; // 未选中菜单背景
	private Bitmap menuSelectBack; // 选中时背景的图片

	float inverY = 30; // 菜单项的间距
	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;
	private Thread thread;
	private Rect tempRect = new Rect();
	private String[] texts = { "重新游戏", "退出游戏" };

	private float menuBtnStartX; // 第一个菜单按钮额位置
	private float menuBtnStartY;
	
	private int selMenu = -1; //选中的菜单项
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
	 * 初始化资源
	 */
	private void init() {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();

		backGround = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg_01);

		scalex = screenWidth / backGround.getWidth(); // 计算缩放比例
		scaley = screenHeight / backGround.getHeight();

		menuBtnBack = getImgByRid(R.drawable.button);
		menuSelectBack = getImgByRid(R.drawable.button2);

	}

	/*
	 * 获取图片
	 */
	private Bitmap getImgByRid(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}

	/*
	 * 绘制
	 */
	private void draw() {
		try {
			float startY = initPlaneY;

			canvas = holder.lockCanvas();
			canvas.save();
			// 计算背景图片与屏幕的比例
			canvas.scale(scalex, scaley);
			// 绘制背景
			canvas.drawBitmap(backGround, 0, 0, paint);
			canvas.restore(); // 还原
			
			//绘制显示分数的文字
			paint.setTextSize(20);
			paint.setColor(Color.GRAY);
			paint.setShadowLayer(0, 0, 10, 10);
			String score = "SCORE:" + PublicData.myScore;
			float scoreWidth = paint.measureText(score);
			canvas.drawText(score, (PublicData.screenWidth - scoreWidth)/2, PublicData.screenHeight/3, paint);
			startY =  PublicData.screenHeight/3 + inverY;
				
			// 绘制菜单项
			paint.setTextSize(30);
			paint.setColor(Color.WHITE);

			// 返回包围整个字符串的最小的一个Rect区域
			paint.getTextBounds(texts[0], 0, texts[0].length(), tempRect);
			float strheight = tempRect.height();
			float strwidth = tempRect.width();

			// 文字的起始位置为文字的底部(不像图片起止点为左上角)所有做如下处理,文字与背景不太垂直剧中，减去4做以调整
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
			// 将位置保存下来用以处理点击事件
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
	//三个按钮的点击事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (x > menuBtnStartX && y > menuBtnStartY
				&& x < menuBtnStartX + menuBtnBack.getWidth()
				&& y < menuBtnStartY + menuBtnBack.getHeight()) {
//			Toast.makeText(context, texts[0], 1).show();// 开始游戏
			selMenu = 0;
			//页面切换
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
	 * 释放资源
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
