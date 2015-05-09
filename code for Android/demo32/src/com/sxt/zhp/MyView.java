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
	private boolean isPausing = false;// ��ͣ����
	private Paint paint;
	private Canvas canvas = null;
	private SurfaceHolder holder;
	private Context context;

	private ImageTools tool;
	private int count = 10;// ����
	private Bitmap[] bitmaps;// ��Ÿ�֡ͼƬ
	private Bitmap scBitmap;// ԭͼ

	private int gameSpeed = 100;// ��Ϸ�ٶ�
	private long startTime;

	private int frameIndex;// �ڼ�֡

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
	 * ��ʼ��ͼ����Դ
	 */
	private void initImgs() {
		tool = new ImageTools(context);
		bitmaps = new Bitmap[count];

		// ��ȡͼƬ
		scBitmap = tool.getBitmap("img/renzhe.png");// ԭͼ
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
		new Thread(this).start();// �����߳�
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		isRunning = false;

	}

	/**
	 * ����
	 */
	private void draw() {

		canvas.drawRGB(0, 0, 0);// ����
		// ----------��һ�ַ�ʽ��ʹ�÷ָ���ͼƬ��֡����------------
		 canvas.drawBitmap(bitmaps[frameIndex], 50, 0, paint);

		// ----------�ڶ��ַ�ʽ��ԭͼ�и����
		int imgWidth = scBitmap.getWidth() / 10;// ��֡ͼ��
		int imgHeight = scBitmap.getHeight();

		int x = 50;
		int y = 100;// ��Ļ���Ƶ�λ������
		canvas.save();

		canvas.clipRect(x, y, x + imgWidth, y + imgHeight);// ָ����Ļ��������
		canvas.drawBitmap(scBitmap, x - frameIndex * imgWidth, y, paint);// ����ԭͼ����Ļָ��λ��

		canvas.restore();

		// ----------�����ַ�ʽ����ȡԭͼ��ָ������ͼ����Ƶ�ָ������Ļλ��
		// srcRectָ����ԭͼ�н�ȡ������
		Rect srcRect = new Rect(frameIndex * imgWidth, 0, frameIndex * imgWidth
				+ imgWidth, imgHeight);
		// dstRect����Ļ�ϻ��Ƶ�����
		Rect dstRect = new Rect(10, 210, 10 + imgWidth, 210 + imgHeight);

		canvas.drawBitmap(scBitmap, srcRect, dstRect, paint);
	}

	/**
	 * �߼�����
	 */
	private void logic() {
		frameIndex++;
		if (frameIndex > bitmaps.length - 1) {
			frameIndex = 0;
		}
	}

	/**
	 * �߳�
	 */
	@Override
	public void run() {
		while (isRunning) {// ѭ������ ������ּ����ļ� �� new ʵ������
			startTime = System.currentTimeMillis();// ��¼��ʼʱ��
			try {
				canvas = this.holder.lockCanvas();// ��������

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
					holder.unlockCanvasAndPost(canvas);// ����������ͼ�����ύ�ı䡣
				}
			}
		}
	}

	//�����Ļ ��ͣ�ͼ����л�
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		System.out.println(">>>====" + arg1);
		if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
			isPausing = !isPausing;//
		}
		return false;
	}
}
