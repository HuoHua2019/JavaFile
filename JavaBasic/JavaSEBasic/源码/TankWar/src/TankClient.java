import java.awt.*;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.IOException;

public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final Color BACKGROUND_COLOR = new Color(255-150, 255-220, 255-255);
	
	Tank myTank = new Tank(GAME_WIDTH/2, GAME_HEIGHT/2, true, Direction.STOP,this);
	
	Wall w1 = new Wall(100, 200, 20, 150, this), w2 = new Wall(400, 100, 300, 20, this);
	
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	Image offScreenImage = null;
	
	MedicalKit mk = new MedicalKit();
	
	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks count:" + tanks.size(), 10, 90);
		g.drawString("tanks hitpoint:" + myTank.getHitPoint(), 10, 110);
		
		if(tanks.size() <= 0) {
			for(int i = 0; i < Integer.parseInt(PropertyManager.getProperty("reProduceTankCount")); i++) {
				tanks.add(new Tank(50+40*(i+1), 50, false, Direction.D, this));
			}
		}
		
		for(int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			//if(!m.isLive()) missiles.remove(m);
			m.draw(g);
		}
		
		for(int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWal(w1);
			t.collidesWithWal(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.draw(g);
		myTank.eat(mk);
		w1.draw(g);
		w2.draw(g);
		mk.draw(g);
	}

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
	
	public void launchFrame() {
		
		int initTankCount = Integer.parseInt(PropertyManager.getProperty("initTankCount"));
		for(int i = 0; i < initTankCount; i++) {
			tanks.add(new Tank(50+40*(i+1), 50, false, Direction.D, this));
		}
		
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
