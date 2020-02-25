#Java learning_Day8(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- Java 流式输入/输出原理
- 节点流类型

##Java 流式输入/输出原理
在 Java 程序中，对于数据的输入/输出操作以“流”（stream）方式进行；JDK 提供了各种各样的“流”类，用以获取不同种类的数据；程序中通过标准的方法输入或输出数据。

![PotPlayerMini64_DVxGM5jkOq.png](https://i.loli.net/2020/02/25/ZfLa6WmIovptUA2.png)

###输入/输出的分类
- java.io 包中定义了多个流类型（类或抽象类）来实现输入/输出功能：可以从不同的角度对其进行分类：
  - 按数据流的方向不同可以分为输入流和输出流。
  - 按处理数据单位不同可以分为字节流和字符流。
  - 按照功能不同可以分为节点流和处理流。

- JDK所提供的所有流类型位于包 java.io 内部，分别继承自一下四种抽象类型。

| - | 字节流 |字符流
|:--:| :-----: | :-----:
| 输入流 | InputStream| Reader
| 输入流 | OutputStream |Writer

###节点流和处理流
- 节点流：可以从一份特定的数据源（节点）读写数据（如：文件，内存）

![PotPlayerMini64_sDCfWTdabh.png](https://i.loli.net/2020/02/25/aPeyVIf5hsXjOYp.png)

- 处理流是“连接”在已存在的流（节点流或处理流）之上，通过对数据的处理为程序提供更为强大的读写功能。

![PotPlayerMini64_A5y8lKo4k1.png](https://i.loli.net/2020/02/25/nYNQfgjLOuMU2V8.png)

###InputStream
继承自 InputStream 的流都是用于向程序中输入数据，且数据的单位为字节（8bit）；下图中深色为节点流，浅色为处理流。

![PotPlayerMini64_KxavQPFX1h.png](https://i.loli.net/2020/02/25/Dh5cgnO2Vi3zbLY.png)

####InputStream 的基本方法

![PotPlayerMini64_8b7uWTMHRf.png](https://i.loli.net/2020/02/25/OqtVd6gAk2rEjSm.png)

###OutputStream
继承自 OutputStream 的流都是用于向程序中输入数据，且数据的单位为字节（8bit）；下图中深色为节点流，浅色为处理流。
####OutputStream 的基本方法

![PotPlayerMini64_Mp8kU0omoN.png](https://i.loli.net/2020/02/25/cjVBtMmHg5Y1Trz.png)

###Reader
继承自 Reader 的流都是用于向程序中输入数据，且数据的单位为字符（16bit）；下图中深色为节点流，浅色为处理流。

![PotPlayerMini64_PuJQrqYJZr.png](https://i.loli.net/2020/02/25/mNbJ1wlDeFITnpg.png)

####Reader 的基本方法

![PotPlayerMini64_Y1EcS3nwq7.png](https://i.loli.net/2020/02/25/9rC8lnUbcR4YtxZ.png)

###Writer
继承自 Writer 的流都是用于向程序中输入数据，且数据的单位为字符（16bit）；下图中深色为节点流，浅色为处理流。

####Writer 的基本方法

![PotPlayerMini64_CCJDDNVKf3.png](https://i.loli.net/2020/02/25/IuxS8JdF75pGaVk.png)

##节点流类型
| 类型 | 字符流 |字节流
|:--:| :-----: | :-----:
| File（文件） | FileReader, FileWriter| FileInputStream, FileOutputStream
| Memory Array | CharArrayReader, CharArrayWriter | ByteArrayInputStream, ByteArrayOutputStream
| Memory String| StringReader, StringWriter | - 
| Pipe（管道）| PipedReader, PipedWriter | PipedInputStream, PipedOutputStream

使用字节流 FileInputStream 示例
```
import java.io.*;

public class TestFileInputStream {

	public static void main(String[] args) {
		int b = 0;
		FileInputStream in = null;
		try {
			in = new FileInputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/TestFileInputStream.java");
		} catch (FileNotFoundException e1) {
			System.out.println("文件不存在");
			System.exit(-1);
		}

		try {
			long num = 0;
			while ((b = in.read()) != -1) {
				System.out.print((char)b);
				num++;
			}
			in.close();
			System.out.println();
			System.out.println("共读取了" + num + "个字节");
		} catch (IOException e2) {
			System.out.println("读取文件错误");
			System.exit(-1);
		}
	}
}


```
但是运行时发现中文无法正确显示，这是因为字节流每次读取1个字节，但是一个汉字占2个字节。

使用字节流 FileOutputStream 示例
```
import java.io.*;

public class TestFileOutputStream {

	public static void main(String[] args) {
		int b = 0;
		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/TestFileOutputStream.java");
			out = new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/output/copy.java");
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e1) {
			System.out.println("未找到文件");
			System.exit(-1);
		} catch (IOException e2) {
			System.out.println("复制文件失败");
			System.exit(-1);
		}

		System.out.println("复制成功");
	}
}

```

在字节流 InputStream 中汉字无法显示的问题，可以用字符流 FileReader 来解决
```
import java.io.*;
public class TestFileReader {

	public static void main(String[] args) {
		int b = 0;
		FileReader fr = null;

		try {
			fr = new FileReader("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/TestFileReader.java");
			while ((b = fr.read()) != -1) {
				System.out.print((char)b);
			}
			fr.close();
		} catch (FileNotFoundException e1) {
			System.out.println("未找到文件");
			System.exit(-1);
		} catch (IOException e2) {
			System.out.println("读取文件错误");
		}
	}
}
```

字符流 FileWriter
```
import java.io.*;

public class TestFileWriter {

	public static void main(String[] args) {
		FileWriter wr = null;
		try {
			wr = new FileWriter("F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/write/unicode.dat");
			for (int i = 0; i < 65535; i++) {
				wr.write((char)i);
			}
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