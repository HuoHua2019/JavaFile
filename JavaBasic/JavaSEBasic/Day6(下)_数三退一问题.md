##问题描述
有100人围成一圈，顺序排号。从第1个人开始报数（从1到3报数），凡报到3的人退出圈子，问最后留下的是原来的第几号的那位。
代码实现：
```
public class Count3Quit1 {
    //利用数组
	public static void main(String[] args) {
		boolean[] arr = new boolean[100];

		for(int i=0; i<arr.length; i++) {
			arr[i] = true;
		}
		
		int leftCount = arr.length;
		int countNum = 0;
		int index = 0;
		
		while(leftCount > 1) {
			if(arr[index] == true) {
				countNum ++;
				if(countNum == 3) {
					countNum = 0;
					arr[index] = false;
					leftCount --;
				}
			}
			
			index ++;
			
			if(index == arr.length) {
				index = 0;
			}
		}
		
		for(int i=0; i<arr.length; i++) {
			if(arr[i] == true) {
				System.out.println(i+1);
			}
		}
	}
}
```
面向对象风格的实现：
```
public class Count3Quit2 {

	public static void main(String[] args) {
		KidCircle kc = new KidCircle(100);
		int deleteCount = 0;
		Kid kid = kc.first;

		while (kc.count > 1) {
			deleteCount++;
			if (deleteCount == 3) {
				deleteCount = 0;
				kc.delete(kid);
			}

			kid = kid.right;
		}

		System.out.println(kid.id+1);
	}
}

class Kid {
	int id;
	Kid left, right;
}

class KidCircle {
	Kid first, last;
	int count = 0;

	public KidCircle(int num) {
		for (int i =0; i < num; i++) {
			add();		
		}
	}

	public void add() {
		Kid kid = new Kid();
		kid.id = count;

		if (count < 0) {
			return ;
		} else if (count == 0) {
			kid.left = kid;
			kid.right = kid;
			first = kid;
			last = kid;
		} else {
			first.left = kid;
			last.right = kid;
			kid.left = last;
			kid.right = first;
			last = kid;
		}

		count++;
	}

	public void delete(Kid kid) {
		if (count <= 0) {
			return ;
		} else if (count == 1) {
			first = last = null;
		} else {
			kid.left.right = kid.right;
			kid.right.left = kid.left;
			if (kid == first) {
				first = kid.right;
			}
			if (kid == last) {
				last = kid.left;
			}
		}

		count--;
	}
}

```