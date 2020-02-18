public class Day2_Test4 {
	//Fibonacci数列：1,1,2,3,5,8，…第40个数的值

	public static void main(String[] args) {
		System.out.println(fib(40));
	}

	public static int fib(int num) {  //使用非递归方法
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
