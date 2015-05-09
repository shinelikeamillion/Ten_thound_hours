package com.sxt.zhp;

import java.util.HashMap;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MyMusic {
	private static SoundPool sp;
	private static HashMap<Integer, Integer> soundPoolMap;

    private static int maxSpNum;
    private static MediaPlayer mp;

  
    private static int[] soundIDs={R.raw.tstart,R.raw.tstop};
    public static MyMusic instance;
   
    private MyMusic(Context context){
    	//����1������ص�����Ч���� ����2���������ͣ�ý�壬�����ȣ�
 	   sp=new SoundPool(5, AudioManager.STREAM_MUSIC, 100); 	
 	   soundPoolMap = new HashMap<Integer, Integer>();  
 	   maxSpNum=soundIDs.length;
 	   for(int i=0;i<maxSpNum;i++){
 		   int spid=sp.load(context, soundIDs[i], 3);//��������Ч���еı��
 		   System.out.println(">>>>>>>>>>>===="+spid);
 		   soundPoolMap.put(i+1, spid);
 	   }
    }
    public static void start(){
    	if(mp!=null&&!mp.isPlaying()){
    		mp.start();    		
    	}    		
    }
    synchronized public static MyMusic getInstance(Context context){
    	if(instance==null){
    		instance=new MyMusic(context);
    	}
    	return instance;
    }

    /**
     * ��ͣ����
     */
    public static void pause(){
    	if(mp!=null&&mp.isPlaying()){
    		mp.pause();
    	}
    }
//    
    /**
     * ֹͣ����
     */
    public static void stopMusic(){
    	if(mp!=null){
    		mp.release();
    	}
    	mp=null;
    }
    
    
    public static void close(){
//    	if(mp_menu!=null&&mp_menu.isPlaying()){
//    		mp_menu.release();
//    	}
//    	if(mp_run!=null&&mp_run.isPlaying()){
//    		mp_run.release();
//    	}
    	instance=null;
    }
    
    
    /**
     * ����Ч����

     */
    public  void startEffect(int id){
    	float v=1f;
    	if(id==2){
    		v=0.6f;
    	}
    	startEffect(id,v);
    }
    /**
     * ����Ч��������������

     * @param id Ч�������

     * @param volume ����
     */
    public  void startEffect(int id,float volume){
    	//��һ��������ͨ��SoundPool.load�����������ص���Ƶ��Ӧid��

    	//�ڶ���������������ʾ����������С��

    	//���ĸ����������ȼ��������������ѭ��������

    	//���һ���ǲ������ʣ�1.0 =�������ţ���Χ��0.5��2.0  
    		if(volume>1){
    			volume=1;
    		}else if(volume<0.2f){
    			volume=0.2f;
    		}    	
    		if(id<=maxSpNum&&id>=1){
    			sp.play(soundPoolMap.get(id), volume, volume, 1, 0, 1f);
    		}else{
    			System.out.println("soundPool ID error");
    		}    		
    }
    
    public static void stopEffect(int id){
    	try{
	    	if(sp!=null){
	    		sp.pause(id);
	    		sp.stop(id);
	    		
	    	}
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }


}
