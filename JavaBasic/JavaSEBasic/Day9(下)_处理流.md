#Java learning_Day9(下)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 处理流类型

##处理流类型
| 类型 | 字符流 |字节流
|:--:| :-----: | :-----
| Buffering | BufferedReader, BufferedWriter| BufferedInputStream, BufferedOutputStream
| Filtering | FilterReader, FilterWriter | FilterInputStream, FilterOutputStream
| Converting between bytes and character| InputStreamReader, OutputStreamWriter | - 
| Object Serialization | - | ObjectInputStream, ObjectOutputStream
| Data conversion | - | DatatInputStream, DataOutputStream
| Counting | LineNumberReader | LineNumberInputStream
| Peeking ahead | PushbackReader | PushbackInputStream 
| Printing | PrintWriter | PrintStream 

###缓冲流
- 缓冲流要“套接”在相应的节点流之上，对读写的数据提供了缓冲的功能，提高了读写的效率，同时增加了一些新的方法。
- JDK 提供了四种缓存刘，其常用的构造方法为：

![PotPlayerMini64_FLutqVoEWv.png](https://i.loli.net/2020/02/25/oV1gHXQDYSrGTzp.png)

  - 缓冲输入流支持其父类的 mark 和 reset 方法。
  - BufferedReader 提供了 readLine 方法用于读取一行字符串（以 \r 或 \n 分隔）。
  - BufferedWriter提供了 newLine 用于写入一个分行隔符。
  - 对于输出的缓冲流，写出的数据会先在内存中缓存，使用 flush 方法将会使内存中的数据立刻写出。

实例(以 BufferedReader 和 BufferedWriter 为例)
```
import java.io.*;

public class TestBufferedStream {

	public static void main(String[] args) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/buffer/buffer.txt"));
			BufferedReader br = new BufferedReader(new FileReader("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/buffer/buffer.txt"));
			String input = null;
			for (int i = 0; i < 100; i++) {
				input = String.valueOf(Math.random());
				bw.write(input);
				bw.newLine();
			}
			bw.flush();
			while ((input = br.readLine()) != null) {
				System.out.println(input);
			}
			bw.close();
			br.close();
		} catch (FileNotFoundException e1) {
			System.out.println("未找到文件");
			System.exit(-1);
		} catch (IOException e2) {
			System.out.println("写入失败");
			System.exit(-1);
		}
		
		System.out.println("写入成功");
	}
}

```

###转换流
- InputStreamReader 和 OutputStreamWriter 用于字节数据到字符数据之间的转换。
- InputStreamReader 需要和 InputStream “套接”。
- OutputStreamWriter 需要和 OutputStream “套接”。
- 转换流在构造时可以指定其编码集合。

示例1：OutputStreamWriter “套接” OutputStream 
```
import java.io.*;

public class TestTransform1 {

	public static void main(String[] args) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(
				new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/streamreader/trans.txt"));
			osw.write("microsoft intel apple google");
			System.out.println(osw.getEncoding());

			osw = new OutputStreamWriter(
				new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/streamreader/trans.txt", true),
				"ISO8859_1"); 
			osw.write("microsoft intel apple google");
			System.out.println(osw.getEncoding());
			osw.close();
		} catch (FileNotFoundException e1) {
			System.out.println("未找到文件");
			System.exit(-1);
		} catch (IOException e2) {
			System.out.println("写入失败");
			System.exit(-1);
		}
			System.out.println("写入成功");
	}
}

``` 
示例2：BufferedReader “套接” InputStreamReader，
```
import java.io.*;

public class TestTransform2 {

	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);  //将控制台输入转换为字符串
		BufferedReader br = new BufferedReader(isr);		

		try {
			String input = null;
			while ((input = br.readLine()) != null) {
				if (input.equalsIgnoreCase("exit")) break;
				System.out.println(input.toUpperCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
```

###数据流
- DataInputStream 和 DateOutputStream 分别继承自 InputStream 和 OutputStream，它属于处理流，需要分别“套接”在 InputStream 和 OutputStream 类型的节点流上。
- DataInputStream 和 DateOutputStream 提供了可以存取与机器无关的 Java 原始类型数据（如：int，double 等）的方法。
- DateInputStream 和 DataOutputStream 的构造方法为：
  - DataInputStream（InputStream in）
  - DataOutputStream（OutputStream out）

示例：
```
import java.io.*;

public class TestDataStream {

	public static void main(String[] args) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeDouble(Math.random());
			dos.writeBoolean(true);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			System.out.println(bais.available());

			DataInputStream dis = new DataInputStream(bais);
			System.out.println(dis.readDouble());
			System.out.println(dis.readBoolean());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

```

###Print 流
- PrintWriter 和 PrintStream 都属于输出流，分别针对字符和字节。
- PrintWriter 和 PrintStream 提供了重载的 print 
- Println 方法用于多种数据类型的输出。
- PrintWriter 和 PrintStream 的输出操作不会抛出异常，用户通过检测错误状态获取错误信息。
- PrintWriter 和 PrintStream 有自动 flush 功能。

示例1
```
public class TestPrintStream1 {

	public static void main(String[] args) {
		PrintStream ps = null;
		try {
			FileOutputStream fos = new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/log/log.txt");
			ps = new PrintStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (ps != null)  System.setOut(ps);  //将命令行输出转移到PrintStream流中

		for (char i = 0, width = 0; i < 65535;i++, width++ ) {
			System.out.print(i + " ");
			if (width == 100) {
				System.out.println();
				width = 0;
			}
		}
	}
}
```

示例2
```
import java.io.*;

public class TestPrintStream2 {

	public static void main(String[] args) {
		String filename = new String(args[0]);

		if (filename != null) {
			list(filename, System.out);
		}
	}

	public static void list(String f, PrintStream fs) {
		try {
			BufferedReader br = new BufferedReader(
				new FileReader(f));  //文件的相对路径
			String s = null;
			while ((s = br.readLine()) != null) {
				fs.println(s);
			}
            br.close();
		} catch (IOException e) {
			System.out.println("无法读取文件");
		}
	}
}
```

示例3
```
import java.io.*;
import java.util.*;

public class TestPrintStream3 {

	public static void main(String[] args) {
		String s = null;
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

		try {
			FileWriter fw = new FileWriter(
				"F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/log/log.txt");
			PrintWriter log = new PrintWriter(fw);

			while ((s = br.readLine()) != null) {
				if (s.equalsIgnoreCase("exit"))  break;
			System.out.println(s.toUpperCase());
			log.println(s.toUpperCase());
			log.println("-------");
			log.flush();
			}

			log.println("===" + new Date() + "===");  //打印当前日期
			log.flush();
			log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}

```
###Object 流
直接将 Object 写入或读出

示例
```
import java.io.*;

public class TestObjectIO {

	public static void main(String[] args) {
		T t = new T();
		t.i = 2;  //注意这里修改了i的值
		try {
			FileOutputStream fos = new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/log/testobject.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(t);
			oos.flush();
			oos.close();

			FileInputStream fis = new FileInputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/log/testobject.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			T tRead = (T)ois.readObject();
			System.out.println(tRead.i + " " + tRead.j + " " + tRead.c + " " + tRead.d + " " + tRead.t);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}
}

class T implements Serializable {  //Serializable 是用于序列化的标记性接口
	int i = 1;
	long j = 100000000000L;
	char c = 'c';
	double d = 3.1415;
	transient int t = 5; //transient表明该变量无法被序列化
}
```
*另外，externalizable接口可以自行控制序列化*