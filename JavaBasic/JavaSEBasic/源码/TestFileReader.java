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
