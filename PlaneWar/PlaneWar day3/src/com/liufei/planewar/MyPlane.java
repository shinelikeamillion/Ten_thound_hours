package com.liufei.planewar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlane extends Sprite {
	private int bulletCount = 15;
	private int bulletSpeed = 20;
	private Bullet[] bullets;
	private Bitmap bulletImg;
	private long gameCount;
	private Bitmap exploreImg;
	private Resources resources;
	
	public MyPlane (Resources resources,Bitmap bitmap, int width, int height) {
		super(bitmap, width, height);
		this.resources = resources;
		exploreImg = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
		System.out.println(exploreImg);
	}

	/*
	 * �����ӵ�ͼƬ
	 */
	public void setBulletImg(Bitmap bulletImg) {
		this.bulletImg = bulletImg;
	}

	public void init() {
		this.hp = 1000; // ��ʼѪֵ
		this.harm = 50;
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
				break;// һ�δ�һ��
			}
		}
	}

	public void logic() {
		gameCount++;
		// �����ӵ�
		if (gameCount % 2 == 0) {// ���Ʒ��ӵ�Ƶ��
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
		
		if (hp <= 0) {
			super.initSprite(exploreImg, exploreImg.getWidth()/2, exploreImg.getHeight());
			if (gameCount % 2 == 0) {
				if (frameIndex < frameCount-1) {
					super.next();
				} else {
					isVisible = false;
				}
			} 
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
			if (bullets[i].isVisible) {
				bullets[i].draw(canvas, paint);
			}
		}
	}
	/*
	 * ��ȡս�����е��ӵ�
	 */
	public Bullet[] getBullets () {
		return bullets;
	}
}
