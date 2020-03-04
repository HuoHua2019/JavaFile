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