package com.sxt.zhp;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MyMusic {
	//所有音效ID
	private static int[] soundResId = {R.raw.birddie,R.raw.run};
	//音效池
	private static SoundPool soundPool;
	//存储音效资源编号和音效池中编号对应映射关系
	private static HashMap<Integer, Integer> soundPoolMap;
	
	private Context context;
	private static MyMusic instance;
	
	private MediaPlayer mediaPlayer;
	
	private MyMusic(){	
		
	}
	synchronized public static MyMusic getInstance(Context context){
		if(instance==null){			
			instance = new MyMusic();	
			instance.context = context;
			instance.init();
		}
		return instance;
	}
	/**
	 * 初始化音效资源
	 */
	private void init(){
		//创建音效池 //参数1：最多重叠的音效个数 参数2：声音类型（媒体，铃声等）
		soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC, 100);
		//
		soundPoolMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < soundResId.length; i++) {
			//映射
			System.out.println(soundPool+"===="+soundResId+"==="+context);
			int sid = soundPool.load(context, soundResId[i], 10);
			soundPoolMap.put(soundResId[i], sid);
		}
		
		//
		mediaPlayer = MediaPlayer.create(context, R.raw.run);
		mediaPlayer.setLooping(true);
	}
	
	public void playSound(int resId){
		soundPool.play(soundPoolMap.get(resId), 1, 1, 1, 0, 1);
	}
	
	/**
	 * 播放背景音乐
	 */
	public void playBackMusic(){
		if(mediaPlayer!=null && !mediaPlayer.isPlaying()){
			mediaPlayer.start();
		}
	}
	/**
	 * 暂停音乐
	 */
	public void pauseBackMusic(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
	}
	/**
	 * 停止音乐
	 */
	public void stopBackMusic(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
	}
}
