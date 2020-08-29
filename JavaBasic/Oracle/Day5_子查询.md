# Oracle learning_Day5

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B?p=2
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 子查询

### 1.使用子查询解决问题

情境引入：假设公司中有一员工表记录了员工的工资信息，现在希望知道谁的工资比 Abel 高?

通过之前 ‘多表查询’ 章节中 ‘自连接’ 的学习，我们知道这个问题可以这么解决：

```sql
--谁的工资比Abel的高
select e2.last_name, e2.salary salary
from employees e1, employees e2
where lower(e1.last_name) = 'abel' and e2.salary > e1.salary;
```

将表employees拆分为Abel和其他员工这两个表，通过连接条件进行相连，从而解决问题。

但是换一个角度，其实这个问题可以被拆分为两个问题：

```sql
--Abel的工资？
select salary
from employees
where lower(last_name) = 'abel';
```

查询结果为11000

```sql
--然后再查询工资大于11000的员工
select last_name, salary
from employees
where salary > 11000;
```

从而得到问题的答案

从上面的问题拆分中，我们发现：第一个问题的答案就是第二个问题的条件，那么我们将第一个问题囊括到第二个问题中

```sql
select last_name, salary
from employees
where salary > (select salary
               from employees
               where lower(last_name) = 'abel');
```

结果与前两次一致，这种语法就是**子查询**语法

<br>

### 2.子查询语法

- 子查询 (内查询) 在主查询之前一次执行完成。
- 子查询的结果被主查询(外查询)使用 。

```sql
SELECT	select_list
FROM	table
WHERE	expr operator
            (SELECT	select_list
             FROM   table);
```

**注意事项：**

- 子查询要包含在括号内。

- 将子查询放在比较条件的右侧。

- 单行操作符对应单行子查询，多行操作符对应多行子查询。

<br>

### 3.子查询类型

1. **单行子查询**

   返回一个子查询结果

   ![image-20200819160430068](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200819160437.png)

<br>

2. **多行子查询**

   返回多个子查询结果

   ![image-20200819160500821](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200819160500.png)

<br>

### 4.单行子查询

- 只返回一行。
- 使用单行比较操作符。

| 单行比较操作符 | 含义       |
| -------------- | ---------- |
| =              | 等于       |
| \>             | 大于       |
| \>=            | 大于或等于 |
| \<             | 小于       |
| \<=            | 小于或等于 |
| \<\>           | 不等于     |

<br>

#### 1）执行单行子查询

```sql
--题目：返回job_id与141号员工相同，salary比143号员工多的员工姓名，job_id 和工资
SELECT last_name, job_id, salary
FROM   employees
WHERE  job_id =  
                (SELECT job_id
                 FROM   employees
                 WHERE  employee_id = 141)
AND    salary >
                (SELECT salary
                 FROM   employees
                 WHERE  employee_id = 143);
```

**注：在单行子查询中返回多行结果是非法的！**

<br>

#### 2）在子查询中使用组函数

```sql
--题目：返回公司工资最少的员工的last_name,job_id和salary
SELECT last_name, job_id, salary
FROM   employees
WHERE  salary = 
                (SELECT MIN(salary)
                 FROM   employees);
```

<br>

#### 3）子查询中的 HAVING 子句

1. 执行子查询。
2. 向主查询中的HAVING 子句返回结果。

```sql
--题目：查询最低工资大于50号部门最低工资的部门id和其最低工资
SELECT   department_id, MIN(salary)
FROM     employees
GROUP BY department_id
HAVING   MIN(salary) >
                       (SELECT MIN(salary)
                        FROM   employees
                        WHERE  department_id = 50);
```



### 5.多行子查询

- 返回多行。
- 使用多行比较操作符。

| 多行子查询操作符 | 含义                                  |
| ---------------- | ------------------------------------- |
| IN               | **等于列表中的任意一个**              |
| ANY              | **和子查询返回的<u>某一个</u>值比较** |
| ALL              | **和子查询返回的<u>所有</u>值比较**   |

<br>

#### 1）在多行子查询中使用 ANY 操作符

```sql
--题目：返回其它部门中比job_id为‘IT_PROG’部门任一工资低的员工的员工号、姓名、job_id 以及salary
SELECT employee_id, last_name, job_id, salary
FROM   employees
/* 只要工资小于‘IT_PROG’部门的最高工资就符合条件 */
WHERE  salary < ANY
                    (SELECT salary
                     FROM   employees
                     WHERE  job_id = 'IT_PROG')
AND    job_id <> 'IT_PROG';
```

<br>

#### 2）在多行子查询中使用 ALL 操作符

```sql
--题目：返回其它部门中比job_id为‘IT_PROG’部门所有工资都低的员工的员工号、姓名、job_id 以及salary
SELECT employee_id, last_name, job_id, salary
FROM   employees
/* 只有当工资小于‘IT_PROG’部门的最低工资才符合条件 */
WHERE  salary < ALL
                    (SELECT salary
                     FROM   employees
                     WHERE  job_id = 'IT_PROG')
AND    job_id <> 'IT_PROG';
```

<br>