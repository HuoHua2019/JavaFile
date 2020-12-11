# Oracle learning_Day12

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## SET 操作符

### 1.UNION 操作符

UNION 操作符返回两个查询的结果集的并集

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201008163844.png" alt="POWERPNT_4yYZyazrdf" style="zoom:50%;" />

举例：

```sql
--获取表employees01和employees02的相关列
SELECT employee_id, job_id
FROM   employees01
UNION
SELECT employee_id, job_id
FROM   employees02;
```

*注：如果要给列起别名，应当起在UNION前的表中而非第二个UNION后的表中，否则将不起作用。*

<br>

#### UNION ALL 操作符

UNION ALL 操作符返回两个查询的结果集的并集。**对于两个结果集的重复部分，不去重**。

<br>

### 2.INTERSECT 操作符

INTERSECT 操作符返回两个结果集的交集

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201008165043.png" alt="POWERPNT_7Ji4VTljnt" style="zoom: 50%;" />

举例

```sql
--获取表employees01和employees02列的共同内容
SELECT employee_id, job_id
FROM   employees01
INTERSECT
SELECT employee_id, job_id
FROM   employees02;            			   
```

<br>

### 3.MINUS 操作符

MINUS操作符：返回两个结果集的差集

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201008165509.png" alt="POWERPNT_mVaqX74ZRv" style="zoom:50%;" />

```sql
--获取表employees01除去employees02列的内容
SELECT employee_id, job_id
FROM   employees01
MINUS
SELECT employee_id, job_id
FROM   employees02;
```

<br>

### 4.使用 SET 操作符注意事项

- 在SELECT 列表中的列名和表达式在**数量**和**数据类型**上要相对应
- 括号可以改变执行的顺序
- ORDER BY 子句:
  - 只能在语句的最后出现
  - 可以使用第一个查询中的列名, 别名或相对位置 
- 除 UNION ALL之外，系统会自动将重复的记录删除
- 系统将第一个查询的列名显示在输出中
- 除 UNION ALL之外，系统自动按照第一个查询中的第一个列的升序排列 

<br>

### 5.匹配SELECT 语句

 当 SET 前后的表所选择的列并不完全一一对应时，需要在选择时进行匹配。

例如：

下面语句中，表 employees 中的列 hire_date 在表 departments 中并没有对应的列，而 hire_date 是 date 类型的变量，因此在表 departments 中填充 TO_DATE(null) 来表示缺省值。而 TO_NUMBER(null) 同理

```sql
SELECT department_id, TO_NUMBER(null) location,
       hire_date
FROM   employees
UNION
SELECT department_id, location_id, TO_DATE(null)
FROM   departments;
```

也可以填充符合类型的对应值，如 0 对应 NUMBER

```sql
SELECT employee_id, job_id, salary
FROM   employees
UNION
SELECT employee_id, job_id, 0
FROM   job_history;
```

<br>

#### 使用相对位置排序

使用各行的相对位置进行自定义排序

```sql
COLUMN order NOPRINT  /* SQLPLUS语法，意为隐藏列 order 使之不在结果中出现*/
SELECT 'sing' AS "My dream", 3 order
FROM dual
UNION
SELECT 'I`d like to teach', 1
FROM dual
UNION 
SELECT 'the world to', 2
FROM dual
ORDER BY 2;
/* 输出
My Dream
------------------
I`d like to teach
the world to
sing
*/
```

<br>