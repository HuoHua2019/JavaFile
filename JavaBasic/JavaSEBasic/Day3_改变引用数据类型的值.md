#Java改变引用数据类型的值
在Java中，引用数据类型的数据传递的是值(地址)的拷贝
对于以下代码
```
class BirthDate {
    private int day;
    private int month;
    private int year;
    
    public BirthDate(int d, int m, int y) {
        day = d; 
        month = m; 
        year = y;
    }
    
    public void setDay(int d) {
    	day = d;
  	}
  	
    public void setMonth(int m) {
    	month = m;
    }
    
    public void setYear(int y) {
    	year = y;
    }
    
    public int getDay() {
    	return day;
    }
    
    public int getMonth() {
    	return month;
    }
    
    public int getYear() {
    	return year;
    }
    
    public void display() {
    	System.out.println
        (day + " - " + month + " - " + year);
    }
}

public class Test {
    public static void main(String args[]){
        Test test = new Test();
        int date = 9;
        BirthDate d1= new BirthDate(7,7,1970);
        BirthDate d2= new BirthDate(1,1,2000);    
        test.change1(date);
        test.change2(d1);
        test.change3(d2);
        System.out.println("date=" + date);
        d1.display();
        d2.display();
    }
    
    public void change1(int i){
    	i = 1234;
    }
    
    public void change2(BirthDate b) {
    	b = new BirthDate(22,2,2004);
    }
    
    public void change3(BirthDate b) {
    	b.setDay(22);
    }
}
```
上述是一个对生日对象进行初始化，并进行修改的程序。程序通过构造函数进行初始化，并尝试使用不同方法对对象存储的值进行修改。

Birthdate是一个引用数据类型，简单地用方法传递值不能改变对象存储的值，应该使用类Birthdate的特定方法setDay。

##图解引用数据类型的值传递和内存变化
局部变量存储在栈内存中，对象的值存储在堆内存中，故进行初始化后，内存中的分布会呈现如下图

![PotPlayerMini64_Lj4JOGFnoF.png](https://i.loli.net/2020/02/19/ifwOIbAjqz84rRD.png)

此时栈内存中存储了对象test、d1、d2的地址以及局部变量date的值，而堆内存中存储了d1的值和d2的值，由栈内存中d1和d2的地址指向堆内存中各自的值。

而当执行了下面程序段后
```
        test.change1(date);
        /*
          省略中间的代码
        */

    public void change1(int i){
    	/*
          尚未执行的代码
        */
    }
```
此时内存如下图所示

![PotPlayerMini64_cI7fsNJcxJ.png](https://i.loli.net/2020/02/19/3rydZD4vA2puCnm.png)

在方法change1中，程序段在栈内存中为形式参数i分配了新的空间，并通过值传递将其赋值为date的值，也就是9。

当开始执行代码段
```
    public void change1(int i){
    	i = 1234;
    }
```

![PotPlayerMini64_BOCqo5i6lq.png](https://i.loli.net/2020/02/19/jr37tWUVRCFOMal.png)

形式参数i的值被改为1234，而date中的值并没有改变。

当change1方法执行完毕后，为局部变量所分配的空间全部消失，而date的值依然为9，修改失败。

![PotPlayerMini64_Lj4JOGFnoF.png](https://i.loli.net/2020/02/19/ifwOIbAjqz84rRD.png)

继续执行change2
```
        test.change2(d1);
        /*
          省略中间的代码
        */

    public void change2(BirthDate b) {
    	/*
          尚未执行的代码
        */
    }
```
此时内存如下图所示

![PotPlayerMini64_x5dlZKd9EM.png](https://i.loli.net/2020/02/19/ld1DkoMG3jeXRcW.png)

系统在栈内存中开辟新的空间给b，并将对象d2的值(地址)传递给了形式参数对象b，使得b的地址指向了一个和d2地址指向相同的堆内存空间，也就是指向对象d2存储的值。看起来就要修改成功了，不是吗？

但当执行下面代码段
```
    public void change2(BirthDate b) {
    	b = new BirthDate(22,2,2004);
    }
```

![GmUsTgE52X.png](https://i.loli.net/2020/02/19/IKJvBYEgPyhVpzu.png)

这时b的地址改为指向新new出来的对象，内容悄然发生改变。

当change2方法执行完毕后，为局部变量所分配的空间全部消失，存储在堆内存中的值也会在一段时间后被垃圾回收机制回收，而d2的值并没有发生变化，修改依然失败。

![PotPlayerMini64_Lj4JOGFnoF.png](https://i.loli.net/2020/02/19/ifwOIbAjqz84rRD.png)

最后看change3代码段
```
        test.change3(d2);
        /*
          省略中间的代码
        */

    public void change3(BirthDate b) {
    	/*
          尚未执行的代码
        */
    }
```

![PotPlayerMini64_x5dlZKd9EM.png](https://i.loli.net/2020/02/19/ld1DkoMG3jeXRcW.png)

类似于change2，系统给形式参数b分配了空间，指向d2对象的值

接下来执行setDay方法
```
    public void setDay(int d) {
    	day = d;
  	}
        /*
          省略中间的代码
        */
    public void change3(BirthDate b) {
    	b.setDay(22);
    }
```
setDay是类Birthdate中的方法，调用setDay方法后，会将setDay(int d)中的形式参数d传递给day这个成员变量，反映在内存中就是b的属性day变为22，由于b和d2在堆内存中所指向的是同一块内存空间，所以修改成功。

![PotPlayerMini64_9uwcBvSpMm.png](https://i.loli.net/2020/02/19/u2IOFGAhBaT6X8i.png)

最后b这块内存消失，此时Birthdate对象d2的值已经发生改变。

![PotPlayerMini64_OB4T6bSMh1.png](https://i.loli.net/2020/02/19/PbyaxTpsjBF4k6u.png)