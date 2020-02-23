##问题1描述
编写一个方法，返回一个double类型二维数组，数组中的元素通过解析字符串参数获得。
###代码实现：
```
public class ArrayParser {
	
	public static void main(String[] args) {
		double[][] d;
		String s = "1,2;3,4,5;6,7,8";
		String[] sFirst = s.split(";");
		d = new double[sFirst.length][];

		for (int i = 0; i < sFirst.length; i++) {
			String[] sSecond = sFirst[i].split(",");
			d[i] = new double[sSecond.length];

			for (int j = 0; j < sSecond.length; j++) {
				d[i][j] = Double.parseDouble(sSecond[j]);
			}
		}
		
		for (int i = 0; i < d.length; i++) {

			for (int j = 0; j < d[i].length; j++) {
				System.out.println(d[i][j] + " ");				
			}

			System.out.println();
		}
	}
}
```
##问题2描述
编写一个程序，在命令行中以树状结构展现特定的文件夹及其子文件（夹）。
###代码实现
```
import java.io.*;

public class FindFile {

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

```