class Animal {
	public String name;
	Animal(String name) {
		this.name = name;
	}	
}

class Cat extends Animal {
	public String eyesColor;
	Cat(String name, String color) {
		super(name);
		eyesColor = color;
	}	
}

class Dog extends Animal {
	public String furColor;
	Dog(String name, String color) {
		super(name);
		furColor = color;
	}	
}

public class TestCasting {

	public static void main(String[] args) {
		Animal a = new Animal("name");
		Cat c = new Cat("cat name", "blue");
		Dog d = new Dog("dog name", "black");

		System.out.println(a instanceof Animal);  //true
		System.out.println(c instanceof Animal);  //true
		System.out.println(d instanceof Animal);  //true
		System.out.println(a instanceof Cat);  //false

		
		Animal a1 = new Dog("dog name", "yellow");  //向上转型
		System.out.println(a1.name );  //true
		System.out.println(a1 instanceof Animal);  //true
		System.out.println(a1 instanceof Dog);  //true
		
        Animal a2 = new Animal("name");		
		a2 = new Dog("dog name", "yellow");
		Dog d1 = (Dog)a2;  //必须强制转换
		System.out.println(d1.furColor);  //yellow
	}
}
