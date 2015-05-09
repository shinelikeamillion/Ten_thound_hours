package com.sxt.zhp;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * ��Ƶ����
 *
 */
public class PlayerActivity extends Activity{
	MediaPlayer mediaPlayer;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player); 
		Button button = (Button)findViewById(R.id.button_player);//������Ƶ��ť
		
		//������Ƶ������Ƶ�SurfaceView �ؼ�
		final SurfaceView surfaceView = (SurfaceView)this.findViewById(R.id.surfaceView);
		surfaceView.getHolder().setFixedSize(176, 144);	//���÷ֱ���

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				fastForward();
				
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					mediaPlayer = new MediaPlayer();
					mediaPlayer.reset();//����Ϊ��ʼ״̬
	
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					/* ����VideoӰƬ��SurfaceHolder���� */
					mediaPlayer.setDisplay(surfaceView.getHolder());
					
					mediaPlayer.setDataSource("/mnt/sdcard/a11.mp4");
					mediaPlayer.prepare();				
					mediaPlayer.start();//����
					surfaceView.requestFocus();
//					mediaPlayer.pause();//��ͣ����
//					mediaPlayer.start();//�ָ�����
//					mediaPlayer.stop();//ֹͣ����
//					mediaPlayer.release();//�ͷ���Դ
					
				}catch (Exception e) {
					e.printStackTrace();
				}

			}
		});		
	}
	/**
	 * ���
	 */
	private void fastForward() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			int cur = mediaPlayer.getCurrentPosition();
			if (cur + 5000 < mediaPlayer.getDuration()) {
				mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
			}else{
				mediaPlayer.seekTo(mediaPlayer.getDuration());
			}			
		}
	}
	/**
	 * VideoView videoView = (VideoView) findViewById(R.id.test);
18
        MediaController mediaController = new MediaController (this);
19
        mediaController.setAnchorView(videoView);
20
        Uri video = Uri.parse("http://commonsware.com/misc/test2.3gp");
21
        videoView.setMediaController(mediaController);
22
        videoView.setVideoURI(video);
23
        videoView.start();

	 */
}
