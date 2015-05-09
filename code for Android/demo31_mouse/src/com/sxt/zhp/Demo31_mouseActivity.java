package com.sxt.zhp;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 单个地鼠
 *
 */
public class Demo31_mouseActivity extends Activity implements OnClickListener{
	private static final int FLAG_MOUSE_SHOW = 1;
	private static final int FLAG_MOUSE_HIDDEN = 2;
    private ImageView[] ivs;
    private int score;//得分
    private boolean isRunning = true;
       
    private Random random = new Random();//
    
	private TextView tvScore;//显示得分
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        tvScore = (TextView) findViewById(R.id.tv_score);
        new GameThread().start();
    }
    
    private void init(){
    	RelativeLayout ly = (RelativeLayout) findViewById(R.id.game_layout);
    	int count = ly.getChildCount();
    	ivs = new ImageView[9];
    	for (int i = 0; i < count; i++) {
    		View v0 = ly.getChildAt(i);
    		if(v0 instanceof ImageView){//因为其中还有textView 其他组件
    			ImageView v = (ImageView) ly.getChildAt(i);
    			v.setVisibility(View.INVISIBLE);
    			v.setOnClickListener(this);//添加上监听
    			ivs[i] = v;
    		}			
		}
    }
    
    private Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case FLAG_MOUSE_SHOW: 
				
				ivs[msg.arg1].setVisibility(View.VISIBLE);//显示地鼠
				break;
			case FLAG_MOUSE_HIDDEN: 
				ivs[msg.arg1].setVisibility(View.GONE);
				break;
			default:
				break;
			}
    	};
    };
    
    class GameThread extends Thread{
    	@Override
    	public void run() {
    		while(isRunning){
    			//产生一个随机数
    			int index = random.nextInt(9);
//    			ivs[index].setVisibility(View.VISIBLE);//显示地鼠
    			Message msg = Message.obtain();
    			msg.arg1 = index;
    			msg.what = FLAG_MOUSE_SHOW;
    			
    			handler.sendMessage(msg);
    			
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
//				ivs[index].setVisibility(View.GONE);//隐藏地鼠
				msg = Message.obtain();
    			msg.arg1 = index;
    			msg.what = FLAG_MOUSE_HIDDEN;
    			
    			handler.sendMessage(msg);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
    		}
    	}
    }

	/**
	 * 点击地鼠
	 */
	public void onClick(View arg0) {
		if(arg0.isShown()){
			score++;
			arg0.setVisibility(View.GONE);
			tvScore.setText("当前得分："+score);
		}
	}
}