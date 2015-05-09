package com.sxt.zcp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sxt.zcp.util.DateUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BrowserFileActivity extends Activity {
	private File root = new File("/");
	private ListView listView;
	private Button backBtn;
	private TextView titlePath;
	List<Map<String, Object>> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		backBtn = (Button) findViewById(R.id.button1);
		listView = (ListView) findViewById(R.id.listView1);
		titlePath = (TextView) findViewById(R.id.textView1);
		init();
		// 返回按钮事件
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取当前root的父文件夹
				File parent = root.getParentFile();
				if (parent == null) {
					Toast.makeText(BrowserFileActivity.this, "已经是根目录了",
							Toast.LENGTH_LONG).show();
					return;
				}
				root = parent;
				// 更新数据源
				updateData();
			}
		});

		// listView监听器
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView tv = (TextView) arg1.findViewById(R.id.textView1);
				File selFile = new File(root, tv.getText() + "");
				if (selFile.isDirectory()) {// 是目录
					File[] childs = selFile.listFiles();
					if (childs == null) {
						Toast.makeText(BrowserFileActivity.this, "您没有权限访问！",
								Toast.LENGTH_LONG).show();
						return;
					}
					root = selFile;//

					updateData();
				}
			}
		});
	}

	// 适配
	private void init() {
		data = new ArrayList<Map<String, Object>>();

		String[] from = { "icon", "title", "date" };
		int[] to = { R.id.imageView1, R.id.textView1, R.id.textView2 };
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
				from, to);
		listView.setAdapter(adapter);

		updateData();
	}

	private void updateData() {
		if (data != null && data.size() > 0) {
			data.clear();
		}
		File[] files = root.listFiles();
		for (File file : files) {
			Map<String, Object> f = new HashMap<String, Object>();
			if (file.isDirectory()) {
				f.put("icon", R.drawable.folder);
			} else {
				f.put("icon", R.drawable.file);
			}

			f.put("title", file.getName());

			f.put("date", DateFormat.format("yyyy年MM月dd日 hh:mm:ss", file
					.lastModified()));

			data.add(f);
		}
		titlePath.setText(root.getAbsolutePath());

		// 通知适配器更新数据
		((SimpleAdapter) listView.getAdapter()).notifyDataSetChanged();
	}
	
	/**
	 * 添加菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("搜索文件").setIcon(android.R.drawable.ic_menu_search);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent it = new Intent();
		it.setClass(this, SearchActivity.class);
		startActivity(it);
		return super.onMenuItemSelected(featureId, item);
	}
}