#Java learning_Day2(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 标识符
- 关键字
- Java基本数据类型

###标识符
凡是自己起的名字都算标识符

标识符命名规则：
1. 由字母、下划线、美元符号或数字组成。
2. 以字母、下划线、美元符号开头。
3. 大小写敏感，长度无限制。
4. 不能与Java语言的关键字重名。

![](https://i.imgur.com/ba1qgA5.png)

###关键字
- 赋以特定的含义，用作专门的用途的字符串。
  大多数编辑器会将关键字用特殊方式标出(如高亮)。
- 所有Java关键字都是小写英文。
- goto和const虽然从未使用，但也作为Java关键字保留。

*Java部分常用关键字*
![](https://i.imgur.com/zu82y3a.png)

##Java常量
Java的常量用字符串表示，区分为不同的数据类型。
![](https://i.imgur.com/ezKDQfO.png)

***注意区分字符常量 `''` 和字符串常量 `""` ***
***另外，常量还可以指那些被 `final` 修饰的，不可变的变量***
##Java变量
Java变量是程序中最基本的存储单元，要素包括变量名，变量类型和作用域。
每一个变量都属于特定的数据类型，在使用前必须对其声明，格式如下：
`type varName [=value]`

![](https://i.imgur.com/nuIdf8b.png)

***注意：上图中语句 `double d1, d2, d3 = 0.123` 只对d3赋了初值0.123***

**从本质上讲，变量其实就是内存中的一小块区域，使用变量名来访问这块区域**

![](https://i.imgur.com/qT6DXh7.png)

###Java变量的分类
- 按被声明的的位置划分：
  #####1. 局部变量：方法(包括方法的参数)或语句块内部定义的变量。
  #####2. 成员变量：方法外部、类的内部定义的变量。
  *注意：类外面不能有变量的声明*

![](https://i.imgur.com/LDThahn.png)

- 按所属的数据类型划分：
  #####1. 基本数据类型变量(4类8种)
  - 逻辑型-boolean
  - 文本型-char
  - 整数型-byte,short,int,long
  - 浮点数型-float,double

  #####2. 引用数据类型变量

![](https://i.imgur.com/VbhvVdT.png)

###基本数据类型的转换
- boolean类型不可以转换为其他的数据类型。
- 整形，字符型，浮点型的数据在混合运算中相互转换，遵循以下原则：
  - 容量小的类型自动转换为容量大的数据类型，容量从小到大排序为：
    - byte,short,char < int < long <float < double
    - byte,short,char之间不会相互转换，三者在计算是首先转换为int类型
  - 容量大的数据类型准换为容量小的数据类型时，要加上强制类型转换符，但可能造成精度降低或溢出。
  - 多种类型的数据混合运算是，系统首先自动的将所有数据装换为容量最大的数据类型，然后再进行计算。
  - 实数常量(如： `1.2` )默认为double。
  - 整数常量(r如： `123` )默认为int。

以下面的代码为例：
```
public class Convert {
	public static void main(String[] args) {
		int i1 = 123;
		int i2 = 456;
		double d1 = (i2+i2)*1.2;  //系统将自动转换为double型进行运算
		float f1 = (float) ((i1+i2)*1.2)  //因为系统会自动转换为double型进行运算，
										  //所以需要加强制转换符(float)
		byte b1 = 1;
		byte b2 = 2;
		byte b3 = (byte)(b1+b2);  //因为系统会自动转换为int型进行运算，
								  //所以需要加强制转换符(byte)
								  
		byte b4 = 67;
		byte b5 = 89;
		byte b6 = (byte)(b4+b5);  //相加后为156，超过byte的上限128。但不会出错，
								  //结果为-100

		double d2 = 1e200;  //科学计数法，等于浮点数1.0*10^200，在cmd中输出1.0E200
		float f2 = (float)d2;  //会产生溢出
		
		float f3 = 1.23f  //浮点数默认为double，所以此处必须加f
		long l1 = 123;
		long l2 = 1234567890000L;  //整数默认为int， 此处超出int型范围，
								   //所以必须加L或l
		float f = l1+l2+l3;  //float容量比long大，系统将自动转换为float型进行计算
		long l = (long)f;  //强制转换会舍去小数部分(不是四舍五入)
		
	}
}
```
对于上述代码中byte b6为什么结果为-100，详见：[Day2_关于byte的溢出问题](file:///F:/github/JavaFile/JavaBasic/JavaSEBasic/Day2_关于byte的溢出问题.md)

通过上面的学习，基本数据类型的转换就基本掌握了，下面是实战练习
```
public class method {  //指出编译时可能产生编译错误或计算溢出的部分
	public static void main(String[] args) {
        int i = 1, j;
        float f1 = 0.1;  float f2 = 123;
		long l1 = 12345678, l2 = 888888888888;
		double d1 = 2e20, d2 = 124;
		byte b1 = 1, b2 = 2, b3 = 129;
		j = j+10;
		i = i/10;
		i = i*0.1;
		char c1 = 'a', c2 = 125;
		byte b = b1-b2;
		char c = c1+c2-1;
		float f3 = f1+f2;
		float f4 = f1+f2*0.1;
		double d = d1*i+j;
		float f = (float)(d1*5+d2);
    }
}
```

个人的解析

```
public class method {
	public static void main(String[] args) {
		int i = 1, j;  //正确
		float f1 = 0.1;  float f2 = 123;  //前一句由于0.1默认为double，改为float f1 = 0.1f或者float f1 = (float)0.1;
										  //后一句由于123默认为int，自动转换为float，故正确
		long l1 = 12345678, l2 = 888888888888;  //前半句正确，后半句由于默认为int,产生溢出，改为l2 = 888888888888L
		double d1 = 2e20, d2 = 124;  //正确,前半句2e20默认为double，后半句124默认为int，自动转换为double
		byte b1 = 1, b2 = 2, b3 = 129;  //最后半句错误，byte的范围为-128~127,默认129为int，转换为byte提示精度损失，改为b3 = (byte)129
		j = j+10;  //错误，j未赋初值
		i = i/10;  //正确，舍去计算结果的小数位，最终输出为0
		i = i*0.1;  //错误，i*0.1自动转换为float，产生精度损失，改为i = (int)(i*0.1)
		char c1 = 'a', c2 = 125;  //前半句正确，后半句125默认为int，无法与char自动转换，改为c2 = (char)125
		byte b = b1-b2;  //错误，b1-b2自动转换为int运算，转换为byte产生精度损失，改为b = (byte)(b1-b2)
		char c = c1+c2-1;  //错误，char与int型无法相互转换，改为char c = (char)(c1+c2-1)
		float f3 = f1+f2;  //正确
		float f4 = f1+f2*0.1;  //错误，f1+f2*0.1自动转换为double运算，产生精度损失，改为float f4 = (float)(f1+f2*0.1)
		double d = d1*i+j;  //正确(假设j有初值)
		float f = (float)(d1*5+d2);  //正确
	}
}
```