#Java learning_Day7(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 常用类
- 枚举类型

##常用类
###String 类
- java.lang.String 类代表**不可变**的字符序列。
- String 类的常见构造方法：
  - String(String original)
    - 创建一个 String 对象为 original 的拷贝。
  - String(char[] value)
    - 用一个字符数组创建一个 String 对象。
  - String(char[] value, int offset, int count)
    - 用一个字符数组从 offset 项开始的 count 个字符序列创建一个 String 对象。

####String 类常用方法

![PotPlayerMini64_DwANaSJvu1.png](https://i.loli.net/2020/02/23/EouApGcqNy5iMmX.png)

![PotPlayerMini64_tl0LlKLCZN.png](https://i.loli.net/2020/02/23/8aQEToHKCiekpcF.png)

- 静态重载方法
  - public static String valueOf(...) 可以将基本类型数据转换为字符串；
    - 例如：
    - public static String valueOf(double d)
    - public static String vauleOf(int i)

  - 方法 public String[] split(String regex) 可以将一个字符串按照指定的分隔符分隔，返回分隔后的字符串数组。

###StringBuffer 类
- java.lang.StringBuffer 代表可变的字符序列。
- StringBuffer 和 String 类似，但StringBuffer 可以对其子串进行改变。
- StringBuffer 类的常见构造方法：
  - StringBuffer()
    - 创建一个不包含字符序列的“空”的 StringBuffer 对象。
  - StringBuffer(String str)
    - 创建一个 StringBuffer 对象，包含与 String 对象 str 相同的字符序列。

####StringBuffer 常用方法
- 重载方法 public StringBuffer append(…) 可以为该 StringBuffer 对象添加字符序列，返回添加后的该 StringBuffer 对象引用

![PotPlayerMini64_vmc7USacdQ.png](https://i.loli.net/2020/02/23/1CosYFmvRuwenS9.png)

- 重载方法 public StringBuffer insert(…) 可以为该 stringBuffer 对象在指定位置插入字符序列， 返回修改后的该 stringBuffer 对象引用

![PotPlayerMini64_oFB66tpDBP.png](https://i.loli.net/2020/02/23/xTuYmC5cnNPOWeg.png)

- 方法 public StringBuffer delete(int start, int end) 可以删除从 start 开始到 end-1 为止的一段字符序列，返回修改后的该 StringBuffer 对象引用。

- 和 String 类含义类似的方法

![PotPlayerMini64_htAbtDhJkK.png](https://i.loli.net/2020/02/23/lXaAFf4eW7ovVQT.png)

- 方法 public StringBuffer reverse() 用于将字符序列逆序，返回修改后的该 StringBuffer 对象引用。

###基本数据类型包装类
- 包装类（如：Integer，Double 等）这些类封装了一个相应的基本数据类型数值，并为其提供了一系列操作。

###Math 类
java.lang.Math 提供了一系列静态方法用于科学计算；其方法的参数和返回值类型一般为 double 型。

![PotPlayerMini64_5nK17cDmnt.png](https://i.loli.net/2020/02/23/kYVGhQvjHT79xcN.png)

###File 类
- java.io.File 类代表系统文件名（路径和文件名）。
- File 类的常见构造方法：
  - public File(String pathname) 以 pathname 为路径创建 File 对象，如果 pathname 是相对路径，则默认的当前路径在系统的属性 user.dir 中存储。
  - public File(Sring parent, String child) 以 parent 为父路径， child 为子路径创建 File 对象。
- File 的静态属性 String separator 存储了当前系统的路径分隔符。

示例
```
package file1.file2;
import java.io.*;

public class  FileTest{

	public static void main(String[] args) {
		String separator = File.separator;
		String filename = "myfile.txt";
		String directory = "mydir1" + separator + "mydir2";
		//String directory = "mydir1/mydir2";  linux
		//String directory = "mydir1\\mydir2";  windows		
		File file = new File(directory ,filename);
		if (file.exists()) {
			System.out.println("文件名" + file.getAbsolutePath());
			System.out.println("文件大小" + file.length());
		} else {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

```
***注意：由于在在开头引入包，因此 file.getParentFile() 操作得到的文件夹应该是包的上一层文件夹而不是 java 文件的父文件夹***

##枚举类型
java.lang.Enum枚举类型
- 只能够取特定值的一个
- 使用 enum 关键字
- 是 java.lang.Enum 类型

实例：
```
public class TestEnum {
	public enum myColor {red, green, yellow};

	public static void main(String[] args) {
		myColor m = myColor.red;
		switch (m) {
		    case red:
				System.out.println("red");
				break;
			case green:
				System.out.println("green");
				break;
			case yellow:
				System.out.println("yellow");
		    default:
				System.out.println("");
		}
	}
}

```