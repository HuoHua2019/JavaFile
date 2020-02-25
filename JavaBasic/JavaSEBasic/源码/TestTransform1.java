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
