#Java learning_Day3(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 继承与权限控制
- 访问控制
- 方法的重写
- 关键字 super
- 继承中的构造方法

##继承与权限控制
- Java中使用 extends 关键字实现类的继承机制，其语法规则为：
```
<modifier> class <name> [extends <superclass>] {
    ... ...
}
```
- 通过继承，子类自动拥有了基类(superclass)的所有成员(成员变量和方法)。
- Java只支持单继承，不允许多重继承：一个子类只能有一个基类，一个基类可以派生出多个子类。

观察下面代码
```
class Person {
    private String name;
    private int age;
    public void setName(String name) {
    	this.name=name;
    }

    public void setAge(int age) {
    	this.age=age;
    }

    public String getName(){
    	return name;
    }

    public int getAge(){
    	return age;
    }
}

class Student extends Person {
    private String school;
    public String getSchool() {
    	return school;
    }

    public void setSchool(String school) {
    	this.school =school;
    } 
}

public class TestPerson {

    public static void main(String arg[]){
        Student student = new Student();
        student.setName("John");
        student.setAge(18);
        student.setSchool("SCH");
        System.out.println(student.getName());
        System.out.println(student.getAge());
        System.out.println(student.getSchool());
    }
}

```

在上述程序中，由于类Student继承了类 Personl，因而可以直接调用父类的所有方法而不需要重新定义方法。

##访问控制
- Java权限修饰符 **public protected private**置于类的成员定义前，用来限定其他对象对该类对象成员的访问权限。

| 修饰符 | 类内部 | 同一个包 | 子类 | 任何地方 |
| ----- | :-----: | :-----: | :-----: | :-----: |
| private | √ | - | - | - |
| default | √ | √ | - | - |
| protected | √ | √ | √ | - |
| public | √ | √ | √ | √ |

- 对于 class 的权限修饰只可以用public和 default。

##方法的重写
- 在子类中可以根据需要对从基类中继承来的方法进行重写。
- 重写方法必须和被重写方法具有相同方法名称、参数列表和返回类型。
- 重写方法不能使用比被重写方法更严格的访问权限。

观察下面代码
```
class Person {
    private String name;
    private int age;
    public void setName(String name) {
		this.name=name;
	}

    public void setAge(int age) {
		this.age=age;
	}

    public String getName(){
		return name;
	}

    public int getAge(){
		return age;
	}

    public String getInfo() {
          return "Name: "+ name + "\n" +"age: "+ age;
  }
}

class Student extends Person {
    private String school;
    public String getSchool() {
		return school;
	}

    public void setSchool(String school) {
		this.school =school;
	}

    public String getInfo() {
      return  "Name: "+ getName() + "\nage: "+ getAge() + "\nschool: "+ school;
	}
}

public class TestOverWrite {

	public static void main(String arg[]) {
        Student student = new Student();
        Person person = new Person();
        person.setName("none");
        person.setAge(1000);
        student.setName("John");    
        student.setAge(18);
        student.setSchool("SCH");
        System.out.println(person.getInfo());
        System.out.println(student.getInfo());
    }
}

```
上述程序中，类 Student 重写了父类 Person 的 getInfo 方法，在 main 中调用的是子类重写父类后的方法。

另外，在重写方法时，可以在重写的方法前添加 `@Override` ,告诉编译器此处是重写方法，仍以上述代码为例
```
    @Override
    public String getInfo() {
      return  "Name: "+ getName() + "\nage: "+ getAge() + "\nschool: "+ school;
	}
```
@Override 是伪代码，可以不加上，但是加上有如下好处：
1. 可以当注释用,方便阅读；
2. 编译器可以给你验证@Override下面的方法名是否是你父类中所有的，如果没有则报错。例如，将上面代码段写错

```
    @Override
    public String GetInfo() {  //方法名注意大小写变化
      return  "Name: "+ getName() + "\nage: "+ getAge() + "\nschool: "+ school;
	}
```
此时编译器会视为你的方法重写出错，将会报错：
```
错误: 方法不会覆盖或实现超类型的方法
```
而不添加 @Override 的程序段将被编译器视为子类特有的方法，编译会通过。

##关键字 super
在Java类中使用super来引用基类的成分
观察下面代码
```
class FatherClass {
    public int value;
    public void f() {
        value = 100;
        System.out.println("FatherClass.value="+value);
    }
}

class ChildClass extends FatherClass {
    public int value;
    public void f() {
        super.f();
        value = 200;
        System.out.println("ChildClass.value="+value);
        System.out.println(value);
        System.out.println(super.value);
    }
}

public class TestInherit {

	public static void main(String[] args) {
		ChildClass cc = new ChildClass();
		cc.f();
	}
}

```
输出
```
FatherClass.value=100
ChildClass.value=200
200
100
```
在上述代码中，子类 ChildClass 通过 super.f() 调用父类的方法，在父类方法执行完成后，继续执行子类方法剩余的代码段。

##继承中的构造方法
- 子类的构造的过程中必须调用其基类的构造方法。
- 子类可以在自己的构造方法中使用 super(argument_list) 调用基类的构造方法。
- 如果子类的构造方法中没有显示地调用基类构造方法，则系统默认调用基类无参数的构造方法。
  - 另外，可以使用 this(arguement_list)调用本类的另外的构造方法。
  - 如果调用 super，必须写在子类构造方法的第一行。
- 如果子类构造方法中没有显示地调用基类构造方法，则系统默认调用基类无参数的构造方法。
- 如果子类构造方法中既没有显示调用基类构造方法，而基类中又没有无参的构造方法，则编译出错。

观察下面代码
```
class Bike {
	String brand;
	public Bike() {
		System.out.println("父类中无参的构造器被调用了");
	}

	public Bike(String brand) {
		this.brand = brand;
		System.out.println("父类中" + brand + "牌构造器被调用了");
	}
}

public class Aceing extends Bike{
	public Aceing() {
		System.out.println("子类中无参的构造器调用了");
	}

	public Aceing(String brand) {
		super(brand);
		System.out.println("子类中" + brand + "牌构造器被调用了");
	}

	public static void main(String[] args) {
		Aceing a1 = new Aceing();
		Aceing a2 = new Aceing("Giant");
	}
}

```
输出
```
父类中无参的构造器被调用了
子类中无参的构造器调用了
父类中Giant牌构造器被调用了
子类中Giant牌构造器被调用了
```
在语句 `Aceing a1 = new Aceing();` 中，调用了子类对象 a1 的 Aceing() 构造方法，而该方法中没有显示地调用父类 Bike 的构造方法，故系统默认调用父类无参数的 Bike() 构造方法，输出 `父类中无参的构造器被调用了` ，紧接着继续执行子类的构造方法，输出 `子类中无参的构造器调用了` 

在语句 `Aceing a2 = new Aceing("Giant");` 中，调用了子类对象 a2的 Aceing(String brand) 构造方法，而构造方法中调用了父类的 Bike(String brand) 方法， 输出 `父类中Giant牌构造器被调用了` ，紧接着继续执行子类的构造方法，输出 `子类中Giant牌构造器被调用了`

##参考资料
Java中@Override的作用|博客园
<https://www.cnblogs.com/scown/p/5374691.html>