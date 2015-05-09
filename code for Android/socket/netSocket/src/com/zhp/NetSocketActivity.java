package com.zhp;

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
/**
 * 实现UDP通信和CS的java服务器端
 * 测试时需要使用ServerFrame.java在java工程中运行
 */
public class NetSocketActivity extends Activity {
   	private Button sendButt;
   	private EditText content;
   	private TextView showmess;
   	private Handler hand = new Handler(){
   		@Override
   		public void handleMessage(Message msg) {
   			showMessage(msg.obj+"", "我");
			Toast.makeText(NetSocketActivity.this, "已经发出", Toast.LENGTH_SHORT).show();
   		}
   	};
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
        startReciver();//启动线程接收消息
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
//			showMessage(msg, "我");
			Message mm = new Message();
			mm.obj = msg;
			hand.sendMessage(mm);
			Toast.makeText(this, "已经发出", Toast.LENGTH_SHORT).show();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * 接收消息
	 */
	private void startReciver(){
		new Thread(){
			public void run(){
				initSocket();
				while(isReciving){
					 byte b[] = new byte[1024];
				     DatagramPacket data = new DatagramPacket(b, b.length);
				     try {
						socket.receive(data);
						String str = (new String(data.getData(),0,data.getLength())).toString();
						
						Message msg = new Message();
						msg.what = 1;
						msg.obj = str;
						handler.sendMessage(msg);
						System.out.println("======收到========"+str);
					} catch (IOException e) {
						e.printStackTrace();
					}
				       
				}
			}
		}.start();
	}
	
	/**
	 * 显示聊天记录
	 */
	private void showMessage(String mess,String user){
		sb.append(user+"说："+mess+"\n");
		showmess.setText(sb.toString()+"\n");
	}
	private void initSocket(){
		if(socket == null){
			try {
				socket = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}
	//
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				showMessage(msg.obj+"", "对方");
				break;

			default:
				break;
			}
		};
		
	};
	private String ip = "192.168.1.99";
	private int port = 8000;
	private DatagramSocket socket;
	private StringBuilder sb = new StringBuilder();//记录聊天信息
	private boolean isReciving = true;
}