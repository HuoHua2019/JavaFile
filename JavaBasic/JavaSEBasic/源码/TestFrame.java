import java.awt.*;

public class TestFrame {

	public static void main(String[] args) {
		Frame frame = new Frame("My first frame");
		frame.setSize(200, 100);  //���ô�С�����ִ�������
		frame.setLocation(300, 300);
		frame.setBackground(Color.blue);
		frame.setResizable(false);  //���ɵ���С
		frame.setVisible(true);
	}
}
