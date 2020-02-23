#Java learning_Day6(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 数组概述
- 一维数组的声明
- 二维数组
- 数组的拷贝

##数组概述
- 数组可以看成是多个相同类型数据组合，对这些数据的统一管理。
- 数组变量属于引用类型，数组也可以看成是对象，数组中的每个元素相当于该对象的成员变量。
- 数组中的元素可以是任何数据类型，包括基本类型和引用类型。

##一维数组的声明
一位数组的声明方式：
```
type var[];
//或
type[] var;
```
例如：
```
int a1[];
int[] a2;
double b[];
Person[] p1;
String s1[];
```
Java 语言中声明数组时不能指定其长度(数组中元素的个数)，例如
```
int a[5];  //非法
```
###数组对象的创建
Java 中使用关键字 new 创建数组对象，格式为
```
数组名 = new 数组元素的类型 [数组元素的个数]
```
例如：
```
public class Test {
    public static void main(String[] args) {
        int[] s;
        s = new int[5];
        for (int i = 0; i < 5; i++) {
            s[i] = 2*i+1;
        }
    }
}
```

***注意：元素为引用数据类型的数组中的每一个元素都需要实例化。***

###数组初始化
动态初始化
数组定义与为数组元素分配空间和赋值的操作分开进行，例如：
```
public class Test {

    public static void main(String[] args) {
        int a[];
        a = new int[3];
        a[0] = 3;
        a[1] = 9;
        a[2] = 8;

        Date days[];
        days = new Date[3];
        days[0] = new Date(1, 4, 2004);
        days[1] = new Date(2, 4, 2004);
        days[2] = new Date(3, 4, 2004);
    }
}

class Date {
    int year, month, day;

    Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
```
###数组元素的默认初始化
数组是引用类型，它的元素相当于类的成员变量，因此数组分配空间后，每个元素也被按照成员变量的规则给隐式初始化。如：
```
public class  ArrayDefaultInitialization{

	public static void main(String[] args) {
	int a[] = new int[5];
	Date[] days = new Date[3];
	System.out.println(a[3]);
	System.out.println(days[2]);
	}
}

class Date {
	int year, month, day;
	
	Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
}
```
输出
```
0
null
```
###数组元素的引用
- 定义并用运算符 **new** 为之分配空间后，才可以引用数组中的每个元素，数组元素的引用方式为：
  - arrayName[index]
    - index 为数组元素下标，可以是整型常量或整型表达式。如：
      a[3], b[i], c[6*i]
    - 数组元素下标从0开始；长度为n的数组的合法下标取值范围为 0~n-1。

- 每个数组都有一个属性 length 指明它的长度，例如：
    a.length 的值为数组 a 的长度(元素个数)。

##二维数组
- 二维数组可以看成以数组为元素的数组。例如：
```
int a[][] = {{1, 2}, {3, 4, 5, 6}, {7, 8, 9}};
```
- Java中多维数组的声明和初始化应按从高维到低维的顺序进行，例如：

```
int a[][] = new int[3][];
a[0] = new int[2];
a[1] = new int[4];
a[2] = new int[3];
int t1[][] = new int[][4];  非法
```
###二维数组初始化
- 静态初始化：

```
int intA[][] = {{1, 2}, {2, 3}, {3, 4, 5}};
int intB[3][2] = {{1, 2}, {2, 3}, {4, 5}};//非法

```
- 动态初始化：

```
int a[][] = new int[3][5];
int b[][] = new int[3][];
b[0] = new int[2];
b[1] = new int[3];
b[2] = new int[5];
```
##数组的拷贝
- 使用 java.lang.System 类的静态方法

```
public static void arraycopy (Object src, int srcPos, Object dest, int destPos, int length)
```
- 可以用于数组 src 从第 srcPos 项元素开始的 length 个元素拷贝到目标数组从 destPos项开始的 length 个位置。
- 如果源数据数目超过目标数组边界会抛出 IndexOutOfBoundsException 异常。