package com.sxt.zhp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sxt.zhp.util.NetUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JsonActivity extends Activity {
	private ProgressDialog dialog;
	private ListView lv;
	private List<Map<String, Object>> data;
	private static final int RESULT_OK = 11;
	private static final int RESULT_FAIL = 12;

	private Handler hanlder = new Handler() {// 消息通信
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case RESULT_OK:// 基本数据加载完毕
				dialog.dismiss();
				JSONArray jsonArray = (JSONArray) msg.obj;
				adapterData(jsonArray);
				break;
			case RESULT_FAIL:
				dialog.dismiss();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query);

		lv = (ListView) findViewById(R.id.listView1);

		dialog = new ProgressDialog(this);
		dialog.setTitle("正在拼命的加载数据...");

		initAdapter();

		loadData();
	}

	/**
	 * 适配数据到界面
	 * 
	 * @param jsonArray
	 */
	private void adapterData(JSONArray jsonArray) {

		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			try {
				JSONObject json = jsonArray.getJSONObject(i);
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("bookName", json.getString("bookName"));
				item.put("author", json.getString("author"));
				item.put("iconUrl", json.getString("iconUrl"));
				item.put("price", json.getString("price"));

				data.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 通知更新
		((SimpleAdapter) lv.getAdapter()).notifyDataSetChanged();
	}

	/**
	 * 初始适配关系
	 */
	private void initAdapter() {
		data = new ArrayList<Map<String, Object>>();
		String[] from = { "bookName", "author", "price","iconUrl" };
		int[] to = { R.id.tv_bookname, R.id.tv_author, R.id.tv_price,R.id.imageView1 };
		SimpleAdapter adapter = new MyAdapter(this, data,
				R.layout.queryitem, from, to);

		lv.setAdapter(adapter);
	}

	/**
	 * 从服务器加载数据
	 */
	private void loadData() {
		dialog.show();
		new Thread() {
			public void run() {
				try {
					String urlStr = "http://192.168.1.99:808/query";
					URL url = new URL(urlStr);
					InputStream is = url.openStream();

					String content = convertStreamToString(is);
					JSONArray jsonArray = new JSONArray(content);
					// for (int i = 0; i < jsonArray.length(); i++) {
					// System.out.println(jsonArray.getJSONObject(i).get("bookName"));
					// }
					Message msg = Message.obtain();
					msg.obj = jsonArray;
					msg.what = RESULT_OK;
					hanlder.sendMessage(msg);

					is.close();
				} catch (Exception e) {
					e.printStackTrace();
					hanlder.sendEmptyMessage(RESULT_FAIL);
				}

			};
		}.start();

	}

	/**
	 * 自定义适配器
	 */
	class MyAdapter extends SimpleAdapter {
		private String[] from;
		private int[] to;
		private Context context;
		private List<? extends Map<String, ?>> data;
		private int resource;

		public MyAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.data = data;
			this.from = from;
			this.to = to;
			this.resource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(resource, null);
			}
			final Map<String, Object> item = (Map<String, Object>) data.get(position);
			TextView tvBookName = (TextView) convertView.findViewById(to[0]);
			TextView tvAuthor = (TextView) convertView.findViewById(to[1]);
			TextView tvPrice = (TextView) convertView.findViewById(to[2]);
			final ImageView imageView = (ImageView) convertView.findViewById(to[3]);
			
			tvBookName.setText(item.get(from[0]).toString());
			tvAuthor.setText(item.get(from[1]).toString());
			tvPrice.setText(item.get(from[2]).toString());
			
			imageView.post(new Runnable() {
				
				@Override
				public void run() {
					
					BitmapDrawable drawable = NetUtil.getImageFromUrl(item.get(from[3]).toString());
					imageView.setImageDrawable(drawable);
				}
			});
			
			return convertView;
		}
	}

	/**
	 * 从流中解析提取出字符串
	 * 
	 * @param is
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
