public class Dog {
	//�����������
	
	char furColor;
	char voice;
	int height;
	int weight;
	
	//��ӵ��ץ�����������
	public void catchMouse(Mouse mouse) {
		//catch mouse
		mouse.scream();  //�����з�������������(����)
	}

	public static void main(String[] args) {
		Dog dog = new Dog();  //�������������
		Mouse mouse = new Mouse();  //���������������
		dog.catchMouse(mouse);  //���ú���
	}
}