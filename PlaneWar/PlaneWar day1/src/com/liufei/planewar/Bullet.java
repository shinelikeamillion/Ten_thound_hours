package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet extends Sprite {
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	private int direction ; //方向变量
	private int speed;
	
	public Bullet (Bitmap bitmap, int width, int height) {
		super(bitmap, width, height);
	}
	
	public void initBullet (float x, float y, int speed, int direction) {
		super.setXY(x, y);
		isVisible = true;
		this.harm = 20;
		this.direction = direction;
		this.speed = speed;
		System.out.println("===========初始化子弹====================");
	}
	@Override
	public void draw(Canvas canvas, Paint paint) {
		System.out.println("=========huazidna============");
		if (isVisible == false) {
			return;
		}
		super.draw(canvas, paint);
	}
	//重置
	public void reset (float x, float y) {
		initBullet(x, y, speed, direction);
	}
	/**
	 * 子弹的移动
	 */
	public void move () {
		if (isVisible == false) {
			return;
		}
		
		if (direction == DIRECTION_UP) {
			super.move(0, -speed);
		} else {
			super.move(0, speed);
		}
		//子弹飞出屏幕
		if (this.y < -getHeight() || y > PublicData.screenHeight ) {
			this.setVisible(false);//无效
		}
	}
}
