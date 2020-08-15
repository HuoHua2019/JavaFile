#Java learning_Day13(上)

>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##主要内容：第一个小小的实践项目(上)

##Chat0.1

摘要：Chat0.1 作为项目的起步版本，主要功能是新建一个窗口。

```
import java.awt.*;

public class ChatClient extends Frame{

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame() {
		setLocation(400, 300);
		setSize(300, 300);
		setVisible(true);
	}
}
```
新建一个 ChatClient.java 的类，类中包含了窗口的基本框架，包括入口函数和设置窗口的 launchFrame 方法。

##Chat0.2
摘要：Chat0.2 在 Chat0.1 的基础上添加了文本输入框。

```
import java.awt.*;

public class ChatClient extends Frame {
	
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
		setVisible(true);
	}
	
}
```
将单行文本输入布局在窗口下方，将多行文本输入布局在了窗口上方。

##Chat0.3

摘要：Chat0.3 现在可以通过关闭按钮正常关闭窗口。

```
import java.awt.*;
import java.awt.event.*;
public class ChatClient extends Frame {
	
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
		setVisible(true);
	}
	
}
```
添加了窗口监听的匿名内部类来使得窗口可以点击关闭。

##Chat0.4
摘要：Chat0.4 现在可以获取单行文本框中的内容并输出至多行文本框中。

```
import java.awt.*;
import java.awt.event.*;

public class ChatClient extends Frame {
	
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
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = tfTxt.getText().trim();
			taContent.setText(s);
			tfTxt.setText("");
		}
		
	}
	
}
```
新增了内部类 TFListener 实现获取文本的功能。

##Chat0.5
摘要：Chat0.5 增加了服务器端。

####Client 端未修改。

####Server 端
```
import java.io.IOException;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true) {
				Socket s = ss.accept();
System.out.println("a client connected");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

```
Server端新建了服务器端口，当有客户端与之连接时会形成消息。

##Chat0.6
摘要：Chat0.6 接通了客户端和服务器端，当双方接通时会形成消息。

####Client 端
```
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
			String s = tfTxt.getText().trim();
			taContent.setText(s);
			tfTxt.setText("");
		}
		
	}
	
}
```
Client 端现在能接通 Server 端并产生连接成功信息。

####Server 端
```
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true) {
				Socket s = ss.accept();
System.out.println("a client connected");
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String str = dis.readUTF();
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
```
Server 端为今后读取 Client 端数据预留了输入流。

##Chat0.7
摘要：服务端现在可以输出从客户端输入的消息了，但仅限1条！

####Client 端
```
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
```
Client 端的 TFListener 中将文本框内的信息以输出流的形式传递给 socket。

####Server 端
```
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true) {
				Socket s = ss.accept();
System.out.println("a client connected");
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String str = dis.readUTF();
				System.out.println(str);
				dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

```
Server 端将打印从客户端输入的消息。

##Chat0.8
摘要：修复了服务端在读取超过1条信息时崩溃的漏洞。

####Client 端
```
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;
	DataOutputStream dos = null;
	
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
				disconnected();
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
			dos = new DataOutputStream(s.getOutputStream());
System.out.println("connected!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnected() {
		try {
			dos.close();
			s.close();
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
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
}
```
Client 端 添加了 disconnected 方法，解决输入多条信息时可能显示 socket close 的问题。

####Server 端
```
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		boolean started = false;
		try {
			ServerSocket ss = new ServerSocket(8888);
			started = true;
			while(started) {
				boolean bConnected = false;
				Socket s = ss.accept();
System.out.println("a client connected");
				bConnected = true;
				DataInputStream dis = new DataInputStream(s.getInputStream());
				while(bConnected) {
					String str = dis.readUTF();
					System.out.println(str);					
				}
				dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
```
Server 端通过引入布尔类型和 while 循环来解决多条数据流输入会产生阻塞的问题。

##Chat0.9
摘要：解决了 Chat0.8 中窗口关闭产生的错误信息。

####Client 端未修改。

####Server 端
```
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		boolean started = false;
		ServerSocket ss = null;
		Socket s = null;
		DataInputStream dis = null;
		
		try {
			ss = new ServerSocket(8888);			
		} catch (BindException e) {
			System.out.print("端口使用中……");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			started = true;
			while(started) {
				boolean bConnected = false;
				s = ss.accept();
System.out.println("a client connected");
				bConnected = true;
				dis = new DataInputStream(s.getInputStream());
				while(bConnected) {
					String str = dis.readUTF();
					System.out.println(str);					
				}
				//dis.close();
			}
		} catch (EOFException e) {
			System.out.println("Client closed!");
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
```
优化端口和流的声明位置和关闭时机，单独解决端口占用的问题。

##Chat1.0
摘要：支持客户端多窗口同时运行。

####Client 端未修改

####Server 端
```
import java.io.*;
import java.net.*;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;
	
	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch (BindException e) {
			System.out.print("端口使用中……");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s);
System.out.println("a client connected");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Client implements Runnable {
		private Socket s;
		private DataInputStream dis = null;
		private boolean bConnected = false;
		
		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				bConnected = true;	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF();
					System.out.println(str);					
				}
				//dis.close();
			} catch (EOFException e) {
				System.out.println("Client closed!");
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
```
Server 端引入多线程解决窗口多开的信息获取问题。