import java.io.*;

public class TestTransform1 {

	public static void main(String[] args) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(
				new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/streamreader/trans.txt"));
			osw.write("microsoft intel apple google");
			System.out.println(osw.getEncoding());

			osw = new OutputStreamWriter(
				new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/streamreader/trans.txt", true),
				"ISO8859_1"); 
			osw.write("microsoft intel apple google");
			System.out.println(osw.getEncoding());
			osw.close();
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
