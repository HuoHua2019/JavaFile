public class Dog {
	//创建狗这个类
	
	char furColor;
	char voice;
	int height;
	int weight;
	
	//狗拥有抓老鼠这个方法
	public void catchMouse(Mouse mouse) {
		//catch mouse
		mouse.scream();  //老鼠有发出尖叫这个动作(方法)
	}

	public static void main(String[] args) {
		Dog dog = new Dog();  //创建狗这个对象
		Mouse mouse = new Mouse();  //创建老鼠这个对象
		dog.catchMouse(mouse);  //狗拿耗子
	}
}