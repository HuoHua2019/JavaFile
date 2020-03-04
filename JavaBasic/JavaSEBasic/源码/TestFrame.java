import java.awt.*;

public class TestFrame {

	public static void main(String[] args) {
		Frame frame = new Frame("My first frame");
		frame.setSize(200, 100);  //设置大小，数字代表像素
		frame.setLocation(300, 300);
		frame.setBackground(Color.blue);
		frame.setResizable(false);  //不可调大小
		frame.setVisible(true);
	}
}
