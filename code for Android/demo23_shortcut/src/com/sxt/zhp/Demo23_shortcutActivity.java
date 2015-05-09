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
		// ��ӿ�ݷ�ʽ
		findViewById(R.id.add_shortcut).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						boolean isRep = repCheckBox.isChecked();
						addShortcut(isRep);
						Toast.makeText(Demo23_shortcutActivity.this, hasShortcut()+"", Toast.LENGTH_LONG).show();
					}
				});
		// ɾ����ݷ�ʽ
		findViewById(R.id.del_shortcut).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						delShortcut();
						
						
					}
				});
	}

	/**
	 * �Ƿ������ظ����
	 * 
	 * @param is
	 */
	private void addShortcut(boolean is) {
		Intent intent = new Intent();

		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");// ������������ݷ�ʽ��action

		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,getString(R.string.app_name));// ��ݷ�ʽ������

		intent.putExtra("duplicate", is); // �������ظ�����

		//���л��ӿ�
		Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
				R.drawable.c);
		//
		
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// ��ݷ�ʽ��ͼ��
		//
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(this,
				Demo23_shortcutActivity.class));// �����ݷ�ʽ������Activity

		this.sendBroadcast(intent);// ���͹㲥
		Toast.makeText(this, "��ӳɹ���", Toast.LENGTH_SHORT).show();

	}
	/**
	 * �ж��Ƿ���ڿ�ݷ�ʽ
	 */
	public boolean hasShortcut() {
		String url = "";
		int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
		/* ����8��ʱ����com.android.launcher2.settings ���ѯ */
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
	 * ɾ������Ŀ�ݷ�ʽ
	 */
	private void delShortcut() {
//		Intent intent = new Intent(
//				"com.android.launcher.action.UNINSTALL_SHORTCUT");
		Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        //��ݷ�ʽ������
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));   
        //ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer   
        //ע��: ComponentName�ĵڶ�����������������������������+�������������޷�ɾ����ݷ�ʽ
        String appClass = this.getPackageName() + "." +this.getLocalClassName();   
        ComponentName comp = new ComponentName(this.getPackageName(), appClass);   
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));   
        sendBroadcast(shortcut);   
		Toast.makeText(this, "ɾ���ɹ���", Toast.LENGTH_SHORT).show();
	}
}