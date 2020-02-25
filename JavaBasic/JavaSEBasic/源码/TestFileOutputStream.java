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
