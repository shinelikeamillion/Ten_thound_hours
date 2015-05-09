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
 * ģ����Ҫ�໥UDP����Ҫ���������ʹ�õĶ˿ں������˿ڰ󶨣�Ȼ�����ʹ��ָ���Ľӿڽ�����Ϣ
 * ���磺avd5554Ҫ������Ϣ������Ҫ
 * telnet 127.0.0.1 5554
 * redir list udp �鿴�˿ڵİ��б�
 * redir add udp:8888:8888----�����˿�8888��ģ������8888�󶨣�����ģ�����еķ���8888�˿ڵ���Ϣ�����Խ�����
 * ������ʾ��������װ����ͬ��ģ��������װǰ�޸Ľ��ն˿ںͰ�һ��
 */
public class SocketActivity extends Activity {
	private int reciverPort = 8888;//���ն˿���Ҫ��ģ�����󶨵Ķ˿�һ��
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
        
        //����������Ϣ�߳�
        recevierThread.start();
    }
    //��ָ��Ip�Ͷ˿ڷ�����Ϣ��
    private void send(){ 	
    	SendThread sendThread = new SendThread();
    	sendThread.start();
    }
    //��ʼSocket����
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
    //������Ϣ���߳�
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
//				socket.close();//������Ϣ�̺߳ͽ�����Ϣ�߳�ʹ��ͬһ�׽��֣��������ﲻ�ܹر�
			}
    	};

    };
    //������Ϣ���߳�
    private Thread recevierThread = new Thread(){
    	public void run() {
			byte[] b = new byte[1024];
			DatagramPacket packet = new DatagramPacket(b, b.length);
    		try {
    			while(true){   				
    				System.out.println("==========�ȴ�����");
    				socket.receive(packet);
    			
    				sendIp = packet.getAddress().getHostAddress();
    				sendPort = packet.getPort();
    				
    				//���ݳ���ʵ���ַ���
    				String mess = new String(b,0,packet.getLength());
    				sb.append(mess+"\n");
    				System.out.println("*****������Ϣ��****"+mess.trim());
    				hanlder.sendEmptyMessage(0);//֪ͨ���߳�
    				
    			}
    			

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				socket.close();
			}
    		
    	};
    };
    //֪ͨ���̸߳�����ͼ
    private Handler hanlder = new Handler(){
    	public void handleMessage(Message msg) {
    		System.out.println("****handleMessage*****"+sb);
    		showmess.setText(sb.toString());
//    		ip.setText(sendIp);
    		port.setText(sendPort+"");
    	};
    };
    private StringBuilder sb = new StringBuilder();//��¼������Ϣ
}