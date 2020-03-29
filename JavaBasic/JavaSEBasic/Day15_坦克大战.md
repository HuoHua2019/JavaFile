# Java learning_Day15

>本人学习视频用的是马士兵的，也在这里献上
><链接：https://pan.baidu.com/s/1qKNGJNh0GgvlJnitTJGqgA>
>提取码：fobs



## 主要内容：

#### 小游戏：坦克大战（图片版）



## 成果图：

<img src="https://i.loli.net/2020/03/29/dPjKUNlAySan2Oi.png" alt="javaw_6RgwZpPkQI.png" style="zoom:50%;" />

## 代码

#### TankClient.java 部分

摘要：下面程序主要用于显示背景界面，添加游戏的所有元素以控制及游戏的开始和结束。
```java
import java.awt.*;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.IOException;

public class TankClient extends Frame {
    //游戏边框的大小和背景颜色设定
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final Color BACKGROUND_COLOR = new Color(255-150, 255-220, 255-255);
	
    //我方坦克的初始化
	Tank myTank = new Tank(GAME_WIDTH/2, GAME_HEIGHT/2, true, Direction.STOP,this);
	
    //游戏增设的两堵墙，子弹和敌方坦克无法穿透，但是我方坦克可以穿过
	Wall w1 = new Wall(100, 200, 20, 150, this), w2 = new Wall(400, 100, 300, 20, this);
	
    //新建三个容器，分别用于装载爆炸、子弹和坦克
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	Image offScreenImage = null;
	
	MedicalKit mk = new MedicalKit();  //急救包，我方坦克可以用于治疗生命值
	
    //游戏中所有元素的绘制
	@Override
	public void paint(Graphics g) {
        //将屏幕上的所有子弹，爆炸，敌方坦克，我方坦克生命值绘制与游戏左上角
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks count:" + tanks.size(), 10, 90);
		g.drawString("tanks hitpoint:" + myTank.getHitPoint(), 10, 110);
		
        //当地方坦克被全部摧毁时，重新创建
		if(tanks.size() <= 0) {
			for(int i = 0; i < Integer.parseInt(PropertyManager.getProperty("reProduceTankCount")); i++) {
				tanks.add(new Tank(50+40*(i+1), 50, false, Direction.D, this));
			}
		}
		
        //子弹的所有效果呈现
		for(int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		
        //爆炸的所有效果呈现
		for(int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
        //坦克的所有效果呈现
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWal(w1);
			t.collidesWithWal(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.draw(g);
		myTank.eat(mk);  //我方坦克吃治疗包回血
		w1.draw(g);
		w2.draw(g);
		mk.draw(g);
	}

    //防止画面闪烁
	public void update(Graphics g) {
		if(offScreenImage == null) offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(BACKGROUND_COLOR);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
    //初始化游戏界面
	public void launchFrame() {
		
        //敌方坦克的初始化
		int initTankCount = Integer.parseInt(PropertyManager.getProperty("initTankCount"));
		for(int i = 0; i < initTankCount; i++) {
			tanks.add(new Tank(50+40*(i+1), 50, false, Direction.D, this));
		}
		
        //背景窗口的参数设定
		this.setLocation(400, 200);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);
		this.setBackground(BACKGROUND_COLOR);
		
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.KeyPressed(e);
		}
	}
}
```



#### Tank.java
摘要：下面程序主要用于相应坦克的相关操作。
```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
    //坦克参数的设定
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;	
	public static final Color GOOD_COLOR = new Color(215, 203, 167);
	public static final Color BAD_COLOR = new Color(191, 134, 114);
	public static final Color BLOOD_COLOR = new Color(202, 79, 79);
	public static final int HITPOINT = 100;
	
	private int x, y;
	private int oldX, oldY;  //记录坦克上一次的位置信息
	
	TankClient tc = null;
	
	private boolean good;
	private boolean live = true;
	private int hitPoint = HITPOINT;
	private BloodBar bb = new BloodBar();

	private static Random r = new Random();

	private boolean bL = false, bU = false, bR = false, bD = false;
	
	private Direction dir = Direction.STOP;  //坦克的移动方向
	private Direction ptDir = Direction.D;  //炮筒的朝向
	
	private int step = r.nextInt(12) + 3;
	
    //坦克的图片的添加和显示
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {  //静态语句块，用于添加坦克图片和按键对应图片的指令
		tankImages = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")),
			};
		//System.out.println("OK");
		
		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
	}
	
    //坦克默认大小的设定
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
    //坦克的具体绘制细节
	public void draw(Graphics g) {		
		//保存这一次的位置信息
        this.oldX = x;
		this.oldY = y;
		
        //判断地方坦克是否死亡，如果是，则移出容器
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		
        //绘制我方坦克的血条
		if(good) bb.draw(g);
		
        //我方坦克对应玩家按键的方向移动
		switch(ptDir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		}
		
        //让炮筒的方向与坦克移动方向保持一致
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		move();
	}
	
    //坦克移动的具体操作
	void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
        //坦克到达游戏边界的处理措施
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

        //设置敌方坦克的随机移动
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);				 
				dir = dirs[rn];
			}
			
			step--;
			
            //设置敌方坦克的随机开火
			if(r.nextInt(40) > 38) this.fire();
		}

	}
	
    //当地方坦克接触到墙时，使其返回上一次位置
	private void stay() {
		x = oldX;
		y = oldY;
	}
	
    //设置我方坦克的按键触发效果
	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_F2:
			if(!this.live) {
				this.live = true;
				this.hitPoint = HITPOINT;
			}
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		locateDirection();
	}
	
	//根据坦克的四个方向是否按下来判断如何前进
	void locateDirection() {
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

    //我方坦克的按键释放触发效果
	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		case KeyEvent.VK_A :
			superFire();
			break;
		}
		locateDirection();
	}
	
	private Missile fire() {
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, ptDir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}

	public Rectangle getRect() { 
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean isGood() {
		return good;
	}
	
    //敌方坦克碰到墙时的处理
	public boolean collidesWithWal(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
    //敌方坦克碰到敌方坦克时的处理
	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live && t.live && this.getRect().intersects(t.getRect())) {
					this.stay();
					return true;
				}
			}
		}
		return false;
	}
	
    //我方坦克的大招：朝八个方向开火
	private void superFire() {
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 8; i++) {
			fire(dirs[i]);
		}
	}
	
	public int getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(int hitPoint) {
		this.hitPoint = hitPoint;
	}
	
    //我方坦克的血条
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(BLOOD_COLOR);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * hitPoint/100;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	
    //我方坦克吃医疗包
	public boolean eat(MedicalKit mk) {
		if(this.live && mk.isLive() && this.getRect().intersects(mk.getRect())) {
			mk.setLive(false);
			this.hitPoint = HITPOINT;
			return true;
		}
		return false;
	}
}
```



#### Missile.java

摘要：下面程序主要用户响应子弹的相关操作。

```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;

public class Missile {
    //子弹参数的设定
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final Color MISSILE_BAD_COLOR = new Color(90, 90, 90);
	public static final Color MISSILE_GOOD_COLOR = new Color(255,111,111);
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	int x, y;
	Direction dir;
	
	private boolean good;  //判断是地方坦克的炮弹还是我方坦克的炮弹
	private boolean live = true;
	
	private TankClient tc;

    //子弹图片的添加和显示
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		missileImages = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/missileLD.gif")),
			};
		//System.out.println("OK");
		
		imgs.put("L", missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);
	}
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
    //子弹的具体绘制细节
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		}
		
		move();
	}

	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		}
		
        //当子弹移动到游戏边界以外，视为子弹移出容器
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live  = false;
			tc.missiles.remove(this);
		}
	}
	
	public boolean isLive() {
		return live;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
    //当子弹打击到坦克的具体动作
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(t.isGood()) {
				t.setHitPoint(t.getHitPoint() - 20);
				if(t.getHitPoint() <= 0) t.setLive(false);
			} else {
				t.setLive(false);
			}
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
    //当子弹打击到墙的具体操作
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
}
```



#### Explode.java

摘要：下面程序主要用于响应子弹打到坦克是的相应操作。

```java
import java.awt.*;

public class Explode {
    //爆炸的参数设定
	public static final Color EXPLODE_COLOR = new Color(245, 172, 110);
	
	int x, y;
	private boolean live = true;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
    //爆炸特效的添加和显示
	private static Image[] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif"))
	};
	
	int step = 0;
	
	private boolean init;  //初始化爆炸效果
	
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
        /*
         * 由于爆炸图片不能在第一次时立即显示，
         * 所以需要对爆炸效果进行初始化。
         */
		if(false == init) {
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
			
		g.drawImage(imgs[step], x, y, null);
		
		step++;
	}
}
```



#### Wall.java

摘要：下面程序是对游戏中墙的具体细节描述。

```java
import java.awt.*;

public class Wall {
    //墙的具体参数
	int x, y, w, h;
	TankClient tc;
	
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
```



#### MedicalKit.java 

摘要：下面程序主要描述医疗包的一些具体细节。

```java
import java.awt.*;

public class MedicalKit {
    //医疗包的参数设置
	int x, y, w, h;
	TankClient tc;
	private static final Color MEDICALKIT_COLOR = new Color(99, 208, 99);
    
	int step = 0;
	private boolean live = true;
	
	private int[][] pos= {  //医疗包会根据特定轨迹进行来回移动
			{350, 300}, {360,300}, {375,275}, {400, 200}, {360, 270}, {365, 290},{340, 280}
	};
	
	public MedicalKit() {
		x = pos[0][0];
		y = pos[0][1];
		w = h = 15;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		
		Color c = g.getColor();
		g.setColor(MEDICALKIT_COLOR);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
    
	private void move() {
		step++;
		if(step/20 == pos.length) {
			step = 0;
		}
		x = pos[step/20][0];
		y = pos[step/20][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
}
```



#### Direction.java

摘要：包含坦克和子弹的八个方向。

```java
public enum Direction {
	L, LU, U, RU, R, RD, D, LD, STOP
}
```



#### PropertyManager.java

摘要：下面程序用于获取配置文件的参数。

```java
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	static Properties props = new Properties();
	
    //加载配置文件
	static {
		try {
			props.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
    
    //返回配置文件中的对应参数
	public static String getProperty(String key) {
			return props.getProperty(key);
	}
}
```



#### tank.properties

摘要：程序的配置文件

```
initTankCount = 10
reProduceTankCount = 3
```

****

