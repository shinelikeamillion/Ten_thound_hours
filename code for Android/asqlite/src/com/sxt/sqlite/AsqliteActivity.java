package com.sxt.sqlite;

import com.sxt.sqlite.dao.UserDao;
import com.sxt.sqlite.entity.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class AsqliteActivity extends Activity {
   private EditText edtUname;
   private EditText edtAge;
   private EditText edtEmail;
   private EditText edtId;
   
   private UserDao dao;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        edtUname = (EditText) findViewById(R.id.editText_uname);
        edtAge = (EditText) findViewById(R.id.editText_age);
        edtEmail = (EditText) findViewById(R.id.editText_email);
        edtId = (EditText) findViewById(R.id.editText_id);
        
        dao = new UserDao(this);
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				BaseDB db = new BaseDB(AsqliteActivity.this);
//				db.open();
				System.out.println("======open()===");
				User user = new User();
				user.setAge(Integer.parseInt(edtAge.getText().toString()));
				user.setEmail(edtEmail.getText().toString());
				user.setUid(Integer.parseInt(edtId.getText().toString()));
				user.setUname(edtUname.getText().toString());
				dao.addUser(user);
				
				Toast.makeText(AsqliteActivity.this, "保存成功", Toast.LENGTH_LONG).show();
			}
		});
        
        //单个查询
        findViewById(R.id.button_sel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String uid = edtId.getText().toString();
				User user = dao.getUser(uid);
				
				if(user==null){
					Toast.makeText(getApplicationContext(), "查无此人！", Toast.LENGTH_LONG).show();
					return;
				}
				
				edtUname.setText(user.getUname());
				edtAge.setText(user.getAge()+"");
				edtEmail.setText(user.getEmail());
			}
		});
    }
}