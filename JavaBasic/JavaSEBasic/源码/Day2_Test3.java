public class Day2_Test3 {
	//Fibonacci���У�1,1,2,3,5,8������40������ֵ

	public static void main(String[] args) {
		System.out.println(fib(40));
	}

	public static int fib(int num) {  //ʹ�õݹ鷽��
		if (num == 1 || num == 2) {
			return 1;			
		} else {
			return fib(num-1) + fib(num-2);
		}
	}
}
