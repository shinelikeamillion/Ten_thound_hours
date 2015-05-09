package com.sxt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Demo9Activity extends Activity {
	private final String FILENAME = "myfile.txt";
	private int mode = Context.MODE_PRIVATE;
    private TextView tv;
    private EditText edtContent;
    private Button btnSave;
    private Button btnRead;
    private CheckBox isAppend;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (TextView) findViewById(R.id.textView1);
        edtContent = (EditText) findViewById(R.id.editText_content);
        btnSave = (Button) findViewById(R.id.btn_write);
        btnRead = (Button) findViewById(R.id.btn_read);
        isAppend = (CheckBox) findViewById(R.id.checkBox_append);
        
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				write();				
			}
		});
        
        btnRead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				read();
				
			}
		});
    }
    /**
     * 读取内部数据
     */
    private void read(){
    	try {
			FileInputStream ins = openFileInput(FILENAME);
			byte[] b = new byte[ins.available()];
			
			ins.read(b);
			
			String content = new String(b);
			tv.setText(content);
			
			ins.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 写出数据
     */
    private void write(){
    	if(isAppend.isChecked()){
    		mode = Context.MODE_APPEND;
    	}else{
    		mode = Context.MODE_PRIVATE;
    	}
    	String content = edtContent.getText().toString();
    	try {
			FileOutputStream out = openFileOutput(FILENAME, mode);
			out.write(content.getBytes());
			out.flush();
			out.close();
			Toast.makeText(this, "写入成功！", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}