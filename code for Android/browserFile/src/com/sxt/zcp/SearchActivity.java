package com.sxt.zcp;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	private CheckBox jpg;
	private CheckBox png;
	private CheckBox gif;
	private EditText key;
	private Button btnSearch;
	private ListView lv;
	private List<String> fileList;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		jpg = (CheckBox) findViewById(R.id.jpg);
		png = (CheckBox) findViewById(R.id.png);
		gif = (CheckBox) findViewById(R.id.gif);
		key = (EditText) findViewById(R.id.editText1);
		lv = (ListView)findViewById(R.id.list);
		
		btnSearch = (Button) findViewById(R.id.search);
		btnSearch.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				putData();
				
			}
		});
		
		//listView��ӵ���¼�
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView tv = (TextView)arg1;
				String filePath = tv.getText().toString();
				ImageView iv = new ImageView(SearchActivity.this);

				Bitmap bp = BitmapFactory.decodeFile(filePath);
				iv.setImageBitmap(bp);

				Dialog dialog = new Dialog(SearchActivity.this);
				dialog.setTitle("Ԥ��");
				dialog.setContentView(iv);
				
				dialog.show();
			}
		});
		
		initListView();
	}
	/**
	 * �����ϵ
	 */
	private void initListView(){
		fileList = new ArrayList<String>();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileList);
		lv.setAdapter(adapter);
	}
	/**
	 * ������� ����ListView
	 */
	private void putData(){
		//����ǰ �����
		fileList.clear();
		
		findFile(new File("/mnt/"));
		
		((ArrayAdapter<String>)lv.getAdapter()).notifyDataSetChanged();
	}

	/**
	 * �ݹ�����ļ�
	 */
	private void findFile(File file) {
		if (file == null) {
			return;
		}

		if (file.isDirectory()) {// ������

			File[] files = file.listFiles(new MyFileFilter());//ʹ���ļ�������

			if (files != null){
				for (File file2 : files) {
					findFile(file2);//�ݹ�
				}
			}
		} else {
//			System.out.println(file.getAbsolutePath());
			fileList.add(file.getAbsolutePath());
		}
	}
	
	/**
	 * �����ļ������� ָ����������
	 */
	class MyFileFilter implements FileFilter{

		@Override
		public boolean accept(File f) {//��д��������
			//
			if(f.isDirectory()){//Ҫ
				return true;
			}
			String k = key.getText().toString();
			String filename = f.getName();
			//�������Ͷ�û��ѡ��
			if(!jpg.isChecked()&&!png.isChecked()&&!gif.isChecked()){
				if(filename.contains(k)||TextUtils.isEmpty(k)){
					return true;
				}else{
					return false;
				}
			}

			
			if(jpg.isChecked()){				
				if(filename.toUpperCase().endsWith(".JPG")){//.JPG
					if(filename.contains(k)||TextUtils.isEmpty(k)){
						return true;
					}else{
						return false;
					}
				}
			}
			if(gif.isChecked()){
				if(filename.toUpperCase().endsWith(".GIF")){
					if(filename.contains(k)||TextUtils.isEmpty(k)){
						return true;
					}else{
						return false;
					}
				}
			}
			if(png.isChecked()){
				if(filename.toUpperCase().endsWith(".PNG")){
					if(filename.contains(k)||TextUtils.isEmpty(k)){
						return true;
					}else{
						return false;
					}
				}
			}

			return false;
		}
		
	}
}
