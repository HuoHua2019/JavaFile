public class Day2_Test1 {
	//���1~100��ǰ5�����Ա�3����������
	
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