public class Day2_Test4 {
	//Fibonacci���У�1,1,2,3,5,8������40������ֵ

	public static void main(String[] args) {
		System.out.println(fib(40));
	}

	public static int fib(int num) {  //ʹ�÷ǵݹ鷽��
		if (num == 1 || num == 2) {
			return 1;
		} else {
			int sum = 0;
			for (int pre1 = 1, pre2 = 1, i = 3; i <= num; i++) {
				sum = pre1+pre2;
				pre1 = pre2;
				pre2 = sum;
			}
			return sum;
		}
	}
}
