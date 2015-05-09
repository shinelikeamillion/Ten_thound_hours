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
 * UDP�����ʾ��
 * @author zhangpeng
 *
 */
public class ServerFrame extends JFrame {
	final JTextArea textArea; // ��ʾ��Ϣ����
	final JTextArea textArea_send; // ������Ϣ�ı���

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
		setTitle("UDP��������-��������");
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
		button.setText("����<ALT+S>");
		button.setBounds(28, 285, 114, 28);
		getContentPane().add(button);

		final JButton button_1 = new JButton();
		button_1.setText("����");
		button_1.setBounds(162, 285, 106, 28);
		getContentPane().add(button_1);
		// ����¼�
		button_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				sb = new StringBuilder();
				textArea.setText(sb.toString());
			}
		});
		// ������Ϣ��ť�¼�
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//������Ϣ
				send();
			}
		});
		textArea_send.addKeyListener(new KeyListener() {

			
			public void keyTyped(KeyEvent e) {
			}

			
			public void keyReleased(KeyEvent e) {
			}

			
			public void keyPressed(KeyEvent e) {
				//ALT+S�¼�
				if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_S) {
					System.out.println("====��ϼ�====");
					send();
				}
			}
		});
		
	////�����߳̽�����Ϣ///
		startReciver();
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
						//Ҫ��ÿͻ��˵������ַ�Ͷ˿�
						ip = data.getAddress().getHostAddress();
						port = data.getPort();
						System.out.println(ip+"============"+port);
						String str = (new String(data.getData(),"utf-8")).toString();
						showMessage(str, "�Է�");
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
		sendData(input, ip, port);	//����������Ϣ
		showMessage(input,userName);//��¼�����¼
		textArea_send.setText("");	//��շ��Ϳ�
	}
	/**
	 * ������Ϣ
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
	 * ��ʾ�����¼
	 */
	private void showMessage(String mess,String user){
		
		sb.append(user+"˵��"+mess+"\n");
		textArea.setText(sb.toString()+"\n");
	}
	private DatagramSocket socket;
	private String ip = "127.0.0.1";
	private int port = 8000;
	private StringBuilder sb = new StringBuilder();//��¼������Ϣ
	private String userName = "��������";
	private boolean isReciving = true;
}
