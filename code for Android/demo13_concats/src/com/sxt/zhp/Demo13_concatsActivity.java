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
    		//������Ϣ��ȡ
    		int id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));//λ�ã��к�
    		String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		System.out.println(id+"====="+name);
    		
    		//��ȡ�绰����  		
    		Cursor phoneCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id, null, null);
    		while(phoneCur.moveToNext()){
    			String phone = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    			System.out.print("  |  "+phone);
    		}
    		phoneCur.close();
    		System.out.println();
    		
    		//��ȡEmail
    		Cursor emails = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?" , new String[]{id+""}, null);
        	while(emails.moveToNext()){
        		//��ȡ�绰����
        		String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        		System.out.println("Email:"+email);
        	}
        	emails.close();
    		
    		//��ȡͷ��
        	//�õ���ϵ��ͷ��ID
    		Long photoid = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
    		//�õ���ϵ��ͷ��Bitamp
    		Bitmap contactPhoto = null;
     
    		//photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�
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