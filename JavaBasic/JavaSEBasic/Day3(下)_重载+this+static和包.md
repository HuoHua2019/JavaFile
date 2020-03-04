#Java learning_Day3(下)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 方法的重载
- 关键字this
- 关键字static
- package和import语句


##方法的重载
方法的**重载**是指一个类中可以定义有相同的名字，但参数不同的多个方法。调用时，会根据不同的参数表选择对应的方法。
比较下面两段代码
```
void max(int a, int b) {
	System.out.println(a > b ? a : b);
}
```
```
void max(float a, float b) {
	System.out.println(a > b ? a : b);		
}

```
上面两个方法的返回类型和方法名相同，参数类型不同，构成重载。
当编译器执行完整的代码
```
public class TestOverload {
	void max(int a, int b) {
		System.out.println(a > b ? a : b);
	}

	void max(float a, float b) {
		System.out.println(a > b ? a : b);		
	}


	public static void main(String[] args) {
		TestOverload t = new TestOverload();
		t.max(3, 4);
	}
}
```
此时编译器可以通过核对方法传入的参数类型得出代码段想要执行的方法。
以上例子可以说明，对于同一类中方法名、返回类型相同的方法，编译器可以通过比较形式参数的数据类型，形式参数的数量，甚至形式参数的排列顺序来得知需要调用的方法。

###构造方法的重载
构造方法也可以构成重载
```
class Person {
	
	Person() {
		id = 0;
		age = 20;
	}
	
	Person(int _id) {
		id = _id;
		age = 23;
	}
	
	Person(int _id, int _age) {
		id = _id;
		age = _age;
	}
```
由于传入的参数不同，对象Person在初始化时将根据传入的参数进行相应的初始化。

##关键字 this
- 在类的方法定义中使用的this关键字代表使用该方法的对象的引用。
- 当必须指出当前使用方法的对象是谁时要使用this。
- 有时使用this可以处理方法中成员变量和参数重名的情况。
- this可以看做是一个变量，它的值是当前对象的引用。

观察下面代码
```
public class Leaf {	
	int i = 0;

	Leaf(int i) {
		this.i = i;  //调用类Leaf的i属性，使之赋值为形参i的值
	}

	Leaf increment() {
		i++;
		return this;  //返回类Leaf本身的引用
	}
	
	void print() {
		System.out.println("i = " + i);
	}

	public static void main(String[] args) {
		Leaf leaf = new Leaf(100);
		leaf.increment().increment().print();  //两次使用increment，每次都返回对象的引用,每次返回前都会使i自增
	}
}
```
输出结果：
```
i = 102
```

##关键字 static
- 再类中，用static声明的成员变量为静态成员变量，它为该类的公用变量，在第一次使用时就被初始化，对于该类的所有对象来说，static成员变量只有一份。
- 用static声明的方法为静态方法，在调用该方法时，不会将对象的引用传递给它，所以在static方法中不可访问非static的成员。
  - 静态方法不再是针对于某个对象调用，所以不能访问非静态成员。
- 可以通过对象引用或类名(不需要实例化)访问静态成员。

观察下面代码
```
public class Cat {
    private static int sid = 0;
    private String name; 
    int id;
    Cat(String name) {
        this.name = name;  
        id = sid++;
    }
    public void info(){
        System.out.println
               ("My name is "+name+" No."+id);
    }
    public static void main(String arg[]){

        Cat.sid = 100;
        Cat mimi = new Cat("mimi");
        mimi.sid = 2000;
        Cat pipi = new Cat("pipi");
        mimi.info(); 
        pipi.info();
		System.out.println(Cat.sid);
    }
}

```
输出结果：
```
My name is mimi No.100
My name is pipi No.2000
2001
```
对于上述代码中的静态变量sid，其不依赖于某个“mimi”或者“pipi”对象，直接通过Cat类进行访问。也因此不会因为某个对象的初始化而改变，从而可以作为计数器。
与静态变量sid相反，成员变量id不能在入口函数main中赋值，因为成员变量依附于某个对象而被存储，否则会报错： `错误: 无法从静态上下文中引用非静态 变量`

##package 和 import 语句
- 为便于管理大型软件系统中数目众多的类，解决类的命名冲突的问题，Java引入包(**package**)机制，提供类的多重命名空间。
- **package**语句作为Java源文件的第一条语句，指明该文件中定义的类所在的包。(若缺省该语句，则指定为无名包)。
  - 格式为： `package pkg1[.pkg2[.pkg3…]];`
- Java编译器把包对应于文件系统的目录管理，package语句中，用 `.` 来指明包(目录)的层次
  - 例如语句： `package com.sxt;` 改文件中所有的类位于 .\com\sxt 目录下
- 如果将一个类打包，则使用该类是，必须使用该类的全名(例如：com.sxt.MyClass)，Java编译器才会找到该类。
- 也可以使用import在文件开头引入要使用的类，例如
```
import com.sxt.Myclass;
import java.util.*  //引入java.util包中所有的类
    ... ... ...
    MyClass myclass = new MyClass();  //可以直接使用类名
```
- 可以不需要用import语句直接使用java.lang包中的类
- class文件的最上层包的父目录必须位于classpath下

###JDK中主要的包
- java.lang：包含一些Java原因的核心类，如String、Math、Integer、System和Thread，提供常用功能。
- java.awt：包含了构成抽象窗口工具集(abstract window toolkits)的多个类，这些类被用来构建和管理应用程序的图形用户界面(GUI)。
- java.applet：包含applet运行所需的一些类。
- java.net：包含执行与网络相关的操作的类。
- java.io：包含能提供多种输入/输出功能的类。
- java.util：包含一些实用工具类，如定义系统特性、使用与日期日历相关的函数。

将自己的java文件打包的方法：
快捷键win+r，输入cmd，cd到java文件的目录下，输入 `jar -cvf xx(包的名字).jar *.*`