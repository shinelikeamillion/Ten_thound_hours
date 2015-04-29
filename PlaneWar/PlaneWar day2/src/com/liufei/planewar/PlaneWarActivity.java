package com.liufei.planewar;

import android.app.Activity;
import android.os.Bundle;

public class PlaneWarActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuView menuView = new MenuView(this);
        setContentView(menuView);
    }
}