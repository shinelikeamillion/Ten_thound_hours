package com.sxt.zhp;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MotionEvent;
/**
 * 1.�Ȼ�ȡ��Ч��
 * 2.������Ч����Ч�ز���ȡ��Ӧ��ID
 * 3.ʹ�ü��ص���ID��������Ч
 *
 */
public class SoundPoolActivity extends Activity {
	//��Ч��
	private SoundPool soundPool;
	private int sid;
	private HashMap<Integer, Integer> soundPoolMap;//�洢��Ч��Դ��ź���Ч���б�Ŷ�Ӧӳ���ϵ
	
	//������ЧID
	private int[] soundResId = {R.raw.birddie,R.raw.run,R.raw.tstart,R.raw.tstop};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();
		System.out.println("=======��ɣ�======");
	}
	/**
	 * ��ʼ�� ������Ч
	 */
	private void init(){
		//������Ч�� //����1������ص�����Ч���� ����2���������ͣ�ý�壬�����ȣ�
		soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC, 100);
		//sid = soundPool.load(this, R.raw.birddie, 10);//������Ч����Ч��  �����ر��
		//
		soundPoolMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < soundResId.length; i++) {
			//ӳ��
			int sid = soundPool.load(this, soundResId[i], 10);
			soundPoolMap.put(soundResId[i], sid);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//������Ч
	    	playSound();
		}
		return super.onTouchEvent(event);
	}
	private void playSound(){
		//��һ��������ͨ��SoundPool.load�����������ص���Ƶ��Ӧid��

    	//�ڶ���������������ʾ����������С��

    	//���ĸ����������ȼ��������������ѭ��������0Ϊ��ѭ����ֻ����һ��

    	//���һ���ǲ������ʣ�1.0 =�������ţ���Χ��0.5��2.0  
		soundPool.play(soundPoolMap.get(R.raw.tstart), 1, 1, 1, 0, 1);
	}
}
