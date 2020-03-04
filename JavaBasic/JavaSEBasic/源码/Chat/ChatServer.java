import java.io.*;
import java.net.*;

public class ChatServer {
	ServerSocket ss = null;
	boolean started = false;
	public static void main(String[] args) throws Exception {
		new ChatServer().start();
	}
	
	public void start() throws Exception {
		//���ӿͻ���
		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch (BindException e) {
			System.out.print("�˿�ʹ���С���");
			System.out.println("��ص���س����������з�������");
			System.exit(0);
		}

		while (started) {
			Socket s = ss.accept();
			Client c = new Client(s);
//System.out.println("Conneted!");
			new Thread(c).start();
		}
			
		ss.close();
	}

	class Client implements Runnable {  //��ȡ�ͻ�����Ϣ�ľ��巽��
		private Socket s = null;
		private DataInputStream dis = null;
		private boolean bConnected = false;

		public Client(Socket s) throws Exception {
			this.s = s;
			dis = new DataInputStream(s.getInputStream());
			bConnected = true;
		}
		
		@Override
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
				}				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) dis.close();
					if(s != null) s.close();
				} catch (IOException e1) {
					e1.printStackTrace();					
				}
			}
		}
	}
}

