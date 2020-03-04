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

/*
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
*/