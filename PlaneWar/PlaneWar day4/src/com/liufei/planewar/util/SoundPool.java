package com.liufei.planewar.util;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;

//��Ч������
public class SoundPool {
	private Context context;
	private SoundPool pool;
	private HashMap<Integer, Integer> map;
	private boolean isOpen;
	public SoundPool() {
		this.context = context;
//		pool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		map = new HashMap<Integer, Integer>();
	}
	//��ʼ��ϵͳ��Ч
	public void initSysSound () {
		if (isOpen) {
//			map.put(1, )
		}
	}
	
	//��ʼ��ϵͳ��Ч
	public void initSound () {
		if (isOpen) {
//			map.put(1, poo)
		}
	}
}
