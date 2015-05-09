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
		// ���ذ�ť�¼�
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��ȡ��ǰroot�ĸ��ļ���
				File parent = root.getParentFile();
				if (parent == null) {
					Toast.makeText(BrowserFileActivity.this, "�Ѿ��Ǹ�Ŀ¼��",
							Toast.LENGTH_LONG).show();
					return;
				}
				root = parent;
				// ��������Դ
				updateData();
			}
		});

		// listView������
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView tv = (TextView) arg1.findViewById(R.id.textView1);
				File selFile = new File(root, tv.getText() + "");
				if (selFile.isDirectory()) {// ��Ŀ¼
					File[] childs = selFile.listFiles();
					if (childs == null) {
						Toast.makeText(BrowserFileActivity.this, "��û��Ȩ�޷��ʣ�",
								Toast.LENGTH_LONG).show();
						return;
					}
					root = selFile;//

					updateData();
				}
			}
		});
	}

	// ����
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

			f.put("date", DateFormat.format("yyyy��MM��dd�� hh:mm:ss", file
					.lastModified()));

			data.add(f);
		}
		titlePath.setText(root.getAbsolutePath());

		// ֪ͨ��������������
		((SimpleAdapter) listView.getAdapter()).notifyDataSetChanged();
	}
	
	/**
	 * ��Ӳ˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("�����ļ�").setIcon(android.R.drawable.ic_menu_search);
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