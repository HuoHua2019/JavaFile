#Java learning_Day3(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- Java面向对象编程
- 对象和类的概念
- 构造方法(构造函数)

##Java 面向对象编程
- 面向对象的基本思想是，从现实世界中客观存在的事物出发来构造软件系统，并在系统的构造中尽可能运用人类的自然思维方式。
- 面向对象更加强调运用人类在日常的思维逻辑中经常采用的思想方法与原则，如抽象、分类、继承、聚合、多态等。

##对象和类的概念
- 对象通过“属性(attribute)”和“方法(method)”来分别对应事物所具有的静态属性和动态属性。
- 类是用于描述同一类型的对象的一个抽象概念，类中定义了这一类对象所应具有的静态和动态属性。
- 类可以看成一类对象的模板，对象可以看成改类的一个具体实例。

![3RZzb6.png](https://s2.ax1x.com/2020/03/02/3RZzb6.png)

###关联关系
- 往往一个方法的参数是另一个类的对象
- 关系不紧密

![3RZO29.png](https://s2.ax1x.com/2020/03/02/3RZO29.png)

###继承关系
- 满足XX是XX(XX is a kind of XX)

![3RZXvR.png](https://s2.ax1x.com/2020/03/02/3RZXvR.png)

###聚合关系
- 整体和部分
- 聚合又可以细分为聚集和组合
  - 聚集关系中的部分可以隶属于多个整体，充当不同的对象。
  - 组合关系的部分隶属于一个整体，充当单一的对象。

![3RZvK1.png](https://s2.ax1x.com/2020/03/02/3RZvK1.png)

###实现关系
- 子类对于父类的特定实现方法

![3RZxDx.png](https://s2.ax1x.com/2020/03/02/3RZxDx.png)

###多态
具体可以参考：[Java_Day4(下)](https:////www.cnblogs.com/HuoHua2020/p/12338065.html)

###创建一个简单的对象，拥有属性和方法
```
public class Dog {
	//创建狗这个类
	
	char furColor;
	char voice;
	int height;
	int weight;
	
	//狗拥有抓老鼠这个方法
	public void catchMouse(Mouse mouse) {
		//catch mouse
		mouse.scream();  //老鼠有发出尖叫这个动作(方法)
	}

	public static void main(String[] args) {
		Dog dog = new Dog();  //创建狗这个对象
		Mouse mouse = new Mouse();  //创建老鼠这个对象
		dog.catchMouse(mouse);  //狗拿耗子
	}
}
```

###类的定义
- 类的定义主要由两方面组成
  1. 成员变量
  2. 方法
1 声明成员变量的格式为： `[<modifiers>] type <arrt_name>[=defaultValue];`

###成员变量
- 成员变量可以使用Java语言中任何一种数据类型(包括基本类型和引用类型)
- 在定义成员变量是可以对其初始化，如果不对其初始化，Java使用默认的值对其初始化，如下表。

| 成员变量类型 | 取值 |
| ------ | ------ |
| byte | 0 |
| short | 0 |
| int | 0 |
| long | 0L |
| char | '\u0000' |
| float | 0.0F |
| double | 0.0D |
| boolean | false |
| 所有引用类型 | NULL |

###对象的创建和使用
- 必须使用**new**关键字创建对象。
- 使用对象(引用).成员变量来引用对象的成员变量。
- 使用对象(引用).方法(参数列表)来调用对象的方法。
- 同一类的每个对象有不同的成员变量存储空间。
- 同一类的每个对象共享该类的方法。

##构造方法(构造函数)
- 使用new+构造方法创建一个新的对象。
- 构造函数是定义在Java类中的一个用来初始化对象的函数。
- 构造函数与类同名且没有返回值。

```
public class Person {
	int id;
	int age;

	Person(int n, int i) {  //Person类的构造函数
		id = n;
		age = i;
	}
}
```
创建对象时，使用构造函数初始化对象的成员变量。
```
class Test {

	public static void main(String[] args) {
		Person tom = new Person(1, 25);
		Person john = new Person(2, 27);
	}	
}
```

当没有指定构造函数时，编译器为类自动添加形如 `类名() { }` 的无参数构造函数
**注意：一旦手动添加了构造函数，编译器将不再默认添加一个无参数构造函数，此时初始化对应的无参数对象编译器将会报错**

构造函数对对象初始化后，如何改变对象存储的值？ 详见：<file:///F:\github\JavaFile\JavaBasic\JavaSEBasic\Day3(中)_改变引用数据类型的值.md>