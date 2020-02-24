import java.util.*;

public class TestMap {

	public static void main(String[] args) {
		//Map m1 = new HashMap();
		Map<String, Integer> m1 = new HashMap<String, Integer>();  //generic
		//Map m2 = new TreeMap();
		Map<String, Character> m2 = new TreeMap<String, Character>();  //generic
		m1.put("one", 1);  //Autoboxing
		//m1.put("one", new Integer(1));
		m1.put("two", 2);  //Autoboxing
		//m1.put("two", new Integer(2));
		m1.put("three", 3);  //Autoboxing
		//m1.put("three", new Integer(3));
		m2.put("A", 'A');  //Autoboxing
		//m2.put("A", new Character('A'));
		m2.put("B", 'B');  //Autoboxing
		//m2.put("B", new Character('B'));
		
		System.out.println(m1.size());  //3
		System.out.println(m1.containsKey("one"));  //true
		System.out.println(m2.containsValue('A'));  //Autoboxing
		//System.out.println(m2.containsValue(new Character('A')));  //true
		if (m2.containsKey("A")) {
			char i = m2.get("A");  //generic
			//*char i = (Character)m2.get("A");  //Autoboxing
			//char i = ((Character)m2.get("A")).charValue();
			System.out.println(i);  //A
		}

		Map<String, Object> m3 = new HashMap<String, Object>(m1);
		m3.putAll(m2);
		System.out.println(m3);
	}
}
