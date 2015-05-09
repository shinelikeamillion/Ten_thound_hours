package com.sxt.zhp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Demo20Activity extends Activity {
	private String ip = "192.168.1.99";
	private int port = 8888;
	private DatagramSocket socket;
	private StringBuilder sb = new StringBuilder();//记录聊天信息
	private boolean isReciving = true;
   	private Button sendButt;
   	private EditText content;
   	private TextView showmess;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sendButt = (Button) findViewById(R.id.button_chat);
        content = (EditText) findViewById(R.id.editText_chat);
        showmess = (TextView) findViewById(R.id.showmess);
        sendButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						sendMsg(content.getText().toString(),ip,port);
					};
				}.start();
			
			}
		});
        initSocket();
    }
    /**
     * 发送消息
     * @param msg
     * @param ip
     * @param port
     */
    private void sendMsg(String msg,String ip,int port){   	
    	try {
    		byte data[] = msg.getBytes("utf-8");
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
			socket.send(packet);	
			handler.sendEmptyMessage(1);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 初始化socket
     */
	private void initSocket(){
		if(socket == null){
			try {
				socket = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		//
		new ReciverThread().start();
	}
	/**
	 * 接收消息线程
	 */
	class ReciverThread extends Thread{
		@Override
		public void run() {
			while(true){
				byte[] b = new byte[1024];
				DatagramPacket packet = new DatagramPacket(b, b.length);
				try {
					socket.receive(packet);
					
					//接收消息
					String msg = new String(b,0,packet.getLength(),"utf-8");
					Message message = Message.obtain();
					message.obj = msg;
					message.what = 2;
					handler.sendMessage(message);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 消息通信
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(Demo20Activity.this, "消息已经发出！", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				//Toast.makeText(Demo20Activity.this, "消息已经发出！", Toast.LENGTH_SHORT).show();
				showmess.append("\n"+msg.obj);
				break;
			default:
				break;
			}
		};
	};
}