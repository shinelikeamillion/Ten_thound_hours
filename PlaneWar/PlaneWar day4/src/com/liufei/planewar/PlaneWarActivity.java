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

	// 线程和主线程通信的handler
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

	// 显示游戏界面
	public void toMainView() {
		currentView = CURRENT_VIEW_MAIN;
		mainView = new MainView(this); // 初始化游戏的主界面
		setContentView(mainView); // 设到内容里面
		menuView = null; // 游戏的开始界面消耗
	}

	// 切换到菜单
	public void toMenuView() {
		currentView = CURRENT_VIEW_MENU;
		menuView = new MenuView(this);
		setContentView(menuView);
		mainView = null;
	}

	// 游戏结束，切换到得分界面
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
	 * 退出游戏的提示
	 */
	private void dialogExitGame () {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示信息...");
		builder.setMessage("您确定要退出游戏吗？");
		builder.setPositiveButton("是的", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	/*
	 * 退出战斗的提示
	 */
	private void dialogEnd () {
		
		if (mainView != null) {
			mainView.pause(); //游戏暂停
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示信息...");
		builder.setMessage("您确定要退出战斗吗？");
		builder.setPositiveButton("是的", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				toEndView();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
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