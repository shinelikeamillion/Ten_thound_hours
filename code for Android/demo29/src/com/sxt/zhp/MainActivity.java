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
 * ��ͼƬ��ˮӡ
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
		
		//��ˮӡ
		findViewById(R.id.btn_water).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {				
				waterImage();
			}
		});
		//����ͼƬ
		findViewById(R.id.btn_export).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {				
				save();
			}
		});
		
		//�仯
		findViewById(R.id.btn_change).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				changeImg();				
			}
		});
	}
	/**
	 * �仯
	 */
	private void changeImg(){
		BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
		Bitmap img = drawable.getBitmap();
		img = testMatrix(img,2);
		iv.setImageBitmap(img);
		
//		img.recycle();//���� ����ֱ�ӻ���  ��Ҫ���ƿ�ͼ�� Ȼ�����ͷ�ԭͼ
	}
	
	/**
	 * ��ˮӡ
	 */
	private void waterImage(){
		String str = inputText.getText().toString();
		System.out.println("====="+iv.getDrawable());
		Bitmap img0 = BitmapFactory.decodeResource(getResources(), R.drawable.c);
		
		
		//drawableתBitmap
		BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
		Bitmap img = drawable.getBitmap();
		
		int wid = img.getWidth();
		int hei = img.getHeight();
		//��ͼ
		Bitmap newImg = Bitmap.createBitmap(wid, hei, Config.ARGB_8888);
		
		Canvas canvas = new Canvas(newImg);//����������ͼ
		
		//����
		Paint paint = new Paint();
		
		paint.setColor(Color.RED);
		paint.setTextSize(20);//���������С
		
		FontMetrics fontMetrics =  paint.getFontMetrics();
		float fontHeight = fontMetrics.bottom-fontMetrics.top;
		
		//��ͼ����ͼ��
		canvas.drawBitmap(img, 0, 0, paint);
		
		//���ڶ���ͼ  ����alpha
		paint.setAlpha(155);//0-255
		canvas.drawBitmap(img0, img.getWidth()-img0.getWidth()-3,img.getHeight()-img0.getHeight()-3, paint);
		
		//�����ֵ���ͼ��
		canvas.drawText(str, wid-100, hei, paint);
		
		canvas.drawRect(0, 10, 0+100, 10+100, paint);//���ƾ���
		canvas.drawCircle(img.getWidth()/2, img.getHeight()/2, 50, paint);//����Բ��
		canvas.drawLine(0, 0, img.getWidth(), img.getHeight(), paint);//���ƶԽ���
		canvas.drawLine(0, img.getHeight(), img.getWidth(),0, paint);//���ƶԽ���
		canvas.drawRoundRect(new RectF(0, 0, 100, 50), img.getWidth()/2, img.getHeight()/2, paint);
		
		//��ʾ��ImageView
		iv.setImageBitmap(newImg);
	}
	/**
	 * ����λͼ��SDCard
	 */
	public void save() {
		try {
			String packageName = getApplicationInfo().packageName;
			File dir = new File(Environment.getExternalStorageDirectory()
					+ File.separator + packageName);
			if (!dir.exists()) {
				dir.mkdirs();// ����Ŀ¼
			}
			File imgFile = new File(dir, System.currentTimeMillis() + ".png");
			
			//��ȡImageView�е�Bitmap
			BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
			Bitmap img = drawable.getBitmap();
			
			
			FileOutputStream out = new FileOutputStream(imgFile);
			img.compress(CompressFormat.PNG, 100, out);//100����
			
			Toast.makeText(this, "����ͼƬ�ɹ���", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, "����ͼƬʧ��", Toast.LENGTH_LONG).show();
		}
	}
	
	// ʹ��Matrix���Զ�ͼƬ����ƽ�ơ���ת�����š���б��
	private Bitmap testMatrix(Bitmap bitmap, int type) {
		Matrix matrix = new Matrix();
		switch (type) {
		case 1:
			matrix.setRotate(90);// ��ͼƬ��ת90��
			break;
		case 2:
			matrix.setRotate(-45);// ��ͼƬ��ת45��
			break;
		case 3:
			matrix.setScale(0.5f, 0.5f);// ��ͼƬ������дһ��
			break;
		case 4:
			matrix.setSkew(0.5f, 0);// ��ͼƬ������б
			break;
		default:
			break;
		}
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}
}
