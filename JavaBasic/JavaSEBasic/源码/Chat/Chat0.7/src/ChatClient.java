import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame() {
		setLocation(400, 300);
		setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
System.out.println("connected!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			taContent.setText(str);
			tfTxt.setText("");
			
			try {
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
				dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
}