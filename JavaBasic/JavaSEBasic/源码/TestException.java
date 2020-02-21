class MyException extends Exception {
	private int id;

	public MyException(String message, int id) {
		super(message);
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
public class TestException {

	public void regist(int num) throws MyException{
		if (num < 0) {
			throw new MyException("����Ϊ������������", 1);
		} else {
			System.out.println("����Ϊ" + num);
		}
	}
		
	public void manager() {
		try {
			regist(-1);
			System.out.println("�Ǽǳɹ�");
		} catch (MyException e) {
			System.out.println("�Ǽ�ʧ�ܣ��������ͣ�" + e.getId());
			e.printStackTrace();
		}

		System.out.println("��������");
	}

	public static void main(String[] args) {
		TestException t = new TestException();
		t.manager();
	}
}
