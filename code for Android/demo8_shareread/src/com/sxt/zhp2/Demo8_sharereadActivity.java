package com.sxt.zhp2;

import com.sxt.zhp2.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle; 
import android.widget.TextView;
/**
 * ��ȡ����Ӧ��������
 *  
 * ��������Ӧ�ó����SharedPreferences����������������
 * 1.��������Ҫ��SharedPreferences�ķ���ģʽ����Ϊȫ�ֶ���ȫ��д
 * 2.��������Ҫ֪�������ߵİ����ƺ�SharedPreferences�����ƣ���ͨ��Context���SharedPreferences����
 * 3.��������Ҫȷ��֪��ÿ�����ݵ����ƺ��������ͣ�������ȷ��ȡ����
 */
public class Demo8_sharereadActivity extends Activity { 
    private final String FILE_NAME = "myshare2";		//�洢������
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
     * ��ȡ����Ӧ���е�����
     * 
     */
    private void readOther(){
    	Context c = null;
		try {			
			//���ݰ�����ȡ����Ӧ�õ�Context����
			//Context.CONTEXT_IGNORE_SECURIT��ʾ�������п��ܲ����İ�ȫ����
			c = this.createPackageContext(PREFERENCE_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
			
			SharedPreferences share = c.getSharedPreferences(FILE_NAME, Context.MODE_WORLD_READABLE);
			String uname = share.getString("uname", "û�ҵ�");
			String upass = share.getString("upass", "û����");
			boolean is = share.getBoolean("ischeck", false);
			
			tv.setText(uname+upass+is);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    }
}