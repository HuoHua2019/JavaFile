#Java learning_Day11
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 网络基础
- 网络通信协议及接口
- IP 协议
- TCP 协议 和 UDP 协议
- Socket

##网络基础
- 什么是计算机网络：把分布在不同地理区域的计算机与专门的外部设备用通信线路互连成一个规模大、功能强的网络系统，从而是众多的计算机可以方便地互相传递信息，共享硬件、软件、数据信息等资源。
- 计算机网络的主要功能
  - 资源共享
  - 信息传输与集中处理
  - 均衡负荷与分布处理
  - 综合信息服务（www/综合业务数字网络 ISDN）

##网络通信协议及接口
- 什么是网络通信协议：计算机网络中实现通信必须有一些约定即通信协议，对速率、传输代码、代码结构、传输控制步骤、出错控制等制定标准。
- 网络通信接口：为了使两个结点之间能进行对话，必须在它们之间建立通信工具（即接口），使彼此之间能进行信息交换。接口包括两部分：
  - 硬件装置：实现结点之间的信息传送
  - 软件装置：规定双方进行通信的约定协议

###通信协议分层的思想
- 为什么要分层
由于结点之间联系很复杂，在制定协议时，把复杂成分分解成一些简单的成分，再将它们复合起来。最常用的复合方式是层次关系，即同层之间可以通信、上一层可以调用下一层，而与再下一层不发生关系。各层互不影响，利于系统的开发和扩展。
- 通信协议的分层规定
把用户应用程序作为最高层，把物理通信线路作为最底层，将其间的协议处理分为若干层，规定每层处理的任务，也规定每层的接口标准。

![PotPlayerMini64_SV4GIEF8u8.png](https://i.loli.net/2020/02/28/noLXbHYxZ8MEVgq.png)

###参考模型

![PotPlayerMini64_fu8OL74IxQ.png](https://i.loli.net/2020/02/28/9v3WxH8rcjen521.png)

###数据封装

![PotPlayerMini64_JtGYCoiRha.png](https://i.loli.net/2020/02/28/4gh9HA8OMiNqJKz.png)

###数据拆分

![PotPlayerMini64_KP4r4Rwycu.png](https://i.loli.net/2020/02/28/FzhRtpHJcTEK9r3.png)

##IP 协议
- IP(Internet Protocol)协议是网际层的主要协议，支持网间互连的数据报通信。它提供主要功能有：
  - 无连接数据报传送
  - 数据报路由选择和差错控制

![PotPlayerMini64_MHW9fGGSL2.png](https://i.loli.net/2020/02/28/LdJNeZUXbl7vSIi.png)

##TCP 协议 和 UDP 协议
- TCP(transmission control protocol)
是专门设计用于在不可靠的因特网上提供可靠的、端到端的字节流通信的协议。它是一种面向连接的协议。TCP 连接是字节流而非报文流。
- UDP(user data protocol)
UDP 向应用程序提供了一种发送到封装的原始 IP 数据报的方法、并且发送时无需建立连接。是一种不可靠的连接。

##Socket
- 两个 Java 应用程序可通过一个双向的网络通信连接实现数据交换，这个双向链路的一段称为 Socket。
- Socket 通常用来实现 client-server 连接。
- java.net 包中定义的两个类 Socket 和 ServerSocket，分别用来实现双向连接的 client 和 server 端。
- 建立连接时所需的寻址信息为远程计算机的 IP 地址和端口号（Port number）

###TCP Socket 通信模型

![PotPlayerMini64_JqhxSxafy3.png](https://i.loli.net/2020/02/28/lHirFwf6p8XukZP.png)

示例
Server 端
```
import java.net.*;
import java.io.*;

public class TCPServer {

	public static void main(String[] args) throws Exception{
		ServerSocket ss = new ServerSocket(6666);
		while (true) {
			Socket s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			System.out.println(dis.readUTF());
			dis.close();
			s.close();
		}
	}
}
```
Client 端
```
import java.net.*;
import java.io.*;

public class TCPClient {

	public static void main(String[] args) throws Exception {
		Socket s = new Socket("127.0.0.1", 6666);
		OutputStream os = new DataOutputStream(s.getOutputStream());
		DataOutputStream dos = new DataOutputStream(os);
		Thread.sleep(3000);
		dos.writeUTF("hello server!");
		dos.flush();
		dos.close();
		s.close();
	}
}

```

###UDP
- 不可靠
- 效率高
- 数据报/非连接
  - 音频
  - 视频
  - ...

示例
Sever 端
```
import java.net.*;
import java.io.*;

	public class TestUDPServer {

	public static void main(String[] args) throws Exception{
		byte[] b = new byte[1024];
		DatagramPacket dp = new DatagramPacket(b, b.length);
		DatagramSocket ds = new DatagramSocket(4567);

		while (true) {
			ds.receive(dp);
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			DataInputStream dis = new DataInputStream(bais);
			System.out.println(dis.readLong());
		}
	}
}
```
Client 端
```
import java.io.*;
import java.net.*;

public class TestUDPClient {

	public static void main(String[] args) throws Exception{
		Long l = 10000L;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeLong(l);

		byte[] b = baos.toByteArray();
		System.out.println(b.length);

		DatagramPacket dp = new DatagramPacket(b, b.length, new InetSocketAddress("127.0.0.1", 4567));
		DatagramSocket ds = new DatagramSocket(7777);
		ds.send(dp);
		ds.close();
	}
}

```