# Oracle learning_Day9

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 视图

常见的数据库对象

| 对象     | 描述                                 |
| -------- | ------------------------------------ |
| 表       | 基本的数据存储集合，由行和列组成     |
| **视图** | **从表中抽出的逻辑上相关的数据集合** |
| 序列     | 提供有规律的数值                     |
| 索引     | 提高查询的效率                       |
| 同义词   | 给对象起别名                         |

<br>

### 1.视图

- 视图是一种虚表。 
- **视图建立在已有表的基础上**, 视图赖以建立的这些表称为**基表**。
- 向视图提供数据内容的语句为 SELECT 语句, 可以将视图理解为**存储起来的 SELECT 语句**. 
- 视图向用户提供基表数据的另一种表现形式

<br>

为什么使用视图

- 控制数据访问
- **简化查询**
- 避免重复访问相同的数据

<br>

### 2.简单视图和复杂视图

| 特性     | 简单视图 | 复杂视图   |
| -------- | -------- | ---------- |
| 表的数量 | 一个     | 一个或多个 |
| 函数     | 没有     | 有         |
| 分组     | 没有     | 有         |
| DML操作  | **可以** | 有时可以   |

<br>

### 3.创建视图

*ps：第一次创建视图时可能出现权限不足的情况，此时以sys的权限登陆，并输入：*

```plsql
grant create view to scott;
--scott是要赋予权限的用户名，也可以是其他你正在使用的用户
```



- 在 **CREATE VIEW** 语句中嵌入子查询

```plsql
CREATE [OR REPLACE] [FORCE|NOFORCE] VIEW view
  [(alias[, alias]...)]
 AS subquery
[WITH CHECK OPTION [CONSTRAINT constraint]]
[WITH READ ONLY [CONSTRAINT constraint]];
```

- 子查询可以是复杂的 SELECT 语句


```plsql
create or replace view empview 
as 
select employee_id emp_id,last_name name,department_name
from employees e,departments d
Where e.department_id = d.department_id
```

创建视图举例

```plsql
CREATE VIEW empvu80
 AS SELECT  employee_id, last_name, salary
    FROM    employees
    WHERE   department_id = 80;
--View created.
```

- 创建视图时在子查询中给列定义**别名**
  - 在选择视图中的列时应使用别名

```plsql
CREATE VIEW salvu50
 AS SELECT  employee_id ID_NUMBER, last_name NAME,
            salary*12 ANN_SALARY
    FROM    employees
    WHERE   department_id = 50;
--View created.
```

<br>

### 4.查询视图

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201004235148.png" alt="POWERPNT_i9WcC4eh1n" style="zoom: 67%;" />

与表的查询类似

```plsql
SELECT *
FROM salvu50;
```

<br>

### 5.修改视图

- 使用CREATE **OR REPLACE** VIEW 子句修改视图
- CREATE VIEW 子句中各列的别名应和子查询中各列相对应

```plsql
CREATE OR REPLACE VIEW empvu80
  (id_number, name, sal, department_id)
AS SELECT  employee_id, first_name || ' ' || last_name, 
           salary, department_id
   FROM    employees
   WHERE   department_id = 80;
--View created.
```

<br>

### 6.创建复杂视图

复杂视图：

> 复杂视图是这样的视图，视图中的列是从基表中的列经过表达式或函数运算而来，或者是对基表进行了DISTINCT查询，或者涉及多个表的操作。总而言之，如果在用CREATE VIEW语句创建视图时，在SELECT之后的列名中使用了表达式、函数，或者使用了DISTINCT关键字，或者对多个表进行了连接查询，这样的视图都是复杂视图。
>
> [51CTO | 1.6.3 复杂视图](https://book.51cto.com/art/201008/219971.htm)

复杂视图举例

```plsql
CREATE VIEW	dept_sum_vu
  (name, minsal, maxsal, avgsal)
AS SELECT	 d.department_name, MIN(e.salary), 
             MAX(e.salary),AVG(e.salary)
   FROM      employees e, departments d
   WHERE     e.department_id = d.department_id 
   GROUP BY  d.department_name;
--View created.
```

<br>

### 7.视图中使用DML的规定

- 可以在简单视图中执行 DML 操作
- 当视图定义中包含以下元素之一时不能使用delete:
  - 组函数
  - GROUP BY 子句
  - DISTINCT 关键字
  - ROWNUM 伪列
- 当视图定义中包含以下元素之一时不能使用update：
  - 组函数
  - GROUP BY子句
  - DISTINCT 关键字
  - ROWNUM 伪列
  - 列的定义为表达式
- 当视图定义中包含以下元素之一时不能使insert：
  - 组函数
  - GROUP BY 子句
  - DISTINCT 关键字
  - ROWNUM 伪列
  - 列的定义为表达式
  - 表中非空的列在视图定义中未包括

<br>

**屏蔽 DML 操作**

- 可以使用 **WITH** **READ** **ONLY** 选项屏蔽对视图的DML 操作
- 任何 DML 操作都会返回一个Oracle server 错误

```plsql
CREATE OR REPLACE VIEW empvu10
    (employee_number, employee_name, job_title)
AS SELECT	employee_id, last_name, job_id
   FROM     employees
   WHERE    department_id = 10
   WITH READ ONLY; /* 屏蔽操作 */
--View created.
```

<br>

### 8.删除视图

删除视图只是删除视图的定义，并不会删除基表的数据

```plsql
DROP VIEW view;
```

```plsql
DROP VIEW empvu80;
--View dropped.
```

<br>

### 9.Top-N 分析

- Top-N 分析查询一个列中最大或最小的 n 个值:
- 最大和最小的值的集合是 Top-N 分析所关心的 
- **rownum**
  -  "伪列" ---- 数据表本身并没有这样的列, 是 oracle 数据库为每个数据表 "加上的"  列。可以标识行号
  - 默认情况下 rownum 按主索引来排序. 若没有主索引则自然排序

查询最大的几个值的 Top-N 分析：

```plsql
SELECT [column_list], ROWNUM  
FROM   (SELECT [column_list] 
        FROM table
        ORDER  BY Top-N_column)
WHERE  ROWNUM <=  N;
```

**注意: 
对隐式 ROWNUM 只能使用 < 或 <=, 而使用 =, >, >= 都将不能返回任何数据。若要包含后者，需要在 SELECT 语句中声明 ROWNUM**

举例，查询工资最高的三名员工：

```plsql
SELECT ROWNUM as RANK, last_name, salary 
FROM  (SELECT last_name,salary FROM employees
       ORDER BY salary DESC)
WHERE ROWNUM <= 3;
```

<br>