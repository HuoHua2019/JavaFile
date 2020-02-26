import java.io.*;

public class TestPrintStream1 {

	public static void main(String[] args) {
		PrintStream ps = null;
		try {
			FileOutputStream fos = new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/log/log.txt");
			ps = new PrintStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (ps != null)  System.setOut(ps);  //�����������ת�Ƶ�PrintStream����

		for (char i = 0, width = 0; i < 65535;i++, width++ ) {
			System.out.print(i + " ");
			if (width == 100) {
				System.out.println();
				width = 0;
			}
		}
	}
}
