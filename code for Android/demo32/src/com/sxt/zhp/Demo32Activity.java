package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;

public class Demo32Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
}