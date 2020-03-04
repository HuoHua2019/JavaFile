import java.awt.*;

public class CenterPanel {

	public static void main(String[] args) {
		MyFrame mf = new MyFrame("FrameWithPanel", 100, 100, 400, 300);
	}
}

class MyFrame extends Frame {
	private Panel p;
	MyFrame(String name, int x, int y, int width, int height) {
		super(name);
		setLayout(null);		
		setBounds(x, y, width, height);
		setVisible(true);
		p = new Panel(null);
		p.setBounds(width/4, height/4, width/2, height/2);
		p.setBackground(Color.YELLOW);
		setBackground(Color.BLUE);
		add(p);
	}
}