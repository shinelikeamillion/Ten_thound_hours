package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;
/**
 * 步骤：
 * 1.建AppWidgetProvider
 * 2.建widget_layout.xml
 * 3.建xml/mywidget.xml
 * 4.配置AndroidManifest.xml
 */
public class Demo28Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}