package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class EnemySmallPlane extends Sprite {
	private int bulletCount = 5;// ��ʼ�ӵ�����
	private int bulletSpeed = 10;
	private int planeSpeed = 8;
	private Bullet[] bullets;
	private Bitmap bulletImg; // �л��ӵ�ͼƬ
	private long gameCount;
	private MyPlane myPlane; //�л��б������ս�������� �Ա��� �е�����ײ
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
		this.hp = 100; // ��ʼѪֵ
		bullets = new Bullet[bulletCount];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(bulletImg, bulletImg.getWidth(), bulletImg
					.getHeight());
		}
	}

	public void reset() {
		this.hp = 100; // ��ʼѪֵ
		isVisible = true;
		frameIndex = 0;
	}

	/**
	 * һ�δ��һ���ӵ�
	 */
	public void fire() {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible == false) {
				// ��ʼ���ӵ�λ�úͷ��� �ٶ�
				bullets[i].initBullet(x + (getWidth() - bullets[i].getWidth())
						/ 2 + 1, y + getHeight() / 2, bulletSpeed,
						Bullet.DIRECTION_DOWN);
				break;// һ�δ��һ���ӵ�
			}
		}
	}

	public void logic() {
		gameCount++;
		// ���ӵ�
//		if (gameCount % 20 == 0) {// ���Ʒ��ӵ�Ƶ��
			fire();
//		}

		// �ӵ�����
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible()) {
				bullets[i].move();
			}
		}
		// �л��ƶ�
		if (isVisible) {
			if (y > PublicData.screenHeight) {
				isVisible = false;
			}
			super.move(0, planeSpeed);
			
			//����е�
			isShot();
			
			//�����ײ
			isCollision();

			if (hp <= 0) {// ���ûѪֵ�� ��ʼ��ը
				explose();
			}
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		if (isVisible == false) {
			return;
		}
		// ���Ƶл�
		super.draw(canvas, paint);

		// �����ӵ�
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible()) {
				bullets[i].draw(canvas, paint);
			}
		}
	}
	/*
	 * ����е�
	 */
	private void isShot () {
		Bullet[] bullets = myPlane.getBullets();
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible) {
				if (bullets[i].isCollWith(this)) { //��ʾ�Լ��е�
					hp -= myPlane.getHarm(); //ʧѪ
					
					bullets[i].setVisible(false); //���ӵ���ʧ
					
					setScore(score += 100);
				}
			}
		}
	}
	/*
	 * ����Ƿ�ײ��
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
				setVisible(false);// ��ը������over
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
