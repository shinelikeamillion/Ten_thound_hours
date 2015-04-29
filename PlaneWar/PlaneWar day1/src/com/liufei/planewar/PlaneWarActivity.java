package com.liufei.planewar;

import com.liufei.planewar.util.PublicData;

import android.app.Activity;
import android.os.Bundle;

public class PlaneWarActivity extends Activity {
	private MainView mainView;
	private MenuView menuView;
	public static PlaneWarActivity instance;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuView menuView = new MenuView(this);
        setContentView(menuView);
        instance = this;
        
        PublicData.screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        PublicData.screenHeight = getWindowManager().getDefaultDisplay().getHeight();
    }
    //显示游戏界面
    public void toMainView() {
    	mainView = new MainView(this); //初始化游戏的主界面
    	setContentView(mainView); //设到内容里面
    	menuView = null; //游戏的开始界面消耗
	}
    //切换到菜单
    public void toMenuView () {
    	menuView = new MenuView(this);
    	setContentView(menuView);
    	mainView = null;
    }
}