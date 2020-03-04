import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public class ChatClient extends Frame {
	//��������˺������
	Socket s = null;
	DataOutputStream dos = null;	

	//��ȡ�ͻ���������ı���
	TextField tfText = new TextField();  
	TextArea taContent = new TextArea();

	public static void main(String[] args) throws Exception {
		new ChatClient().launchFrame();
	}
	
	//��ʼ�����ڣ����ڵĹرգ��ͻ��˵�����
	public void launchFrame() throws Exception {
		setBounds(300, 300, 300, 200);
		add(tfText, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
//				//����ͻ��˵Ĺرպ����Ĺرշ���
				System.exit(0);
			}
		});
		tfText.addActionListener(new TFListener());
		setVisible(true);
		connected();
	}
	
	//�ͻ������еľ��巽��
	public void connected() throws Exception {
		s = new Socket("127.0.0.1", 8888);
		dos = new DataOutputStream(s.getOutputStream());
//System.out.println("Connected!");
	}

	//�ͻ��˽����ľ��巽��
	public void disconnected() throws Exception {
		dos.close();
		s.close();
	}
	
	//��ȡ�ͻ�������ļ�����
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
