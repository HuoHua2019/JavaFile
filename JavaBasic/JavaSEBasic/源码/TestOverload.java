public class TestOverload {
	void max(int a, int b) {
		System.out.println(a > b ? a : b);
	}

	void max(float a, float b) {
		System.out.println(a > b ? a : b);		
	}


	public static void main(String[] args) {
		TestOverload t = new TestOverload();
		t.max(3, 4);
	}
}
