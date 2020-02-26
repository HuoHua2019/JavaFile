import java.io.*;
import java.util.*;

public class TestPrintStream3 {

	public static void main(String[] args) {
		String s = null;
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

		try {
			FileWriter fw = new FileWriter(
				"F:/github/JavaFile/JavaBasic/JavaSEBasic/源码/log/log.txt");
			PrintWriter log = new PrintWriter(fw);

			while ((s = br.readLine()) != null) {
				if (s.equalsIgnoreCase("exit"))  break;
			System.out.println(s.toUpperCase());
			log.println(s.toUpperCase());
			log.println("-------");
			log.flush();
			}

			log.println("===" + new Date() + "===");  //打印当前日期
			log.flush();
			log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
