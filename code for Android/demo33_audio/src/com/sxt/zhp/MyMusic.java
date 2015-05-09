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
    	//参数1：最多重叠的音效个数 参数2：声音类型（媒体，铃声等）
 	   sp=new SoundPool(5, AudioManager.STREAM_MUSIC, 100); 	
 	   soundPoolMap = new HashMap<Integer, Integer>();  
 	   maxSpNum=soundIDs.length;
 	   for(int i=0;i<maxSpNum;i++){
 		   int spid=sp.load(context, soundIDs[i], 3);//返回在音效池中的编号
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
     * 暂停音乐
     */
    public static void pause(){
    	if(mp!=null&&mp.isPlaying()){
    		mp.pause();
    	}
    }
//    
    /**
     * 停止音乐
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
     * 开启效果音

     */
    public  void startEffect(int id){
    	float v=1f;
    	if(id==2){
    		v=0.6f;
    	}
    	startEffect(id,v);
    }
    /**
     * 开启效果音并设置音量

     * @param id 效果音序号

     * @param volume 音量
     */
    public  void startEffect(int id,float volume){
    	//第一个参数是通过SoundPool.load（）方法返回的音频对应id，

    	//第二个第三个参数表示左右声道大小，

    	//第四个参数是优先级，第五个参数是循环次数，

    	//最后一个是播放速率（1.0 =正常播放，范围是0.5至2.0  
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
