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
		//addWindowListener(new MyWindowMonitor());

		addWindowListener(  //匿名内部类
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			} 
		});
	}

	/*
	class MyWindowMonitor extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			setVisible(false);
			System.exit(0);
		} 
	}
	*/
}
