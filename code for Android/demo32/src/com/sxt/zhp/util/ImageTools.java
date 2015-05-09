package com.sxt.zhp.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageTools {
	
	private Context context;
	public ImageTools(Context context){
		this.context = context;
	}
	
	public Bitmap getBitmap(String path){
		AssetManager am = context.getAssets();
		try {
			InputStream ins = am.open(path);
			return BitmapFactory.decodeStream(ins);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
