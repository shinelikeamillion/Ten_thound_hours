package com.sxt.zhp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Demo10Activity extends Activity {
//    private MyService myService = new MyService();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //启动服务
        findViewById(R.id.btnStart).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent it = new Intent(Demo10Activity.this,MyService.class);
				startService(it);
			}
		});
        
        //停止服务
        findViewById(R.id.btnStop).setOnClickListener(new OnClickListener(){			       	
			public void onClick(View v) {
				Intent it = new Intent(Demo10Activity.this,MyService.class);
				stopService(it);				
			}
		});
    }
}