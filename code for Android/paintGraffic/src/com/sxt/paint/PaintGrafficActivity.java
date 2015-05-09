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
	private final static int ACTION_RUBBER = 1;// ��Ƥ��
	private final static int ACTION_DRAW = 2;// ����
	private ImageView baseImageView;
	private Canvas canvas;//����
	private View setting;
	private AlertDialog alert;
	
	private int strokeWidth = 5;
	private int color = Color.RED;
	private int alpha = 255; // ͸���� ԽСԽ͸��0-255
	private Cap strokeCap = Cap.BUTT;
	
	private Bitmap baseBitmap;
	private Button btnRubber;
	private int width;
	private int height;
	private int flagAction = ACTION_DRAW;// ��¼��ǰ�������� Ĭ�ϳ�ʼΪ����
	
	//���ʿ�ʼ����
	private float startX;
	private float startY;
	
	private RadioGroup colorGroup;
	private RadioGroup styleGroup;
	
	private SeekBar size_SeekBar;
	private SeekBar alpha_SeekBar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��ʼ����
		initPaint();

		// ��Ļ��Ⱥ͸߶�
		width = this.getWindowManager().getDefaultDisplay().getWidth();
		height = this.getWindowManager().getDefaultDisplay().getHeight() - 50;// ����һ��Ҫ��ȥ���水ť�ĸ߶ȣ�����ử��λ

		//
		baseImageView = (ImageView) findViewById(R.id.imageView);
		baseImageView.setOnTouchListener(this);
		
		//��ͼ
		baseBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas = new Canvas(baseBitmap);
		
		//��Ƥ����ť  ��Ҫ�л�
		btnRubber = (Button) findViewById(R.id.btnRubber);
		
		// ���ÿ�
		initSetting();
		
		// �ҳ����ò��� ע��������õ���setting��findViewById()
		size_SeekBar = (SeekBar) setting.findViewById(R.id.size_seekBar);
		size_SeekBar.setProgress(strokeWidth);
		
		colorGroup = (RadioGroup) setting.findViewById(R.id.color_radioGroup);
		styleGroup = (RadioGroup) setting.findViewById(R.id.style_radioGroup);

		alpha_SeekBar = (SeekBar) setting.findViewById(R.id.aphilt_seekBar);
		alpha_SeekBar.setMax(255);//��ʼ��͸��
	}

	/**
	 * ��ʼ����
	 */
	private void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);// ���û��ʵľ��Ч��
		paint.setDither(true);// ͼ�񶶶�����
		paint.setStyle(Style.FILL);// ʵ����ʽ
		paint.setColor(Color.BLACK);
	}
	// ��ʼalert����
	private void initSetting() {
		// �������ò���
		setting = LayoutInflater.from(this).inflate(R.layout.setting, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("����");
		builder.setView(setting);

		builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setOk();
			}
		});
		builder.setNeutralButton("�ر�", null);
		alert = builder.create();
	}

	// ��������¼�
	public void setOk() {
		strokeWidth = size_SeekBar.getProgress();
		paint.setStrokeWidth(strokeWidth);

		// ��ˢ����
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

		// ��ɫ
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

		// ͸����
		alpha = 255 - alpha_SeekBar.getProgress();
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		switch (event.getAction()) {// ��ȡ����
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
		return true;//һ��ע�⣺����false �������¼�����
	}
	// ����
	public void setPaint(View view) {
		alert.show();
	}
	// ����
	public void erasure(View view) {
		if (flagAction == ACTION_DRAW) {
			flagAction = ACTION_RUBBER;
			btnRubber.setText("Ϳѻ");
		} else {
			flagAction = ACTION_DRAW;
			btnRubber.setText("��Ƥ��");
		}
	}
}