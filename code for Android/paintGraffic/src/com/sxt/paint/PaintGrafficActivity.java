package com.sxt.paint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class PaintGrafficActivity extends Activity implements OnTouchListener {
	private Paint paint;
	private final static int ACTION_RUBBER = 1;// 橡皮擦
	private final static int ACTION_DRAW = 2;// 画笔
	private ImageView baseImageView;
	private Canvas canvas;//画布
	private View setting;
	private AlertDialog alert;
	
	private int strokeWidth = 5;
	private int color = Color.RED;
	private int alpha = 255; // 透明度 越小越透明0-255
	private Cap strokeCap = Cap.BUTT;
	
	private Bitmap baseBitmap;
	private Button btnRubber;
	private int width;
	private int height;
	private int flagAction = ACTION_DRAW;// 记录当前动作类型 默认初始为画笔
	
	//画笔开始坐标
	private float startX;
	private float startY;
	
	private RadioGroup colorGroup;
	private RadioGroup styleGroup;
	
	private SeekBar size_SeekBar;
	private SeekBar alpha_SeekBar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始画笔
		initPaint();

		// 屏幕宽度和高度
		width = this.getWindowManager().getDefaultDisplay().getWidth();
		height = this.getWindowManager().getDefaultDisplay().getHeight() - 50;// 这里一定要减去下面按钮的高度，否则会画错位

		//
		baseImageView = (ImageView) findViewById(R.id.imageView);
		baseImageView.setOnTouchListener(this);
		
		//空图
		baseBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas = new Canvas(baseBitmap);
		
		//橡皮擦按钮  需要切换
		btnRubber = (Button) findViewById(R.id.btnRubber);
		
		// 设置框
		initSetting();
		
		// 找出设置参数 注意下面调用的是setting的findViewById()
		size_SeekBar = (SeekBar) setting.findViewById(R.id.size_seekBar);
		size_SeekBar.setProgress(strokeWidth);
		
		colorGroup = (RadioGroup) setting.findViewById(R.id.color_radioGroup);
		styleGroup = (RadioGroup) setting.findViewById(R.id.style_radioGroup);

		alpha_SeekBar = (SeekBar) setting.findViewById(R.id.aphilt_seekBar);
		alpha_SeekBar.setMax(255);//初始不透明
	}

	/**
	 * 初始画笔
	 */
	private void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);// 设置画笔的锯齿效果
		paint.setDither(true);// 图像抖动处理
		paint.setStyle(Style.FILL);// 实心样式
		paint.setColor(Color.BLACK);
	}
	// 初始alert设置
	private void initSetting() {
		// 加载设置布局
		setting = LayoutInflater.from(this).inflate(R.layout.setting, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置");
		builder.setView(setting);

		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setOk();
			}
		});
		builder.setNeutralButton("关闭", null);
		alert = builder.create();
	}

	// 点击设置事件
	public void setOk() {
		strokeWidth = size_SeekBar.getProgress();
		paint.setStrokeWidth(strokeWidth);

		// 笔刷类型
		switch (styleGroup.getCheckedRadioButtonId()) {
		case R.id.default_radio:
			strokeCap = Cap.BUTT;
			break;
		case R.id.round_radio:
			strokeCap = Cap.ROUND;
			break;
		case R.id.square_radio:
			strokeCap = Cap.SQUARE;
			break;
		}

		// 颜色
		switch (colorGroup.getCheckedRadioButtonId()) {
		case R.id.red_radio:
			color = Color.RED;
			break;
		case R.id.blank_radio:
			color = Color.BLACK;
			break;
		case R.id.bule_radio:
			color = Color.BLUE;
			break;
		case R.id.green_radio:
			color = Color.GREEN;
			break;
		case R.id.yellow_radio:
			color = Color.YELLOW;
			break;
		default:
			break;
		}

		// 透明度
		alpha = 255 - alpha_SeekBar.getProgress();
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		switch (event.getAction()) {// 获取动作
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float endX = event.getX();
			float endY = event.getY();
			
			canvas.drawLine(startX, startY, endX, endY, paint);
			baseImageView.setImageBitmap(baseBitmap);
			
			startX = endX;
			startY = endY;
			break;
		case MotionEvent.ACTION_UP:			
			break;
		}
		return true;//一定注意：返回false 拦截了事件传递
	}
	// 设置
	public void setPaint(View view) {
		alert.show();
	}
	// 擦除
	public void erasure(View view) {
		if (flagAction == ACTION_DRAW) {
			flagAction = ACTION_RUBBER;
			btnRubber.setText("涂鸦");
		} else {
			flagAction = ACTION_DRAW;
			btnRubber.setText("橡皮擦");
		}
	}
}