import java.awt.*;

public class TestFlowLayout2 {

	public static void main(String[] args) {
		Frame frame = new Frame("Flow Layout");
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 20, 40);  //水平间距20，垂直间距40
		frame.setLayout(f);
		frame.setLocation(200, 200);
		frame.setSize(100, 100);
		for (int i = 0; i < 5; i++) {
			frame.add(new Button("Button"));
		}
		frame.setVisible(true);
	}
}
