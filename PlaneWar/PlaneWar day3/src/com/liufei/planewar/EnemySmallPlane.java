package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class EnemySmallPlane extends Sprite {
	private int bulletCount = 5;// 初始子弹数量
	private int bulletSpeed = 10;
	private int planeSpeed = 8;
	private Bullet[] bullets;
	private Bitmap bulletImg; // 敌机子弹图片
	private long gameCount;
	private MyPlane myPlane; //敌机中保留玩家战机的引用 以便检测 中弹和碰撞
	private Resources resource;

	public EnemySmallPlane(Resources resources) {
		this.resource = resources;
		bulletImg = BitmapFactory.decodeResource(resource, R.drawable.bullet);
		Bitmap planeImg = BitmapFactory.decodeResource(resource,
				R.drawable.small);

		super.initSprite(planeImg, planeImg.getWidth(),
				planeImg.getHeight() / 3);

		init();
	}

	private void init() {
		this.hp = 100; // 初始血值
		bullets = new Bullet[bulletCount];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(bulletImg, bulletImg.getWidth(), bulletImg
					.getHeight());
		}
	}

	public void reset() {
		this.hp = 100; // 初始血值
		isVisible = true;
		frameIndex = 0;
	}

	/**
	 * 一次打出一颗子弹
	 */
	public void fire() {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible == false) {
				// 初始了子弹位置和方向 速度
				bullets[i].initBullet(x + (getWidth() - bullets[i].getWidth())
						/ 2 + 1, y + getHeight() / 2, bulletSpeed,
						Bullet.DIRECTION_DOWN);
				break;// 一次打出一颗子弹
			}
		}
	}

	public void logic() {
		gameCount++;
		// 发子弹
//		if (gameCount % 20 == 0) {// 控制发子弹频率
			fire();
//		}

		// 子弹动画
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible()) {
				bullets[i].move();
			}
		}
		// 敌机移动
		if (isVisible) {
			if (y > PublicData.screenHeight) {
				isVisible = false;
			}
			super.move(0, planeSpeed);
			
			//检测中弹
			isShot();
			
			//检测碰撞
			isCollision();

			if (hp <= 0) {// 如果没血值了 开始爆炸
				explose();
			}
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		if (isVisible == false) {
			return;
		}
		// 绘制敌机
		super.draw(canvas, paint);

		// 绘制子弹
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible()) {
				bullets[i].draw(canvas, paint);
			}
		}
	}
	/*
	 * 检测中弹
	 */
	private void isShot () {
		Bullet[] bullets = myPlane.getBullets();
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible) {
				if (bullets[i].isCollWith(this)) { //表示自己中弹
					hp -= myPlane.getHarm(); //失血
					
					bullets[i].setVisible(false); //让子弹消失
					
					setScore(score += 100);
				}
			}
		}
	}
	/*
	 * 检测是否撞机
	 */
	private void isCollision () {
		if (isVisible && myPlane.isVisible) {
			if (this.isCollWith(myPlane)) {
				myPlane.hp -= myPlane.hp;
				hp = 0;
			}
		}
	}
	
	
	private void explose() {
		if (gameCount % 1 == 0) {
			if (frameIndex < frameCount - 1) {
				next();
			} else {
				setVisible(false);// 爆炸结束后over
			}
		}
	}

	public MyPlane getMyPlane() {
		return myPlane;
	}

	public void setMyPlane(MyPlane myPlane) {
		this.myPlane = myPlane;
	}
}
