package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

public class PlaneWarActivity extends Activity {
	public static final int TO_ENDVIEW = 1;
	public static final int CURRENT_VIEW_MENU = 1;
	public static final int CURRENT_VIEW_MAIN = 2;
	public static final int CURRENT_VIEW_END = 3;

	private int currentView = 1;
	private MainView mainView;
	private MenuView menuView;
	private EndView endView;
	public static PlaneWarActivity instance;

	// �̺߳����߳�ͨ�ŵ�handler
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TO_ENDVIEW:
				toEndView();
				break;
			case CURRENT_VIEW_END:
				finish();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MenuView menuView = new MenuView(this);
		setContentView(menuView);
		instance = this;

		PublicData.screenWidth = getWindowManager().getDefaultDisplay()
				.getWidth();
		PublicData.screenHeight = getWindowManager().getDefaultDisplay()
				.getHeight();
	}

	// ��ʾ��Ϸ����
	public void toMainView() {
		currentView = CURRENT_VIEW_MAIN;
		mainView = new MainView(this); // ��ʼ����Ϸ��������
		setContentView(mainView); // �赽��������
		menuView = null; // ��Ϸ�Ŀ�ʼ��������
	}

	// �л����˵�
	public void toMenuView() {
		currentView = CURRENT_VIEW_MENU;
		menuView = new MenuView(this);
		setContentView(menuView);
		mainView = null;
	}

	// ��Ϸ�������л����÷ֽ���
	public void toEndView() {
		currentView = CURRENT_VIEW_END;
		endView = new EndView(this);
		setContentView(endView);
		mainView = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (currentView) {
			case CURRENT_VIEW_MAIN:
				dialogEnd();
				break;
			case CURRENT_VIEW_MENU:
				dialogExitGame();
				break;
			case CURRENT_VIEW_END:
				toEndView();
				break;

			default:
				break;
			}
		}

		return false;
	}
	
	/*
	 * �˳���Ϸ����ʾ
	 */
	private void dialogExitGame () {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ��Ϣ...");
		builder.setMessage("��ȷ��Ҫ�˳���Ϸ��");
		builder.setPositiveButton("�ǵ�", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		builder.setNegativeButton("ȡ��", null);
		builder.create().show();
	}
	/*
	 * �˳�ս������ʾ
	 */
	private void dialogEnd () {
		
		if (mainView != null) {
			mainView.pause(); //��Ϸ��ͣ
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ��Ϣ...");
		builder.setMessage("��ȷ��Ҫ�˳�ս����");
		builder.setPositiveButton("�ǵ�", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				toEndView();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if (mainView != null) {
					mainView.goContinue();
				}
			}
		});
		builder.create().show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}