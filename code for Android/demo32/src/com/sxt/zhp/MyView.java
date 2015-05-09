package com.sxt.zhp;

import com.sxt.zhp.util.ImageTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable, OnTouchListener {
	private boolean isRunning = true;
	private boolean isPausing = false;// 暂停控制
	private Paint paint;
	private Canvas canvas = null;
	private SurfaceHolder holder;
	private Context context;

	private ImageTools tool;
	private int count = 10;// 总数
	private Bitmap[] bitmaps;// 存放各帧图片
	private Bitmap scBitmap;// 原图

	private int gameSpeed = 100;// 游戏速度
	private long startTime;

	private int frameIndex;// 第几帧

	public MyView(Context context) {
		super(context);
		this.context = context;
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.setOnTouchListener(this);
		paint = new Paint();

		initImgs();

	}

	/**
	 * 初始化图像资源
	 */
	private void initImgs() {
		tool = new ImageTools(context);
		bitmaps = new Bitmap[count];

		// 截取图片
		scBitmap = tool.getBitmap("img/renzhe.png");// 原图
		int width = scBitmap.getWidth() / 10;
		int height = scBitmap.getHeight();

		for (int i = 0; i < bitmaps.length; i++) {
			bitmaps[i] = Bitmap.createBitmap(scBitmap, i * width, 0, width,
					height);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		new Thread(this).start();// 启动线程
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		isRunning = false;

	}

	/**
	 * 绘制
	 */
	private void draw() {

		canvas.drawRGB(0, 0, 0);// 清屏
		// ----------第一种方式：使用分割后的图片逐帧绘制------------
		 canvas.drawBitmap(bitmaps[frameIndex], 50, 0, paint);

		// ----------第二种方式：原图切割绘制
		int imgWidth = scBitmap.getWidth() / 10;// 单帧图宽
		int imgHeight = scBitmap.getHeight();

		int x = 50;
		int y = 100;// 屏幕绘制的位置坐标
		canvas.save();

		canvas.clipRect(x, y, x + imgWidth, y + imgHeight);// 指定屏幕可视区域
		canvas.drawBitmap(scBitmap, x - frameIndex * imgWidth, y, paint);// 绘制原图到屏幕指定位置

		canvas.restore();

		// ----------第三种方式：提取原图中指定区域图像绘制到指定的屏幕位置
		// srcRect指的是原图中截取的区域
		Rect srcRect = new Rect(frameIndex * imgWidth, 0, frameIndex * imgWidth
				+ imgWidth, imgHeight);
		// dstRect在屏幕上绘制的区域
		Rect dstRect = new Rect(10, 210, 10 + imgWidth, 210 + imgHeight);

		canvas.drawBitmap(scBitmap, srcRect, dstRect, paint);
	}

	/**
	 * 逻辑功能
	 */
	private void logic() {
		frameIndex++;
		if (frameIndex > bitmaps.length - 1) {
			frameIndex = 0;
		}
	}

	/**
	 * 线程
	 */
	@Override
	public void run() {
		while (isRunning) {// 循环体中 避免出现加载文件 或 new 实例对象
			startTime = System.currentTimeMillis();// 记录开始时间
			try {
				canvas = this.holder.lockCanvas();// 锁定画布

				draw();

				if (!isPausing) {
					logic();
				}

				long useTime = System.currentTimeMillis() - startTime;
				if(gameSpeed > useTime){
					Thread.sleep(gameSpeed - useTime);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);// 结束锁定画图，并提交改变。
				}
			}
		}
	}

	//点击屏幕 暂停和继续切换
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		System.out.println(">>>====" + arg1);
		if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
			isPausing = !isPausing;//
		}
		return false;
	}
}
