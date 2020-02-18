public class Day2_Test1 {
	//输出1~100内前5个可以被3整除的数。
	
	public static void main(String[] args) {
		int i = 1, count = 0;
		while(i <= 100) {
			if(i % 3 == 0) {
				System.out.println(i);
				count++;
			}
			if(count == 5) break;
			i++;
		}
	}
}