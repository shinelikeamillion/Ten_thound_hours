package com.sxt.zhp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * 
 * <uses-permission
 * android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>
 * 
 */
public class Demo23_shortcutActivity extends Activity {
	private CheckBox repCheckBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setContentView(R.layout.shortcut);
		repCheckBox = (CheckBox) findViewById(R.id.checkBox1);
		// 添加快捷方式
		findViewById(R.id.add_shortcut).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						boolean isRep = repCheckBox.isChecked();
						addShortcut(isRep);
						Toast.makeText(Demo23_shortcutActivity.this, hasShortcut()+"", Toast.LENGTH_LONG).show();
					}
				});
		// 删除快捷方式
		findViewById(R.id.del_shortcut).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						delShortcut();
						
						
					}
				});
	}

	/**
	 * 是否允许重复添加
	 * 
	 * @param is
	 */
	private void addShortcut(boolean is) {
		Intent intent = new Intent();

		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");// 设置添加桌面快捷方式的action

		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,getString(R.string.app_name));// 快捷方式的名称

		intent.putExtra("duplicate", is); // 不允许重复创建

		//序列化接口
		Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
				R.drawable.c);
		//
		
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标
		//
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(this,
				Demo23_shortcutActivity.class));// 点击快捷方式启动的Activity

		this.sendBroadcast(intent);// 发送广播
		Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();

	}
	/**
	 * 判断是否存在快捷方式
	 */
	public boolean hasShortcut() {
		String url = "";
		int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
		/* 大于8的时候在com.android.launcher2.settings 里查询 */
		if (systemversion < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
				new String[] { getString(R.string.app_name) }, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	/**
	 * 删除程序的快捷方式
	 */
	private void delShortcut() {
//		Intent intent = new Intent(
//				"com.android.launcher.action.UNINSTALL_SHORTCUT");
		Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));   
        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer   
        //注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
        String appClass = this.getPackageName() + "." +this.getLocalClassName();   
        ComponentName comp = new ComponentName(this.getPackageName(), appClass);   
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));   
        sendBroadcast(shortcut);   
		Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
	}
}