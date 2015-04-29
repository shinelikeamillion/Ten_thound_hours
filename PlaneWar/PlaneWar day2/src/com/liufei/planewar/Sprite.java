package com.liufei.planewar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * ��Ϸ������
 * ��������
 */

public class Sprite {
	private float x;
	private float y;
	private RectF drawRect  = new RectF();
	private Bitmap bitmap;
	private int frameCount =1;   //֡��
	private Bitmap[] frames;
	private int width;           //ÿ֡���
	private int height;          //ÿ֡�߶�
	
	private int frameIndex = 0;  //��ǰ֡
	
	public Sprite() {
	}
	/**
	 * ����ԭͼ��Ȼ����ݾ����ͼ���ж��������л���������
	 */
	public Sprite(Bitmap bitmap, int width, int height) {
		System.out.println("===========coming=============");
		this.width = width;
		this.height = height;
		int bwidth = bitmap.getWidth();
		int bheight = bitmap.getHeight();
		
		int xFrame = bwidth / width;
		int yFrame = bheight / height;
		
		//�ж��Ǻỹ�����Ž�ȡͼƬ
		if (xFrame > 1) {
			frameCount = xFrame;
			xFrame = 2;
		}
		else if (yFrame > 1) {
			frameCount = yFrame;
			yFrame = 2;
		}
		//��ȡͼƬ
		frames = new Bitmap[frameCount];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = Bitmap.createBitmap(
					bitmap, i*(xFrame - 1)*width,
					i*(yFrame - 1)*height, width, height);
		}
	}
	/**
	 * �л�֡
	 */
	public void next() {
		frameIndex ++;
		if (frameIndex >= frameCount) {
			frameIndex = 0;
		}
	}
	//����
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(frames[frameIndex], null, drawRect, paint);
	}
	//�ж��Ƿ���ײ
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
	 * �ͷ���Դ
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
