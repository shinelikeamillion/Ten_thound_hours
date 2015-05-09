package com.sxt.app;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class Demo40_appActivity extends Activity implements OnItemLongClickListener{
	private ListView lv;
	private List<Map<String, Object>> dataList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemLongClickListener(this);
		initListView();
	}

	/**
	 * 适配listview
	 */
	private void initListView() {
		dataList = new ArrayList<Map<String,Object>>();
		
		putData();

		String[] from = {"img","title","size"};
		int[] to = {R.id.appIcon,R.id.appTitle,R.id.appSize};
		MyAdapter adapter = new MyAdapter(this, dataList, R.layout.item, from, to);
		
		lv.setAdapter(adapter);
	}
	/**
	 * 获取应用的数据
	 */
	private void putData(){
		System.out.println("======putData()==============");
		PackageManager pm = getPackageManager();
		List<PackageInfo> list = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : list) {
			if ((packageInfo.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0){
				continue;
			}
			String packagename = packageInfo.packageName;
			String name = packageInfo.applicationInfo
					.loadLabel(getPackageManager())
					+ "";
			String path = packageInfo.applicationInfo.publicSourceDir; // 应用安装路径
			
			String appSize = fileSize(path);
			Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());
			
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("img", icon);
			itemMap.put("title", name);
			itemMap.put("size", appSize);
			itemMap.put("pname", packagename);
			itemMap.put("date", new Date(packageInfo.lastUpdateTime));
			
			dataList.add(itemMap);
			
		}
	}
    /**
     * 卸载应用程序
     */
    private void uninstall(String packageName) {
        Uri uri = Uri.parse("package:"+packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        startActivity(intent);
    }
	/**
	 * 计算应用大小
	 * 
	 * @param path
	 * @return
	 */
	private String fileSize(String path) {
		File file = new File(path);
		float size = file.length();
		if (size > 1024 * 1024) {
			return size / 1024f / 1024 + "M";
		} else if (size > 1024) {
			return size / 1024f + "K";
		} else {
			return size + "B";
		}
	}
	/**
	 * 提示框
	 */
	private void dialog(final String appName,final String pack,final String time){
		final Dialog dialog = new Dialog(this);
		dialog.setTitle(appName+"信息");
		dialog.setContentView(R.layout.appdialog);
		dialog.setCanceledOnTouchOutside(true);//点击di
		
		//获得应用大小
		TextView appText = (TextView) dialog.findViewById(R.id.textView_dialogmsg);
		appText.setText("修改时间："+time);
		
		Button unBtn = (Button) dialog.findViewById(R.id.button_uninstall);
		Button cancelBtn = (Button) dialog.findViewById(R.id.button_cancel);
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		//卸载
		unBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
				uninstall(pack);
			}
		});

		dialog.show();
	}
	/**
	 * 长按应用选项
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2,
			long arg3) {
		//应用名称 包名  时间
		Map<String, Object> item = (Map<String, Object>)view.getTag();
		dialog(item.get("title")+"", item.get("pname")+"", item.get("date")+"");
		return false;
	}
	/**
	 * 自定义适配器
	 *
	 */
	class MyAdapter extends SimpleAdapter {
		private List<? extends Map<String, ?>> data;
		private Context context;
		private String[] from;
		private int[] to;
		private int resource;

		public MyAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.from = from;
			this.to = to;
			this.data = data;
			this.resource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Map<String, ?> item = data.get(position);
			if (convertView == null) {				
				convertView = LayoutInflater.from(context).inflate(resource, null);
			}
			convertView.setTag(item);
			TextView tvTitle = (TextView) convertView.findViewById(to[1]);
			TextView tvSize = (TextView) convertView.findViewById(to[2]);
			
			String title = (String) item.get(from[1]);
			String size = (String) item.get(from[2]);
			tvTitle.setText(title);
			tvSize.setText(size);
			
			Drawable img = (Drawable)item.get(from[0]);
			
			ImageView iv = (ImageView) convertView.findViewById(to[0]);
			
			iv.setImageDrawable(img);
			
		
			return convertView;
		}
	}
	
}