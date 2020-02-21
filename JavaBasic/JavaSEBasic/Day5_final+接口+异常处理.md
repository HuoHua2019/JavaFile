#Java learning_Day5
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 关键字 final
- 接口(interface)
- 异常处理

##关键字 final
- final 的变量的值不能够被改变。
- final 的方法不能够被重写。
- final 的类不能够被继承。

以下面程序段为例
```
final class Example {  //定义一个 final 的类，该类不能被继承
	final int i = 8;  //定义一个 final 的变量，该变量不能被修改

	public final void example() {  //定义一个 final 的方法，该类的子类不能重写此方法
        
	}
}
```
##接口(interface)
- 多个无关的类可以实现同一接口。
- 一个类可以实现多个无关的接口。
- 与继承关系类似，接口与实现类之间存在多态性。
- 定义 Java 类的语法格式：

```
<modifier> class <name> [extends <superclass>] [implements <interface1>, <interface2>*, ...] {
    ...
}
```
- 接口(interface)是抽象方法和常量值的定义的集合。
- 从本质上讲，接口是一种特殊的抽象类，这种抽象类只包含常量和方法的定义，而没有变量和方法的实现。

关于接口的实现，以下面程序段为例：
```
public interface Runner {
    public static final int id = 1;

    public void start();
    public void run();
    public void stop();
}
```
####接口的特性
- 接口可以多重实现。
- 接口中声明的属性默认为 public static final 的，也只能是 public static final 的。即在接口中，语句 `public static final int id = 1;` 等价于语句 `int id = 1;`
- 接口中只能定义抽象方法，而且这些方法默认为 public 的、也只能是 public 的。
- 接口可以继承其他的接口，并添加新的属性和抽象方法。

***在Java1.8以后，接口可以拥有默认方法。详见：[Java 8 默认方法（Default Methods）| 博客园](https://www.cnblogs.com/sidesky/p/9287710.html)***

##异常处理
###异常的概念
- Java异常是指Java提供的用于处理程序中错误的一种机制。
- 所谓错误是指在程序运行的过程中发生的一些异常事件(如：除0溢出，数组下标越界，所要读取的文件不存在)。
- 设计良好的程序应该在异常发生时提供处理这些错误的方法，使得程序不会因为异常的发生而阻断或产生不可预见的结果。
- Java程序的执行过程中如出现异常事件，可以生成一个异常类对象，该异常对象封装了异常事件的信息并将被提交给Java运行时系统，这个过程称为抛出(throw)异常。
- 当Java运行时系统接收到异常对象时，会寻找能处理这一异常的代码并把当前异常对象交给其处理，这一过程称为捕获(catch)异常。

以下面程序段为例
```
public void someMethod() throws SomeException {  //声明该方法可能抛出异常
    if (someCondition()) {  //构造并抛出异常对象
        throw new SomeException("错误原因");
    }
}
```
以上程序段声明该方法可能抛出异常，构造并抛出异常对象。

再看下面程序段
```
try {  //调用该方法时试图捕获异常
    someMethod();
} catch (SomeException e) {
    //异常处理代码
}
```
上述语句试图执行一个可能抛出异常的方法，并提供了异常处理。
###异常分类
**Error**：称为错误，由Java虚拟机生成并抛出，包括动态链接失败、虚拟机错误等，程序对其不做处理。
**Exception**：所有异常类的父类，其子类 对应了各种各样可能出现的异常事件，一般需要用户显示的声明或捕获。
**Runtime Exception**：一类特殊的异常，如被0除、数组下标越界等，其产生比较麻烦，处理麻烦，如果显示的声明或捕获将会对程序可读性和运行效率影响很大。因袭由系统自动检测并将它们交给缺省的异常处理程序(用户可不比对其处理)。

![PotPlayerMini64_Nx2Y97mx74.png](https://i.loli.net/2020/02/21/a2vqTxL8fCtei1J.png)

###异常的捕获和处理
```
try {
    //可能抛出异常的语句
} catch (SomeException1 e) {
    ... ... ...
} catch (SomeException2 e) {
    ... ... ...
} finally {
    ... ... ...
}
```
- try 代码段包含可能产生异常的代码。
- try 代码段后跟有一个或多个 catch 代码段。
- 每个 catch 代码段声明其能处理的一种特定类型的异常并提供处理的方法。
- 当异常发生时，根据获取异常的类型去执行相应的 catch 代码段。
- finally 段的代码无论是否发生异常都会执行。

![PotPlayerMini64_q1XPSk5asR.png](https://i.loli.net/2020/02/21/SyQqgAioMTnj7lZ.png)

此外，SomeException2 应该是 SomeException的同级异常或父类异常，否则永远无法抛出 SomeException2 异常，这是由于程序先执行语句段 `catch (SomeException1 e)` 决定的，换句话说，编写程序时，在同一个 try ... catch ... 语句中，应该先写子类异常的 catch 语句，后写父类异常的 catch 语句。 

####catch语句
- 在 catch 中声明的异常对象 (catch (someException e)) 封装了异常事件发生的信息，在 catch 语句块中可以使用这个对象的一些方法获取这些信息。
  - `getMessage()` 方法，用来得到有关异常事件的信息。
  - `printStackTrace()` 方法，用来跟踪异常事件发生时执行堆栈的内容。

####finally 语句
- finally 语句为异常处理提供一个统一的出口，使得在控制流程转到程序其他部分以前，能够对程序的装太做统一的管理。
- 通常在 finally 语句中可以进行资源的清除工作，如
  - 关闭打开的文件
  - 删除临时的文件
  - ...

####自定义异常
使用自定义异常一般有如下步骤：
1. 通过继承 java.lang.Exception 类声明自己的异常类。
2. 在方法适当的位置生成自定义异常的实例，并用 throw 语句抛出。
3. 在方法的声明部分用 throws 语句声明该方法可能抛出的异常。

以下面程序段为例
```
class MyException extends Exception {  //自定义异常MyException, 继承父类异常Exception
	private int id;

	public MyException(String message, int id) {
		super(message);  //继承分类中的参数为message的构造方法，获取出错提示message
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
public class TestException {

	public void regist(int num) throws MyException{  声明执行此方法可能会抛出异常
		if (num < 0) {
			throw new MyException("人数为负数，不合理", 1);
		} else {
			System.out.println("人数为" + num);
		}
	}
		
	public void manager() {
		try {
			regist(-1);
			System.out.println("登记成功");
		} catch (MyException e) {
			System.out.println("登记失败，出错类型：" + e.getId());
			e.printStackTrace();
		}

		System.out.println("操作结束");
	}

	public static void main(String[] args) {
		TestException t = new TestException();
		t.manager();
	}
}

```
上面程序段中，自定义 MyException 异常，并加入了一个子类特有的构造方法，用于处理出错信息和出错 id，当 TestException 类中的 regist 方法生成该异常时，抛出该异常返回给 manager 方法，由其进行异常处理。

**注意：**
**重写方法需要抛出与原方法所抛出异常类型一致异常或不抛出异常。**

![PotPlayerMini64_CdjqnSRMBE.png](https://i.loli.net/2020/02/21/1JQi97FyPWK2m3h.png)