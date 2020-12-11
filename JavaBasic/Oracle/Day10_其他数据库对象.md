# Oracle learning_Day10

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

## 一、序列（sequence）

序列: 可供多个用户用来产生唯一数值的数据库对象

- 自动提供唯一的数值
- 共享对象
- **主要用于提供主键值**
- 将序列值装入内存可以提高访问效率

<br>

### 1.创建序列

*ps：第一次创建序列时可能出现权限不足的情况，此时以sys的权限登陆，并输入：*

```sql
grant create sequence to scott;
--scott是要赋予权限的用户名，也可以是其他你正在使用的用户
```

<br>

CREATE SEQUENCE 语句

```sql
CREATE SEQUENCE sequence
                [INCREMENT BY n]  --每次增长的数值
                [[START WITH n]    --从哪个值开始
                [[{MAXVALUE n | NOMAXVALUE}]
                [[{MINVALUE n | NOMINVALUE}]
                [[{CYCLE | NOCYCLE}]     --是否需要循环
                [[{CACHE n | NOCACHE}];  --是否缓存登录
```

举例：

```sql
--创建序列 DEPT_DEPTID_SEQ为表 DEPARTMENTS 提供主键
--不使用 CYCLE 选项
CREATE SEQUENCE dept_deptid_seq
                INCREMENT BY 10
                START WITH 120
                MAXVALUE 9999
                NOCACHE
                NOCYCLE;
--Sequence created.
```

<br>

### 2.查询序列

- 查询数据字典视图 **USER_SEQUENCES** 获取序列定义信息
- 如果指定NOCACHE 选项，则列LAST_NUMBER 显示序列中**下一个有效**的值

```sql
SELECT	sequence_name, min_value, max_value, 
	increment_by, last_number
FROM	user_sequences;
```

<br>

#### NEXTVAL 和 CURRVAL 伪列

- NEXTVAL 返回序列中下一个有效的值，任何用户都可以引用
- CURRVAL 中存放序列的当前值 
- **NEXTVAL 应在 CURRVAL 之前指定** ，否则会报CURRVAL 尚未在此会话中定义的错误

```sql
--使用方法：序列名.NEXTVAL/CURRVAL
SELECT	dept_deptid_seq.CURRVAL
FROM	dual;
```

<br>

### 4.使用序列

- 将序列值装入内存可提高访问效率
- 序列在下列情况下出现**裂缝**：
  - 回滚
  - 系统异常
  - 多个表同时使用同一序列
- 如果不将序列的值装入内存(NOCACHE), 可使用表 USER_SEQUENCES 查看序列当前的有效值

与表的查询类似

```plsql
SELECT *
FROM salvu50;
```

<br>

### 5.修改序列

修改序列的增量, 最大值, 最小值, 循环选项, 或是否装入内存

```sql
ALTER SEQUENCE sequence
               [INCREMENT BY n]
               [START WITH n]
               [{MAXVALUE n | NOMAXVALUE}]
               [{MINVALUE n | NOMINVALUE}]
               [{CYCLE | NOCYCLE}]
               [{CACHE n | NOCACHE}];
--Sequence altered.               
```

**修改序列的注意事项**

- 必须是序列的拥有者或对序列有 ALTER 权限
- 只有将来的序列值会被改变
- 改变序列的**初始值**只能通过删除序列之后重建序列的方法实现

<br>

### 6.删除序列

- 使用 DROP SEQUENCE 语句删除序列
- 删除之后，序列不能再次被引用

```sql
DROP SEQUENCE dept_deptid_seq;
--Sequence dropped.
```

<br>

## 二、索引（index）

- 一种独立于表的模式对象, 可以存储在与表不同的磁盘或表空间中
- 索引被删除或损坏, 不会对表产生影响, 其影响的只是**查询**的速度
- 索引一旦建立, Oracle 管理系统会对其进行自动维护, 而且由 Oracle 管理系统决定何时使用索引。用户不用在查询语句中指定使用哪个索引
- 在删除一个表时,所有基于该表的索引会自动被删除
- 通过指针**加速 Oracle 服务器的查询速度**
- 通过快速定位数据的方法，减少磁盘 I/O

<br>

### 1.创建索引

- 自动创建：在定义 PRIMARY KEY 或 UNIQUE 约束后系统自动在相应的列上创建**唯一性**索引
- 手动创建： 用户可以在其它列上创建非唯一的索引，以加速查询

在一个或多个列上创建索引

```sql
CREATE INDEX index
ON table (column[, column]...);
```

举例：

```sql
--在表 EMPLOYEES的列 LAST_NAME 上创建索引
CREATE INDEX emp_last_name_idx
ON 	         employees(last_name);
--Index created.
```

<br>

**什么时候创建索引**

- 列中数据值分布范围很广
- 列经常在 WHERE 子句或连接条件中出现
- 表经常被访问而且数据量很大 ，访问的数据大概占数据总量的2%到4%

<br>

**什么时候不要创建索引**

- 表很小
- 列不经常作为连接条件或出现在WHERE子句中
- 查询的数据大于2%到4%
- 表经常更新

<br>

索引不需要用，只是说我们在用name进行查询的时候，速度会更快。当然查的速度快了，插入的速度就会慢。因为插入数据的同时，还需要维护一个索引。

<br>

### 2.查询索引

可以使用数据字典视图 USER_INDEXES 和 USER_IND_COLUMNS 查看索引的信息

```sql
SELECT ic.index_name, ic.column_name,
       ic.column_position col_pos,ix.uniqueness
FROM   user_indexes ix, user_ind_columns ic
WHERE  ic.index_name = ix.index_name
AND    ic.table_name = 'EMPLOYEES';
```

<br>

### 3.删除索引

- 使用DROP INDEX 命令删除索引
- 只有索引的拥有者或拥有DROP ANY INDEX 权限的用户才可以删除索引
- 删除操作是不可回滚的

```sql
DROP INDEX index;
```

举例：

```sql
DROP INDEX upper_last_name_idx;
--Index dropped.
```

<br>

## 三、同义词（synonym）

使用同义词访问相同的对象:

- 方便访问其它用户的对象
- 缩短对象名字的长度

<br>

### 1.创建同义词

```sql
CREATE [PUBLIC] SYNONYM synonym
FOR    object;
```

举例：

```sql
CREATE SYNONYM e
FOR    employees;
--Synonym Created.
select * from e;
```

<br>

### 2.删除同义词

```sql
DROP SYNONYM synonym;
--Synonym dropped.
```

<br>