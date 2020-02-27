#生产者消费者问题

##问题描述
有两个进程：一组生产者进程和一组消费者进程共享一个初始为空、固定大小为n的缓存（缓冲区）。生产者的工作是制造一段数据，只有缓冲区没满时，生产者才能把消息放入到缓冲区，否则必须等待，如此反复; 同时，只有缓冲区不空时，消费者才能从中取出消息，一次消费一段数据（即将其从缓存中移出），否则必须等待。由于缓冲区是临界资源，它只允许一个生产者放入消息，或者一个消费者从中取出消息。

##代码实现
```
public class ProducerConsumer {

	public static void main(String[] args) {
		Basket basket = new Basket();
		Producer producer = new Producer(basket);
		Consumer consumer = new Consumer(basket);
		new Thread(producer).start();
		new Thread(consumer).start();
	}
}

class Basket {  //存放产品
	int index = 0;
	static final int MAX_CAPICITY = 10;
	static final int MIN_CAPICITY = 0;
	Product[] arrproduct = new Product[MAX_CAPICITY];

	public synchronized void push(Product product) {  //生产指令
		while (index == MAX_CAPICITY) {  //当产量等于最大容量是进入等待状态
			try {
				this.wait();	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}

		this.notify();  //告知消费者进行消费
		arrproduct[index] = product;
		index++;
	}

	public synchronized Product pop() {  //消费指令
		while (index == MIN_CAPICITY) {  //当消费量等于最小容量时进入等待状态
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.notify();  //告知生产者进行生产
		index--;
		return arrproduct[index];
	}
}

class Product {
	int id;

	Product(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "product id--" + id;
	}
}

class Producer implements Runnable {
	Basket basket = null;
	Producer(Basket basket) {
		this.basket = basket;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			Product product = new Product(i);
			basket.push(product);
			System.out.println("生产了：" + product);
			try {
				Thread.sleep((int)(Math.random()*200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {
	Basket basket = null;
	Consumer(Basket basket) {
		this.basket = basket;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			Product product = basket.pop();
			System.out.println("消费了：" + product);
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
```
##样例输出
```
消费了：product id--0
生产了：product id--0
生产了：product id--1
生产了：product id--2
生产了：product id--3
消费了：product id--3
消费了：product id--2
生产了：product id--4
生产了：product id--5
生产了：product id--6
生产了：product id--7
生产了：product id--8
生产了：product id--9
消费了：product id--9
生产了：product id--10
生产了：product id--11
生产了：product id--12
生产了：product id--13
消费了：product id--13
消费了：product id--12
生产了：product id--14
生产了：product id--15
消费了：product id--15
生产了：product id--16
消费了：product id--16
生产了：product id--17
消费了：product id--17
生产了：product id--18
消费了：product id--18
生产了：product id--19
消费了：product id--19
消费了：product id--14
消费了：product id--11
消费了：product id--10
消费了：product id--8
消费了：product id--7
消费了：product id--6
消费了：product id--5
消费了：product id--4
消费了：product id--1
```