package com.sxt.zhp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private MyThread myThread;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		holder = this.getHolder();
		holder.addCallback(this);
		myThread = new MyThread(holder);// 创建一个绘图线程
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		myThread.isRun = true;
		myThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		myThread.isRun = false;
	}

}

// 线程内部类
class MyThread extends Thread {
	private SurfaceHolder holder;
	public boolean isRun;

	public MyThread(SurfaceHolder holder) {
		this.holder = holder;
		isRun = true;
	}

	@Override
	public void run() {
		int count = 0;
		while (isRun) {
			Canvas c = null;
			try {
				synchronized (holder) {
					c = holder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。

					Thread.sleep(1000);// 睡眠时间为1秒
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					holder.unlockCanvasAndPost(c);// 结束锁定画图，并提交改变。

				}
			}
		}
	}
}
