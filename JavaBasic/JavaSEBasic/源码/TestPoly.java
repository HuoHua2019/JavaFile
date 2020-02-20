class Animal {
	private String name;

	Animal(String name) {
		this.name = name;
	}
	
	public void enjoy(){
	  System.out.println("½ÐÉù......");
	}	
}

class Cat extends Animal {
	private String eyesColor;

	Cat(String n,String c) {
		super(n); 
		eyesColor = c;
	}

	public void enjoy() {
		System.out.println("Ã¨½ÐÉù......");
	}
}

class Dog extends Animal {
	private String furColor;

	Dog(String n,String c) {
		super(n); 
		furColor = c;
	}

	public void enjoy() {
		System.out.println("¹·½ÐÉù......");
	}
}

class Lady {
    private String name;
    private Animal pet;

    Lady(String name, Animal pet) {
        this.name = name; 
        this.pet = pet;
    }

    public void myPetEnjoy(){
        pet.enjoy();
    }
}

public class TestPoly {

    public static void main(String args[]){
        Cat c = new Cat("catname","blue");
        Dog d = new Dog("dogname","black");
        Lady l1 = new Lady("l1",c);
        Lady l2 = new Lady("l2",d);
        l1.myPetEnjoy();
        l2.myPetEnjoy();
    }
}