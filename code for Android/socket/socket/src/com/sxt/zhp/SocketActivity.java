package com.sxt.zhp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 模拟器要相互UDP，需要把虚拟机中使用的端口和主机端口绑定，然后才能使用指定的接口接收消息
 * 例如：avd5554要接收消息，则需要
 * telnet 127.0.0.1 5554
 * redir list udp 查看端口的绑定列表
 * redir add udp:8888:8888----主机端口8888和模拟器的8888绑定，这样模拟器中的发送8888端口的消息都可以接收了
 * 本例演示方法：安装到不同的模拟器，安装前修改接收端口和绑定一致
 */
public class SocketActivity extends Activity {
	private int reciverPort = 8888;//接收端口需要和模拟器绑定的端口一致
    private Button btn;
    private EditText ip;
    private EditText port;
    private EditText content;
    private TextView showmess;
    private DatagramSocket socket = null;
    private String sendIp = "192.168.1.99";
    private int sendPort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ip = (EditText) findViewById(R.id.editText_IP);
        ip.setText(sendIp);
        port = (EditText) findViewById(R.id.editText_port);
        content = (EditText) findViewById(R.id.editText_sendMsg);
        showmess = (TextView) findViewById(R.id.showText);
        
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				send();				
			}
		});
        
        initSocket();
        
        //启动接收消息线程
        recevierThread.start();
    }
    //按指定Ip和端口发送消息报
    private void send(){ 	
    	SendThread sendThread = new SendThread();
    	sendThread.start();
    }
    //初始Socket对象
	private void initSocket(){
		if(socket == null){
			try {
				socket = new DatagramSocket();
				InetAddress ip = socket.getLocalAddress();
				int port = socket.getLocalPort();
				content.setHint(port+"");
				System.out.println(ip+"========="+port);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}
    //发送消息的线程
    class SendThread extends Thread{

    	public void run() {
        	String sendIp = ip.getText()+"";
        	String sendMsg = content.getText().toString();
        	int sendPort = Integer.parseInt(port.getText()+"");
        	
    		byte[] str = sendMsg.getBytes();
    		try {
				DatagramPacket packet = new DatagramPacket(str, str.length, InetAddress.getByName(sendIp), sendPort);
				socket.send(packet);
    		} catch (Exception e) {
				e.printStackTrace();
			}finally{
//				socket.close();//发送消息线程和接收消息线程使用同一套接字，所以这里不能关闭
			}
    	};

    };
    //接收消息的线程
    private Thread recevierThread = new Thread(){
    	public void run() {
			byte[] b = new byte[1024];
			DatagramPacket packet = new DatagramPacket(b, b.length);
    		try {
    			while(true){   				
    				System.out.println("==========等待接收");
    				socket.receive(packet);
    			
    				sendIp = packet.getAddress().getHostAddress();
    				sendPort = packet.getPort();
    				
    				//根据长度实例字符串
    				String mess = new String(b,0,packet.getLength());
    				sb.append(mess+"\n");
    				System.out.println("*****接收消息：****"+mess.trim());
    				hanlder.sendEmptyMessage(0);//通知主线程
    				
    			}
    			

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				socket.close();
			}
    		
    	};
    };
    //通知主线程更新视图
    private Handler hanlder = new Handler(){
    	public void handleMessage(Message msg) {
    		System.out.println("****handleMessage*****"+sb);
    		showmess.setText(sb.toString());
//    		ip.setText(sendIp);
    		port.setText(sendPort+"");
    	};
    };
    private StringBuilder sb = new StringBuilder();//记录聊天信息
}