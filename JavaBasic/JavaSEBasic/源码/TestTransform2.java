import java.io.*;

public class TestTransform2 {

	public static void main(String[] args) {
		InputStreamReader isr = new InputStreamReader(System.in);  //������̨����ת��Ϊ�ַ���
		BufferedReader br = new BufferedReader(isr);		

		try {
			String input = null;
			while ((input = br.readLine()) != null) {
				if (input.equalsIgnoreCase("exit")) break;
				System.out.println(input.toUpperCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
