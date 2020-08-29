# Oracle learning_Day2(下)

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B?p=2
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 一、SQL中不同类型的函数

### 1.图解SQL函数

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816012017.png" alt="POWERPNT_XdoFiUB3UM" style="zoom: 67%;" />

<br>

### 2.图解两种SQL函数

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816103815.png" alt="image-20200816103745716" style="zoom: 67%;" />

<br>

## 二、单行函数

单行函数:

- 操作数据对象
- 接受参数返回一个结果
- **只对一行进行变换**
- **每行返回一个结果**
- 可以转换数据类型
- 可以嵌套
- 参数可以是一列或一个值

格式：

```sql
function_name [(arg1, arg2,...)]
```

<br>

### 1.字符函数

字符函数又分为 **大小写控制函数 **和 **字符控制函数**

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816104418.png" alt="image-20200816104418467" style="zoom: 67%;" />

#### (1) 大小写控制函数

这类函数改变字符的大小写。

| **函数**                  | 作用                         | **结果**       |
| ------------------------- | ---------------------------- | -------------- |
| **LOWER('SQL Course')**   | **结果变为小写**             | **sql course** |
| **UPPER('SQL Course')**   | **结果变为大写**             | **SQL COURSE** |
| **INITCAP('SQL Course')** | **每个单词的首字母变为大写** | **Sql Course** |

举个例子：

```sql
--查找名字为higgins的列
SELECT employee_id, last_name, department_id
FROM employees
WHERE last_name = 'higgins';

--没有查找结果，可能是有字母大写
--此时通过LOWER函数将last_name的内容转换为小写
SELECT employee_id, last_name, department_id
FROM employees
WHERE LOWER(last_name) = 'higgins';

--也可以使用UPPER函数
SELECT employee_id, last_name, department_id
FROM employees
WHERE UPPER(last_name) = 'HIGGINS';

--也可以使用INITCAOP函数
SELECT employee_id, last_name, department_id
FROM employees
WHERE INITCAPlast_name) = 'Higgins';
```

<br>

#### (2) 字符控制函数

这类函数控制字符

| 函数                            | 作用                                                         | 结果              |
| ------------------------------- | ------------------------------------------------------------ | ----------------- |
| **CONCAT('Hello', 'World')**    | 将两个字符串连成一个字符串                                   | **HelloWorld**    |
| **SUBSTR('HelloWorld',6,5)**    | 设三个参数分别为a,b,c；将字符串a从第b位开始向后取c个长度的子串 | **world**         |
| **LENGTH('HelloWorld')**        | 返回字符串的**长度**                                         | **10**            |
| **INSTR('HelloWorld', 'W')**    | 设两个参数分别为a,b；返回字符b在字符串a中首次出现的位置      | **6**             |
| **LPAD(salary,10,'\*')**        | 设三个参数分别为a,b,c；如果列a中的内容长度不足b，则向**左填充**字符c直至结果长度等于b | ***\**\**24000**  |
| **RPAD(salary, 10, '\*')**      | 设三个参数分别为a,b,c；如果列a中的内容长度不足b，则向**右填充**字符c直至结果长度等于b | **24000\**\**\*** |
| **TRIM('H' FROM 'HelloWorld')** | 设两个参数分别为a,b；**去除**字符串b**首尾**指定的字符b      | **elloWorld**     |
| **REPLACE(‘abcd’,’b’,’m’)**     | 设三个参数a,b,c；将字符串a中的**所有**字符b**替换**为c       | **amcd**          |

举个例子：

```sql
SELECT employee_id, CONCAT(first_name, last_name) NAME, 
       job_id, LENGTH (last_name), 
       INSTR(last_name, 'a') "Contains 'a'?"
FROM employees
WHERE SUBSTR(job_id, 4) = 'REP';
```

部分输出结果：

![image-20200816122510336](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816122510.png)

<br>

### 2.数字函数

| 函数      | 作用     |
| --------- | -------- |
| **ROUND** | 四舍五入 |
| **TRUNC** | 截断     |
| **MOD**   | 求余     |

1. **ROUND**

格式：

```sql
--将参数a保留b位小数，四舍五入
--如果b为负数，则保留小数点前|b|位，四舍五入
--参数b缺省保留整数，四舍五入
ROUND(a,b)
```

举个例子：

```sql
--结果为123
SELECT ROUND(123.45) FROM DUAL;

--结果为123.5
SELECT ROUND(123.45, 1) FROM DUAL;

--结果为120
SELECT ROUND(123.45, -1) FROM DUAL;
```

**TIP：DUAL 是一个‘伪表’，可以用来测试函数和表达式**

<br>

2. **TRUNC**

格式：

```sql
--将参数a保留b位小数，不四舍五入
--如果b为负数，则保留小数点前|b|位，不四舍五入
--参数b缺省保留整数，不四舍五入
TRUNC(a,b)
```

举个例子：

```sql
--结果为123
SELECT TRUNC(123.45) FROM DUAL;

--结果为123.4
SELECT TRUNC(123.45, 1) FROM DUAL;

--结果为120
SELECT TRUNC(123.45, -1) FROM DUAL;
```

<br>

3. **MOD**

格式：

```sql
--求参数a除以参数b的余数
TRUNC(a,b)
```

举个例子

```sql
--结果为100
SELECT MOD(1600,300) FROM DUAL;
```

<br>

### 3.日期函数

Oracle 中的日期型数据实际含有两个值: **日期**和**时间**。

#### 1.日期的数学运算：

- 在日期上加上或减去一个数字结果仍为日期。
- **两个日期相减返回日期之间相差的天数。**
  - 日期不允许做加法运算，无意义
- 可以用数字除24来向日期中加上或减去天数。

举个例子：

```sql
SELECT last_name, (SYSDATE-hire_date)/7 AS WEEKS
FROM employees
WHERE department_id = 90;
```

输出结果

![image-20200816135425935](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816135425.png)

<br>

#### 2.日期函数

| **函数**           | **描述**                               |
| ------------------ | -------------------------------------- |
| **MONTHS_BETWEEN** | **两个日期相差的月数**                 |
| **ADD_MONTHS**     | **向指定日期中加上若干月数**           |
| **NEXT_DAY**       | **指定日期的下一个星期 \* 对应的日期** |
| **LAST_DAY**       | **本月的最后一天**                     |
| **ROUND**          | **日期四舍五入**                       |
| **TRUNC**          | **日期截断**                           |

1. **MONTHS_BETWEEN**

格式：

```sql
--返回日期a和日期b之间相差的月份
MONTHS_BETWEEN(a,b)
```

举个例子：

```sql
--结果为9.29032258064516
 select months_between('1-10月-2020', '10-1月-2020') from dual;
```

<br>

2. **ADD_MONTHS**

格式：

```sql
--返回日期a加上b个月之后的日期
--如果b为负数，则变为减去
ADD_MONTHS(a,b)
```

举个例子：

```sql
--结果为2020/7/1
select add_months('1-1月-2020', '6') from dual;
```

<br>

3. **NEXT_DAY**

格式：

```sql
--返回最接近且大于日期a的星期b
NEXT_DAY(a,b)
```

举个例子：

```sql
--结果为2020/1/6
select next_day('1-1月-2020', '星期一') from dual;
```

<br>

4. **LAST_DAY**

格式：

```sql
--返回日期a所处的月份的最后一天
LAST_DAY(a)
```

举个例子：

```sql
--结果为2020/2/29
select last_day('1-2月-2020') from dual;
```

<br>

5. **ROUND和TRUNC**

格式：

作用与在字符函数中类似，用于四舍五入和截取

```sql
--以b为精度的对日期a进行四舍五入
ROUND(a,b)

--以b为精度的对日期a进行截取
TRUNC(a,b)
```

举个例子：

```sql
select round(sysdate, 'month'), round(sysdate, 'mm'), trunc(sysdate, 'hh')
from dual;
```

输出结果：

![image-20200816152356694](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816152356.png)

TIP：关于日期格式化参数，可以参考这篇文章：[ORACLE日期时间函数大全](https://www.cnblogs.com/chuncn/archive/2009/01/29/1381282.html)

<br>

### 4.转换函数

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816153734.png" alt="image-20200816153734921" style="zoom: 67%;" />

<br>

#### 1.隐式数据类型转换

Oracle 自动完成下列转换：

| 源数据类型           | 目标数据类型 |
| -------------------- | ------------ |
| **VARCHAR2 or CHAR** | **NUMBER**   |
| **VARCHAR2 or CHAR** | **DATE**     |
| **NUMBER**           | **VARCHAR2** |
| **DATE**             | **VARCHAR2** |

![image-20200816154630399](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816154630.png)

例如：

```sql
--自动将日期和数字进行相加并返回日期
--结果为2020/8/18 1
select sysdate + '2' from dual;
```

<br>

#### 2.显式数据类型转换

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816161852.png" alt="image-20200816161852577" style="zoom:67%;" />

**(1) TO_CHAR函数对日期的转换**

使用 **TO_CHAR** 函数将日期转换成字符

- 必须包含在**单引号**中而且**大小写敏感**。

- 可以包含任意的有效的日期格式。
- 日期之间用**逗号**隔开。

格式：

```sql
--将日期date按照自定义格式format_model以字符串返回
TO_CHAR(date, 'format_model')
```

举个例子：

```sql
--结果为2020-08-16 04:27:11
SELECT TO_CHAR(sysdate, 'yyyy-mm-dd hh:mi:ss') FROM dual;
```

更多实际应用：

```sql
select employee_id,last_name,hire_date
from employees
where to_char(hire_date,'yyyy-mm-dd') = '1987-09-17'
```

输出结果：

![image-20200816164932928](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816164932.png)

TIP1：关于日期格式化参数，可以参考这篇文章：[ORACLE日期时间函数大全](https://www.cnblogs.com/chuncn/archive/2009/01/29/1381282.html)

TIP2：使用双引号向日期中添加字符

​	例如：yyyy**”**年**”**mm**“**月**”**dd**“**日**”** --> 2020年8月16日

<br>

**(2) TO_CHAR 函数对数字的转换**

使用 **TO_CHAR** 函数将数字转换成字符

格式：

```sql
--将数字number按照自定义格式format_model以字符串返回
TO_CHAR(number, 'format_model')
```

下面是在TO_CHAR 函数中经常使用的几种格式:

| 字符 | 含义         |
| ---- | ------------ |
| 9    | 数字         |
| 0    | 零           |
| \$   | 美元符       |
| L    | 本地货币符号 |
| .    | 小数点       |
| ,    | 千位符       |

举个例子：

```sql
--结果1,234,567.89
select to_char(1234567.89, '999,999,999.99')
from dual;

--结果001,234,567.890
select to_char(1234567.89, '000,000,000.000')
from dual;

--结果￥001,234,567.890
select to_char(1234567.89, 'L000,000,000.000')
from dual;
```



**(3) TO_DATE 函数对字符的转换**

使用 **TO_DATE** 函数将字符转换成日期

格式：

```sql
--将字符串char以日期返回
--char的日期格式应当与format_mordel日期格式一致
--若第二个参数缺省，则以默认格式转换
TO_DATE(char[,'format_model'])
```

举个例子：

```sql
--结果为2020/8/16 4:27:11
SELECT TO_DATE('2020年8月16日 04:27:11', 'yyyy"年"mm"月"dd"日"hh:mi:ss')
From dual;
```



**(4) TO_NUMBER 函数对字符的转换**

使用 **TO_NUMBER** 函数将字符转换成数字

格式：

```sql
--将字符char按照自定义格式format_model以数字返回
--char的日期格式应当与format_mordel数字格式一致
--若第二个参数缺省，则以默认格式转换
TO_NUMBER(char[, 'format_model'])
```

举个例子：

```sql
--结果为2020/8/16 4:27:11
SELECT TO_NUMBER('$001,234,567.890', '$000,000,000.000')
From dual;
```

<br>

### 5.通用函数

这些函数**适用于任何数据类型，同时也适用于空值**：

- NVL (expr1, expr2)
- NVL2 (expr1, expr2, expr3)
- NULLIF (expr1, expr2)
- COALESCE (expr1, expr2, ..., expr*n*)

注：**参数的类型** 或者 **参数所对应的列的类型** 必须一致，否则应**提前对参数进行类型转换**

<br>

**1.NVL 函数**

格式：

```sql
--第一个参数指定列的名称，第二个参数指定转换的内容
NVL(expr1, expr2)
```

**将空值转换成一个特定值**

- 可以使用的数据类型有日期、字符、数字。

- 函数的常用形式:

  - NVL(commission_pct,0)

  - NVL(hire_date,'01-JAN-97')

  - NVL(job_id,'No Job Yet')

举个例子：

```sql
--如果列commission_pct为空，则用0代替
SELECT last_name, salary, NVL(commission_pct, 0),
   (salary*12) + (salary*12*NVL(commission_pct, 0)) AN_SAL
FROM employees;
```

部分输出结果：

![image-20200816191603466](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816191603.png)

<br>

**2.NVL2 函数**

格式：

```sql
--若参数1所指定的列的内容不为空，返回参数2，否则返回参数3
NVL2(expr1, expr2, expr3)
```

用法与NVL函数类似

举个例子：

```sql
--如果列commission_pct不为空，用SAL+COMM代替；为空，则用SAL代替
SELECT last_name,  salary, commission_pct,
       NVL2(commission_pct, 'SAL+COMM', 'SAL') income
FROM employees WHERE department_id IN (50, 80);
```

部分输出结果：

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816192652.png" alt="image-20200816192651956"  />

<br>

**3.NULLIF 函数**

格式：

```sql
--若参数1和参数2相等返回NULL,不等返回参数1
NULLIF(expr1, expr2)
```

举个例子：

```sql
SELECT first_name, LENGTH(first_name) "expr1", 
       last_name, LENGTH(last_name) "expr2",
       NULLIF(LENGTH(first_name), LENGTH(last_name)) result
FROM employees;
```

部分输出结果：

![image-20200816193108927](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816193108.png)

<br>

**4.COALESCE 函数**

格式：

```sql
COALESCE (expr1, expr2, ..., exprn)
```

- COALESCE 与 NVL 相比的优点在于 COALESCE 可以同时处理交替的多个值。
- 如果第一个表达式为空,则返回下一个表达式，对其他的参数进行COALESCE 。

举个例子：

```sql
--如果列commission为空，输出salary，如果salary依然为空，输出10
SELECT last_name, commission_pct, salary,
       COALESCE(commission_pct, salary, 10) comm
FROM employees
ORDER BY commission_pct;
```

部分输出结果：

![image-20200816194506932](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816194507.png)

<br>

### 6.条件表达式

- 在 SQL 语句中使用IF-THEN-ELSE 逻辑
- 使用两种方法:
  - CASE 表达式
  - DECODE 函数

<br>

**1.CASE表达式**

在需要使用 IF-THEN-ELSE 逻辑时:

```sql
--参数expr满足条件comparison_expr1则返回return_expr1
--满足条件comparison_expr2则返回return_expr2
--以此类推
--如果都不满足则返回else_expr
CASE expr WHEN comparison_expr1 THEN return_expr1
         [WHEN comparison_expr2 THEN return_expr2
          WHEN comparison_exprn THEN return_exprn
          ELSE else_expr]
END
```

举个例子：

```sql
SELECT last_name, job_id, salary,
       CASE job_id WHEN 'IT_PROG'  THEN  1.10*salary
                   WHEN 'ST_CLERK' THEN  1.15*salary
                   WHEN 'SA_REP'   THEN  1.20*salary
                   ELSE salary 
       END "REVISED_SALARY"
FROM   employees;
```

输出部分结果：

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816222902.png" alt="image-20200816222902637"  />

<br>

**2.DECODE函数**

在需要使用 IF-THEN-ELSE 逻辑时:

```sql
DECODE(col|expression, search1, result1 ,
                    [, search2, result2,...,]
                    [, default])
```

举个例子：

```sql
SELECT last_name, job_id, salary,
       DECODE(job_id, 'IT_PROG',  1.10*salary,
                      'ST_CLERK', 1.15*salary,
                      'SA_REP',   1.20*salary,
                      salary)
       REVISED_SALARY
FROM employees;
```

部分输出结果：

![image-20200816224443384](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200816224443.png)

<br>

## 参考资料

1.[ORACLE日期时间函数大全 | cnblogs](https://www.cnblogs.com/chuncn/archive/2009/01/29/1381282.html)

