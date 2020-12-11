# Oracle learning_Day8

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 约束

- 约束是表级的强制规定
- 有以下五种约束：
  - **NOT NULL**
  - **UNIQUE** 
  - **PRIMARY KEY**
  - **FOREIGN KEY**
  - **CHECK**

<br>

**注意事项：**

- 如果不指定约束名 ，Oracle server 自动按照 SYS_C*n* 的格式指定约束名
- 创建和修改约束:
  - **建表的同时**
  - **建表之后**
- 可以在**表级**或**列级**定义约束
- 可以通过数据字典视图查看约束

<br>

### 1.表级约束和列级约束

- **作用范围**：
   ①列级约束只能作用在一个列上
   ②表级约束可以作用在多个列上（当然表级约束也 可以作用在一个列上）
- **定义方式**：列约束必须跟在列的定义后面，表约束不与列一起，而是单独定义。
- **非空(not null) 约束只能定义在列上**

```plsql
--定义约束
CREATE TABLE [schema.]table
	    (column datatype [DEFAULT expr]
		[column_constraint],
		...
		[table_constraint][,...]);

--示例
CREATE TABLE employees(
         employee_id  NUMBER(6),
         first_name   VARCHAR2(20),
  	     ...
         job_id       VARCHAR2(10) NOT NULL,
         CONSTRAINT emp_emp_id_pk 
                    PRIMARY KEY (EMPLOYEE_ID));
```

<br>

**定义约束**

- 列级

```plsql
column [CONSTRAINT constraint_name] constraint_type,
```

- 表级

```plsql
column,...
  [CONSTRAINT constraint_name] constraint_type
  (column, ...),
```

<br>

### 2.五种约束

#### 1）NOT NULL 约束

保证列值不能为空：

**只能定义在列级**

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200927164653.png" style="zoom:67%;" />

```plsql
CREATE TABLE employees(
    employee_id    NUMBER(6),
    last_name      VARCHAR2(25) NOT NULL,
    salary         NUMBER(8,2),
    commission_pct NUMBER(2,2),
    hire_date      DATE 
                   CONSTRAINT emp_hire_date_nn
                   NOT NULL,
...  
```

用户定命名的约束可以在PL/SQLDEV的My objects-Tables-employees-Check constraints中查看

<br>

#### 2）UNIQUE 约束

唯一约束，允许出现多个空值：NULL。

**可以定义在表级或列级**

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201003131915.png" style="zoom:67%;" />

```plsql
CREATE TABLE employees(
    employee_id      NUMBER(6),
    last_name        VARCHAR2(25) UNIQUE,
    email            VARCHAR2(25),
    salary           NUMBER(8,2),
    commission_pct   NUMBER(2,2),
    hire_date        DATE NOT NULL,
...  
    CONSTRAINT emp_email_uk UNIQUE(email));
```

<br>

#### 3）PRIMARY KEY（主键）约束

不允许出现空值，也不允许内容重复

**可以定义在表级或列级**

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201003132617.png" alt="POWERPNT_cNc7MTSY7x" style="zoom:80%;" />

```plsql
CREATE TABLE   departments(
    department_id        NUMBER(4),
    department_name      VARCHAR2(30)
      CONSTRAINT dept_name_nn NOT NULL,
    manager_id           NUMBER(6),
    location_id          NUMBER(4),
      CONSTRAINT dept_id_pk PRIMARY KEY(department_id));
```

2. 显示方式: 在VALUES 子句中指定空值。

```plsql
--假设表departments拥有4个列
INSERT INTO	departments
VALUES	    (100, 'Finance', NULL, NULL);
--1 row inserted
```

<br>

#### 4）FOREIGN KEY（外键）约束

父表与子表间的约束关系

**引用的列起码要有一个唯一约束**

**可以定义在表级或列级**

FOREIGN KEY 约束的关键字：

- FOREIGN KEY: 在表级指定子表中的列
- REFERENCES: 标示在父表中的列
- **ON DELETE CASCADE(级联删除)**: 当父表中的列被删除时，子表中相对应的列也被删除
- **ON DELETE SET NULL(级联置空)**: 子表中相应的列置空

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201003132656.png" style="zoom:67%;" />

```plsql
CREATE TABLE employees(
    employee_id      NUMBER(6),
    last_name        VARCHAR2(25) NOT NULL,
    email            VARCHAR2(25),
    salary           NUMBER(8,2),
    commission_pct   NUMBER(2,2),
    hire_date        DATE NOT NULL,
...
    department_id    NUMBER(4),
    CONSTRAINT emp_dept_fk FOREIGN KEY (department_id)
      REFERENCES departments(department_id),
    CONSTRAINT emp_email_uk UNIQUE(email));
```

<br>

#### 5）CHECK 约束

定义每一行必须满足的条件

![POWERPNT_YclMiNiYWi](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201003132850.png)

```plsql
..., salary	NUMBER(2)
     CONSTRAINT emp_salary_min  
            CHECK (salary > 0),...
```

<br>

### 3.添加约束的语法

使用 ALTER TABLE 语句:

- 添加或删除约束,**但是不能修改约束**
- 有效化或无效化约束
- **添加 NOT NULL 约束要使用 MODIFY 语句**

```plsql
  ALTER TABLE	 table
  ADD [CONSTRAINT constraint] type (column);
```

以 create table emp as select * from employees; 为例，添加和删除约束

```plsql
Alter table emp modify(empname varchar2(50) not null);
```

添加约束举例

```plsql
ALTER TABLE     employees
ADD CONSTRAINT  emp_manager_fk 
    FOREIGN KEY(manager_id) 
    REFERENCES employees(employee_id);
--Table altered.
```

<br>

### 4.删除约束

使用 **DROP** 子句从表 EMPLOYEES 中删除约束

```plsql
ALTER TABLE      employees
DROP CONSTRAINT  emp_manager_fk;
--Table altered.
```

另外，如果要删除的约束是**主键约束（Primary Key）且是另一个表的外键约束（Foreign Key）**，那么需要后缀 **CASCADE** 才能删除

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201004114038.png" alt="vmware_wVUVMDbFI6" style="zoom:67%;" />

```plsql
ALTER TABLE      employees
DROP CONSTRAINT  emp_dept_id_pk CASCADE;
--Table altered.
```

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201004114156.png" alt="vmware_XOLVRH904e" style="zoom:67%;" />

<br>

### 5.无效化约束

在ALTER TABLE 语句中使用 **DISABLE** 子句将约束无效化

```plsql
ALTER TABLE		employees
DISABLE CONSTRAINT	emp_emp_id_pk;
--Table altered.
```

<br>

### 6.激活约束

- ENABLE 子句可将当前无效的约束激活


- 当定义或激活 UNIQUE 或 PRIMARY KEY 约束时系统会自动创建 UNIQUE 或 PRIMARY KEY索引

```plsql
ALTER TABLE		    employees
ENABLE CONSTRAINT	emp_emp_id_pk;
--Table altered.
```

<br>

### 7.查询约束

查询数据字典视图 **USER_CONSTRAINTS**

```plsql
SELECT  constraint_name, constraint_type,
        search_condition
FROM    user_constraints
WHERE   table_name = 'EMPLOYEES';
```

![vmware_uVNGNwqdUF](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201003140226.png)

注：通过 DESCRIBE 命令只能看到 NOT NULL 约束，如果想要查找所有约束，就要用到上述方法。同时，在 CONSTRAIN_TYPE 中，C 代表 CHECK 约束，P 代表 PRIMARY 约束，R 代表引用完整性约束（FOREIGN KEY约束），U 代表 UNIQUE 约束。

<br>

查询数据字典视图 **USER_CONS_COLUMNS**

```plsql
SELECT	constraint_name, column_name
FROM	user_cons_columns
WHERE	table_name = 'EMPLOYEES';
```

![vmware_u7KuaJJNsl](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201003141806.png)

通过列名的方式查看约束，特别适用于查看系统命名的约束。

<br>