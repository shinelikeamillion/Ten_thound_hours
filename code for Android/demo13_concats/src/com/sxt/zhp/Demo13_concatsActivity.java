package com.sxt.zhp;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

public class Demo13_concatsActivity extends Activity {
   private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listView1);
        init();
    }
    
    private void init(){
    	ContentResolver resolver = getContentResolver();
    	Cursor cur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    	while(cur.moveToNext()){
    		//基本信息提取
    		int id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));//位置，行号
    		String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		System.out.println(id+"====="+name);
    		
    		//读取电话号码  		
    		Cursor phoneCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id, null, null);
    		while(phoneCur.moveToNext()){
    			String phone = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    			System.out.print("  |  "+phone);
    		}
    		phoneCur.close();
    		System.out.println();
    		
    		//读取Email
    		Cursor emails = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?" , new String[]{id+""}, null);
        	while(emails.moveToNext()){
        		//获取电话号码
        		String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        		System.out.println("Email:"+email);
        	}
        	emails.close();
    		
    		//读取头像
        	//得到联系人头像ID
    		Long photoid = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
    		//得到联系人头像Bitamp
    		Bitmap contactPhoto = null;
     
    		//photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
    		if(photoid > 0 ) {
    		    Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,id);
    		    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
    		    contactPhoto = BitmapFactory.decodeStream(input);
    		}else {
    		    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    		}
    	}
    }
}