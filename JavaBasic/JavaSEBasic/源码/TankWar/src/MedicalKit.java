import java.awt.*;

public class MedicalKit {
	int x, y, w, h;
	TankClient tc;
	
	int step = 0;
	private boolean live = true;

	private static final Color MEDICALKIT_COLOR = new Color(99, 208, 99);
	
	private int[][] pos= {
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
