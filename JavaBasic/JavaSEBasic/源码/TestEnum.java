public class TestEnum {
	public enum myColor {red, green, yellow};

	public static void main(String[] args) {
		myColor m = myColor.red;
		switch (m) {
		    case red:
				System.out.println("red");
				break;
			case green:
				System.out.println("green");
				break;
			case yellow:
				System.out.println("yellow");
		    default:
				System.out.println("");
		}
	}
}
