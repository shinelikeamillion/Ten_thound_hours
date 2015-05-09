package com.liufei.planewar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.liufei.planewar.util.PublicData;

public class EnemySmallPlane extends Sprite {
	private int bulletCount = 5;// ��ʼ�ӵ�����
	private int bulletSpeed = 15;
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
		if (gameCount % 10 == 0 && this.isVisible) {// ���Ʒ��ӵ�Ƶ��
			fire();
		}

		// �ӵ�����
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible()) {
				bullets[i].move();
			}
		}
		//�������Ƿ��е�
		isShotMyPhone();
		//����ӵ��Ƿ���ײ
		isBulletCollision();
		
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
		// �����ӵ�
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible()) {
				bullets[i].draw(canvas, paint);
			}
		}
		if (isVisible == false) {
			return;
		}
		// ���Ƶл�
		super.draw(canvas, paint);

	}
	/*
	 * ����е�
	 */
	private void isShot () {
		Bullet[] bullets = myPlane.getBullets();
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible && this.isVisible) {
				if (bullets[i].isCollWith(this)) { //��ʾ�Լ��е�
					if (hp > 0) {
						PublicData.myScore += myPlane.getHarm();
					}
					hp -= myPlane.getHarm(); //ʧѪ
					
					bullets[i].setVisible(false); //���ӵ���ʧ
				}
			}
		}
	}
	/*
	 * �������Ƿ��е�
	 */
	private void isShotMyPhone () {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isVisible) {
				if (myPlane.isVisible && myPlane.isCollWith(bullets[i])) {
					if (myPlane.hp > bullets[i].getHarm()) {
						myPlane.hp -= bullets[i].getHarm();
					} else {
						myPlane.hp = 0;
					}
					bullets[i].setVisible(false);
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
//				myPlane.hp -= this.hp;
				PublicData.myScore += this.hp;
				myPlane.hp = 0;
				hp = 0;
			}
		}
	}
	/*
	 * ����ӵ�����ײ
	 */
	private void isBulletCollision () {
		Bullet[] myBullets = myPlane.getBullets();
		for (int i = 0; i < myBullets.length; i++) {
			for (int j = 0; j < bullets.length; j++) {
				if (myBullets[i].isVisible && bullets[j].isVisible) {
					if (bullets[j].isCollWith(myBullets[i])) {
						bullets[j].setVisible(false);
						myBullets[i].setVisible(false);
					}
				}
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
