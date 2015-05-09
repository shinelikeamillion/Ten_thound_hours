package com.sxt.zhp;

import java.io.File;    
import java.io.IOException;    
    
import android.app.Activity;    
import android.media.MediaRecorder;    
import android.os.Bundle;    
import android.os.Environment;    
import android.view.SurfaceHolder;    
import android.view.SurfaceView;    
import android.view.View;    
import android.view.View.OnClickListener;    
import android.widget.Button;    
import android.widget.TextView;
 /**
  * ¼����Ƶ
  * <uses-permission android:name="android.permission.RECORD_AUDIO"/>  
  * @author zhangpeng
  *
  */
public class VideoActivity extends Activity {    
    
        
    private File myRecAudioFile;    
    private SurfaceView mSurfaceView;       
    private SurfaceHolder mSurfaceHolder;     
    private Button buttonStart;    
    private Button buttonStop;    
    private File dir;    
    private MediaRecorder recorder;    
        
        
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.video);    
        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);       
        mSurfaceHolder = mSurfaceView.getHolder();       
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);     
        buttonStart=(Button)findViewById(R.id.start);    
        buttonStop=(Button)findViewById(R.id.stop);  
        
        //���������Ƶ��Ŀ¼
        File defaultDir = Environment.getExternalStorageDirectory();    
        String path = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator;//�����ļ��д����Ƶ   
        dir = new File(path);    
        if(!dir.exists()){    
            dir.mkdir();    
        }   
        //ʵ����¼��
        recorder = new MediaRecorder();    
            
        buttonStart.setOnClickListener(new OnClickListener() {    
            @Override    
            public void onClick(View v) {    
                recorder();    
            }    
        });    
            
        buttonStop.setOnClickListener(new OnClickListener() {    
            @Override    
            public void onClick(View v) {    
                 recorder.stop();    
                 recorder.reset();    
                 recorder.release();    
                 recorder=null;  
                 TextView tv = (TextView)findViewById(R.id.textView1);
                 tv.setText("�����ڣ�"+myRecAudioFile.getAbsolutePath());
            }    
        });    
    }    
        
        
        
    public void recorder() {    
        try {   //mnt/sdcard/V/v-234234.3pg
            myRecAudioFile = File.createTempFile("v-", ".3gp",dir);//������ʱ�ļ�    
            recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//Ԥ��    
            
            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//��ƵԴ    
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //¼��ԴΪ��˷�   
            
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//�����ʽΪ3gp    
            recorder.setVideoSize(800, 480);//��Ƶ�ߴ�    
            recorder.setVideoFrameRate(15);//��Ƶ֡Ƶ��    
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//��Ƶ����    
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//��Ƶ����    
            recorder.setMaxDuration(10000);//�������    
            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());//����·��    
            recorder.prepare();    
            recorder.start();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }    
} 
