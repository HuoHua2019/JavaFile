# Oracle learning_Day6

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 创建和管理表

### 1.常见的数据库对象

| 对象   | 描述                               |
| ------ | ---------------------------------- |
| 表     | 基本的数据存储集合，由行和列组成。 |
| 视图   | 从表中抽出的逻辑上相关的数据集合。 |
| 序列   | 提供有规律的数值。                 |
| 索引   | 提高查询的效率。                   |
| 同义词 | 给对象起别名。                     |

<br>

### 2.Oracle 数据库中的表

- 用户定义的表：
  - 用户自己创建并维护的一组表
  - 包含了用户所需的信息
    如：SELECT * FROM user_tables  查看用户创建的表
- 数据字典：
  - 由 Oracle Server 自动创建的一组表
  - 包含数据库信息

<br>

### 3.查询数据字典

- 查看用户定义的表：

```plsql
SELECT table_name
FROM user_tables;
```

- 查看用户定义的各种数据库对象

```plsql
SELECT DISTINCT object_type
FROM user_objects;
```

- 查看用户定义的表，视图，同义词和序列

```plsql
SELECT *
FROM user_catalog;
```

<br>

#### 命名规则

表名和列名:

- 必须**以字母开头**
- 必须在 1–30 个字符之间
- 必须只能包含 A–Z, a–z, 0–9, _, $, 和 **\#**
- 必须不能和用户定义的其他对象重名
- 必须不能是Oracle 的保留字

<br>

### 4.创建表

#### 1）CREATE TABLE 语句

- 必须具备:
  - CREATE TABLE权限
- 存储空间
- 必须指定:
  - 表名
  - 列名, 数据类型, **尺寸**

```plsql
CREATE TABLE [schema.]table
             (column datatype [DEFAULT expr][, ...]);
```

举个例子：

```plsql
CREATE TABLE dept
            (deptno NUMBER(2),
             dname 	VARCHAR2(14),
             loc 	VARCHAR2(13));
--创建成功后显示Table created.
--查看创建的表
DESCRIBE dept
```

<br>

#### 2）数据类型

| 数据类型           | 描述                                   |
| ------------------ | -------------------------------------- |
| **VARCHAR2(size)** | 可变长字符数据                         |
| **CHAR(size)**     | 定长字符数据                           |
| **NUMBER(p, s)**   | 可变长数值数据                         |
| **DATE**           | 日期型数据                             |
| **LONG**           | 可变长字符数据，最大可达到2G           |
| **CLOB**           | 字符数据，最大可到到4G                 |
| **RAW (LONG RAW)** | 原始的二进制数据                       |
| **BLOB**           | 二进制数据，最大可达到4G               |
| **BFILE**          | 存储外部文件的二进制数据，最大可达到4G |
| **ROWID**          | 行地址                                 |

> 想要更加深入了解Oracle数据类型，可以参考一下链接：
>
>  [ORACLE基本数据类型总结 | cnblog](https://www.cnblogs.com/kerrycode/p/3265120.html)

<br>

#### 3）使用子查询创建表

- 使用 AS subquery 选项，**将创建表和插入数据结合起来**
- 指定的列和子查询中的列要一一对应
- 通过列名和默认值定义列

```plsql
CREATE TABLE table
  	   [(column, column...)]
AS subquery;
```

举个例子：

```plsql
--将表employees的内容创建为表emp1
create table emp1 as select * from employees;
--创建的emp2是空表
create table emp2 as select * from employees where 1=2;

--实例
CREATE TABLE dept80
AS     
    SELECT  employee_id, last_name, 
            salary*12 ANNSAL, 
            hire_date
    FROM    employees
    WHERE   department_id = 80;
```

<br>

### 5.修改表结构

**ALTER TABLE语句**

使用 ALTER TABLE 语句可以：

- 追加新的列
- 修改现有的列
- 为新追加的列定义默认值
- 删除一个列
- 重命名表的一个列名

```plsql
--追加
ALTER TABLE table
ADD         (column datatype [DEFAULT expr]
            [, column datatype]...);
		   
--修改
ALTER TABLE table
MODIFY 	    (column datatype [DEFAULT expr]
	   		[, column datatype]...);
		   
--删除列
ALTER TABLE table
DROP COLUMN column_name;

--重命名列名
ALTER TABLE table_name RENAME COLUMM old_column_name 
TO new_column_name
```

<br>

#### 1）追加一个新列

- 使用ADD子句追加一个新列
- 新列是表中的最后一列

```plsql
ALTER TABLE dept80
ADD		    (job_id VARCHAR2(9));
```

<br>

#### 2）修改一个列

- 可以修改列的**数据类型**、**尺寸**和**默认值**
- 对默认值的修改只影响今后对表的修改

```plsql
ALTER TABLE	dept80
MODIFY	    (last_name VARCHAR2(30));

ALTER TABLE	dept80
MODIFY	    (salary NUMBER(9,2) DEFAULT 1000);
```

<br>

#### 3）删除一个列

使用 DROP COLUMN 子句删除不再需要的列。

```plsql
ALTER TABLE dept80
DROP COLUMN job_id; 
```

<br>

#### 4）重命名一个列

使用 RENAME COLUMN [table_name] TO子句重命名列

```plsql
ALTER TABLE dept80
RENAME COLUMN job_id TO id; 
```

<br>

#### \*5）设置列不可用

使用 SET UNUSED COLUMN [column_name] 设置列不可用

- 清除字典信息（撤消存储空间），**不可恢复**
  -  使用 SET UNUSED 选项标记一列或者多列不可用。
  -  使用DROP SET UNUSED 选项删除被标记为不可用的列。

```plsql
--设置列不可用
ALTER TABLE emp5
SET UNUSED COLUMN test_column;

--删除不可用的列
ALTER TABLE emp5
DROP UNUSED COLUMNS;
```

> 更多关于设置列不可用的详细内容，参考
>
> [ORACLE删除字段（set unused的用法）| CSDN](https://blog.csdn.net/kechengtan/article/details/6200532)

<br>

### 6.删除表

- 数据和结构都被删除
- 所有正在运行的相关事务被提交
- 所有相关索引被删除
- DROP TABLE 语句不能回滚

```plsql
DROP TABLE dept80;
```

<br>

### 7.清空表

- TRUNCATE TABLE 语句：
  - 删除表中所有的数据
  - 释放表的存储空间
  - **不能回滚**

```plsql
TRUNCATE TABLE detail_dept;
```

- DELETE 语句
  - 删除表中所有的数据
  - 不释放表的存储空间
  - **可以回滚**

```plsql
DELETE FROM emp2;
/* 表为空表 */
SELECT * FROM emp2;
/* 回滚 */
rollback;
/* 表恢复原状 */
SELECT * FROM emp2;
```

<br>

### 8.改变对象的名称

- 执行 RENAME 语句改变表，视图，序列，或同义词的名称
- 必须是对象的拥有者

```plsql
RENAME dept TO detail_dept;
```

<br>

## 参考资料

1. [ORACLE基本数据类型总结 | cnblog](https://www.cnblogs.com/kerrycode/p/3265120.html)

2. [ORACLE删除字段（set unused的用法）| CSDN](https://blog.csdn.net/kechengtan/article/details/6200532)

<br>

