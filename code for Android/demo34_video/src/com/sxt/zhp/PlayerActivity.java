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
 * 视频播放
 *
 */
public class PlayerActivity extends Activity{
	MediaPlayer mediaPlayer;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player); 
		Button button = (Button)findViewById(R.id.button_player);//播放视频按钮
		
		//用于视频画面绘制的SurfaceView 控件
		final SurfaceView surfaceView = (SurfaceView)this.findViewById(R.id.surfaceView);
		surfaceView.getHolder().setFixedSize(176, 144);	//设置分辨率

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
					mediaPlayer.reset();//重置为初始状态
	
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					/* 设置Video影片以SurfaceHolder播放 */
					mediaPlayer.setDisplay(surfaceView.getHolder());
					
					mediaPlayer.setDataSource("/mnt/sdcard/a11.mp4");
					mediaPlayer.prepare();				
					mediaPlayer.start();//播放
					surfaceView.requestFocus();
//					mediaPlayer.pause();//暂停播放
//					mediaPlayer.start();//恢复播放
//					mediaPlayer.stop();//停止播放
//					mediaPlayer.release();//释放资源
					
				}catch (Exception e) {
					e.printStackTrace();
				}

			}
		});		
	}
	/**
	 * 快进
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
