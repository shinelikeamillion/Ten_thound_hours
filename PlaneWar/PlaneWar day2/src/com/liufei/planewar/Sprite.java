package com.liufei.planewar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 游戏精灵类
 * 动画基类
 */

public class Sprite {
	private float x;
	private float y;
	private RectF drawRect  = new RectF();
	private Bitmap bitmap;
	private int frameCount =1;   //帧数
	private Bitmap[] frames;
	private int width;           //每帧宽度
	private int height;          //每帧高度
	
	private int frameIndex = 0;  //当前帧
	
	public Sprite() {
	}
	/**
	 * 传入原图：然后根据具体的图像判断是上下切还是左右切
	 */
	public Sprite(Bitmap bitmap, int width, int height) {
		System.out.println("===========coming=============");
		this.width = width;
		this.height = height;
		int bwidth = bitmap.getWidth();
		int bheight = bitmap.getHeight();
		
		int xFrame = bwidth / width;
		int yFrame = bheight / height;
		
		//判断是横还是纵着截取图片
		if (xFrame > 1) {
			frameCount = xFrame;
			xFrame = 2;
		}
		else if (yFrame > 1) {
			frameCount = yFrame;
			yFrame = 2;
		}
		//截取图片
		frames = new Bitmap[frameCount];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = Bitmap.createBitmap(
					bitmap, i*(xFrame - 1)*width,
					i*(yFrame - 1)*height, width, height);
		}
	}
	/**
	 * 切换帧
	 */
	public void next() {
		frameIndex ++;
		if (frameIndex >= frameCount) {
			frameIndex = 0;
		}
	}
	//绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(frames[frameIndex], null, drawRect, paint);
	}
	//判断是否碰撞
//	public boolean isCollWith (Sprite sprite) {
//		return RectF.intersects(drawRect, sprite.getD)
//	}
	public void setXY (float x, float y) {
		this.x = x;
		this.y = y;
		this.drawRect.set(x, y, x+width, y+height);
	}
	public RectF getDrawRect () {
		return drawRect;
	}
	
	/**
	 * 释放资源
	 */
	public void release () {
		if (frames != null) {
			for (Bitmap bitmap : frames) {
				if (!bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
			}
			frames = null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
