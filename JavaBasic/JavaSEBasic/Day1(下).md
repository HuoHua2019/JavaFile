#Java learning_Day1(上)
正式开始JavaSE的基础学习
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##Java基础
1. 语法基础
2. OO
3. Exception
4. Array
5. 基础类
6. I/O Stream
7. Collection / Generic
8. Thread
9. TCP/UDP
10. GUI
11. Meta Data
12. Regular Expression

##Java体系
####1. JDK初步
其他方向
####2. Java Web编程
针对Web程序
####3. J2EE
企业级应用
####4. J2ME
移动设备
####5. 移动增值
SP运营商

##Java程序运行机制
####1. Java虚拟机(Java Virtual Machine)

![](https://i.imgur.com/D1Rj3hw.png)

**此外，Java是解释型的语言**
编译和解释的区别：编译型语言可以一次编译成为操作系统可以识别运行的的可执行文件，解释型语言需要先编译成为对应语言的文件，再将第一次编译的文件解释成为操作系统可以识别运行的可执行文件

![](https://i.imgur.com/ZdQ35Lh.png)

####2. 垃圾收集机制(Grabage collection)
不同于C/C++等语言，Java中不再使用的内存空间会被自动检查并释放

##Java Application起步
- 源文件以"java"为扩展名
- 源文件的基本组成部分是类(class)
- 一个源文件之多只能有一个public类，其他类个数不限。
- 源文件若包含public类，则必需按该类名命名。
- Java的执行入口是main()方法，格式为 `pubic static void main(String[] args) {...}`
- Java语言对大小写敏感(严格区分大小写)
- 语句以 `;`结束
- 三种注释方式：

```
//用于单行注释`

/*
  用于多行注释
*/

/**
 * 用于多行注释，且可以被doc工具解析
 */
```