import java.awt.*;

public class TenButtons {

	public static void main(String[] args) {
		Frame f = new Frame("Java Frame");
		f.setBounds(100, 100, 400, 300);
		f.setBackground(null);
		f.setLayout(new GridLayout(2,1));  //��������Ϊ����һ��
		Panel p1 = new Panel(new BorderLayout());
		Panel p2 = new Panel(new BorderLayout());
		Panel p11 = new Panel(new GridLayout(2,1));  //���һ������һ�е�panel
		Panel p21 = new Panel(new GridLayout(2,2));  //���һ���������е�panel
		p1.add(new Button("BUTTON"), BorderLayout.WEST);
		p1.add(new Button("BUTTON"), BorderLayout.EAST);
		p11.add(new Button("BUTTON"));
		p11.add(new Button("BUTTON"));
		p2.add(new Button("BUTTON"), BorderLayout.WEST);
		p2.add(new Button("BUTTON"), BorderLayout.EAST);
		for (int i = 0; i < 4; i++) {  //����ĸ���ť
			p21.add(new Button("BUTTON"));
		}
		p1.add(p11);
		p2.add(p21);
		f.add(p1);
		f.add(p2);
		f.setVisible(true);
	}
}
