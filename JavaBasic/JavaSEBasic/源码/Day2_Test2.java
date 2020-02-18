public class Day2_Test2 {
	//输出101~200内的质数。
 
	public static void main(String[] args) {
		for (int odd = 101; odd <= 200; odd += 2) {
			boolean flag = true;
			for(int div = 3; div < odd/2; div += 2) {
				if (odd % div == 0) {
					flag = false;
					break;
				}
			}
			if (!flag) continue;
			System.out.println(odd);
		}
	}
}