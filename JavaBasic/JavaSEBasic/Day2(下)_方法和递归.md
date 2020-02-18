#Java learning_Day2(下)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##方法
Java的方法类似于其他语言的函数，是一段用来完成特定功能的代码片段，格式如下
```
[修饰符1 修饰符2 …] 返回值类型 方法名(形式参数列表) {
    Java语句；… … …
}
```

**形式参数**：在方法被调用时用于接收外界输入的数据。
**实参**：吊桶方法是实际给方法的数据。
**返回值**：方法在执行完毕后返回给调用它的环境的数据。
**返回值类型**：事先约定的返回值的数据类型，无返回值为void。

Java语言中形式调用方法： `对象名.方法名(实参列表)`
实参的数目、数据类型和次序必须和所调用方法声明的形参列表匹配。
return语句终止方法的运行并指定要返回的数据。

Java中进行函数调用中传递参数时，遵循值传递的原则：
- 基本类型传递的是该数据本身。
- 引用类型传递的是对象的引用，非对象本身。

##递归
在一个方法内部，对自身进行调用。
例如：求Fibonacci数列：1,1,2,3,5,8，…第40个数的值。数列满足地推公式：
![](https://i.imgur.com/6FAXxCz.png)

图解递归调用：
![](https://i.imgur.com/ZQcy8Id.png)

递归调用代码：
```
public class Day2_Test3 {
	//Fibonacci数列：1,1,2,3,5,8，…第40个数的值

	public static void main(String[] args) {
		System.out.println(fib(40));
	}

	public static int fib(int num) {  //使用递归方法
		if (num == 1 || num == 2) {
			return 1;			
		} else {
			return fib(num-1) + fib(num-2);
		}
	}
}

```

对比非递归方法：
```
public class Day2_Test4 {
	//Fibonacci数列：1,1,2,3,5,8，…第40个数的值

	public static void main(String[] args) {
		System.out.println(fib(40));
	}

	public static int fib(int num) {  //使用非递归方法
		if (num == 1 || num == 2) {
			return 1;
		} else {
			int sum = 0;
			for (int pre1 = 1, pre2 = 1, i = 3; i <= num; i++) {
				sum = pre1+pre2;
				pre1 = pre2;
				pre2 = sum;
			}
			return sum;
		}
	}
}
```