#Java learning_Day1(上)
一切准备工作已经做好，虽然自己之前也零零碎碎学了一些Java的基础知识，貌似现在忘得差不多了，趁寒假契机从头开始学习吧
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

附上第一阶段的学习顺序：
> 马士兵\_JDK5.0\_下载-安装-配置
> 马士兵\_J2SE\_5.0\_第01章\_JAVA简介\_源代码\_及重要说明
> 马士兵\_J2SE\_5.0\_第02章\_递归补充\_
> 马士兵\_J2SE\_5.0\_第02章\_基础语法\_
> 马士兵\_J2SE\_5.0\_第03章\_面向对象\_
> 马士兵\_J2SE\_5.0\_第04章\_异常处理
> 马士兵\_J2SE\_5.0\_第05章\_数组
> 马士兵\_J2SE\_5.0\_第06章\_常用类
> 马士兵\_J2SE\_5.0\_第07章\_容器
> 马士兵\_J2SE\_5.0\_第08章\_IO
> 马士兵\_J2SE\_5.0\_第09章\_线程
> 马士兵\_J2SE\_5.0\_第10章\_网络
> 马士兵\_J2SE\_5.0\_第11章\_GUI
> 马士兵\_J2SE\_5.0\_专题\_日期处理
> 马士兵\_J2SE\_专题\_正则表达式
> 马士兵\_J2SE\_5.0\_第12章_反射机制详解

##第一步 JDK的下载和环境变量配置
- ###JDK的下载
  视频中使用的是Java 5.0的版本，不过我之前已经安装了Java 11，所以就略写啦
  >官网下载地址：<https://www.oracle.com/java/technologies/javase-downloads.html>

  然后选择自己想要的版本(一般最新版即可)，然后安装

  ![mVEi2mBtxO.png](https://i.loli.net/2020/03/02/Qnxfu8Ojg4mKdiU.png)

  选择JDK Download即可

- ###环境变量的配置
  桌面找到"此电脑"，然后右键选择属性，在出现窗口的左上角找到"高级系统设置"

  ![S2AXC5yVmf.png](https://i.loli.net/2020/03/02/o4d5KjHqfFsVJCY.png)

  在出现的窗口中找到"高级"一栏，在右下角找到"环境变量"

  ![t6Pf0fYHCk.png](https://i.loli.net/2020/03/02/ZUIEjsyvz5S78MW.png)

  在下方"系统变量"中，新建变量 ` CLASSPATH ` ，变量值为 ` .;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar; `
  ***注意变量值最前面有一个点 ` . ` ***

  ![SystemPropertiesAdvanced_RvaSCnbQfb.png](https://i.loli.net/2020/03/02/MbldhaY4toJUFqs.png)

  接着新建变量 ` JAVA_HOME `，变量值为你安装的JDK路径，一般默认在` C:\Program Files\Java\ `文件夹下，本人安装在了E盘

  ![SystemPropertiesAdvanced_at8yg1fr7l.png](https://i.loli.net/2020/03/02/DSQzrRJLUZfnhvu.png)

  最后，找到名为"Path"的变量，双击。在出现的窗口中点击"新建"按钮，并输入 ` %JAVA_HOME%\bin `
  然后再新建一次，输入` %JAVA_HOME%\jre\bin `

  现在环境变量就设置好了，通过CMD进行验证
  快捷键 ` Win+R ` ， 在跳出的窗口中输入 ` cmd ` *(也即打开命令行)*

  ![explorer_8tA8UXsq3D.png](https://i.loli.net/2020/03/02/7cW6RUeKCXNGtns.png)

  在出现的窗口中输入 ` java ` ，若看到类似图示的一大串文字，说明环境变量设置成功

  ![cmd_XtNQs2A3LO.png](https://i.loli.net/2020/03/02/hcGXLHYwrWCgb18.png)

##第二步 编译第一个java程序
- ###利用文本编辑器写代码
  *这里不推荐用系统默认的文本编辑器写代码，可以下载Notepad++，更加好用*
  > *下载地址：<https://pan.baidu.com/s/1S-V9WzXlF02G4zMZpz7YnA>
提取码：u7n7*

  新建一个文本，打开后输入以下代码：
  ```
  public class HelloWorld {
  	public static void main(String[] args) {
  		System.out.println("Hello World");
  	}
  }
  ```

  保存，然后将文件重命名为 ` HelloWorld.java `
  接下来进行编译，快捷键 ` Win+R ` ， 在跳出的窗口中输入 ` cmd `
  接下来输入文件所在的盘符，例如F盘，我输入 ` f: `
  再找到文件所在的路径，例如 ` F:\Java `

  ![cmd_aAW6fVtY3x.png](https://i.loli.net/2020/03/02/vRpcmr7Ju8MH4fF.png)

  继续，现在开始进行编译，输入 ` javac HelloWorld.java `

  ![cmd_J49CR0YfbQ.png](https://i.loli.net/2020/03/02/Rnw3NurbVjeIkTZ.png)

  此时按下回车，会发现原文件夹下多了一个 *HelloWorld.class* 文件，这是java文件编译后生成的字节码文件，此时在命令行中输入 ` java HelloWorld `，在窗口中显示 Hello World ， 编译成功

  ![MarkdownPad2_8ae5eOZr7w.png](https://i.loli.net/2020/03/02/y27IKgLFNsQWrlC.png)