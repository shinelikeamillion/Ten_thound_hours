package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;
/**
 * ���裺
 * 1.��AppWidgetProvider
 * 2.��widget_layout.xml
 * 3.��xml/mywidget.xml
 * 4.����AndroidManifest.xml
 */
public class Demo28Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}