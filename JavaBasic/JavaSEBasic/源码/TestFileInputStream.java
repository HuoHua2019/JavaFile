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
