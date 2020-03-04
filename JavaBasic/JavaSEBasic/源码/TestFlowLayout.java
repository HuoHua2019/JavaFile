import java.awt.*;

public class TestFlowLayout {

	public static void main(String[] args) {
		Frame frame = new Frame("FlowLayout");
		Button b1 = new Button("These");
		Button b2 = new Button("are");
		Button b3 = new Button("buttons");
		frame.setLayout(new FlowLayout());	//Ä¬ÈÏ¾ÓÖÐ¶ÔÆë
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.setSize(100, 100);
		frame.setVisible(true);
	}
}
