public class ArrayParser {
	//编写一个方法，返回一个double类型二维数组，数组中的元素通过解析字符串参数获得。
	public static void main(String[] args) {
		double[][] d;
		String s = "1,2;3,4,5;6,7,8";
		String[] sFirst = s.split(";");
		/*检验
			for (int i = 0; i < sFirst.length; i++) {
				System.out.println(sFirst[i]);
			}
		*/
		d = new double[sFirst.length][];
		for (int i = 0; i < sFirst.length; i++) {
			String[] sSecond = sFirst[i].split(",");
			d[i] = new double[sSecond.length];
			for (int j = 0; j < sSecond.length; j++) {
				d[i][j] = Double.parseDouble(sSecond[j]);
			}
			/*检验
				for (int j = 0; j < sSecond.length; j++) {
					System.out.println(sSecond[j]);
				}
			*/

		}
		
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				System.out.println(d[i][j] + " ");				
			}
			System.out.println();
		}
	}
}