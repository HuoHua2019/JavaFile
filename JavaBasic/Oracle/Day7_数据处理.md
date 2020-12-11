# Oracle learning_Day7

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]

## 数据处理

### 1.数据操纵语言

- DML(Data Manipulation Language – 数据操纵语言) 可以在下列条件下执行:
  - 向表中**插入**数据
  - **修改**现存数据
  - **删除**现存数据
- 事务是由完成若干项工作的DML语句组成的

<br>

### 2.插入数据

#### 1）INSERT 语句

向表中插入新的数据 使用 INSERT 语句

- 使用这种语法一次只能向表中插入**一条**数据

```plsql
INSERT INTO	table [(column [, column...])]
VALUES	    (value [, value...]);
```

<br>

#### 2）插入数据

- 为每一列添加一个新值。
- 按列的默认顺序列出各个列的值。 
- 在 INSERT 子句中随意列出列名和他们的值。
- **字符和日期型数据应包含在<u>单引号</u>中**。

```plsql
--按列的默认顺序列出各个列
/* 假设表emp1拥有4个特定的列 */
INSERT INTO emp1
VALUES 	    (1001, 'AA', sysdate, 10000)
--1 row inserted

--在 INSERT 子句中随意列出列名和他们的值
INSERT INTO departments(department_id, department_name, 
                        manager_id, location_id)
VALUES      (70, 'Public Relations', 100, 1700);
--1 row inserted

INSERT INTO employees(employee_id,last_name,email,hire_date,job_id)
VALUES      (300,’Tom’,’tom@126.com’,to_date(‘2012-3-21’,’yyyy-mm-
dd’),’SA_RAP’);
--1 row inserted
```

<br>

#### 3）向表中插入空值

1. 隐式方式: 在列名表中省略该列的值。

```plsql
--假设表departments拥有多个列（超过两个）
INSERT INTO	departments (department_id, 
                        department_name    )
VALUES	    (30, 'Purchasing');
--1 row inserted
```

2. 显示方式: 在VALUES 子句中指定空值。

```plsql
--假设表departments拥有4个列
INSERT INTO	departments
VALUES	    (100, 'Finance', NULL, NULL);
--1 row inserted
```

<br>

#### \*4）创建脚本

- 在plsql 语句中使用 \& 变量指定列值。
- \& 变量放在VALUES子句中。

```plsql
--用&代替实际数据，执行后会跳出弹窗需要你填入数据
INSERT INTO departments 
            (department_id, department_name, location_id)
VALUES      (&department_id, '&department_name', &location);
```

![image-20200829170728531](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200829170728.png)

<br>

#### 5）从其它表中拷贝数据

- 在 INSERT 语句中加入子查询。 
- 不必书写 VALUES 子句。 
- 子查询中的值列表应与 INSERT 子句中的列名对应

```plsql
--复制所有内容
INSERT INTO emp2 
SELECT * 
FROM employees
WHERE department_id = 90;
--3 rows inserted

--复制指定列
INSERT INTO sales_reps(id, name, salary, commission_pct)
SELECT employee_id, last_name, salary, commission_pct
FROM   employees
WHERE  job_id LIKE '%REP%';
--4 rows inserted
```

<br>

### 3.更新数据

#### 1）UPDATE 语句

- 使用 UPDATE 语句更新数据。
- 可以一次更新多条数据。

```plsql
UPDATE	table
SET	    column = value [, column = value, ...]
[WHERE 	condition];
```

- 使用 WHERE 子句指定需要更新的数据。

```plsql
UPDATE employees
SET    department_id = 70
WHERE  employee_id = 113;
--1 row updated
```

- 如果省略 WHERE 子句，则表中的所有数据都将被更新。

```plsql
UPDATE copy_emp
SET    department_id = 110;
--22 rows updated
```

<br>

#### 2）在 UPDATE 语句中使用子查询

```plsql
--更新 114号员工的工作和工资使其与205号员工相同。
UPDATE   employees
SET      job_id  = (SELECT  job_id 
                    FROM    employees 
                    WHERE   employee_id = 205), 
         salary  = (SELECT  salary 
                    FROM    employees 
                    WHERE   employee_id = 205) 
WHERE    employee_id    =  114;
--1 row updated
```

<br>

#### \*3）更新中的数据完整性错误

当更新的内容受到约束时，更新可能无法成功。

```plsql
--假设表employees中的department_id来自于表departments
--此时更新department_id，若想要更新的id不存在与表departments中，则更新失败
UPDATE employees
SET    department_id = 55
WHERE  department_id = 110;
--ORA-02291: 违反完整约束条件 (SCOTT.EMP_DEPT_FK) - 未找到父项关键字
```

<br>

### 4.删除数据

#### 1）DELETE 语句

使用 DELETE 语句从表中删除数据。

```plsql
DELETE FROM table
[WHERE	  condition];
```

- 使用 WHERE 子句删除指定的记录。

```plsql
 DELETE FROM departments
 WHERE  department_name = 'Finance';
--1 row deleted
```

- 如果省略 WHERE 子句，则表中的全部数据将被删除

```plsql
DELETE FROM  copy_emp;
--22 rows deleted
```

<br>

#### 2）在 DELETE 中使用子查询

在 DELETE 中使用子查询，使删除基于另一个表中的数据。

```plsql
--题目：从emp1表中删除dept1部门名称中含Public字符的部门id
DELETE FROM emp1
WHERE  department_id =
                       (SELECT department_id
                        FROM   dept1
                        WHERE  department_name LIKE '%Public%');
--1 row deleted
```

<br>

#### 3）删除中的数据完整性错误

当删除的内容受到约束时，删除可能无法成功

```plsql
--假设表department_id中的department_id关联表employees
--此时删除department_id，若想要删除的id存在于表departments中，则删除失败
DELETE FROM departments
WHERE       department_id = 60;
```

<br>

### 5.数据库事务

- 事务：一组逻辑操作单元,使数据从一种状态变换到另一种状态。
- 数据库事务由以下的部分组成:
  - 一个或多个DML 语句
  - 一个 DDL(Data Definition Language – 数据定义语言) 语句
  - 一个 DCL(Data Control Language – 数据控制语言) 语句



#### 1）数据库事务

- 以第一个 DML 语句的执行作为开始
- 以下面的其中之一作为结束:
  - **COMMIT** 或 **ROLLBACK** 语句
  - DDL 语句（**自动提交**）
  - 用户会话正常结束
  - 系统异常终止

<br>

**COMMIT和ROLLBACK语句的优点**

使用COMMIT 和 ROLLBACK语句,我们可以: 

- 确保数据完整性。
- 数据改变被提交之前预览。
- 将逻辑上相关的操作分组。

<br>

#### 2）控制事务

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20200902203423.png" alt="image-20200902203416024" style="zoom:67%;" />

<br>

**回滚到保留点**

- 使用 **SAVEPOINT** 语句在当前事务中创建保存点。
- 使用 **ROLLBACK TO** SAVEPOINT 语句回滚到创建的保存点。

```plsql
UPDATE...
SAVEPOINT update_done;
--Savepoint created
INSERT...
ROLLBACK TO update_done;
--Rollback complete
```

<br>

#### 3）事务进程

- 自动提交在以下情况中执行:
  - DDL 语句。
  - DCL 语句。
  - 不使用 COMMIT 或 ROLLBACK 语句提交或回滚，正常结束会话。
- 会话异常结束或系统异常会导致自动回滚。

<br>

**提交或回滚前的数据状态**

- 改变前的数据状态是可以恢复的
- 执行 DML 操作的用户可以通过 SELECT 语句查询之前的修正
- **其他用户不能看到当前用户所做的改变，直到当前用户结束事务。**
- **DML语句所涉及到的行被锁定，其他用户不能操作**。

<br>

**提交后的数据状态**

- 数据的改变已经被保存到数据库中。
- 改变前的数据已经丢失。
- 所有用户可以看到结果。
- **锁被释放,其他用户可以操作涉及到的数据。**
- 所有保存点被释放。

<br>

#### 4）提交数据

1. 改变数据

    ```plsql
    DELETE FROM employees
    WHERE  employee_id = 99999;
    --1 row deleted.

    INSERT INTO departments 
    VALUES (290, 'Corporate Tax', NULL, 1700);
    --1 row inserted.
    ```

2. 提交改变

    ```plsql
    COMMIT;
    --Commit complete
    ```

<br>

#### 5）数据回滚后的状态

使用 **ROLLBACK** 语句可使数据变化失效：

- 数据改变被取消。
- 修改前的数据状态被恢复。
- 锁被释放。

```plsql
DELETE FROM copy_emp;
--22 rows deleted
ROLLBACK;
--Rollback complete
```

<br>

