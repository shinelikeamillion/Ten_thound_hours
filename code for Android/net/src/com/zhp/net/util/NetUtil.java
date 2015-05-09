package com.zhp.net.util;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetUtil 
//extends BroadcastReceiver
{
	// ��������״̬�ı仯
//	public void onReceive(Context context, Intent intent) {
//		ConnectivityManager connectivityManager = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//		if (info != null && info.isConnected()) {
//			// �жϵ�ǰ�����Ƿ��Ѿ�����
//			if (info.getState() == NetworkInfo.State.CONNECTED) {
//				Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
//			}
//		} else {
//			Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
//		}
//	}

	/**
	 * ��������Ƿ����
	 * ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi, net�����ӵĹ���
	 * ��Ҫ���������������״̬
	 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {//

		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// ��ȡ�������ӹ���Ķ���

				NetworkInfo info = connectivity.getActiveNetworkInfo();
//				System.out.println(">>>>>>>>>>>>Net:"+info);
				if (info == null || !info.isAvailable()){
					return false;
				}else{
					return true;
				}
//				if (info != null && info.isConnected()) {
//					// �жϵ�ǰ�����Ƿ��Ѿ�����
//					if (info.getState() == NetworkInfo.State.CONNECTED) {
//						return true;
//					}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static BitmapDrawable getImageFromUrl(String url){
		
		try {
			return getImageFromURL(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// ��һ��URL��ȡͼƬ
	public static BitmapDrawable getImageFromURL(URL url) {
		BitmapDrawable bd = null;
		try {
			// ��������
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			// ��ȡ����
			bd = new BitmapDrawable(hc.getInputStream());
			
			// �ر�����
			hc.disconnect();
		} catch (Exception e) {
		}
		return bd;
	}
	// ��һ��URL��ȡBitmapͼƬ
    public static Bitmap getBitmapFromUrl(String imgurl){
		try {
			URL url = new URL(imgurl);
			// ��������
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			// ��ȡ����
			Bitmap bitmap = BitmapFactory.decodeStream(hc.getInputStream());
			
			// �ر�����
			hc.disconnect();
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
