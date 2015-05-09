package com.sxt.zhp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * @author zhangchangpeng
 * 
 * SharedPreferences是一种轻量级的数据保存方式
 * 通过SharedPreferences可以将NVP（Name/Value Pair，名称/值对）保存在Android的文件系统中，
 * 而且SharedPreferences完全屏蔽的对文件系统的操作过程
 */
public class Demo8_shareprenActivity extends Activity {
    private final String FILE_NAME = "myshare2";		//存储的名称
    private final int mode = Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE;	//全局读写模式
    
    private EditText edtUname;
    private EditText edtUpass;
    private CheckBox box;
    private Button loginBtn;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        edtUname = (EditText) findViewById(R.id.editText_uanme);
        edtUpass = (EditText) findViewById(R.id.editText_upass);
        box = (CheckBox) findViewById(R.id.checkBox_is);
        loginBtn = (Button) findViewById(R.id.button1);
        
        loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				login();
				
			}
		});
    }
    
    /**
     * 登录逻辑
     */
    private void login(){
    	String uname = edtUname.getText().toString();
    	String upass = edtUpass.getText().toString();
    	if("zhang".equals(uname)&&"123".equals(upass)){
    		Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    		saveData();
    		///
    	}else{
    		Toast.makeText(this, "用户名或密码有误", Toast.LENGTH_LONG).show();
    	}
    }
    /**
     * 保存数据
     */
    private void saveData(){
    	//私有模式
    	SharedPreferences share = getSharedPreferences(FILE_NAME, mode);
      	Editor edit = share.edit();
    	if(box.isChecked()==false){
    		edit.clear();//清空
    		edit.commit();
    		System.out.println("=======isChecked========");
    		return;
    	}
    	String uname = edtUname.getText().toString();
    	String upass = edtUpass.getText().toString();

  
    	edit.putString("uname", uname);
    	edit.putString("upass", upass);
    	edit.putBoolean("ischeck", true);
    	
    	edit.commit();//提交
    }
    
    @Override
    protected void onStart() {   
    	super.onStart();
    	readShareData();
    }
    /**
     * 读取数据
     */
    private void readShareData(){
    	//私有模式
    	SharedPreferences share = getSharedPreferences(FILE_NAME, mode);
    	boolean is = share.getBoolean("ischeck", false);
    	String uname = share.getString("uname", "");
    	String upass = share.getString("upass", "");
    	edtUname.setText(uname);
    	edtUpass.setText(upass);
    	box.setChecked(is);
    }
}