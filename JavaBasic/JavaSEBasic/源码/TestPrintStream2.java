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
				new FileReader(f));  //�ļ������·��
			String s = null;
			while ((s = br.readLine()) != null) {
				fs.println(s);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("�޷���ȡ�ļ�");
		}
	}
}
