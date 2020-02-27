public class ProducerConsumer {

	public static void main(String[] args) {
		Basket basket = new Basket();
		Producer producer = new Producer(basket);
		Consumer consumer = new Consumer(basket);
		new Thread(producer).start();
		new Thread(consumer).start();
	}
}

class Basket {  //��Ų�Ʒ
	int index = 0;
	static final int MAX_CAPICITY = 10;
	static final int MIN_CAPICITY = 0;
	Product[] arrproduct = new Product[MAX_CAPICITY];

	public synchronized void push(Product product) {  //����ָ��
		while (index == MAX_CAPICITY) {  //������������������ǽ���ȴ�״̬
			try {
				this.wait();	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}

		this.notify();  //��֪�����߽�������
		arrproduct[index] = product;
		index++;
	}

	public synchronized Product pop() {  //����ָ��
		while (index == MIN_CAPICITY) {  //��������������С����ʱ����ȴ�״̬
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.notify();  //��֪�����߽�������
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
			System.out.println("�����ˣ�" + product);
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
			System.out.println("�����ˣ�" + product);
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}