#Java循环与分支练习

##习题1：
输出1~100内前5个可以被3整除的数。
```
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
```
输出：
```
3
6
9
12
15
```
##习题2：
输出101~200内的质数。
```
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
```
输出
```
101
103
107
109
113
127
131
137
139
149
151
157
163
167
173
179
181
191
193
197
199
```