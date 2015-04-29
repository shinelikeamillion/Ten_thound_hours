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
	 * �����ӵ�ͼƬ
	 */
	public void setBulletImg(Bitmap bulletImg) {
		this.bulletImg = bulletImg;
	}

	public void init() {
		this.hp = 1000; // ��ʼѪֵ
		isVisible = true;
		bullets = new Bullet[bulletCount];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(bulletImg, bulletImg.getWidth(), bulletImg
					.getHeight());
		}
	}

	/*
	 * һ�η���һ���ӵ�
	 */
	public void fire() {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible == false) {
				// ��ʼ�ӵ�������ٶ�
				bullets[i].initBullet(x + (getWidth() - bullets[i].getWidth())
						/ 2 + 1, y, bulletSpeed, Bullet.DIRECTION_UP);
				System.out.println("=================");
				break;// һ�δ�һ��
			}
		}
	}

	public void logic() {
		gameCount++;
		// �����ӵ�
		if (gameCount % 6 == 0) {// ���Ʒ��ӵ�Ƶ��
			fire();
		}

		// �ӵ�����
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible) {
				bullets[i].move();
			}
		}

		// myPlane ����
		if (isVisible) {
			super.next();
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {

		if (isVisible == false) {
			return;
		}
		// ���Ʒɻ�
		super.draw(canvas, paint);

		// �����ӵ�
		for (int i = 0; i < bullets.length; i++) {
			System.out.println(bullets[i].isVisible);
			if (bullets[i].isVisible) {
				bullets[i].draw(canvas, paint);
			}
		}
	}
}
