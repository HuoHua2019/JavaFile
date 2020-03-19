# Java learning_Day14

>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
>提取码：fobs



## 主要内容：

#### 小游戏：贪吃蛇



## 成果图：

![MUNcLp7qa6.png](https://i.loli.net/2020/03/09/Gz93u2vyjNPnxO1.png)

## 代码

#### Yard.java 部分

摘要：下面程序段主要用于显示背景界面以及游戏的开始与结束。

````java
import java.awt.*;
import java.awt.event.*;

public class Yard extends Frame {
	PaintThread paintThread = new PaintThread();
	
	private boolean flag = true;  //判断游戏是否结束
	
    //游戏的行列和每个格子的大小
	public static final int ROWS = 30;
	public static final int COLS = 30;
	public static final int BLOCK_SIZE = 15;
	
	private int score = 0;
	
	Snake s = new Snake(this);
	Egg e = new Egg();
	
	Image offScreenImage = null;
	
    //初始化游戏界面
	public void launch() {
		this.setLocation(200, 200);
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());;
		
		new Thread(paintThread).start();
	}
	
	public static void main(String[] args) {
		new Yard().launch();
	}
	
    //当游戏终止操作
	public void stop() {
		flag = false;
	}

    //画出背景，游戏动画的执行部分
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		g.setColor(Color.DARK_GRAY);
		//画出横线
		for (int i = 1; i < ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
		}
		//画出竖线
		for (int i = 1; i < COLS; i++) {
			g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, ROWS * BLOCK_SIZE);
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("score" + score, 10, 60);
		
		
		s.eat(e);
		e.draw(g);
		s.draw(g);
		
        //当游戏结束时，提示玩家
		if(flag == false) {
			g.setFont(new Font("华文彩云", Font.BOLD | Font.HANGING_BASELINE, 50));
			g.drawString("game over", 105, 240);
			paintThread.pause();
		}
		g.setColor(c);
	}
	
    //防止画面闪烁
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

    //控制动作的进行和终止
	private class PaintThread implements Runnable {
		private boolean pause = false;
		
		public void run() {
			while(flag) {
				if(pause) continue;
				else repaint();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void pause() {
			this.pause = true;
		}
		
		public void reStart() {
			pause = false;
			s = new Snake(Yard.this);
		}
	}
	
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_F2) paintThread.reStart();  //当按下F2时重玩游戏
			s.keyPressed(e);
		}
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		
	}
	
}
````



#### Snake.java

摘要：下面程序段主要用于响应蛇的相关操作。

```java
import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	
	private Node n = new Node(20, 30, Dir.L);
	private Yard y;
	
	public  Snake(Node node) {
		head = node;
		tail = node;
		size = 1;
	}
	
	public Snake(Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	
    //将节点加在蛇的尾巴上
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L :
			node = new Node(tail.row, tail.col + 1, tail.dir);
			break;
		case U :
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case R :
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case D :
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size++;
	}
	
    //将节点加在蛇的头部
	public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case L :
			node = new Node(head.row, head.col - 1, head.dir);
			break;
		case U :
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case R :
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case D :
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size++;
	}
	
    //画蛇
	public void draw(Graphics g) {
		if (size <= 0) return;
		move();
		for (Node n = head; n != null; n = n.next) {
			n.draw(g);
		}
	}
	
    //控制蛇的移动
	private void move() {
		addToHead();
		deleteFromTail();
		checkDead();
	}

    //检验蛇是否死亡
	private void checkDead() {
		if(head.row < 2 || head.col < 0 || head.row > Yard.ROWS || head.col > Yard.COLS) {
			y.stop();
		}
		
		for(Node n = head.next; n != null; n = n.next) {
			if(head.row == n.row & head.col == n.col) y.stop();
		}
		
	}

    //将蛇的尾部去除，主要用于蛇的移动
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.prev;
		tail.next = null;
		
	}

    //蛇的每一个节点的属性
	private class Node {
		int w = Yard.BLOCK_SIZE;
		int h = Yard.BLOCK_SIZE;
		int row, col;
		Dir dir = Dir.L;
		Node next = null;
		Node prev = null; 
		
		Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
        //蛇的身体的绘画
		void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			g.setColor(c);
		}
	}

	//蛇吃了蛋的响应
	public void eat(Egg e) {
		if(this.getRect().intersects(e.getRect())) {
			e.reAppear();
			this.addToHead();
			y.setScore(y.getScore() + 5);
		}
	}
	
    //返回蛇头的位置
	private Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	}
	
    //响应玩家按键
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			if(head.dir != Dir.R) head.dir = Dir.L;
			break;
		case KeyEvent.VK_UP :
			if(head.dir != Dir.D) head.dir = Dir.U;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir != Dir.L) head.dir = Dir.R;
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir != Dir.U) head.dir = Dir.D;
			break;
		}
	}
}
```



#### Egg.java 部分

摘要：下面程序段主要用于生成蛋。

```java
import java.awt.*;
import java.util.Random;

public class Egg {

	int row, col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	private static Random r = new Random();
	private Color color = Color.GREEN;

	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Egg() {
		this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS));
	}
	
    //使蛋随机出现
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2) + 2;
		this.col = r.nextInt(Yard.COLS);
	}
	
    //返回蛋的位置
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * this.col, Yard.BLOCK_SIZE * this.row, this.w, this.h);
	}
	
    //画蛋
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
		g.setColor(c);
		if(color == Color.GREEN) color = Color.RED;
		else color = Color.GREEN;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
```



#### Dir.java 部分

摘要：四个方向键。

```java
public enum Dir {
	L, U, R, D
}
```

