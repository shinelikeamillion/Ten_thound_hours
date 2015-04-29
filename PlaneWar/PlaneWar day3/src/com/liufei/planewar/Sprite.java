package com.liufei.planewar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * ��Ϸ������ ��������
 */

public class Sprite {
	protected float x;
	protected float y;
	protected int score; // ����
	protected int hp; // Ѫֵ
	protected int harm; // �˺�
	protected RectF drawRect = new RectF();
	protected Bitmap bitmap;
	protected int frameCount = 1; // ֡��
	protected Bitmap[] frames;
	protected int width; // ÿ֡���
	protected int height; // ÿ֡�߶�

	protected int frameIndex = 0; // ��ǰ֡
	protected boolean isVisible;

	public Sprite() {
	}

	/**
	 * ����ԭͼ��Ȼ����ݾ����ͼ���ж��������л���������
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

		// �ж��Ǻỹ�����Ž�ȡͼƬ
		if (xFrame > 1) {
			frameCount = xFrame;
			xFrame = 2;
		} else if (yFrame > 1) {
			frameCount = yFrame;
			yFrame = 2;
		}
		// ��ȡͼƬ
		frames = new Bitmap[frameCount];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = Bitmap.createBitmap(bitmap, i * (xFrame - 1) * width, i
					* (yFrame - 1) * height, width, height);
		}
	}

	/**
	 * �л�֡
	 */
	public void next() {
		frameIndex++;
		if (frameIndex >= frameCount) {
			frameIndex = 0;
		}
	}

	// ����
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(frames[frameIndex], null, drawRect, paint);
	}

	// �ж��Ƿ���ײ
	public boolean isCollWith(Sprite sprite) {
		return RectF.intersects(drawRect, sprite.getDrawRect());
	}

	// ������Ƿ���������
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
	 * �ͷ���Դ
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
