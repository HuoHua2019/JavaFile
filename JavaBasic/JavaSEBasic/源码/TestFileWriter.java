import java.io.*;

public class TestFileWriter {

	public static void main(String[] args) {
		FileWriter wr = null;
		try {
			wr = new FileWriter("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/write/unicode.dat");
			for (int i = 0; i < 65535; i++) {
				wr.write((char)i);
			}
		} catch (FileNotFoundException e1) {
			System.out.println("δ�ҵ��ļ�");
			System.exit(-1);
		} catch (IOException e2) {
			System.out.println("д��ʧ��");
			System.exit(-1);
		}

		System.out.println("д��ɹ�");
	}
}
