import java.io.*;

public class TestFileOutputStream {

	public static void main(String[] args) {
		int b = 0;
		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/TestFileOutputStream.java");
			out = new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/output/copy.java");
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e1) {
			System.out.println("δ�ҵ��ļ�");
			System.exit(-1);
		} catch (IOException e2) {
			System.out.println("�����ļ�ʧ��");
			System.exit(-1);
		}

		System.out.println("���Ƴɹ�");
	}
}
