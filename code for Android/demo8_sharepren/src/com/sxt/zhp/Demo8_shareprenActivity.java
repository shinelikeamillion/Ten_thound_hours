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
 * SharedPreferences��һ�������������ݱ��淽ʽ
 * ͨ��SharedPreferences���Խ�NVP��Name/Value Pair������/ֵ�ԣ�������Android���ļ�ϵͳ�У�
 * ����SharedPreferences��ȫ���εĶ��ļ�ϵͳ�Ĳ�������
 */
public class Demo8_shareprenActivity extends Activity {
    private final String FILE_NAME = "myshare2";		//�洢������
    private final int mode = Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE;	//ȫ�ֶ�дģʽ
    
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
     * ��¼�߼�
     */
    private void login(){
    	String uname = edtUname.getText().toString();
    	String upass = edtUpass.getText().toString();
    	if("zhang".equals(uname)&&"123".equals(upass)){
    		Toast.makeText(this, "��¼�ɹ�", Toast.LENGTH_LONG).show();
    		saveData();
    		///
    	}else{
    		Toast.makeText(this, "�û�������������", Toast.LENGTH_LONG).show();
    	}
    }
    /**
     * ��������
     */
    private void saveData(){
    	//˽��ģʽ
    	SharedPreferences share = getSharedPreferences(FILE_NAME, mode);
      	Editor edit = share.edit();
    	if(box.isChecked()==false){
    		edit.clear();//���
    		edit.commit();
    		System.out.println("=======isChecked========");
    		return;
    	}
    	String uname = edtUname.getText().toString();
    	String upass = edtUpass.getText().toString();

  
    	edit.putString("uname", uname);
    	edit.putString("upass", upass);
    	edit.putBoolean("ischeck", true);
    	
    	edit.commit();//�ύ
    }
    
    @Override
    protected void onStart() {   
    	super.onStart();
    	readShareData();
    }
    /**
     * ��ȡ����
     */
    private void readShareData(){
    	//˽��ģʽ
    	SharedPreferences share = getSharedPreferences(FILE_NAME, mode);
    	boolean is = share.getBoolean("ischeck", false);
    	String uname = share.getString("uname", "");
    	String upass = share.getString("upass", "");
    	edtUname.setText(uname);
    	edtUpass.setText(upass);
    	box.setChecked(is);
    }
}