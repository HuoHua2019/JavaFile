class Bike {
	String brand;
	public Bike() {
		System.out.println("�������޲εĹ�������������");
	}

	public Bike(String brand) {
		this.brand = brand;
		System.out.println("������" + brand + "�ƹ�������������");
	}
}

public class Aceing extends Bike{
	public Aceing() {
		System.out.println("�������޲εĹ�����������");
	}

	public Aceing(String brand) {
		super(brand);
		System.out.println("������" + brand + "�ƹ�������������");
	}

	public static void main(String[] args) {
		Aceing a1 = new Aceing();
		Aceing a2 = new Aceing("Giant");
	}
}
