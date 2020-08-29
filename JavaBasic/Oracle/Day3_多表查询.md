# Oracle learning_Day3

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B?p=2
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 多表查询

### 1.笛卡尔集

情境引入：

假设公司有两个表单，一个表记录员工基本信息，另一个表记录部门信息。现在要求查询员工信息的同时查询员工所在部门的信息，显然我们要同时查询两个表，此时应该如何查询？

经过之前的学习，我们不难想到：

```sql
--假设employees表有107条记录，departments表有27条记录
SELECT last_name, department_name
FROM employees, departments;
```

输出结果：

![image-20200817160218924](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200817160218.png)

我们预计应该会有107条记录，但是实际有多达107\*27=2889条记录，这显然不符合我们的预期，是哪里出错了呢？

这被称为**笛卡尔集的错误**

- **笛卡尔集会在下面条件下产生**:
  - 省略连接条件
  - 连接条件无效
  - 所有表中的所有行互相连接

<br>

### 2.等值连接

- 为了避免笛卡尔集， 可以在 WHERE 加入有效的**连接条件**。

```sql
--假设表table1中的列column1和表table2中的列column2内容相同，我们可以将其作为连接条件
SELECT t1.column, t2.column
/*下面设置了表的别名*/
FROM table1 t1, table2 t2
WHERE t1.column1 = t2.column2;
```

- **在** **WHERE** **子句中写入连接条件**
- **在表中有相同列时，在列名之前加上表名前缀**
  - 使用表名前缀在多个表中区分相同的列
  - 在不同表中具有相同列名的列可以用**表的别名**加以区分
    - 使用别名可以简化查询
    - 使用表名前缀可以提高执行效率

<br>

回到一开始的情境，我们通过避免笛卡尔集，可以得到：

```sql
SELECT employee_id, department_name
FROM   employees, departments
WHERE  employees.department_id = departments.department_id;
```

输出结果：

![image-20200817160450255](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200817160450.png)

当然，在实际应用中，我们不可能只需要连接两个表，当我们需要连接更多表时，就需要更多的连接条件。

```sql
SELECT table1.column, table2.column, table3.column, ...
FROM table1, table2, table3, ...
WHERE table1.column1 = table2.column2 AND 
      table2.column3 = table3.column4 AND ...
```

- **连接 n个表,<u>至少</u>需要 n-1个连接条件**。 例如：连接三个表，至少需要两个连接条件。

<br>

### 3.非等值连接

非等值连接与等值连接类似，只是要连接的表之间没有显式的连接关系，我们可以创造指定的连接关系。

举个例子：

```sql
--查找员工的薪资等级，表e的员工薪资在表j中是个区间
SELECT e.last_name, e.salary, j.grade_level
FROM   employees e, job_grades j
WHERE  e.salary
       /*薪资在最高和最低区间*/
       BETWEEN j.lowest_sal AND j.highest_sal;
```

部分输出结果：

![image-20200817155948956](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200817155956.png)

<br>

### 4.内连接和外连接（SQL:1992）

#### (1) 内连接

合并具有同一列的两个以上的表的行, **结果集中不包含一个表与另一个表不匹配的行**

之前的等值连接和不等值连接操作，都是内连接。

<br>

#### (2) 外连接

- 除返回满足连接条件的行外，还**返回左（或右）表中<u>不满足</u>条件的行** ，这种连接称为**左（或右） 外连接**
- 没有匹配的行时, 结果表中相应的列为空(NULL)
- 外连接的 WHERE 子句条件类似于内部连接, 但**连接条件中没有匹配行的表的列后面**要加**外连接运算符,即用圆括号括起来的加号(+)**
- **使用外连接可以查询不满足连接条件的数据**

左外连接：

```sql
SELECT table1.column, table2.column
FROM table1, table2
/*左外连接，显示左边表的全部行*/
WHERE table1.column = table2.column(+);
```

右外连接：

```sql
SELECT table1.column, table2.column
FROM table1, table2
/*右外连接，显示右边表的全部行*/
WHERE table1.column(+) = table2.column;
```

<br>

#### (3) 自连接

顾名思义，一个表与自己按照连接条件进行连接。

<br>

### 5.使用SQL: 1999语法连接

格式：

```sql
SELECT table1.column, table2.column
FROM table1
[CROSS JOIN table2] |
[NATURAL JOIN table2] |
[JOIN table2 USING (column_name)] |
[JOIN table2 
  ON (table1.column_name = table2.column_name)] |
[LEFT|RIGHT|FULL OUTER JOIN table2 
  ON (table1.column_name = table2.column_name)];
```

<br>

#### (1) 叉集

- 使用 **CROSS JOIN** 子句使连接的表产生叉集
- 叉集和笛卡尔集是相同的

依然以开头的情境为例：

```sql
SELECT last_name, department_name
FROM employees
CROSS JOIN departments;
```

同样会产生笛卡尔集

![](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200817160545.png)

<br>

#### (2) 自然连接

- NATURAL JOIN 子句，**会以两个表中具有相同名字的列为条件创建等值连接**
- 在表中查询满足等值条件的数据
- 如果只是列名相同而**数据类型不同**，则会产生错误

*注：返回的是，两个表中具有相同名字的列的“且、交集”，而非“或，并集”。即：比如employee类和department类都有department_id和manager_id，返回二者都相同的结果。*

举个例子：

```sql
--表departments和表locations都有location_id
SELECT department_id, department_name,
       location_id, city
FROM departments
NATURAL JOIN locations;
```

<br>

#### (3) 使用 USING 子句创建连接

- 在NATURAL JOIN 子句创建等值连接时，可以**使用** **USING** **子句指定等值连接中需要用到的列**
- 使用 USING 可以在有多个列满足条件时进行选择
- **给选中的列中加上表名前缀或别名**
- **JOIN** **和** **USING** **子句经常同时使用**

以上文自然连接中的举例为例，如果我们希望指定按照department_id进行等值连接：

```sql
select last_name,department_name 
from employees 
join departments using (department_id);
```

更多例子：

```sql
SELECT e.employee_id, e.last_name, d.location_id
FROM employees e JOIN departments d
USING (department_id);
```

**Tips：USING子句指定等值连接中需要用到的列名称必须一致。比如，即便表A中的列a_id和表B中的列b_id内容是一样的，但是由于a_id和b_id名称不一致，无法使用USING。针对这个问题，下面有更好的解决办法。**

<br>

#### (4) 使用ON子句创建连接（常用）

- 自然连接中是以具有相同名字的列为连接条件的。
- **可以使用** **ON** **子句指定额外的连接条件**。
- 这个连接条件是与其它条件分开的。
- **ON** **子句使语句具有更高的易读性**。

举个例子：

```sql
SELECT e.employee_id, e.last_name, e.department_id, 
       d.department_id, d.location_id
FROM employees e JOIN departments d
ON (e.department_id = d.department_id);
```

部分输出结果：

![image-20200817162451011](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200817162451.png)

<br>

**使用ON子句创建多表连接**

示例：

```sql
--通过多个JOIN ON子句，按照指定的连接条件连接三个表
SELECT employee_id, city, department_name
FROM employees e 
JOIN departments d
ON d.department_id = e.department_id 
JOIN locations l
ON d.location_id = l.location_id;
```

<br>

### 6.内连接和外连接（SQL：1999）

- 在SQL: 1999中，内连接只返回满足连接条件的数据
- 两个表在连接过程中除了返回满足连接条件的行以外还返回左（或右）表中不满足条件的行，这种连接称为左（或右） 外连接。
- 两个表在连接过程中除了返回满足连接条件的行以外还返回两个表中不满足条件的行 ，这种连接称为**满外连接**。

<br>

#### (1) 左外连接

用法：在 **JOIN** 前缀 **LEFT OUTER** 

示例：

```sql
SELECT e.last_name, e.department_id, d.department_name
FROM employees e
LEFT OUTER JOIN departments d
ON (e.department_id = d.department_id) ;
```

<br>

#### (2) 右外连接

用法：在 **JOIN** 前缀 **RIGHT OUTER** 

示例：

```sql
SELECT e.last_name, e.department_id, d.department_name
FROM employees e
RIGHT OUTER JOIN departments d
ON (e.department_id = d.department_id) ;
```

<br>



#### (3) 满连接

相当于**即实现了左外连接又实现了右外连接**

用法：在 **JOIN** 前缀 **FULL OUTER** 

示例：

```sql
SELECT e.last_name, e.department_id, d.department_name
FROM employees e
FULL OUTER JOIN departments d
ON (e.department_id = d.department_id) ;
```

<br>

