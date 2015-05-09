package com.sxt.zhp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.crypto.spec.IvParameterSpec;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 给图片打水印
 *
 */
public class MainActivity extends Activity {
	private ImageView iv ;
	private EditText inputText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		inputText = (EditText) findViewById(R.id.editText1);
		
		//打水印
		findViewById(R.id.btn_water).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {				
				waterImage();
			}
		});
		//导出图片
		findViewById(R.id.btn_export).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {				
				save();
			}
		});
		
		//变化
		findViewById(R.id.btn_change).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				changeImg();				
			}
		});
	}
	/**
	 * 变化
	 */
	private void changeImg(){
		BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
		Bitmap img = drawable.getBitmap();
		img = testMatrix(img,2);
		iv.setImageBitmap(img);
		
//		img.recycle();//回收 不能直接回收  需要绘制空图上 然后再释放原图
	}
	
	/**
	 * 打水印
	 */
	private void waterImage(){
		String str = inputText.getText().toString();
		System.out.println("====="+iv.getDrawable());
		Bitmap img0 = BitmapFactory.decodeResource(getResources(), R.drawable.c);
		
		
		//drawable转Bitmap
		BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
		Bitmap img = drawable.getBitmap();
		
		int wid = img.getWidth();
		int hei = img.getHeight();
		//空图
		Bitmap newImg = Bitmap.createBitmap(wid, hei, Config.ARGB_8888);
		
		Canvas canvas = new Canvas(newImg);//画布属于新图
		
		//画笔
		Paint paint = new Paint();
		
		paint.setColor(Color.RED);
		paint.setTextSize(20);//设置字体大小
		
		FontMetrics fontMetrics =  paint.getFontMetrics();
		float fontHeight = fontMetrics.bottom-fontMetrics.top;
		
		//画图到空图上
		canvas.drawBitmap(img, 0, 0, paint);
		
		//画第二张图  设置alpha
		paint.setAlpha(155);//0-255
		canvas.drawBitmap(img0, img.getWidth()-img0.getWidth()-3,img.getHeight()-img0.getHeight()-3, paint);
		
		//画文字到空图上
		canvas.drawText(str, wid-100, hei, paint);
		
		canvas.drawRect(0, 10, 0+100, 10+100, paint);//绘制矩形
		canvas.drawCircle(img.getWidth()/2, img.getHeight()/2, 50, paint);//绘制圆形
		canvas.drawLine(0, 0, img.getWidth(), img.getHeight(), paint);//绘制对角线
		canvas.drawLine(0, img.getHeight(), img.getWidth(),0, paint);//绘制对角线
		canvas.drawRoundRect(new RectF(0, 0, 100, 50), img.getWidth()/2, img.getHeight()/2, paint);
		
		//显示到ImageView
		iv.setImageBitmap(newImg);
	}
	/**
	 * 保存位图到SDCard
	 */
	public void save() {
		try {
			String packageName = getApplicationInfo().packageName;
			File dir = new File(Environment.getExternalStorageDirectory()
					+ File.separator + packageName);
			if (!dir.exists()) {
				dir.mkdirs();// 创建目录
			}
			File imgFile = new File(dir, System.currentTimeMillis() + ".png");
			
			//获取ImageView中的Bitmap
			BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
			Bitmap img = drawable.getBitmap();
			
			
			FileOutputStream out = new FileOutputStream(imgFile);
			img.compress(CompressFormat.PNG, 100, out);//100质量
			
			Toast.makeText(this, "保存图片成功！", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, "保存图片失败", Toast.LENGTH_LONG).show();
		}
	}
	
	// 使用Matrix可以对图片进行平移、旋转、缩放、倾斜等
	private Bitmap testMatrix(Bitmap bitmap, int type) {
		Matrix matrix = new Matrix();
		switch (type) {
		case 1:
			matrix.setRotate(90);// 将图片旋转90度
			break;
		case 2:
			matrix.setRotate(-45);// 将图片旋转45度
			break;
		case 3:
			matrix.setScale(0.5f, 0.5f);// 将图片长宽缩写一倍
			break;
		case 4:
			matrix.setSkew(0.5f, 0);// 将图片向左倾斜
			break;
		default:
			break;
		}
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}
}
