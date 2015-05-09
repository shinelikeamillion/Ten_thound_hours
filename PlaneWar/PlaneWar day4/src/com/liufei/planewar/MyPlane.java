package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.nfc.Tag;

public class MyPlane extends Sprite {
	private int bulletCount = 15;
	private int bulletSpeed = 20;
	private Bullet[] bullets;
	private Bitmap bulletImg;
	private long gameCount;
	private Bitmap exploreImg;
	private Resources resources;

	public MyPlane(Resources resources, Bitmap bitmap, int width, int height) {
		super(bitmap, width, height);
		this.resources = resources;
		exploreImg = BitmapFactory.decodeResource(resources,
				R.drawable.myplaneexplosion);
		System.out.println(exploreImg);
	}

	/*
	 * 设置子弹图片
	 */
	public void setBulletImg(Bitmap bulletImg) {
		this.bulletImg = bulletImg;
	}

	public void init() {
		this.hp = PublicData.myHp; // 初始血值
		this.harm = 50;
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
				break;// 一次打一颗
			}
		}
	}

	public void logic() {
		gameCount++;
		// 发射子弹
		if (gameCount % 2 == 0) {// 控制发子弹频率
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

		if (hp <= 0) {
			super.initSprite(exploreImg, exploreImg.getWidth() / 2, exploreImg
					.getHeight());
			if (gameCount % 2 == 0) {
				if (frameIndex < frameCount - 1) {
					super.next();
				} else {
					isVisible = false;
					PlaneWarActivity.instance.handler
							.sendEmptyMessage(PlaneWarActivity.TO_ENDVIEW);
				}
			}
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
			if (bullets[i].isVisible) {
				bullets[i].draw(canvas, paint);
			}
		}
	}

	/*
	 * 获取战机所有的子弹
	 */
	public Bullet[] getBullets() {
		return bullets;
	}
}
