package com.liufei.planewar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 游戏精灵类 动画基类
 */

public class Sprite {
	protected float x;
	protected float y;
	protected int score; // 分数
	protected int hp; // 血值
	protected int harm; // 伤害
	protected RectF drawRect = new RectF();
	protected Bitmap bitmap;
	protected int frameCount = 1; // 帧数
	protected Bitmap[] frames;
	protected int width; // 每帧宽度
	protected int height; // 每帧高度

	protected int frameIndex = 0; // 当前帧
	protected boolean isVisible;

	public Sprite() {
	}

	/**
	 * 传入原图：然后根据具体的图像判断是上下切还是左右切
	 */
	public Sprite(Bitmap bitmap, int width, int height) {
		initSprite(bitmap, width, height);
	}
	protected void initSprite(Bitmap bitmap, int width, int height) {
		this.width = width;
		this.height = height;
		int bwidth = bitmap.getWidth();
		int bheight = bitmap.getHeight();

		int xFrame = bwidth / width;
		int yFrame = bheight / height;

		// 判断是横还是纵着截取图片
		if (xFrame > 1) {
			frameCount = xFrame;
			xFrame = 2;
		} else if (yFrame > 1) {
			frameCount = yFrame;
			yFrame = 2;
		}
		// 截取图片
		frames = new Bitmap[frameCount];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = Bitmap.createBitmap(bitmap, i * (xFrame - 1) * width, i
					* (yFrame - 1) * height, width, height);
		}
	}

	/**
	 * 切换帧
	 */
	public void next() {
		frameIndex++;
		if (frameIndex >= frameCount) {
			frameIndex = 0;
		}
	}

	// 绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(frames[frameIndex], null, drawRect, paint);
	}

	// 判断是否碰撞
	public boolean isCollWith(Sprite sprite) {
		return RectF.intersects(drawRect, sprite.getDrawRect());
	}

	// 坐标点是否在区域内
	public boolean isCollWith(float x, float y) {
		if (x >= getX() && y >= getY() && x <= getX() + getWidth()
				&& y <= getY() + getHeight()) {
			return true;
		}
		return false;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
		this.drawRect.set(x, y, x + width, y + height);
	}

	public void move(float x0, float y0) {
		this.x += x0;
		this.y += y0;
		this.drawRect.set(x, y, x + width, y + height);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public RectF getDrawRect() {
		return drawRect;
	}

	public void setDrawRect(RectF drawRect) {
		this.drawRect = drawRect;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * 释放资源
	 */
	public void release() {

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

	public int getHarm() {
		return harm;
	}

	public void setHarm(int harm) {
		this.harm = harm;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
