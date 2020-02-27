#Java learning_Day10(上)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 线程的基本概念
- 线程的创建和使用
- 线程同步

##线程的基本概念
- 线程是一个程序内部的顺序控制流。
- 线程和进程的区别
  - 每个进程都有独立的代码和数据空间（进程上下文），进程间的切换会有较大的开销。
  - 线程可以看成是轻量级的进程，同一类线程共享代码和数据空间，每个线程有独立的运行栈和程序计数器（PC），线程切换的开销小。
  - 多进程：在操作系统中能同时运行多个任务（程序）
  - 多线程：在同一应用程序中有多个顺序流同时执行

- Java的线程是通过 java.lang.Thread 类来实现的。
- VM 启动时会有一个由主方法（public static void main() {}）所定义的线程。
- 可以通过创建 Thread 的实例来创建新的线程。
- 每个线程都是通过某个特定 Thread 对象所对应的方法 run() 来完成其操作的，方法 run() 称为线程体。
- 通过调用 Thread 类的 start() 方法来启动一个线程。

##线程的创建和使用
可以有两种方式创建新的线程。
1. 第一种
  - 定义线程类实现 Runnable 接口
  - `Thread myThread = new Thread(target)  //target 为 Runnable 接口类型`
  - Runnable 中只有一个方法：
    - `public void run();`  用以定义线程运行体。
  - 使用 Runnable 接口可以为多个线程提供共享的数据。
  - 在实现 Runnable 接口的类的 run 方法定义中可以使用 Thread 的静态方法：
    - `public static Thread currentThread()` 获取当前线程的引用。
2. 第二种
  - 可以定义一个 Thread 的子类并重写其 run 方法如：
```
class MyThread extends Thread {
    public void run() {...}
}
```
  - 然后生成该类的对象：
`MyThread myThread = new MyThread(...)`

第一种方法示例
```
public class TestThread1 {

	public static void main(String[] args) {
		Runner1 r = new Runner1();
		Thread t = new Thread(r);
		t.start();

		for (int i = 0; i < 100; i++) {
			System.out.println("Main Thread:" + i);
		}
	}
}

class Runner1 implements Runnable { 
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner:" + i);
		}
	}
}
```

第二种方法示例
```
public class TestThread1 {

	public static void main(String[] args) {
		Runner1 r = new Runner1();
		r.start();

		for (int i = 0; i < 100; i++) {
			System.out.println("Main Thread:" + i);
		}
	}
}

class Runner1 extends Thread {
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner:" + i);
		}
	}
}
```
###线程状态装换

![PotPlayerMini64_lYHTYVKt9D.png](https://i.loli.net/2020/02/27/rWB91nOCxZSAMsy.png)

###线程控制基本方法

![PotPlayerMini64_a2Mw7FDhAb.png](https://i.loli.net/2020/02/27/e51WDRgw9N6t7Ys.png)

###sleep/join/yield 方法
- sleep方法
  - 可以调用 Thread 的静态方法：
`public static void sleep(long millis) throws InterruptedException`
使得当前线程休眠（暂时停止执行 millis 毫秒）。
  - 由于是静态方法，sleep 可以由类名直接调用：
`Thread.sleep(...)`
- join方法
  - 合并某个线程
- yield 方法
  - 让出CPU，给其他线程执行的机会。

###线程的优先级别
- Java 提供一个线程调度器来监控程序中启动后进入就绪状态的所有线程。线程调度器按照线程的优先级决定应调度哪个线程来执行。
- 线程的优先级用数字表示，范围从1到10，一个线程的缺省优先级是5。
  - `Thread.MIN_PRIORITY = 1`
  - `Thread.MAX_PRIORITY = 10`
  - `Thread.NORM_PRIORITY = 5`
- 使用下述线程方法获得或设置线程对象的优先级。
  - `int getPriority();`
  - `void setPriority(int newPriority);`

##线程同步
- 在 Java 语言中，引入了对象互斥的概念，保证共享数据的完整性。每个对象都对应于一个可称为“互斥锁”的标记，这个标记保证在任一时刻，只能有一个线程访问该对象。
- 关键字 synchronized 来与对象的互斥锁联系。当某一个对象 synchronized 修饰时，表明该对象在任一时刻只能由一个线程访问。
  - synchronized 的使用方法：
```
synchronized(this) {
    ...
}
```
  - synchronized 还可以放在方法声明中，表示整个方法为同步方法，如
```
synchronized public void methodName() {
    ...
}
```

###wait 和 sleep 区别
- wait 时别的线程可以访问锁定对象
  - 调用 wait 方法的时候必需锁定该对象
- sleep 时别的线程也不可以访问锁定对象

###notify 和 notifyAll
- notify 使得一个处于 wait 状态的线程继续运行
- notifyAll 使得所有处于 wait 状态的线程继续运行