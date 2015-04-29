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
    //��ʾ��Ϸ����
    public void toMainView() {
    	mainView = new MainView(this); //��ʼ����Ϸ��������
    	setContentView(mainView); //�赽��������
    	menuView = null; //��Ϸ�Ŀ�ʼ��������
	}
    //�л����˵�
    public void toMenuView () {
    	menuView = new MenuView(this);
    	setContentView(menuView);
    	mainView = null;
    }
}