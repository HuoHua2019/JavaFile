import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public class ChatClient extends Frame {
	//声明服务端和输出流
	Socket s = null;
	DataOutputStream dos = null;	

	//获取客户端输入的文本框
	TextField tfText = new TextField();  
	TextArea taContent = new TextArea();

	public static void main(String[] args) throws Exception {
		new ChatClient().launchFrame();
	}
	
	//初始换窗口，窗口的关闭，客户端的运行
	public void launchFrame() throws Exception {
		setBounds(300, 300, 300, 200);
		add(tfText, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
//				//处理客户端的关闭和流的关闭方法
				System.exit(0);
			}
		});
		tfText.addActionListener(new TFListener());
		setVisible(true);
		connected();
	}
	
	//客户端运行的具体方法
	public void connected() throws Exception {
		s = new Socket("127.0.0.1", 8888);
		dos = new DataOutputStream(s.getOutputStream());
//System.out.println("Connected!");
	}

	//客户端结束的具体方法
	public void disconnected() throws Exception {
		dos.close();
		s.close();
	}
	
	//获取客户端输入的监听器
	private class TFListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfText.getText().trim();
			taContent.setText(str);
			tfText.setText("");
			try {
				dos.writeUTF(str);
				dos.flush();				
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
}
