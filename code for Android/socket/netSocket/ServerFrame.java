package com.zhp.udp.form.cs;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
/**
 * UDP聊天简单示例
 * @author zhangpeng
 *
 */
public class ServerFrame extends JFrame {
	final JTextArea textArea; // 显示消息区域
	final JTextArea textArea_send; // 发送消息文本域

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		ServerFrame frame = new ServerFrame();
		frame.setVisible(true);

	}

	/**
	 * Create the frame
	 */
	public ServerFrame() {
		super();
		setTitle("UDP网络聊天-服务器端");
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		textArea.setFocusable(false);
		textArea.setBorder(new LineBorder(Color.black, 1, false));
		textArea.setBounds(0, 0, 408, 219);
		getContentPane().add(textArea);

		textArea_send = new JTextArea();
		textArea_send.setBorder(new LineBorder(Color.black, 1, false));
		textArea_send.setBounds(0, 225, 408, 39);
		getContentPane().add(textArea_send);

		final JButton button = new JButton();
		button.setText("发送<ALT+S>");
		button.setBounds(28, 285, 114, 28);
		getContentPane().add(button);

		final JButton button_1 = new JButton();
		button_1.setText("清屏");
		button_1.setBounds(162, 285, 106, 28);
		getContentPane().add(button_1);
		// 清空事件
		button_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				sb = new StringBuilder();
				textArea.setText(sb.toString());
			}
		});
		// 发送消息按钮事件
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//发送消息
				send();
			}
		});
		textArea_send.addKeyListener(new KeyListener() {

			
			public void keyTyped(KeyEvent e) {
			}

			
			public void keyReleased(KeyEvent e) {
			}

			
			public void keyPressed(KeyEvent e) {
				//ALT+S事件
				if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_S) {
					System.out.println("====组合键====");
					send();
				}
			}
		});
		
	////启动线程接收消息///
		startReciver();
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
						//要获得客户端的网络地址和端口
						ip = data.getAddress().getHostAddress();
						port = data.getPort();
						System.out.println(ip+"============"+port);
						String str = (new String(data.getData(),"utf-8")).toString();
						showMessage(str, "对方");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				       
				}
			}
		}.start();
	}
	private void send(){
		String input = textArea_send.getText().trim();
		sendData(input, ip, port);	//发送聊天信息
		showMessage(input,userName);//记录聊天记录
		textArea_send.setText("");	//清空发送框
	}
	/**
	 * 发送消息
	 * @param str
	 * @param ip
	 * @param port
	 */
	private void sendData(String str, String ip, int port) {
		
		try {
			if(socket == null){
				initSocket();
			}
			byte b[] = str.getBytes();
			DatagramPacket data = new DatagramPacket(b, b.length, InetAddress
					.getByName(ip), port);
			socket.send(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initSocket(){
		if(socket == null){
			try {
				socket = new DatagramSocket(8000);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 显示聊天记录
	 */
	private void showMessage(String mess,String user){
		
		sb.append(user+"说："+mess+"\n");
		textArea.setText(sb.toString()+"\n");
	}
	private DatagramSocket socket;
	private String ip = "127.0.0.1";
	private int port = 8000;
	private StringBuilder sb = new StringBuilder();//记录聊天信息
	private String userName = "服务器端";
	private boolean isReciving = true;
}
