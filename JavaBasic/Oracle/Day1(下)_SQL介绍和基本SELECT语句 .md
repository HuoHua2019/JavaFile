# Oracle learning_Day1(下)

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B?p=2
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 一、SQL语句介绍

SQL语句分为三种类型：

1. DML: Data Manipulation Language 数据操纵语言
2. DDL: Data Definition Language 数据定义语言
3. DCL: Data Control Language数据控制语言
 <br>

DML用于查询与修改数据记录,包括如下SQL语句：

- INSERT:添加数据到数据库中
- UPDATE:修改数据库中的数据
- DELETE:删除数据库中的数据
- SELECT:选择(查询)数据
  	- SELECT是SQL语言的基础,最为重要。
    	  <br>

DDL用于定义数据库的结构,比如创建、修改或删除数据库对象,包括如下SQL语句:

- CREATE TABLE:创建数据库表

- ALTER TABLE:更改表结构、添加、删除、修改列长度

- DROP TABLE:删除表

- CREATE INDEX:在表上建立索引

- DROP INDEX:删除索引
  <br>

DCL用来控制数据库的访问,包括如下SQL语句:

-   GRANT:授予访问权限

-   REVOKE:撤销访问权限

-   COMMIT:提交事务处理

-   ROLLBACK:事务处理回退

-   SAVEPOINT:设置保存点

-   LOCK:对数据库的特定部分进行锁定

    

## 二、基本SQL--SELECT语句

### 1.基本SELECT语句框架

```sql
SELECT *|{[DISTINCT] column|expression [alias],...}
FROM table;
```

• SELECT  标识 选择哪些列。

• FROM   标识从哪个表中选择。

<br>

简单的，可以看做

```sql
SELECT ... FROM ...;
```

更多用法，例如

```sql
--选择全部列，*代表全部
SELECT *
FROM departments;

--选择特定列，在SELECT后跟上要选定的列的名称
SELECT department_id, location_id
FROM departments;
```

<br>

**关于SQL语言的一些补充说明(重要)：**

• SQL 语言**大小写不敏感**。

• SQL 可以写在一行或者多行

• **关键字不能被缩写也不能分行**

• 各子句一般要分行写。

• 使用缩进提高语句的可读性。
<br>

### 2.算数运算符

**数字和日期**可以使用的算术运算符：

| 操作符 | 描述 |
| ------ | ---- |
| +      | 加   |
| -      | 减   |
| *      | 乘   |
| /      | 除   |

举个例子：

```sql
SELECT last_name, salary, salary + 300
FROM employees;
```

<br>

注：使用数字时，注意空值(null)不同于0或空格，凡是空值参与的运算，结果都为空值，初学者应加以区分。**

<br>

**操作符优先级：**

•乘除的优先级高于加减。

•同一优先级运算符从左向右执行。

•**括号**内的运算先执行。

<br>

### 3.给列起一个别名

什么是列的别名:

•重命名一个列。

•便于计算。

•紧跟列名，**也可以在列名和别名之间加入关键字‘AS’，别名使用<u>双引号</u>**，**以便在别名中包含空格或特殊的字符并区分大小写**。

举个例子：

```sql
--给列last_name起个别名name，给列commission_pct起个别名comm
SELECT last_name AS name, commission_pct comm
FROM employees;
--可以看到关键字'AS'可加可不加

--更多例子
--给列last_name起个别名Name，给列commission_pct起个别名comm
SELECT last_name "Name", salary*12 "Annual Salary"
FROM employees;
--注意，如果不加双引号，最后列的名称会默认是大写的，即便你之前起的别名是小写的，建议动手试试体会区别。
```

<br>

### 4.连接符

连接符:

•把列与列，列与字符连接在一起。

•用 ‘**||**’表示。

•可以用来‘合成’列。

举个例子：

```sql
--把列last_name和job_id合并，并以Employees作为列的新名称输出
SELECT last_name||job_id AS "Employees"
FROM employees;
```

<br>

### 5.字符串

•字符串可以是 SELECT 列表中的一个字符,数字,日期。

•**日期和字符只能在<u>单引号</u>中出现**。

•每当返回一行时，字符串被输出一次。

举个例子：

```sql
SELECT last_name||' is a '||job_id 
       AS "Employee Details"
FROM employees;
--输出结果中每一行都有字符串'is a'位于last_name和job_id之间
```

<br>

### 6.重复行

默认情况下,查询会返回全部行,包括重复行。

在SELECT子句中使用关键字 ‘**DISTINCT**’ 删除重复行。

举个例子：

```sql
SELECT DISTINCT department_id
FROM employees;
```

<br>

### 7.显示表结构

如果你想要知道某一个表都含有那些列，可以使用 **DESCRIBE** 命令，显示表结构

```sql
DESC[RIBE] tablename
```

举个例子：

```sql
--显示表employees中列的详细数据
DESCRIBE employees

--也可以写做
DESC employees
```

