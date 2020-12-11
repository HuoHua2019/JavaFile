# Oracle learning_Day13

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 高级子查询

子查询是嵌套在 SQL 语句中的另一个SELECT 语句

![POWERPNT_XPEPH9mi7n](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201009183159.png)

```sql
SELECT	select_list
FROM	table
WHERE	expr operator (SELECT select_list
		               FROM   table);
```

- 子查询 (内查询) 在主查询执行之前执行
- 主查询(外查询)使用子查询的结果

举例：

```sql
--查询工资大于149号员工工资的员工的信息
SELECT last_name
FROM   employees
WHERE  salary > 
                (SELECT salary
                 FROM   employees
                 WHERE  employee_id = 149) ;
```

<br>

### 1.多列子查询

主查询与子查询返回的**多个列**进行比较

多列子查询中的比较分为两种:

-  成对比较
- 不成对比较

成对比较举例：

```sql
--查询与141号或174号员工的manager_id和department_id相同的其他员工的employee_id, manager_id, -department_id  
SELECT	employee_id, manager_id, department_id
FROM	employees
WHERE  (manager_id, department_id) IN
                      (SELECT manager_id, department_id
                       FROM   employees
                       WHERE  employee_id IN (141,174))
AND	employee_id NOT IN (141,174);
```

不成对比较举例

```sql
SELECT  employee_id, manager_id, department_id
FROM    employees
WHERE   manager_id IN
                  (SELECT  manager_id
                   FROM    employees
                   WHERE   employee_id IN (174,141))
AND     department_id IN
                  (SELECT  department_id
                   FROM    employees
                   WHERE   employee_id IN (174,141))

AND	employee_id NOT IN(174,141);
```

<br>

### 2.在 FROM 子句中使用子查询

举例：返回比本部门平均工资高的员工的last_name, department_id, salary及平均工资

方法一（原方法）：

```sql
SELECT last_name, department_id, salary,
       (SELECT avg(salary)
        FROM employees e3 
        WHERE e1.department_id = e3.department_id 
        GROUP BY department_id) avg_salary
FROM   employees e1
WHERE  salary > 
              (SELECT avg(salary)
               FROM employees e2  
               WHERE e1.department_id = e2.department_id
               GROUP BY department_id)
```

方法二（在FROM中使用子查询）：

```sql
SELECT  a.last_name, a.salary, 
        a.department_id, b.salavg
FROM    employees a, (SELECT   department_id, 
                      AVG(salary) salavg
                      FROM     employees
                      GROUP BY department_id) b
WHERE   a.department_id = b.department_id
AND     a.salary > b.salavg;
```

<br>

### 3.单列子查询表达式

- 单列子查询表达式是在一行中只返回一列的子查询

- Oracle8i 只在下列情况下可以使用, 例如:
  - SELECT 语句 (FROM 和 WHERE 子句)
  - INSERT 语句中的VALUES列表中
- Oracle9i 中单列子查询表达式可在下列情况下使用:
  - DECODE  和 CASE
  - SELECT 中除 GROUP BY 子句以外的所有子句中

#### 1）在 CASE 表达式中使用单列子查询

举例：显式员工的employee_id,last_name 和 location。其中，若员工department_id 与 location_id为1800的 department_id 相同，则 location 为 ’Canada’ ,其余则为 ’USA’ 。

```sql
SELECT employee_id, last_name,
       (CASE
        WHEN department_id =
              (SELECT department_id FROM departments
               WHERE location_id = 1800)     
        THEN 'Canada' ELSE 'USA' END) location
FROM   employees;
```

<br>

#### 2）在 ORDER BY 子句中使用单列子查询

举例：查询员工的employee_id,last_name,要求按照员工的department_name排序

```sql
SELECT   employee_id, last_name
FROM     employees e
ORDER BY (SELECT department_name
          FROM departments d
          WHERE e.department_id = d.department_id);
```

<br>

### 4.相关子查询

相关子查询按照一行接一行的顺序执行，**主查询的每一行都执行一次子查询**

```sql
 SELECT column1, column2, ...
 FROM   table1 outer
 WHERE  column1 operator 
			          (SELECT  colum1, column2
                       FROM    table2
                       WHERE   expr1 = 	outer.expr2);
```

举例1：

```sql
--查询员工中工资大于本部门平均工资的员工的last_name,salary和其department_id
SELECT last_name, salary, department_id
FROM   employees outer
WHERE  salary > (SELECT AVG(salary)
                 FROM   employees
                 WHERE  department_id =  
                        outer.department_id) ;
```

举例2：

```sql
--若employees表中employee_id与job_history表中employee_id相同的数目不小于2，输出这些相同id的员工的employee_id,last_name和其job_id
SELECT e.employee_id, last_name,e.job_id
FROM   employees e 
WHERE  2 <= (SELECT COUNT(*)
             FROM   job_history 
             WHERE  employee_id = e.employee_id);
```

<br>

### 5.EXISTS 操作符

- EXISTS 操作符检查在子查询中是否存在满足条件的行
- 如果在子查询中存在满足条件的行：
  - 不在子查询中继续查找
  - 条件返回 TRUE
- 如果在子查询中不存在满足条件的行：
  - 条件返回 FALSE
  - 继续在子查询中查找

#### 1）EXISTS 操作符应用举例

```sql
--查询公司管理者的employee_id,last_name,job_id,department_id信息
SELECT employee_id, last_name, job_id, department_id
FROM   employees outer
WHERE  EXISTS (SELECT 'X'  /* SELECT的内容任意 */
               FROM   employees
               WHERE  manager_id = 
                      outer.employee_id);
```

<br>

#### 2）NOT EXISTS 操作符应用举例

```sql
--查询departments表中，不存在于employees表中的部门的department_id和department_name
SELECT department_id, department_name
FROM   departments d
WHERE  NOT EXISTS (SELECT 'X'
                   FROM   employees
                   WHERE  department_id 
                          = d.department_id);
```

<br>

### 6.相关更新

使用相关子查询依据一个表中的数据更新另一个表的数据

```sql
UPDATE table1 alias1
SET    column = (SELECT expression
                 FROM   table2 alias2
                 WHERE  alias1.column =    
                        alias2.column);
```

举例：

```sql
ALTER TABLE employees
ADD(department_name VARCHAR2(14))
UPDATE employees e
SET    department_name = 
              (SELECT department_name 
	           FROM   departments d
	           WHERE  e.department_id = d.department_id);
```

<br>

### 7.相关删除

使用相关子查询依据一个表中的数据删除另一个表的数据

```sql
DELETE FROM table1 alias1
WHERE  column operator 
              (SELECT expression
               FROM   table2 alias2
               WHERE  alias1.column = alias2.column);
```

举例：

```sql
--删除表employees中，其与emp_history表皆有的数据
DELETE FROM employees E
WHERE employee_id =  
           (SELECT employee_id
            FROM   emp_history 
            WHERE  employee_id = E.employee_id);
```

<br>

### 8.WITH 子句

- 使用 WITH 子句, 可以避免在 SELECT 语句中重复书写相同的语句块
- WITH 子句将该子句中的语句块执行一次并存储到用户的临时表空间中
- 使用 WITH 子句可以提高查询效率

```sql
WITH table_new1 AS (
                   SELECT expression1
                   FROM   table1 alias1
                   WHERE  alias1.column = alias2.column)，
     table_new2 AS (
                    ...),
     ...
```

举例：

```sql
--查询公司中各部门的总工资大于公司中各部门的平均总工资的部门信息
WITH
dept_costs  AS (
   SELECT  d.department_name, SUM(e.salary) dept_total
   FROM    employees e, departments d
   WHERE   e.department_id = d.department_id
   GROUP BY d.department_name),
avg_cost    AS (
   SELECT SUM(dept_total)/COUNT(*) dept_avg
   FROM   dept_costs)
SELECT * 
FROM   dept_costs 
WHERE  dept_total >
        (SELECT dept_avg 
         FROM avg_cost)
ORDER BY department_name;
```

<br>