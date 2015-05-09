package com.sxt.zhp;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 音频录制
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
			System.out.println("======开始录音=====");
			//从麦克风采集声音
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//内容输出格式
			recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			//音频编码方式
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			
			recorder.setOutputFile("/sdcard/bbb.amr");//记住开SD权限
			
			recorder.prepare();//预期准备
			recorder.start();   //开始刻录
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void stopRecord(){
		recorder.stop();//停止刻录
		recorder.reset();   //重设
		recorder.release(); //刻录完成一定要释放资源
	}
}
