package com.liufei.planewar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlane extends Sprite {
	private int bulletCount = 15;
	private int bulletSpeed = 5;
	private Bullet[] bullets;
	private Bitmap bulletImg;
	private long gameCount;

	public MyPlane(Bitmap bitmap, int width, int height) {
		super(bitmap, width, height);
	}

	/*
	 * 设置子弹图片
	 */
	public void setBulletImg(Bitmap bulletImg) {
		this.bulletImg = bulletImg;
	}

	public void init() {
		this.hp = 1000; // 初始血值
		isVisible = true;
		bullets = new Bullet[bulletCount];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(bulletImg, bulletImg.getWidth(), bulletImg
					.getHeight());
		}
	}

	/*
	 * 一次发射一颗子弹
	 */
	public void fire() {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible == false) {
				// 初始子弹方向和速度
				bullets[i].initBullet(x + (getWidth() - bullets[i].getWidth())
						/ 2 + 1, y, bulletSpeed, Bullet.DIRECTION_UP);
				System.out.println("=================");
				break;// 一次打一颗
			}
		}
	}

	public void logic() {
		gameCount++;
		// 发射子弹
		if (gameCount % 6 == 0) {// 控制发子弹频率
			fire();
		}

		// 子弹动画
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible) {
				bullets[i].move();
			}
		}

		// myPlane 动画
		if (isVisible) {
			super.next();
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {

		if (isVisible == false) {
			return;
		}
		// 绘制飞机
		super.draw(canvas, paint);

		// 绘制子弹
		for (int i = 0; i < bullets.length; i++) {
			System.out.println(bullets[i].isVisible);
			if (bullets[i].isVisible) {
				bullets[i].draw(canvas, paint);
			}
		}
	}
}
