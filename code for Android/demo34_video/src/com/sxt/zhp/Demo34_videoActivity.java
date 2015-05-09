package com.sxt.zhp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Demo34_videoActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);

		MediaController mediaController = new MediaController(this);

		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);

		videoView.setVideoPath("/mnt/sdcard/a11.mp4");
		
		// Uri video = Uri.parse("http://commonsware.com/misc/test2.3gp");
		// videoView.setVideoURI(video);

		videoView.start();
		videoView.requestFocus();
	}
}