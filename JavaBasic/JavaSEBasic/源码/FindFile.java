import java.io.*;

public class FindFile {
//��дһ��������������������״�ṹչ���ض����ļ��м������ļ����У���

	public static void main(String[] args) {
		File file = new File("F:/github/JavaFile/JavaBasic");
		System.out.println(file.getName());
		int level = 0;
		getChild(file, level);
	}

	public static void getChild(File file, int level) {
		String space = "";

		for (int i = 0; i <= level; i++) {
			space += "	";
		}

		File[] childfile = file.listFiles();
		for (int i = 0; i < childfile.length; i++) {
			System.out.println(space + childfile[i].getName());
			if (childfile[i].isDirectory()) {
				getChild(childfile[i], level+1);
			}
		}
	}
}
