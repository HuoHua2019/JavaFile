public class  ArrayDefaultInitialization{

	public static void main(String[] args) {
	int a[] = new int[5];
	Date[] days = new Date[3];
	System.out.println(a[3]);
	System.out.println(days[2]);
	}
}

class Date {
	int year, month, day;
	
	Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
}