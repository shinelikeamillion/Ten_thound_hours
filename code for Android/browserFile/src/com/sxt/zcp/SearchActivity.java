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
		
		//listView添加点击事件
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
				dialog.setTitle("预览");
				dialog.setContentView(iv);
				
				dialog.show();
			}
		});
		
		initListView();
	}
	/**
	 * 适配关系
	 */
	private void initListView(){
		fileList = new ArrayList<String>();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileList);
		lv.setAdapter(adapter);
	}
	/**
	 * 填充数据 更新ListView
	 */
	private void putData(){
		//遍历前 先清空
		fileList.clear();
		
		findFile(new File("/mnt/"));
		
		((ArrayAdapter<String>)lv.getAdapter()).notifyDataSetChanged();
	}

	/**
	 * 递归遍历文件
	 */
	private void findFile(File file) {
		if (file == null) {
			return;
		}

		if (file.isDirectory()) {// 继续找

			File[] files = file.listFiles(new MyFileFilter());//使用文件过滤器

			if (files != null){
				for (File file2 : files) {
					findFile(file2);//递归
				}
			}
		} else {
//			System.out.println(file.getAbsolutePath());
			fileList.add(file.getAbsolutePath());
		}
	}
	
	/**
	 * 定义文件过滤器 指定搜索规则
	 */
	class MyFileFilter implements FileFilter{

		@Override
		public boolean accept(File f) {//编写搜索规则
			//
			if(f.isDirectory()){//要
				return true;
			}
			String k = key.getText().toString();
			String filename = f.getName();
			//三种类型都没有选择
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
