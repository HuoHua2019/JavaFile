#Java learning_Day4(下)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- Object 类
- 对象转型(casting)
- 动态绑定和多态(polymorphism)
- 抽象类

##Object 类
- Object类是所有Java类的根基类
- 如果在类的声明中未使用extends关键字指明其基类，则默认基类为Object类
即
```
public class Person {
    ...
}
```
等价于
```
public class Person extends Object {
    ...
}
```
###toString() 方法
- Object 类中有定义 public String toString() 方法，其返回值是 String 类型，描述当前对象的有关信息。
- 在进行String与其他类型数据的连接操作时(如：System.out.println("info" + person))，将自动调用该对象类的 toString()方法。
- 可以根据需要在用户自定义类型中重写 toString() 方法。

###equals 方法
- public boolean equals(Object obj) 方法，提供定义对象是否"相等"的逻辑。
- Object 的 equals. 方法定义为： x.equals(y) 当 x 和 y 是哦、同一个对象的引用时返回 true 否则返回 false
- JDK提供的一些类，如String，Date等，重写了Object的equals方法，调用这些类的equals方法， x.equals(y)，当 x 和 y 所引用的对象是同一类对象且属性内容相等时(并不一定是相同对象)，返回 true 否则返回 false。
- 可以根据需要在用户自定义类型中重写 equals 方法。

##对象转型(casting)
- 一个基类的引用类型变量可以“指向”其子类的对象。
- 一个基类的引用不可以访问其子类对象新增加的成员(属性和方法)。
- 可以使用引用变量 instanceof 类名来判断该引用型变量所“指向”的对象是否属于该类或该类的子类。
- 子类的对象可以当作基类的对象来使用称作向上转型(upcasting)，反之称为向下转型(downcasting)。

以下面程序为例
```
class Animal {
	public String name;
	Animal(String name) {
		this.name = name;
	}	
}

class Cat extends Animal {
	public String eyesColor;
	Cat(String name, String color) {
		super(name);
		eyesColor = color;
	}	
}

class Dog extends Animal {
	public String furColor;
	Dog(String name, String color) {
		super(name);
		furColor = color;
	}	
}

public class TestCasting {

	public static void main(String[] args) {
		Animal a = new Animal("name");
		Cat c = new Cat("cat name", "blue");
		Dog d = new Dog("dog name", "black");

		System.out.println(a instanceof Animal);  //true
		System.out.println(c instanceof Animal);  //true
		System.out.println(d instanceof Animal);  //true
		System.out.println(a instanceof Cat);  //false

		
		Animal a1 = new Dog("dog name", "yellow");  //向上转型
		System.out.println(a1.name );  //true
		System.out.println(a1 instanceof Animal);  //true
		System.out.println(a1 instanceof Dog);  //true
		
        Animal a2 = new Animal("name");		
		Dog d1 = (Dog)a2;  //向下转型，!error
		a2 = new Dog("dog name", "yellow");
		System.out.println(d1.furColor);
	}
}
```
输出结果
```
true
true
true
false
dog name
true
true
Exception in thread "main" java.lang.ClassCastException: class Animal cannot be cast to class Dog (Animal and Dog are in unnamed module of loader 'app')
```
####对程序段进行分析
程序段 `Animal a1 = new Dog("dog name", "yellow");` 即是向上转型，将父类对象 Animal 转换成子类对象 Dog。转型成功，编译器顺利执行。

而在程序段
```
Animal a2 = new Animal("name");		
Dog d1 = (Dog)a2;  //向下转型，!error
a2 = new Dog("dog name", "yellow");
System.out.println(d1.furColor);
```
此处即是向下转型，在语句 `Dog d1 = (Dog)a2;` 中，父类对象 Animal 无法直接转换为子类对象 Dog。因为此时 父类对象 Animal 还没有完全拥有子类对象 Dog 的所有属性,必须改为：
```
Animal a2 = new Animal("name");		      
a2 = new Dog("dog name", "yellow");       
Dog d1 = (Dog)a2;  //必须强制转换          
System.out.println(d1.furColor);  //yellow
```
此时，向下转型才能编译成功。

##动态绑定和多态(polymorphism)
- 动态绑定是指在执行期间(而非编译期)判断所引用对象的实际类型，根据其实际的类型调用其相应的方法。
- 动态绑定的条件：
  1. 要有继承
  2. 子类重写父类方法
  3. 父类引用指向子类对象

以下面程序为例
```
class Animal {
	private String name;

	Animal(String name) {
		this.name = name;
	}
	
	public void enjoy(){
	  System.out.println("叫声......");
	}	
}

class Cat extends Animal {
	private String eyesColor;

	Cat(String n,String c) {
		super(n); 
		eyesColor = c;
	}

	public void enjoy() {
		System.out.println("猫叫声......");
	}
}

class Dog extends Animal {
	private String furColor;

	Dog(String n,String c) {
		super(n); 
		furColor = c;
	}

	public void enjoy() {
		System.out.println("狗叫声......");
	}
}

class Lady {
    private String name;
    private Animal pet;

    Lady(String name, Animal pet) {
        this.name = name; 
        this.pet = pet;
    }

    public void myPetEnjoy(){
        pet.enjoy();
    }
}

public class TestPoly {

    public static void main(String args[]){
        Cat c = new Cat("catname","blue");
        Dog d = new Dog("dogname","black");
        Lady l1 = new Lady("l1",c);
        Lady l2 = new Lady("l2",d);
        l1.myPetEnjoy();
        l2.myPetEnjoy();
    }
}
```
输出
```
猫叫声......
狗叫声......
```
在上述程序中，程序段
```
Lady(String name, Animal pet) {
    this.name = name; 
    this.pet = pet;
}
```
说明Lady类对象拥有类型为 Animal 的 pet，但是输出结果不是 Animal的方法 "叫声......"，而是分别输出了其子类对象的方法，这其实就是多态机制的一种体现，在程序执行期间，Lady l1 对象拥有的 pet 为 Cat，因而调用 Cat 的方法；Lady l2 对象拥有的 pet 为 Dog，因而调用 Dog 的方法。

##抽象类
- 用 **abstract** 关键字来修饰一个类时，这个类叫做抽象类；用 **abstract** 来修饰一个方法时，该方法叫做抽象方法。
- 含有抽象方法的类必须被声明为抽象类，抽象类必须被继承，抽象方法必须被重写。
- 抽象类不能被实例化。
- 抽象方法只需要声明，不需要实现。

以下面程序为例
```
abstract class Animal {
	private String name;

	Animal(String name) {
	  this.name = name;
	}

  public abstract void enjoy();
}

class Cat extends Animal {
	private String eyesColor;

	Cat(String n,String c) {
	  super(n); 
	  eyesColor = c;
	}

	public void enjoy() {
		System.out.println("猫叫声......");
  }
}

class Dog extends Animal {
	private String furColor;

	Dog(String n,String c) {
		super(n);
		furColor = c;
	}
 
	public void enjoy() {
		System.out.println("狗叫声......");
  }
}

class Lady {
    private String name;
    private Animal pet;
    Lady(String name,Animal pet) {
		this.name = name; 
		this.pet = pet;
    }

    public void myPetEnjoy() {
		pet.enjoy();
	}
}

public class TestAbstract {

	public static void main(String args[]){
		Cat c = new Cat("catname","blue");
		Dog d = new Dog("dogname","black");
		Lady l1 = new Lady("l1",c);
		Lady l2 = new Lady("l2",d);
		l1.myPetEnjoy();
		l2.myPetEnjoy();
	}
}
```
父类 Animal 没有去实现 enjoy 方法，而是选择将其声明为 abstract 方法，同时将类本身修饰为 abstract 类，让其子类实现它的方法。

##参考资料
Java转型(向上或向下转型) | CSDN
<https://blog.csdn.net/shanghui815/article/details/6088588>