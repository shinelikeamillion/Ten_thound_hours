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
 * ʵ��UDPͨ�ź�CS��java��������
 * ����ʱ��Ҫʹ��ServerFrame.java��java����������
 */
public class NetSocketActivity extends Activity {
   	private Button sendButt;
   	private EditText content;
   	private TextView showmess;
   	private Handler hand = new Handler(){
   		@Override
   		public void handleMessage(Message msg) {
   			showMessage(msg.obj+"", "��");
			Toast.makeText(NetSocketActivity.this, "�Ѿ�����", Toast.LENGTH_SHORT).show();
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
        startReciver();//�����߳̽�����Ϣ
    }
    /**
     * ������Ϣ
     * @param msg
     * @param ip
     * @param port
     */
    private void sendMsg(String msg,String ip,int port){   	
    	try {
    		byte data[] = msg.getBytes("utf-8");
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);

			socket.send(packet);			
//			showMessage(msg, "��");
			Message mm = new Message();
			mm.obj = msg;
			hand.sendMessage(mm);
			Toast.makeText(this, "�Ѿ�����", Toast.LENGTH_SHORT).show();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * ������Ϣ
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
						System.out.println("======�յ�========"+str);
					} catch (IOException e) {
						e.printStackTrace();
					}
				       
				}
			}
		}.start();
	}
	
	/**
	 * ��ʾ�����¼
	 */
	private void showMessage(String mess,String user){
		sb.append(user+"˵��"+mess+"\n");
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
				showMessage(msg.obj+"", "�Է�");
				break;

			default:
				break;
			}
		};
		
	};
	private String ip = "192.168.1.99";
	private int port = 8000;
	private DatagramSocket socket;
	private StringBuilder sb = new StringBuilder();//��¼������Ϣ
	private boolean isReciving = true;
}