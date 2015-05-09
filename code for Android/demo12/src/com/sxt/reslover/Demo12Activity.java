package com.sxt.reslover;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Demo12Activity extends Activity {
	private String uriStr = "content://com.sxt.provider";
	private Uri uri;

	private EditText edtUname;
	private EditText edtAge;
	private EditText edtEmail;
	private EditText edtId;
	private ListView lv;
	private List<String> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		uri = Uri.parse(uriStr);
		
        edtUname = (EditText) findViewById(R.id.editText_uname);
        edtAge = (EditText) findViewById(R.id.editText_age);
        edtEmail = (EditText) findViewById(R.id.editText_email);
        edtId = (EditText) findViewById(R.id.editText_id);
        lv = (ListView) findViewById(R.id.listView1);
        
        //插入数据
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ContentResolver resolver = getContentResolver();	
				Uri uri = Uri.parse(uriStr+"/people");
				
				
				resolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
					@Override
					public void onChange(boolean selfChange) {
						System.out.println("============"+selfChange);
					}
					
				});
				
				
				
				ContentValues v = new ContentValues();
				v.put("uname", edtUname.getText().toString());
				v.put("age", edtAge.getText().toString());
				v.put("email", edtEmail.getText().toString());
				
				uri = resolver.insert(uri, v);
				
				long id = ContentUris.parseId(uri);//保存记录的ID
				
				Toast.makeText(Demo12Activity.this, "保存成功"+id, Toast.LENGTH_LONG).show();
			}
		});
		
		//查询全部
        findViewById(R.id.button_selAll).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				ContentResolver resolver = getContentResolver();
				
				Uri uri = Uri.parse(uriStr+"/people");
				
				Cursor cur = resolver.query(uri, new String[]{"uid","uname","age","email"}, null, null, null);
				System.out.println(cur+"");
				while(cur.moveToNext()){
					String uid = cur.getString(cur.getColumnIndex("uid"));
					String uname = cur.getString(cur.getColumnIndex("uname"));
					String age = cur.getString(cur.getColumnIndex("age"));
					String email = cur.getString(cur.getColumnIndex("email"));
					list.add(uid+"|"+uname+"|"+age+"|"+email);
				}
				
				((ArrayAdapter<String>)lv.getAdapter()).notifyDataSetChanged();
				System.out.println("======onClick======="+list);
			}
		});
		
	     init();
    }
    
    private void init(){
    	list = new ArrayList<String>();
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
//    	System.out.println("======="+lv);
    	lv.setAdapter(adapter);
    }
}