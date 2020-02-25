import java.io.*;

public class TestBufferedStream {

	public static void main(String[] args) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/buffer/buffer.txt"));
			BufferedReader br = new BufferedReader(new FileReader("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/buffer/buffer.txt"));
			String input = null;
			for (int i = 0; i < 100; i++) {
				input = String.valueOf(Math.random());
				bw.write(input);
				bw.newLine();
			}
			bw.flush();
			while ((input = br.readLine()) != null) {
				System.out.println(input);
			}
			bw.close();
			br.close();
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
