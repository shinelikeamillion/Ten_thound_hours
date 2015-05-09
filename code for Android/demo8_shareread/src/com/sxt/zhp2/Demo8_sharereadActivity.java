package com.sxt.zhp2;

import com.sxt.zhp2.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle; 
import android.widget.TextView;
/**
 * 读取其他应用中数据
 *  
 * 访问其他应用程序的SharedPreferences必须满足三个条件
 * 1.共享者需要将SharedPreferences的访问模式设置为全局读或全局写
 * 2.访问者需要知道共享者的包名称和SharedPreferences的名称，以通过Context获得SharedPreferences对象
 * 3.访问者需要确切知道每个数据的名称和数据类型，用以正确读取数据
 */
public class Demo8_sharereadActivity extends Activity { 
    private final String FILE_NAME = "myshare2";		//存储的名称
    private String PREFERENCE_PACKAGE = "com.sxt.zhp";//
    private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView)findViewById(R.id.mess);
        System.out.println("-----------------------");
        readOther();
    }
    
    /**
     * 读取其他应用中的数据
     * 
     */
    private void readOther(){
    	Context c = null;
		try {			
			//根据包名获取其他应用的Context对象
			//Context.CONTEXT_IGNORE_SECURIT表示忽略所有可能产生的安全问题
			c = this.createPackageContext(PREFERENCE_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
			
			SharedPreferences share = c.getSharedPreferences(FILE_NAME, Context.MODE_WORLD_READABLE);
			String uname = share.getString("uname", "没找到");
			String upass = share.getString("upass", "没密码");
			boolean is = share.getBoolean("ischeck", false);
			
			tv.setText(uname+upass+is);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    }
}