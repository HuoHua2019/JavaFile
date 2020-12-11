# Oracle learning_Day11

>  本人学习视频用的是尚硅谷的，也在这里献上
>  视频链接：https://www.bilibili.com/video/BV17b411V75B
>  Oracle 数据库-sql plsql - Java 学习 - 尚硅谷


[TOC]


常见的数据库对象

| 对象       | 描述                             |
| ---------- | -------------------------------- |
| 表         | 基本的数据存储集合，由行和列组成 |
| 视图       | 从表中抽出的逻辑上相关的数据集合 |
| **序列**   | **提供有规律的数值**             |
| **索引**   | **提高查询的效率**               |
| **同义词** | **给对象起别名**                 |

<br>

## 一、权限

- 数据库安全性:
  - 系统安全性
  - 数据安全性
- 系统权限: 对于数据库的权限
- 对象权限: 操作数据库对象的权限

<br>

### 1.系统权限

- 超过一百多种有效的权限
- 数据库管理员具有高级权限以完成管理任务，例如:
  - 创建新用户
  - 删除用户
  - 删除表
  - 备份表

<br>

### 2.创建用户

- DBA 使用 CREATE USER 语句创建用户

```sql
CREATE USER user              			   
IDENTIFIED BY   password;
```

举例：

```sql
CREATE USER  scott
IDENTIFIED BY   tiger;
--User created.
```

<br>

### 3.用户的系统权限

- 用户创建之后, DBA 会赋予用户一些系统权限
- 以应用程序开发者为例, 一般具有下列系统权限:
  - **CREATE SESSION（创建会话）**
  - CREATE TABLE（创建表）
  - CREATE SEQUENCE（创建序列）
  - CREATE VIEW（创建视图）
  - CREATE PROCEDURE（创建过程）

```sql
GRANT privilege [, privilege...]			
TO user [, user| role, PUBLIC...];
```

<br>

### 5.赋予系统权限

 DBA 可以赋予用户特定的权限

```sql
GRANT  create session, create table, 
       create sequence, create view
TO     scott;
--Grant succeeded.
```

<br>

### *6.创建用户表空间

 用户拥有create table权限之外，还需要分配相应的表空间才可开辟存储空间用于创建的表

```sql
ALTER USER exampleuser QUOTA UNLIMITED 
ON users
--User altered.
```

<br>

### 7.角色

使用角色可以方便权限的分配，无需每次单独分配权限给一个用户

<img src="https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201007154853.png" alt="POWERPNT_yPSt9AhezL" style="zoom:67%;" />

<br>

### 8.创建角色并赋予权限

- 创建角色

```sql
CREATE ROLE manager;
--Role created. 
```

- 为角色赋予权限

```sql
GRANT create table, create view 		  
TO manager; 
--Grant succeeded. 
```

- 将角色赋予用户

```sql
GRANT manager TO user1, user2;     
--Grant succeeded. 
```

<br>

### 9.修改密码

- DBA 可以创建用户和修改密码
- 用户本人可以使用 ALTER USER 语句修改密码

```sql
ALTER USER scott             			  
IDENTIFIED BY lion;
--User altered.
```

<br>

### 10.对象权限

- 不同的对象具有不同的对象权限
- 对象的拥有者拥有所有权限
- 对象的拥有者可以向外分配权限

```sql
 GRANT  object_priv [(columns)]
 ON	    object
 TO	    {user|role|PUBLIC}
 [WITH GRANT OPTION];
```

| 对象权限          | 表   | 视图 | 序列 | 过程 |
| ----------------- | ---- | ---- | ---- | ---- |
| 修改（alter）     | √    |      | √    |      |
| 删除（delete）    | √    | √    |      |      |
| 执行（execute）   |      |      |      | √    |
| 索引（index）     | √    |      |      |      |
| 插入（insert）    | √    | √    |      |      |
| 关联（reference） | √    | √    |      |      |
| 选择（select）    | √    | √    | √    |      |
| 更新（update）    | √    | √    |      |      |

<br>

#### 1）分配对象权限

- 分配表 EMPLOYEES 的查询权限


```sql
GRANT  select
ON     table1
TO     user1, user2;
--Grant succeeded.
```

- 分配表中各个列的更新权限

```sql
GRANT  update 
ON     user1.table
TO     user2
--Grant succeeded.
```

<br>

#### 2）WITH GRANT OPTION和PUBLIC关键字

- WITH GRANT OPTION 使用户同样具有分配权限的权利


```sql
GRANT  select, insert
ON     table1
TO     user2
WITH   GRANT OPTION;
--Grant succeeded.
```

- 向数据库中所有用户分配权限

```sql
GRANT  select
ON	   user1.table1
TO	   PUBLIC;
--Grant succeeded.
```

<br>

#### *3）查询权限分配情况

| 数据字典视图        | 描述                       |
| ------------------- | -------------------------- |
| ROLE_SYS_PRIVS      | 角色拥有的系统权限         |
| ROLE_TAB_PRIVS      | 角色拥有的对象权限         |
| USER_TAB_PRIVS_MADE | 用户拥有的角色             |
| USER_TAB_PRIVS_RECD | 用户分配的关于表对象权限   |
| USER_COL_PRIVS_MADE | 用户分配的关于列的对象权限 |
| USER_COL_PRIVS_RECD | 用户拥有的关于列的对象权限 |
| USER_SYS_PRIVS      | 用户拥有的系统权限         |

以 USER_TAB_PRIVS_RECD 为例：

```sql
SELECT *
FROM USER_SYS_PRIVS;
```

![vmware_v9VVw2Gbvm](https://cdn.jsdelivr.net/gh/Huohua2019/Img/img/20201007172754.png)

<br>

#### 4）收回对象权限

- 使用 REVOKE 语句收回权限
- 使用 WITH GRANT OPTION 子句所分配的权限同样被收回

```sql
REVOKE {privilege [, privilege...]|ALL}
ON	  object
FROM   {user[, user...]|role|PUBLIC}
[CASCADE CONSTRAINTS];
```

举例：

```sql
REVOKE  select, insert
ON      departments
FROM    scott;
--Revoke succeeded.
```

<br>