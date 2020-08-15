# Oracle learning_Day2(下)

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B?p=2
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 一、SQL中不同类型的函数

### 1.图解SQL函数



<br>

### 2.字符和日期的处理

•**字符和日期要包含在<u>单引号</u>中**。

•**字符大小写敏感，日期格式敏感**。

•默认的日期格式是 DD-MON月-RR。

举个例子：

```sql
--筛选人名为Whalen的特定行
SELECT last_name, job_id, department_id
FROM employees
WHERE last_name = 'Whalen';

--筛选日期为1994年6月7日的特定行
SELECT last_name, hire_date, department_id
FROM employees
WHERE hire_date = '7-6月-1994'
```

<br>

### 3.比较运算

| 操作符 | 含义                         |
| ------ | ---------------------------- |
| **=**  | **等于** **(不是 ==)**       |
| **>**  | **大于**                     |
| **>=** | **大于、等于**               |
| **<**  | **小于**                     |
| **<=** | **小于、等于**               |
| **<>** | **不等于** **(也可以是 !=)** |

**注：赋值使用** **:=** **符号**

举个例子：

```sql
--筛选薪水不大于3000的特定行
SELECT last_name, salary
FROM employees
WHERE salary <= 3000;
```

<br>

### 4.其它比较运算

| 操作符                  | 含义                            |
| ----------------------- | ------------------------------- |
| **BETWEEN ... AND ...** | **在两个值之间** **(包含边界)** |
| **IN(set)**             | **等于值列表中的一个**          |
| **LIKE**                | **模糊查询**                    |
| **IS NULL**             | **空值**                        |

<br>

**(1) BETWEEN**

使用 BETWEEN 运算来显示在一个区间内的值

举个例子：

```sql
--筛选薪水在2500到3500(包括2500和3500)的特定行
SELECT last_name, salary
FROM employees
WHERE salary BETWEEN 2500 AND 3500;
```

<br>

**(2) IN**

使用 IN运算显示列表中的值

举个例子：

```sql
--筛选管理编号是100,101,201三者之一的特定行
SELECT employee_id, last_name, salary, manager_id
FROM employees
WHERE manager_id IN (100, 101, 201);
```

<br>

**(3) LIKE**

•使用 LIKE 运算选择类似的值

•选择条件可以包含字符或数字:

​	-- **‘%’** 代表零个或多个字符(任意个字符)。

​	-- **‘\_’** 代表一个字符。

​	-- **‘%’** 和 **‘\_’** 可以同时使用。

举个例子：

```sql
--筛选姓氏中以S开头的特定行
SELECT first_name
FROM employees
WHERE first_name LIKE 'S%';

--筛选姓名的第二个字母是o的特定行
SELECT last_name
FROM employees
WHERE last_name LIKE '_o%';
```

<br>
**Tips--关于转义符：**

在上面的内容中，我们知道字符 ‘%’ 和 ‘\_’ 拥有一些特殊含义，但是如果只是想表达字符串中含有该字符，我们需要外加说明。

•回避特殊符号的：**使用转义符**。例如：将[%]转为[\%]、[_]转为[\\\_]，然后再加上[ESCAPE ‘\’] 即可。

举个例子：

```sql
--筛选工作编号中以IT_开头的特定行
SELECT job_id
FROM jobs
WHERE job_id LIKE 'IT\_%' escape '\';
```

<br>

**(4) NULL**

使用 IS (NOT) NULL 判断空值。

举个例子：

```sql
--筛选管理编号为空的特定行
SELECT last_name, manager_id
FROM employees
WHERE manager_id IS NULL;
```



### 5.逻辑运算

| 操作符  | 含义       |
| ------- | ---------- |
| **AND** | **逻辑并** |
| **OR**  | **逻辑或** |
| **NOt** | **逻辑否** |

<br>

**(1) AND**

AND 要求并的关系为真。

举个例子：

```sql
--筛选薪水大于或等于10000，且工作编号中含有MAN的特定行
SELECT employee_id, last_name, job_id, salary
FROM employees
WHERE salary >=10000
AND job_id LIKE '%MAN%';
```

<br>

**(2) OR**

OR 要求或关系为真

举个例子：

```sql
--筛选薪水大于或等于10000，或者工作编号中含有MAN的特定行
SELECT employee_id, last_name, job_id, salary
FROM employees
WHERE salary >= 10000
OR job_id LIKE '%MAN%';
```

<br>

**(3) NOT**

举个例子：

```sql
--筛选工作编号不含有'IT_PROG', 'ST_CLERK', 'SA_REP'的特定行
SELECT last_name, job_id
FROM employees
WHERE job_id 
NOT IN ('IT_PROG', 'ST_CLERK', 'SA_REP');
```



### 6.优先级

| 优先级 |                                    |
| ------ | ---------------------------------- |
| **1**  | **算术运算符**                     |
| **2**  | **连接符**                         |
| **3**  | **比较符**                         |
| **4**  | **IS [NOT] NULL, LIKE,  [NOT] IN** |
| **5**  | **[NOT] BETWEEN**                  |
| **6**  | **NOT**                            |
| **7**  | **AND**                            |
| **8**  | **OR**                             |

**可以使用括号改变优先级顺序**

<br>

## 二、排序

### ORDER BY子句

•使用 ORDER BY 子句排序

​	-- **ASC**（ascend）**:** **升序**

​	-- **DESC**（descend）**:** **降序**

•**ORDER BY 子句在SELECT语句的<u>结尾</u>。**

•默认**升序**排序

```sql
SELECT *|{[DISTINCT] column|expression [alias],...}
FROM table
[WHERE condition(s)]
[ORDER BY {column, expr, alias} [ASC|DESC]];
```

举个例子：

```sql
--默认升序排序
--将特定列按照列hire_date进行升序排序
SELECT last_name, job_id, department_id, hire_date
FROM employees
ORDER BY hire_date ;

--降序排序
--将特定列按照列hire_date进行降序排序
SELECT last_name, job_id, department_id, hire_date
FROM employees
ORDER BY hire_date DESC ;

--按别名排序
--将特定列按照列annsal(salary*12的别名)进行降序排序
SELECT employee_id, last_name, salary*12 annsal
FROM employees
ORDER BY annsal;

--多个列排序
--将特定列按照列department_id进行升序排序，其中数值相同的列再按照salary进行降序排序
SELECT last_name, department_id, salary
FROM employees
ORDER BY department_id, salary DESC;

--可以使用不在SELECT列表中的列排序
SELECT last_name
FROM employees
ORDER BY department_id, salary DESC;
```

