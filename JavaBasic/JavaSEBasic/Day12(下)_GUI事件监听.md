#Java learning_Day12(下)
>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
提取码：fobs

##内容
- 事件监听
- TestField 类
- Graphics 类
- Window 事件

##事件监听
模型：
![image.png](https://i.loli.net/2020/02/29/BJaps1mwiVce3Y2.png)

###Button 事件监听
示例
```
import java.awt.*;
import java.awt.event.*;

public class TestActionEvent {

	public static void main(String[] args) {
		Frame f = new Frame("ActionEvent");
		Button b = new Button("Press me!");
		MyMonitor m = new MyMonitor();
		b.addActionListener(m);
		f.add(b, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}
}

class MyMonitor implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		System.out.println("A button has been pressed!");
	}
}
```

##TestField 类
- java.awt.TextField 类用来创建文本框对象、
- TextField 有如下常用方法：

    ![image.png](https://i.loli.net/2020/02/29/XT21fNhM8uCynK3.png)

###TextField 事件监听
- TextField 对象可能发生 Action（光标在文本框内敲回车）时间。与该事件对应的事件类是 java.awt.event.ActionEvent。
- 用来处理 ActionEvent 事件是实现了 java.awt.event.ActionListener 接口的类的对象。ActionListener 接口定义有方法：
  - public void actionPerformed(ActionEvent)
- 实现该接口的类要在该方法中添加处理该时间（Action）的语句。
- 使用 addActionListener(ActionListener l) 方法为 TextField 对象注册一个 ActionListener 对象，当 TextField 对象发生 Action 事件时，会生成一个 ActionEvent 对象，该对象作为参数传递给 ActionListener 对象的 actionPerformer 方法在方法中可以获取该对象的信息，并做相应的处理。

示例
```
import java.awt.*;
import java.awt.event.*;

public class TFActionEvent {

	public static void main(String[] args) {

		new TFFrame();
	}

}

class TFFrame extends Frame
{
	TFFrame()
	{
		TextField tf = new TextField();
		add(tf);
		tf.addActionListener(new TFActionListener());
		tf.setEchoChar('*');  //设置回显字符
		pack();
		setVisible(true);
	}
}

class TFActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		TextField tf = (TextField)e.getSource();
		System.out.println(tf.getText());
		tf.setText("");  //输入一次后清空
	}
}
```
###持有对方引用
拥有 Button 对象后，如何通过监听事件 Button 来获取 TextField 的内容？
以下面程序为例
```
import java.awt.*;
import java.awt.event.*;

public class TFMath {

	public static void main(String[] args) {
		new TFFrame().launchFrame(); 
	}
}

class TFFrame extends Frame {
	TextField num1, num2, num3; 
	
	public void launchFrame() {
		num1 = new TextField(10);
		num2 = new TextField(10);
		num3 = new TextField(20);
		Button b = new Button("=");
		Label l = new Label("+");
		b.addActionListener(new TFActionListener(this));  //通过 this 传入整个类对象
		setLayout(new FlowLayout());
		add(num1);
		add(l);
		add(num2);
		add(b);
		add(num3);
		pack();
		setVisible(true);
	}
}

class TFActionListener implements ActionListener {
	TFFrame t = null;

	public TFActionListener(TFFrame t) {
		this.t = t;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int n1 = Integer.parseInt(t.num1.getText());
		int n2 = Integer.parseInt(t.num2.getText());
		t.num3.setText("" + (n1 + n2));
	}
}
```
上述程序是一个简单的加法计数器，在前两个文本框内输入数字后，点击 “=” 按钮，在第三个文本框内输出和。为了能够获取文本框中的信息，在 ActionListener 中使用了一个传入 Frame 引用的构造函数，借以调用 Frame 中的成员变量。

上述程序也可以使用**内部类**实现
```
import java.awt.*;
import java.awt.event.*;

public class TFMath {

	public static void main(String[] args) {
		new TFFrame().launchFrame(); 
	}
}

class TFFrame extends Frame {
	TextField num1, num2, num3; 
	
	public void launchFrame() {
		num1 = new TextField(10);
		num2 = new TextField(10);
		num3 = new TextField(20);
		Button b = new Button("=");
		Label l = new Label("+");
		b.addActionListener(new TFActionListener());
		setLayout(new FlowLayout());
		add(num1);
		add(l);
		add(num2);
		add(b);
		add(num3);
		pack();
		setVisible(true);
	}

	private class TFActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int n1 = Integer.parseInt(num1.getText());
			int n2 = Integer.parseInt(num2.getText());
			num3.setText("" + (n1 + n2));
		}
	}
}
```
###内部类
- 好处：
  - 可以方便的访问包装类的成员
  - 可以更清除的组织逻辑，防止不应该被其它类访问的类访问。
- 何时使用：
  - 该类不允许或不需要其他类进行访问时

##Graphics 类
- 每个 Component 都有一个 paint(Graphics g) 用于实现绘图目的，每次重画该 Component 时都自动调用 paint 方法。
- Graphics 类中提供了许多绘图方法，如：
  - drawRectangle(int x, int y, int width, int height)
  - fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)等

###Graphics 类 Paint 方法
- 每个 Component 都有一个 paint(Graphics g)用于实现绘图目的，每次重画该 Component 时都自动调用 paint 方法。
- Graphics 类中提供了许多绘图方法，具体请查询 API 文档。

####鼠标事件适配器
- 抽象类 java.awt.event.MouseAdapter 实现了 MouseListener 接口，可以使用其子类作为 MouseEvent 的监听器，只要重写其相应的方法即可。
- 对于其他的监听器，也有对应的适配器。
- 使用适配器可以避免监听器类定义没有必要的孔方法。
- repaint方法的调用顺序：repaint() -> upfate() -> paint()

示例
```
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MyMouseAdapter {

	public static void main(String[] args) {
		new MyFrame("Drawing");
	}
}

class MyFrame extends Frame {
	ArrayList<Point> points = null;
	MyFrame(String s) {
		super(s);
		points = new ArrayList<Point>();
		setLayout(null);
		setBounds(300, 300, 300, 300);
		setBackground(new Color(100, 180,240));
		setVisible(true);
		addMouseListener(new MyMonitor());
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}

	@Override
	public void paint(Graphics g) {
		Iterator<Point> i = points.iterator();
		while (i.hasNext()) {
			Point p = (Point)i.next();
			g.setColor(Color.BLUE);
			g.fillOval(p.x, p.y, 5, 5);
		}
	}
}

class MyMonitor extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
		MyFrame f = (MyFrame)e.getSource();
		f.addPoint(new Point(e.getX(), e.getY()));
		f.repaint();  //使得添加的点可以立即显示
	}
}
```

以上程序新建了一个MyFrame类，通过监听鼠标的点击事件，在 MyFrame 对象中根据鼠标触点位置添加点对象。根据 repaint() 方法的调用顺序，在 MyFrame 类的 paint 方法中添加或者说重画点。

##Window 事件
- Window 事件所对应的事件类为 WindowEvent，所对应的的事件监听接口为 WindowListener。
- WindowListener 定义的方法有：
```
public void windowOpened(WindowEvent e)
public void windowClosing(WindowEvent e)
public void windowClosed(WindowEvent e)
public void windowIconified(WindowEvent e)
public void windowDeiconified(WindowEvent e)
public void windowActivated(WindowEvent e)
public void windowDeactivated(WindowEvent e)
```
- 与 WindowListener 对应的适配器为 WindowAdapter。

以 WindowListener 中的 windowClosing 方法为例
```
import java.awt.*;
import java.awt.event.*;

public class TestWindowClose {

	public static void main(String[] args) {
		new MyFrame("Window closing");
	}
}

class MyFrame extends Frame {
	MyFrame(String s) {
		super(s);
		setLayout(null);
		setBounds(300, 300, 300, 300);
		setVisible(true);
		setBackground(new Color(100, 180,240));
		addWindowListener(new MyWindowMonitor());
	}

	class MyWindowMonitor extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			setVisible(false);
			System.exit(0);
		} 
	}
}
```
上述是一个简单的通过内部类来实现的关闭窗口程序。

上述程序也可以用**匿名类来实现**
```
import java.awt.*;
import java.awt.event.*;

public class TestWindowClose {

	public static void main(String[] args) {
		new MyFrame("Window closing");
	}
}

class MyFrame extends Frame {
	MyFrame(String s) {
		super(s);
		setLayout(null);
		setBounds(300, 300, 300, 300);
		setVisible(true);
		setBackground(new Color(100, 180,240));
        MyWindowMonitor());

		addWindowListener(  //匿名内部类
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			} 
		});
	}
}
```
