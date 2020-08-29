---

---

# Oracle learning_Day4

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B?p=2
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 分组函数

分组函数作用于一组数据，并对一组数据返回一个值。

### 1.组函数类型

- **AVG**
- **COUNT**
- **MAX**
- **MIN**
- **STDDEV**
- **SUM**

组函数语法：

```sql
SELECT [column,] group_function(column), ...
FROM   table
[WHERE condition]
[GROUP BY column]
[ORDER BY column];
```

<br>

#### 1) AVG(平均值)和SUM(合计)函数

可以对**数值型数据**使用AVG 和 SUM 函数。

```sql
SELECT AVG(salary), SUM(salary)
FROM   employees
WHERE  job_id LIKE '%REP%';
```

<br>

#### 2) MIN（最小值）和 MAX（最大值）函数

可以对**任意数据类型**的数据使用 MIN 和 MAX 函数。

```sql
SELECT MIN(hire_date), MAX(hire_date)
FROM   employees;
```

<br>

#### 3) COUNT（计数）函数

- COUNT(\*) 返回表中记录总数,适用于**任意数据类型**。

```sql
SELECT COUNT(*)
FROM   employees
WHERE  department_id = 50;
```

- COUNT(*expr*) 返回*expr***不为空**的记录总数*。*

```sql
SELECT COUNT(commission_pct)
FROM   employees
WHERE  department_id = 50;
```



### 2.组函数与空值

组函数**忽略空值**。

```sql
--commission_pct共有107条记录，其中包含空记录
Select avg(commission_pct), sum(commission_pct)/107,
       sum(commission_pct)/count(commission_pct)
From   employees;
```

执行结果：

![image-20200818172106097](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200818172113.png)

<br>

#### 1)  在组函数中使用NVL函数

- **NVL**函数使分组函数无法忽略空值。

```sql
SELECT AVG(NVL(commission_pct, 0))
FROM   employees;
```

关于NVL函数可以参考这篇文章：[Oracle_Day2(下)](https://www.cnblogs.com/HuoHua2020/p/13514987.html#5通用函数)

<br>

#### 2) DISTINCT 关键字

COUNT(**DISTINCT** *expr*)返回*expr***非空且不重复**的记录总数。

```sql
SELECT COUNT(DISTINCT department_id)
FROM   employees;
```



### 3.分组数据

#### GROUP BY子句语法

- 可以使用GROUP BY子句将表中的数据分成若干组。

举个例子：

```sql
SELECT	  column, group_function(column)
FROM      table
[WHERE    condition]
[GROUP BY group_by_expression]
[ORDER BY column];
```

- 在SELECT 列表中**所有未包含在组函数中的列都应该包含在 GROUP BY 子句中**。

```sql
SELECT   department_id, AVG(salary)
FROM     employees
/* 如果不写group by子句，会报错，因为department_id未包含在组函数中 */
GROUP BY department_id ;
```

- 包含在 GROUP BY 子句中的列不必包含在SELECT 列表中

```sql
SELECT   AVG(salary)
FROM     employees
GROUP BY department_id ;
```

- 在GROUP BY子句中包含多个列

```sql
SELECT   department_id dept_id, job_id, SUM(salary)
FROM     employees
GROUP BY department_id, job_id ;
```

- 不能在 **WHERE** 子句中使用组函数。
- 可以在 **HAVING** 子句中使用组函数（下面会讲到）。

<br>

### 4.过滤分组

#### HAVING子句

使用 HAVING 过滤分组条件:

1. 行已经被分组。
2. 使用了组函数。
3. 满足HAVING 子句中条件的分组将被显示。

```sql
SELECT	  column, group_function
FROM      table
[WHERE	  condition]
[GROUP BY group_by_expression]
[HAVING	  group_condition]
[ORDER BY column];
```

举个例子：

```sql
SELECT   department_id, MAX(salary)
FROM     employees
GROUP BY department_id
HAVING   MAX(salary)>10000;
```

<br>

### 5.嵌套组函数

组函数像其他函数一样，可以嵌套。

```sql
--显示各部门平均工资的最大值
SELECT   MAX(AVG(salary))
FROM     employees
GROUP BY department_id;
```

<br>