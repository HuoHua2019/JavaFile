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
			throw new MyException("人数为负数，不合理", 1);
		} else {
			System.out.println("人数为" + num);
		}
	}
		
	public void manager() {
		try {
			regist(-1);
			System.out.println("登记成功");
		} catch (MyException e) {
			System.out.println("登记失败，出错类型：" + e.getId());
			e.printStackTrace();
		}

		System.out.println("操作结束");
	}

	public static void main(String[] args) {
		TestException t = new TestException();
		t.manager();
	}
}
