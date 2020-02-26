import java.io.*;

public class TestObjectIO {

	public static void main(String[] args) {
		T t = new T();
		t.i = 2;  //ע�������޸���i��ֵ
		try {
			FileOutputStream fos = new FileOutputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/log/testobject.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(t);
			oos.flush();
			oos.close();

			FileInputStream fis = new FileInputStream("F:/github/JavaFile/JavaBasic/JavaSEBasic/Դ��/log/testobject.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			T tRead = (T)ois.readObject();
			System.out.println(tRead.i + " " + tRead.j + " " + tRead.c + " " + tRead.d + " " + tRead.t);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}
}

class T implements Serializable {
	int i = 1;
	long j = 100000000000L;
	char c = 'c';
	double d = 3.1415;
	transient int t = 5; //transient�����ñ����޷������л�
}