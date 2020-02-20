class Bike {
	String brand;
	public Bike() {
		System.out.println("父类中无参的构造器被调用了");
	}

	public Bike(String brand) {
		this.brand = brand;
		System.out.println("父类中" + brand + "牌构造器被调用了");
	}
}

public class Aceing extends Bike{
	public Aceing() {
		System.out.println("子类中无参的构造器调用了");
	}

	public Aceing(String brand) {
		super(brand);
		System.out.println("子类中" + brand + "牌构造器被调用了");
	}

	public static void main(String[] args) {
		Aceing a1 = new Aceing();
		Aceing a2 = new Aceing("Giant");
	}
}
