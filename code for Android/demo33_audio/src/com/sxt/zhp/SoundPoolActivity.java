package com.sxt.zhp;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MotionEvent;
/**
 * 1.先获取音效池
 * 2.加载音效到音效池并获取对应的ID
 * 3.使用加载到的ID来播放音效
 *
 */
public class SoundPoolActivity extends Activity {
	//音效池
	private SoundPool soundPool;
	private int sid;
	private HashMap<Integer, Integer> soundPoolMap;//存储音效资源编号和音效池中编号对应映射关系
	
	//所有音效ID
	private int[] soundResId = {R.raw.birddie,R.raw.run,R.raw.tstart,R.raw.tstop};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();
		System.out.println("=======完成！======");
	}
	/**
	 * 初始化 加载音效
	 */
	private void init(){
		//创建音效池 //参数1：最多重叠的音效个数 参数2：声音类型（媒体，铃声等）
		soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC, 100);
		//sid = soundPool.load(this, R.raw.birddie, 10);//加载音效到音效池  并返回编号
		//
		soundPoolMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < soundResId.length; i++) {
			//映射
			int sid = soundPool.load(this, soundResId[i], 10);
			soundPoolMap.put(soundResId[i], sid);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//播放音效
	    	playSound();
		}
		return super.onTouchEvent(event);
	}
	private void playSound(){
		//第一个参数是通过SoundPool.load（）方法返回的音频对应id，

    	//第二个第三个参数表示左右声道大小，

    	//第四个参数是优先级，第五个参数是循环次数，0为无循环即只播放一次

    	//最后一个是播放速率（1.0 =正常播放，范围是0.5至2.0  
		soundPool.play(soundPoolMap.get(R.raw.tstart), 1, 1, 1, 0, 1);
	}
}
