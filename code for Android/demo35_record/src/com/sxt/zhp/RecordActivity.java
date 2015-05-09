package com.sxt.zhp;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * ��Ƶ¼��
 *
 */
public class RecordActivity extends Activity{
	MediaRecorder recorder = null;
	ToggleButton toggleButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		recorder = new MediaRecorder();
		toggleButton = (ToggleButton)findViewById(R.id.toggleButton_Record);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					startRecord();
				}else{
					stopRecord();
				}
				
			}
		});
	}
	public void startRecord(){
		try{
			System.out.println("======��ʼ¼��=====");
			//����˷�ɼ�����
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//���������ʽ
			recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			//��Ƶ���뷽ʽ
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			
			recorder.setOutputFile("/sdcard/bbb.amr");//��ס��SDȨ��
			
			recorder.prepare();//Ԥ��׼��
			recorder.start();   //��ʼ��¼
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void stopRecord(){
		recorder.stop();//ֹͣ��¼
		recorder.reset();   //����
		recorder.release(); //��¼���һ��Ҫ�ͷ���Դ
	}
}
