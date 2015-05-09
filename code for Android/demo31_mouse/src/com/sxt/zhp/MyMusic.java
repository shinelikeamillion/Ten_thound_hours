package com.sxt.zhp;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MyMusic {
	//������ЧID
	private static int[] soundResId = {R.raw.birddie,R.raw.run};
	//��Ч��
	private static SoundPool soundPool;
	//�洢��Ч��Դ��ź���Ч���б�Ŷ�Ӧӳ���ϵ
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
	 * ��ʼ����Ч��Դ
	 */
	private void init(){
		//������Ч�� //����1������ص�����Ч���� ����2���������ͣ�ý�壬�����ȣ�
		soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC, 100);
		//
		soundPoolMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < soundResId.length; i++) {
			//ӳ��
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
	 * ���ű�������
	 */
	public void playBackMusic(){
		if(mediaPlayer!=null && !mediaPlayer.isPlaying()){
			mediaPlayer.start();
		}
	}
	/**
	 * ��ͣ����
	 */
	public void pauseBackMusic(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
	}
	/**
	 * ֹͣ����
	 */
	public void stopBackMusic(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
	}
}
