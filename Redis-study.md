# Redis-study

## 1、NoSQL概述

### 1.1、为什么要用Nosql

#### 1.1.1、单机MySQL的年代！

APP—>DAL—>MySql

Data Access Layer（数据访问层）

90年代，一个基本的网站访问量一般不会太大，单个数据库完全足够！

那个时候，更多的去使用静态网页 Html ~ 服务器根本没有太大的压力！

思考一下，这种情况下：整个网站的瓶颈是什么？

1. 数据量如果太大、一个机器放不下了；
2. 数据的索引 （B+ Tree），一个机器内存也放不下；
3. 访问量（读写混合），一个服务器承受不了。

只要你开始出现以上的三种情况之一，那么你就必须要升级！

#### 1.1.2、Memcached（缓存） + MySQL + 垂直拆分 （读写分离）

网站80%的情况都是在读，每次都要去查询数据库的话就十分的麻烦！所以说我们希望减轻数据的压力，我们可以使用缓存来保证效率！

发展过程： 优化数据结构和索引–> 文件缓存（IO）—> Memcached（分布式缓存）当时最热门的技术！

![](https://github.com/ZhenxingWan/redis-study/blob/master/img/1.png)

#### 1.1.3、分库分表 + 水平拆分 + MySQL集群

技术和业务在发展的同时，对人的要求也越来越高！

本质：数据库（读，写）

早些年使用MyISAM： 表锁（），十分影响效率！高并发下就会出现严重的锁问题

转战Innodb：行锁

慢慢的就开始使用分库分表来解决写的压力！ MySQL 在那个年代推出 了表分区！但这个技术并没有多少公司使用！

MySQL 的 集群，很好地满足那个年代的所有需求！
![](https://github.com/ZhenxingWan/redis-study/blob/master/img/2.png)

#### 1.1.4、如今最近的年代

2010–2020 十年之间，世界已经发生了翻天覆地的变化；（定位，也是一种数据，音乐，热榜！）

MySQL 等关系型数据库就不够用了！数据量很多，变化很快！

MySQL 有的使用它来存储一些比较大的文件，博客，图片！数据库表很大，效率就低了！如果有一种数据库来专门处理这种数据，MySQL压力就变得十分小（研究如何处理这些问题！）大数据的IO压力下，表几乎没法更改！

> 目前一个基本的互联网项目！

![](D:\2021\Redis\redis-study\img\3.png)

> 为什么要用NoSQL！

用户的个人信息，社交网络，地理位置。用户自己产生的数据，用户日志等等爆发式增长！

这时候我们就需要使用NoSQL数据库的，Nosql 可以很好的处理以上的情况！

### 1.2、什么是NoSQL

> NoSQL

NoSQL=Not Only SQL（非关系型数据库）（不仅仅是SQL）

关系型数据库：表格 ，行 ，列。

NoSQL，泛指非关系型的数据库。随着互联网web2.0网站的兴起，传统的关系数据库在处理web2.0网站，特别是超大规模和高并发的SNS类型的web2.0纯动态网站已经显得力不从心，出现了很多难以克服的问题，而非关系型的数据库则由于其本身的特点得到了非常迅速的发展。NoSQL数据库的产生就是为了解决大规模数据集合多重数据种类带来的挑战，特别是大数据应用难题。

不需要多余的操作就可以横向扩展的！ Map<String，Object> 使用键值对来控制！

> NoSQL 特点

解耦！

1. 方便扩展（数据之间没有关系，很好扩展！）
2. 大数据量高性能（Redis 一秒写8万次，读取11万，NoSQL的缓存记录级，是一种细粒度的缓存，性能会比较高！）
3. 数据类型是多样型的！（不需要事先设计数据库！随取随用！如果是数据量十分大的表，很多人就无法设计了！）
4. 传统 RDBMS 和 NoSQL的区别：

a.传统的 RDBMS：
- 结构化组织；
- SQL；
- 数据和关系都存在单独的表中 row col；
- 操作操作，数据定义语言；
- 严格的一致性；
- 基础的事务。
- .....

b.Nosql：

- 不仅仅是数据；
- 没有固定的查询语言；
- 键值对存储，列存储，文档存储，图形数据库（社交关系）；
- 最终一致性；
- CAP定理和BASE （异地多活） 初级架构师；
- 高性能，高可用，高可扩。
- ....

> 了解：3V+3高

大数据时代的3V：主要是描述问题的

1. 海量Volume
2. 多样Variety
3. 实时Velocity

大数据时代的3高：主要是对程序的要求

1. 高并发
2. 高可扩
3. 高性能

真正在公司中的实践：NoSQL + RDBMS 一起使用才是最强的，阿里巴巴的架构演进！

技术没有高低之分，就看你如何去使用！（提升内功，思维的提高！）

### 1.3、阿里巴巴演进分析

思考问题：这么多东西难道都是在一个数据库中的吗?
![](D:\2021\Redis\redis-study\img\4.png)

技术急不得，越是慢慢学，才能越扎实！

开源才是技术的王道！

任何一家互联网的公司，都不可能只是简简单单让用户能用就好了！

大量公司做的都是相同的业务；（竞品协议）

随着这样的竞争，业务是越来越完善，然后对于开发者的要求也是越来越高！

![](D:\2021\Redis\redis-study\img\5.png)

如果你未来相当一个架构师： 没有什么是加一层解决不了的！

```bash
# 1、商品的基本信息
    名称、价格、商家信息；
    关系型数据库就可以解决了！ MySQL / Oracle （淘宝早年就去IOE了！- 王坚：推荐文章：阿里云的这群疯子：40分钟重要！）
    淘宝内部的 MySQL 不是大家用的 MySQL
# 2、商品的描述、评论（文字比较多）
    文档型数据库中，MongoDB
# 3、图片
    分布式文件系统 FastDFS
    - 淘宝自己的 TFS
    - Gooale的 GFS
    - Hadoop HDFS
    - 阿里云的 oss
# 4、商品的关键字 （搜索）
    - 搜索引擎 solr elasticsearch
    - ISerach：多隆（多去了解一下这些技术大佬！）
    所有牛逼的人都有一段苦逼的岁月！但是你只要像SB一样的去坚持，终将牛逼！
# 5、商品热门的波段信息
    - 内存数据库
    - Redis Tair、Memache...
# 6、商品的交易，外部的支付接口
    - 三方应用
```

要知道，一个简单地网页背后的技术一定不是大家所想的那么简单！

大型互联网应用问题：

- 数据类型太多了！
- 数据源繁多，经常重构！
- 数据要改造，大面积改造？

解决问题：

![](D:\2021\Redis\redis-study\img\6.png)

![](D:\2021\Redis\redis-study\img\7.png)

这里以上都是NoSQL入门概述，不仅能够提高大家的知识，还可以帮助大家了解大厂的工作内容！

### 1.4、NoSQL的四大分类

**KV键值对：**

- 新浪：Redis
- 美团：Redis + Tair
- 阿里、百度：Redis + memecache

**文档型数据库（bson格式 和json一样）：**

- MongoDB （一般必须要掌握）
- MongoDB 是一个基于分布式文件存储的数据库，C++ 编写，主要用来处理大量的文档！
- MongoDB 是一个介于关系型数据库和非关系型数据库中间的产品！MongoDB 是非关系型数据库中功能最丰富，最像关系型数据库的！
- ConthDB(国外)

**列存储数据库**

- HBase
- 分布式文件系统

**图关系数据库**
![](D:\2021\Redis\redis-study\img\8.png)

- 他不是存图形，放的是关系，比如：朋友圈社交网络，广告推荐！
- Neo4j，InfoGrid；

> 四者对比！

![](D:\2021\Redis\redis-study\img\9.png)

敬畏之心可以使人进步！宇宙！科幻！

活着的意义？ 追求幸福（帮助他人，感恩之心），探索未知（努力的学习，不要被这个社会抛弃）

## 2、Redis 入门

### 2.1、概述

> Redis 是什么？

Redis（Remote Dictionary Server )，即（远程字典服务） !

是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。

![](D:\2021\Redis\redis-study\img\10.png)

redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave（主从）同步。

免费和开源！是当下最热门的 NoSQL 技术之一！也被人们称之为结构化数据库！

> Redis 能干嘛？

1. 内存存储、持久化，内存中是断电即失、所以说持久化很重要（rdb、aof）；
2. 效率高，可以用于高速缓存；
3. 发布订阅系统；
4. 地图信息分析；
5. 计时器、计数器（浏览量！）；
6. …

> 特性

1、多样的数据类型；

2、持久化；

3、集群；

4、事务。

> 学习中需要用到的东西

1、官网：https://redis.io/

2、中文网：http://www.redis.cn/
![](D:\2021\Redis\redis-study\img\11.png)

3、下载地址：通过官网下载即可！
![](D:\2021\Redis\redis-study\img\12.png)

注意：Wdinow在 Github上下载（停更很久了！）

Redis推荐都是在Linux服务器上搭建的，我们是基于Linux学习！

### 2.2、Windows安装

1、下载安装包：https://github.com/tporadowski/redis/releases；

2、下载完毕得到压缩包；

3、解压到自己电脑上的环境目录下的就可以的！Redis 十分的小，只有几M；

4、开启Redis，双击运行服务即可！
![](D:\2021\Redis\redis-study\img\13.png)

5、使用redis客户端来来连接redis;
![](D:\2021\Redis\redis-study\img\14.png)

记住一句话，Window下使用确实简单，但是Redis 推荐我们使用Linux去开发使用！
![](D:\2021\Redis\redis-study\img\15.png)

### 2.3、Linux安装

1、下载安装包！官网：https://redis.io/ redis-7.2.3.tar.gz；

2、解压Redis的安装包！ 程序移动到 /opt 目录下；

```shell
# 解压
root@wzx:/home/wzx/software# tar -zxvf redis-7.2.3.tar.gz
# 移动到opt目录下
[root@localhost redis]# mv redis-7.2.3 /opt
[root@localhost redis]# ls
redis-7.2.3.tar.gz
# 查看opt目录
[root@localhost redis]# cd /opt
[root@localhost opt]# ls
containerd  redis-7.2.3
[root@localhost opt]# 
```

3、进入解压后的文件，可以看到我们redis的配置文件；

```shell
[root@localhost redis-7.2.3]# ls
00-RELEASENOTES     CONTRIBUTING.md  INSTALL    README.md   runtest-cluster    SECURITY.md    tests
BUGS                COPYING          Makefile   redis.conf  runtest-moduleapi  sentinel.conf  TLS.md
CODE_OF_CONDUCT.md  deps             MANIFESTO  runtest     runtest-sentinel   src            utils
[root@localhost redis-7.2.3]# 
```

4、基本的环境安装；

```shell
yum install gcc-c++   # 如果redis是6.0以上版本 安装会报error

[root@localhost redis-7.2.3]# make
[root@localhost redis-7.2.3]# make install
```

5、redis的默认安装路径 /usr/local/bin；

```shell
[root@localhost redis-7.2.3]# cd /usr/local/bin
[root@localhost bin]# ls
redis-benchmark  redis-check-aof  redis-check-rdb  redis-cli  redis-sentinel  redis-server
```

6、将redis配置文件，复制到我们当前目录下；

```shell
[root@localhost bin]# mkdir xconfig
[root@localhost bin]# cp /opt/redis-7.2.3/redis.conf xconfig
[root@localhost bin]# ls
redis-benchmark  redis-check-aof  redis-check-rdb  redis-cli  redis-sentinel  redis-server  xconfig
[root@localhost bin]# cd xconfig
[root@localhost xconfig]# ls
redis.conf
[root@localhost xconfig]# 
```

7、redis默认不是后台启动的，所以需要修改配置文件！

```shell
[root@localhost ~]# cd /usr/local/bin
[root@localhost ~]# cd xconfig
[root@localhost ~]# ls
redis.conf
[root@localhost xconfig]# vi redis.conf
```

![](D:\2021\Redis\redis-study\img\16.png)

8、启动Redis服务！

```shell
# 跳转到 cd /usr/local/bin 目录；
[root@localhost redis-7.2.3]# cd /usr/local/bin
[root@localhost bin]# ls
redis-benchmark  redis-check-aof  redis-check-rdb  redis-cli  redis-sentinel  redis-server  xconfig
[root@localhost bin]# 
# 通过自定义的配置文件启动服务；
[root@localhost bin]# redis-server xconfig/redis.conf
7603:C 30 Nov 2023 18:02:00.607 # WARNING Memory overcommit must be enabled! Without it, a background save or replication may fail under low memory condition. Being disabled, it can also cause failures without low memory condition, see https://github.com/jemalloc/jemalloc/issues/1328. To fix this issue add 'vm.overcommit_memory = 1' to /etc/sysctl.conf and then reboot or run the command 'sysctl vm.overcommit_memory=1' for this to take effect.
[root@localhost bin]# 

# 报错问题解决；
打开/etc/sysctl.conf文件，在文件末尾添加一行vm.overcommit_memory = 1
接下来，运行命令sysctl -p /etc/sysctl.conf使修改生效。
[root@localhost bin]# sysctl -p /etc/sysctl.conf
vm.overcommit_memory = 1
# 启动Redis。
[root@localhost bin]# redis-server xconfig/redis.conf
[root@localhost bin]# 
```

9、使用redis-cli 进行连接测试！

```shell
# 使用Redis客户端进行连接
[root@localhost bin]# redis-cli -p 6379
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> set name xing
OK
127.0.0.1:6379> get name
"xing"
127.0.0.1:6379> keys * # 查看所有的key
1) "name"
127.0.0.1:6379> 
```

10、查看redis的进程是否开启！

```shell
[root@localhost ~]# ps -ef|grep redis
root       1896      1  0 09:08 ?        00:00:04 redis-server 127.0.0.1:6379
root       1978   1834  0 10:27 pts/0    00:00:00 redis-cli -p 6379
root       1980   1963  0 10:27 pts/1    00:00:00 grep --color=auto redis
[root@localhost ~]# 
```

11、如何关闭Redis服务呢？shutdown。

```shell
127.0.0.1:6379> shutdown
not connected> exit
```

12、再次查看进程是否存在。

```shell
[root@localhost bin]# ps -ef|grep redis|
root       1987   1834  0 10:46 pts/0    00:00:00 grep --color=auto redis
[root@localhost bin]# 
```

13、后面我们会使用单机多Redis启动集群测试！

### 2.4、测试性能

redis-benchmark 是一个压力测试工具！

官方自带的性能测试工具！

redis-benchmark 命令参数！

图片来自菜鸟教程：
![](D:\2021\Redis\redis-study\img\17.png)

我们来简单测试下：

```bash
# 启动redis
[root@localhost bin]# redis-server xconfig/redis.conf
[root@localhost bin]# 

# 连接
[root@localhost bin]# redis-cli -p 6379
127.0.0.1:6379> 
[root@localhost bin]# 

# 测试：100个并发连接 100000请求
redis-benchmark -h localhost -p 6379 -c 100 -n 100000
```

测试redis性能；
![](D:\2021\Redis\redis-study\img\18.png)

如何查看这些分析呢？
![](D:\2021\Redis\redis-study\img\19.png)

### 2.5、基础的知识

redis默认有16个数据库；
![](D:\2021\Redis\redis-study\img\20.png)

在############# SNAPSHOTTING  ##################模块上面。

默认使用的是第0个。

可以使用 select 进行切换数据库！

```shell
[root@localhost bin]# redis-cli -p 6379
127.0.0.1:6379> select 3  # 切换数据库
OK
127.0.0.1:6379[3]> dbsize # 查看DB大小
(integer) 0
127.0.0.1:6379[3]> 
127.0.0.1:6379[3]> keys * # 查看数据库所有的key
1) "name"
```

- 清除**当前**数据库 **flushdb**
- 清除**全部**数据库的内容 **FLUSHALL**

```shell
127.0.0.1:6379> flushdb # 清楚当前数据库
OK
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> 
```

思考：为什么redis是 6379！粉丝效应！（了解一下即可！）

#### 2.5.1 Redis 是单线程的！

明白Redis是很快的，官方表示，Redis是基于内存操作，CPU不是Redis性能瓶颈，Redis的瓶颈是根据机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了！所有就使用了单线程了！

Redis 是C 语言写的，官方提供的数据为 100000+ 的QPS，完全不比同样是使用 key-vale的Memcache差！

#### 2.5.2 Redis 为什么单线程还这么快？

1、误区1：高性能的服务器一定是多线程的？
2、误区2：多线程（CPU上下文会切换！）一定比单线程效率高！

先去 **CPU>内存>硬盘** 的速度要有所了解！

**核心**：

1. **纯内存操作**： Redis 的所有数据都存储在内存中，这意味着读写速度非常快。与磁盘相比，内存的访问时间几乎可以忽略不计。
2. **避免了锁和多线程上下文切换**： 由于 Redis 是单线程的，它不需要处理复杂的锁机制来保护数据结构，这减少了并发控制带来的开销。同时，没有多线程之间的上下文切换，也节省了大量的 CPU 时间。
3. **非阻塞 I/O 模型**： Redis 使用了 I/O 多路复用技术（如 epoll、kqueue 等），使得一个线程可以同时处理多个客户端连接。当某个客户端的请求正在执行时，服务器可以继续处理其他客户端的请求，而不会被阻塞。
4. **数据结构简单且经过优化**： Redis 的数据结构是基于字典（哈希表）和跳跃表等高效的数据结构实现的，并针对这些数据结构进行了大量的优化，比如预分配内存、延迟更新策略等。
5. **CPU 密集型任务**： Redis 主要处理的是简单的键值对操作，大部分工作都在内存中完成，属于 CPU 密集型任务。在一个现代处理器上，单个核心就可以提供足够的计算能力来处理这些任务。
6. **系统调用次数少**： Redis 尽量减少系统调用的次数，因为每次系统调用都会带来一定的性能开销。例如，Redis 会使用自己的内存池管理内存，而不是频繁地向操作系统申请和释放内存。
7. **设计简洁**： Redis 的代码库相对较小，设计简洁，因此它的运行速度快，资源消耗少。

综上所述，Redis 通过精心的设计和优化，使其单线程模型能够在许多场景下保持高效的性能。然而，在某些情况下，特别是在高并发读写或大数据量的情况下，多线程的优势可能会显现出来。这就是为什么 Redis 在较新的版本中引入了多线程支持的原因之一。

## 3、Redis五大数据类型

官网文档
![](D:\2021\Redis\redis-study\img\21.png)

**全段翻译：**
Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。 它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合（sets）， 有序集合（sorted sets） 与范围查询， bitmaps， hyperloglogs 和 地理空间（geospatial） 索引半径查询。 Redis 内置了 复制（replication），LUA脚本（Lua scripting）， LRU驱动事件（LRU eviction），事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过 Redis哨兵（Sentinel）和自动 分区（Cluster）提供高可用性（high availability）。

### 3.1、Redis-Key

```shell
- keys *           查看所有的key
- set name wan     set key value
- EXISTS name      判断当前的key是否存在
- move name 1      移动当前的key到表1
- EXPIRE name 10   设置key的过期时间，单位是秒
- ttl name         查看当前key的剩余时间
- type name        查看当前key的一个类型
```

```shell
127.0.0.1:6379> keys *
1) "name"
127.0.0.1:6379> get na
(nil)
127.0.0.1:6379> get name
"wan"
127.0.0.1:6379> set age 26
OK
127.0.0.1:6379> keys *
1) "age"
2) "name"
127.0.0.1:6379> exists name
(integer) 1
127.0.0.1:6379> exists nam
(integer) 0
127.0.0.1:6379> move name 3
(integer) 1
127.0.0.1:6379> keys *
1) "age"
127.0.0.1:6379> select 3
OK
127.0.0.1:6379[3]> keys *
1) "name"
127.0.0.1:6379[3]> 
127.0.0.1:6379[3]> select 0
OK
127.0.0.1:6379> 
127.0.0.1:6379> keys *
1) "age"
2) "name"
127.0.0.1:6379> expire name 10
(integer) 1
127.0.0.1:6379> ttl name
(integer) 4
127.0.0.1:6379> type name
none
127.0.0.1:6379> set name wan
OK
127.0.0.1:6379> keys *
1) "age"
2) "name"
127.0.0.1:6379> type name
string
127.0.0.1:6379> 
```

后面如果遇到不会的命令，可以在官网查看帮助文档！

官网命令：http://www.redis.cn/commands.html

### 3.2、String（字符串）

90% 的 java程序员使用 redis 只会使用一个String类型！

```shell
- set key1 v1            设置值
- get key1               获得值
- keys *                 获得所有的key
- EXISTS key1            判断某一个key是否存在
- APPEND key1 “hello”    追加字符串，如果当前key不存在，就相当于setkey
- STRLEN key1            获取字符串的长度！
- incr views             自增1
- decr views             自减1
- INCRBY views 10        可以设置步长，指定增量！
- DECRBY views 5         指定减量
- GETRANGE key1 0 3      截取字符串 [0,3]
- GETRANGE key1 0 -1     获取全部的字符串 和 get key是一样的
- SETRANGE key2 1 xx     替换指定位置开始的字符串！
- setex key3 30 “hello”  设置key3 的值为 hello,30秒后过期
- setex (set with expire)    设置过期时间
- setnx mykey “redis”        如果mykey 不存在，创建mykey，如果mykey存在，创建失败！
- setnx (set if not exist)   不存在在设置 （在分布式锁中会常常使用！）
- mset k1 v1 k2 v2 k3 v3     同时设置多个值
- mget k1 k2 k3              同时获取多个值
- msetnx k1 v1 k4 v4 msetnx  是一个原子性的操作，要么一起成功，要么一起失败。
- mset user:1:name zhangsan user:1:age 2
- 对象 
- set user:1 {name:zhangsan,age:3}  # 设置一个user:1 对象 值为 json字符来保存一个对象！
- 这里的key是一个巧妙的设计： user:{id}:{filed} , 如此设计在Redis中是完全OK了！
- mget user:1:name user:1:age
- getset db redis                   # 如果不存在值，则返回 nil，并创建key-value值，如果存在值，获取原来的值，并设置新的值
- getset                            # 先get然后在set
```

```shell
##########################################################################
127.0.0.1:6379> keys *
1) "name"
2) "age"
127.0.0.1:6379> set key1 v1 # 设置值
OK
127.0.0.1:6379> get key1    # 获得值
"v1"
127.0.0.1:6379> keys *      # 获得所有的key
1) "key1"
2) "name"
3) "age"
127.0.0.1:6379> exists key1 # 判断某一个key是否存在
(integer) 1
127.0.0.1:6379> append key1 hello # 追加字符串，如果当前key不存在，就相当于set key
(integer) 7
127.0.0.1:6379> strlen key1 # 获取字符串的长度！
(integer) 7
127.0.0.1:6379> append key1 ,xing # 追加字符串...
(integer) 12
127.0.0.1:6379> strlen key1 
(integer) 12
127.0.0.1:6379> get key1
"v1hello,xing"
127.0.0.1:6379> 
##########################################################################
# i++
# 步长 i+=
127.0.0.1:6379> set views 0 # 初始浏览量为0
OK
127.0.0.1:6379> get views
"0"
127.0.0.1:6379> incr views # 自增1 浏览量变为1
(integer) 1
127.0.0.1:6379> incr views
(integer) 2
127.0.0.1:6379> get views
"2"
127.0.0.1:6379> decr views # 自减1 浏览量-1
(integer) 1
127.0.0.1:6379> decr views
(integer) 0
127.0.0.1:6379> decr views
(integer) -1
127.0.0.1:6379> get views
"-1"
127.0.0.1:6379> incrby views 10 # 可以设置步长，指定增量！
(integer) 9
127.0.0.1:6379> incrby views 10
(integer) 19
127.0.0.1:6379> decrby views 5
(integer) 14
##########################################################################
# 字符串范围 range
127.0.0.1:6379> set key1 hello,xing # 设置 key1 的值
OK
127.0.0.1:6379> get key1
"hello,xing"
127.0.0.1:6379> getrange key1 0 3   # 截取字符串 [0,3]
"hell"
127.0.0.1:6379> getrange key1 0 -1  # 获取全部的字符串 和 get key是一样的
"hello,xing"
127.0.0.1:6379> 
# 替换！
127.0.0.1:6379> set key2 abcdefg
OK
127.0.0.1:6379> get key2
"abcdefg"
127.0.0.1:6379> SETRANGE key2 1 xx # 替换指定位置开始的字符串！
(integer) 7
127.0.0.1:6379> get key2
"axxdefg"
##########################################################################
# setex (set with expire) # 设置过期时间
# setnx (set if not exist) # 不存在在设置 （在分布式锁中会常常使用！）
127.0.0.1:6379> setex key3 30 "hello" # 设置key3 的值为 hello,30秒后过期
OK
127.0.0.1:6379> ttl key3
(integer) 26
127.0.0.1:6379> get key3
"hello"
127.0.0.1:6379> setnx mykey "redis" # 如果mykey 不存在，创建mykey
(integer) 1
127.0.0.1:6379> keys *
1) "key2"
2) "mykey"
3) "key1"
127.0.0.1:6379> ttl key3
(integer) -2
127.0.0.1:6379> setnx mykey "MongoDB" # 如果mykey存在，创建失败！
(integer) 0
127.0.0.1:6379> get mykey
"MongoDB"
##########################################################################
mset
mget
127.0.0.1:6379> flushdb  # 清空数据库
OK
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3 # 同时设置多个值
OK
127.0.0.1:6379> keys *
1) "k1"
2) "k2"
3) "k3"
127.0.0.1:6379> mget k1 k2 k3 # 同时获取多个值
1) "v1"
2) "v2"
3) "v3"
127.0.0.1:6379> msetnx k1 v1 k4 v4 # msetnx 是一个原子性的操作，要么一起成功，要么一起失败！
(integer) 0
127.0.0.1:6379> get k4
(nil)

# 对象
# set user:1 {name:zhangsan,age:3} # 设置一个user:1 对象 值为 json字符来保存一个对象！
# 这里的key是一个巧妙的设计：user:{id}:{filed} , 如此设计在Redis中是完全OK了！
127.0.0.1:6379> mset user:1:name zhangsan user:1:age 2
OK
127.0.0.1:6379> keys *
1) "user:1:name"
2) "user:1:age"
127.0.0.1:6379> mget user:1:name user:1:age
1) "zhangsan"
2) "2"
##########################################################################
getset # 先get然后在set
127.0.0.1:6379> getset db redis # 如果不存在值，则返回 nil，并创建key-value值
(nil)
127.0.0.1:6379> get db
"redis
127.0.0.1:6379> getset db mongodb # 如果存在值，获取原来的值，并设置新的值
"redis"
127.0.0.1:6379> get db
"mongodb"
```

数据结构是相同的！

String类似的使用场景：value除了是我们的字符串还可以是我们的数字！

- 计数器；
- 统计多单位的数量；
- 粉丝数；
- 对象缓存存储！

### 3.3、List（列表）

基本的数据类型，列表：
![List](D:\2021\Redis\redis-study\img\22.png)

在redis里面，我们可以把 list 玩成栈、队列、阻塞队列！

所有的list命令都是用 l 开头的，Redis不区分大小命令：

```shell
- LPUSH list one     # 将一个值或者多个值，插入到列表头部 （左）
- LRANGE list 0 -1   # 获取list中值！
- LRANGE list 0 1    # 通过区间获取具体的值！
- Rpush list righr   # 将一个值或者多个值，插入到列表位部 （右）
- Lpop list          # 移除list的第一个元素
- Rpop list          # 移除list的最后一个元素
- lindex list 1      # 通过下标获得 list 中的某一个值！
- Llen list          # 返回列表的长度
- lrem list 1 one    # 移除list集合中指定个数的value，精确匹配
- ltrim mylist 1 2   # 通过下标截取指定的长度，这个list已经被改变了，只剩下截取的元素！
- rpoplpush mylist myotherlist # 移除列表的最后一个元素，将他移动到新的
- EXISTS list                  # 判断这个列表是否存在
- lset list 0 item             # 如果不存在列表我们去更新就会报错， 如果存在，更新当前下标的值
- LINSERT mylist before “world” “other”
- LINSERT mylist after “world” “new”    # 将某个具体的value插入到列把你中某个元素的前面或者后面！
```



- LPUSH list one      # 将一个值或者多个值，插入到列表头部 （左）
- LRANGE list 0 -1   # 获取list中值！
- LRANGE list 0 1    # 通过区间获取具体的值！
- Rpush list righr      # 将一个值或者多个值，插入到列表位部 （右）
- Lpop list                 # 移除list的第一个元素
- Rpop list                # 移除list的最后一个元素
- lindex list 1            # 通过下标获得 list 中的某一个值！
- Llen list                  # 返回列表的长度
- lrem list 1 one        # 移除list集合中指定个数的value，精确匹配
- ltrim mylist 1 2       # 通过下标截取指定的长度，这个list已经被改变了，只剩下截取的元素！
- rpoplpush mylist myotherlist   # 移除列表的最后一个元素，将他移动到新的
- EXISTS list                              # 判断这个列表是否存在
- lset list 0 item                           # 如果不存在列表我们去更新就会报错， 如果存在，更新当前下标的值
- LINSERT mylist before “world” “other”
- LINSERT mylist after “world” “new”         # 将某个具体的value插入到列把你中某个元素的前面或者后面！

```shell
##########################################################################
127.0.0.1:6379> lpush list one   # 将一个值或者多个值，插入到列表头部 （左）
(integer) 1
127.0.0.1:6379> lpush list two
(integer) 2
127.0.0.1:6379> lpush list three
(integer) 3
127.0.0.1:6379> lrange list 0 -1 # 获取list中值！
1) "three"
2) "two"
3) "one"
127.0.0.1:6379> lrange list 0 1  # 通过区间获取具体的值！
1) "three"
2) "two"
127.0.0.1:6379> rpush list right # 将一个值或者多个值，插入到列表位部 （右）
(integer) 4
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "two"
3) "one"
4) "right"
127.0.0.1:6379> 
##########################################################################
LPOP
RPOP
127.0.0.1:6379> lpop list # 移除list的第一个元素
"three"
127.0.0.1:6379> rpop list # 移除list的最后一个元素
"righr"
127.0.0.1:6379> lrange list 0 -1
1) "two"
2) "one"
##########################################################################
lindex
127.0.0.1:6379> LRANGE list 0 -1
1) "two"
2) "one"
127.0.0.1:6379> lindex list 1 # 通过下标获得 list 中的某一个值！
"one"
127.0.0.1:6379> lindex list 0
"two"
##########################################################################
llen
127.0.0.1:6379> lpush list one
(integer) 1
127.0.0.1:6379> lpush list two
(integer) 2
127.0.0.1:6379> lpush list three
(integer) 3
127.0.0.1:6379> llen list # 返回列表的长度
(integer) 3
##########################################################################
移除指定的值！
取关 uid
lrem
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "three"
3) "two"
4) "one"
127.0.0.1:6379> lrem list 1 one   # 移除list集合中指定个数的value，精确匹配
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "three"
3) "two"
127.0.0.1:6379> lrem list 1 three
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "two"
127.0.0.1:6379> lpush list three
(integer) 3
127.0.0.1:6379> lrem list 2 three  # 移除list集合中指定个数的value，精确匹配
(integer) 2
127.0.0.1:6379> LRANGE list 0 -1
1) "two"
##########################################################################
trim 修剪。； list 截断! flushdb
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> rpush mylist hello
(integer) 1
127.0.0.1:6379> rpush mylist hello1
(integer) 2
127.0.0.1:6379> rpush mylist hello2
(integer) 3
127.0.0.1:6379> rpush mylist hello3
(integer) 4
127.0.0.1:6379> ltrim mylist 1 2 # 通过下标截取指定(1~2)的长度，这个list已经被改变了，截断了，只剩下截取的元素！
OK
127.0.0.1:6379> lrange mylist 0 -1
1) "hello1"
2) "hello2"
##########################################################################
rpoplpush # 移除列表的最后一个元素，将他移动到新的列表中！
127.0.0.1:6379> rpush mylist hello
(integer) 1
127.0.0.1:6379> rpush mylist hello1
(integer) 2
127.0.0.1:6379> rpush mylist hello2
(integer) 3
127.0.0.1:6379> rpoplpush mylist myotherlist  # 移除列表的最后一个元素，将他移动到新的列表中！
"hello2"
127.0.0.1:6379> lrange mylist 0 -1       # 查看原来的列表
1) "hello"
2) "hello1"
127.0.0.1:6379> lrange myotherlist 0 -1  # 查看目标列表中，确实存在改值！
1) "hello2"
##########################################################################
lset # 将列表中指定下标的值替换为另外一个值，更新操作
127.0.0.1:6379> exists list # 判断这个列表是否存在
(integer) 0
127.0.0.1:6379> lset list 0 item # 如果不存在列表我们去更新就会报错
(error) ERR no such key
127.0.0.1:6379> lpush list value1
(integer) 1
127.0.0.1:6379> lrange list 0 0
1) "value1"
127.0.0.1:6379> lset list 0 item # 如果存在，更新当前下标的值
OK
127.0.0.1:6379> lrange list 0 0
1) "item"
127.0.0.1:6379> lset list 1 item2 # 如果不存在，则会报错！
(error) ERR index out of range
##########################################################################
linsert # 将某个具体的value插入到列中某个元素的前面或者后面！
127.0.0.1:6379> rpush mylist hello
(integer) 1
127.0.0.1:6379> rpush mylist world
(integer) 2
127.0.0.1:6379> linsert mylist before world be  # 之前
(integer) 3
127.0.0.1:6379> lrange mylist 0 -1
1) "hello"
2) "be"
3) "world"
127.0.0.1:6379> linsert mylist after world af    # 之后
(integer) 4
127.0.0.1:6379> lrange mylist 0 -1
1) "hello"
2) "be"
3) "world"
4) "af"
```

**小结**

- 他实际上是一个链表，before Node after ， left，right 都可以插入值；
- 如果 key 不存在，创建新的链表；
- 如果 key 存在，新增内容；
- 如果移除了所有值，空链表，也代表不存在！
- 在两边插入或者改动值，效率最高！ 中间元素，相对来说效率会低一点~

消息排队！消息队列 （Lpush Rpop），栈（ Lpush Lpop）！

### 3.4、Set（集合）

set中的值是不能重读的！

- list 是有序列表，值可重复；
- set 是无序列表，值不可重复。

```shell
##########################################################################
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> sadd myset hello   # set集合中添加元素
(integer) 1
127.0.0.1:6379> sadd myset xing
(integer) 1
127.0.0.1:6379> sadd myset wzxing
(integer) 1
127.0.0.1:6379> smembers myset     # 查看指定set的所有值
1) "hello"
2) "xing"
3) "wzxing"
127.0.0.1:6379> sismember myset hello # 判断某一个值是不是在set集合中！
(integer) 1
127.0.0.1:6379> sismember myset hello1
(integer) 0
##########################################################################
127.0.0.1:6379> scard myset # 获取set集合中的内容元素个数！
(integer) 3
##########################################################################
rem
127.0.0.1:6379> srem myset hello # 移除set集合中的指定元素
(integer) 1
127.0.0.1:6379> scard myset
(integer) 2
127.0.0.1:6379> smembers myset
1) "xing"
2) "wzxing"
127.0.0.1:6379> 
##########################################################################
set # 无序不重复集合。抽随机！
127.0.0.1:6379> smembers myset
1) "xing"
2) "wzxing"
3) "wzxing2"
127.0.0.1:6379> srandmember myset  # 随机抽选出一个元素
"wzxing"
127.0.0.1:6379> srandmember myset
"xing"
127.0.0.1:6379> srandmember myset
"wzxing"
127.0.0.1:6379> srandmember myset
"wzxing"
127.0.0.1:6379> srandmember myset
"wzxing2"
127.0.0.1:6379> srandmember myset 2 # 随机抽选出指定个数的元素
1) "xing"
2) "wzxing"
127.0.0.1:6379> srandmember myset # 随机抽选出一个元素
"wzxing2"
##########################################################################
spop # 删除定的key，随机删除key！
127.0.0.1:6379> smembers myset
1) "xing"
2) "wzxing"
3) "wzxing2"
127.0.0.1:6379> spop myset # 随机删除一些set集合中的元素！
"wzxing"
127.0.0.1:6379> spop myset
"wzxing2"
127.0.0.1:6379> smembers myset
1) "xing"
##########################################################################
 # 将一个指定的值，移动到另外一个set集合！
 127.0.0.1:6379> sadd myset hello
(integer) 1
127.0.0.1:6379> sadd myset world
(integer) 1
127.0.0.1:6379> sadd myset xing
(integer) 0
127.0.0.1:6379> sadd myset2 ste2
(integer) 1
127.0.0.1:6379> smembers myset   # 查看指定set的所有值
1) "xing"
2) "hello"
3) "world"
127.0.0.1:6379> smembers myset2  # 查看指定set的所有值
1) "ste2"
127.0.0.1:6379> smove myset myset2 xing # 将一个指定的值，移动到另外一个set集合！
(integer) 1
127.0.0.1:6379> smembers myset
1) "hello"
2) "world"
127.0.0.1:6379> smembers myset2
1) "ste2"
2) "xing"
127.0.0.1:6379> 
##########################################################################
# flushdb
# 微博，B站，共同关注！(并集)
# 数字集合类：
# - 差集 sdiff   记作 A \ B 或 A - B，表示只属于集合 A、但不属于集合 B 的元素组成的集合。
# - 交集 sinter  记作 A ∩ B，表示同时属于集合 A 和 B 的元素组成的集合。
# - 并集 sunion  记作 A ∪ B，表示集合 A 和 B 中的所有元素组成的集合。
127.0.0.1:6379> sadd key1 a
(integer) 1
127.0.0.1:6379> sadd key1 b
(integer) 1
127.0.0.1:6379> sadd key1 c
(integer) 1
127.0.0.1:6379> sadd key2 c
(integer) 1
127.0.0.1:6379> sadd key2 d
(integer) 1
127.0.0.1:6379> sadd key2 e
(integer) 1
127.0.0.1:6379> keys *
1) "key2"
2) "key1"
127.0.0.1:6379> sdiff key1 key2   # 差集
1) "a"
2) "b"
127.0.0.1:6379> sinter key1 key2  # 交集 共同好友就可以这样实现
1) "c"
127.0.0.1:6379> sunion key1 key2  # 并集
1) "a"
2) "b"
3) "c"
4) "d"
5) "e"
127.0.0.1:6379> 
```

微博，A用户将所有关注的人放在一个set集合中！将它的粉丝也放在一个集合中！

共同关注，共同爱好，二度好友，推荐好友！（六度分割理论）

### 3.5、Hash（哈希）

Map集合，key - map! 时候这个值是一个map集合！ **本质和String类型没有太大区别**，还是一个简单的key - vlaue！

```shell
hset myhash field xing
##########################################################################
127.0.0.1:6379> hset myhash field xing  # set一个具体 key-vlaue
(integer) 1
127.0.0.1:6379> hget myhash field       # 获取一个字段值
"xing"
127.0.0.1:6379> hmset myhash field hello field1 world # set多个 key-vlaue
OK
127.0.0.1:6379> hmget myhash field field1             # 获取多个字段值
1) "hello"
2) "world"
127.0.0.1:6379> 
127.0.0.1:6379> hgetall myhash     # 获取全部的数据，
1) "field"
2) "hello"
3) "field1"
4) "world"
127.0.0.1:6379> hdel myhash field  # 删除hash指定key字段！对应的value值也就消失了！
(integer) 1
127.0.0.1:6379> hgetall myhash
1) "field1"
2) "world"
##########################################################################
hlen
127.0.0.1:6379> hmset myhash field1 hello field2 world
OK
127.0.0.1:6379> HGETALL myhash
1) "field2"
2) "world"
3) "field1"
4) "hello"
127.0.0.1:6379> hlen myhash # 获取hash表的字段数量！
(integer) 2
##########################################################################
127.0.0.1:6379> HEXISTS myhash field1 # 判断hash中指定字段是否存在！
(integer) 1
127.0.0.1:6379> HEXISTS myhash field3
(integer) 0
##########################################################################
# 只获得所有field
# 只获得所有value
127.0.0.1:6379> hkeys myhash # 只获得所有field
1) "field2"
2) "field1"
127.0.0.1:6379> hvals myhash # 只获得所有value
1) "world"
2) "hello"
##########################################################################
incr decr
127.0.0.1:6379> hset myhash field3 5 # 指定增量！
(integer) 1
127.0.0.1:6379> HINCRBY myhash field3 1
(integer) 6
127.0.0.1:6379> HINCRBY myhash field3 -1
(integer) 5
127.0.0.1:6379> hsetnx myhash field4 hello # 如果不存在则可以设置
(integer) 1
127.0.0.1:6379> hsetnx myhash field4 world # 如果存在则不能设置
(integer) 0
```

hash变更的数据 user name age，尤其是是用户信息之类的，经常变动的信息！hash 更适合于对象的存储，String更加适合字符串存储！

### 3.6、Sorted Set /  Zset（有序集合）

在set的基础上，增加了一个值，set k1 v1 zset k1 score1 v1

```shell
127.0.0.1:6379> zadd myset 1 one         # 添加一个值
(integer) 1
127.0.0.1:6379> zadd myset 2 two 3 three # 添加多个值
(integer) 2
127.0.0.1:6379> zrange myset 0 -1
1) "one"
2) "two"
3) "three"
##########################################################################
# 排序如何实现
127.0.0.1:6379> zadd salary 2500 xiaohong # 添加三个用户
(integer) 1
127.0.0.1:6379> zadd salary 5000 zhangsan
(integer) 1
127.0.0.1:6379> zadd salary 500 xing
(integer) 1
# zrangebyscore key min max
127.0.0.1:6379> zrangebyscore salary -inf +inf # 显示全部的用户 从小到大！
1) "xing"
2) "xiaohong"
3) "zhangsan"
127.0.0.1:6379> zrevrange salary 0 -1 # 从大到进行排序！
1) "zhangsan"
2) "xiaohong"
3) "xing"
127.0.0.1:6379> zrangebyscore salary -inf +inf withscores # 显示全部的用户并且附带成绩
1) "xing"
2) "500"
3) "xiaohong"
4) "2500"
5) "zhangsan"
6) "5000"
127.0.0.1:6379> zrangebyscore salary -inf 2500 withscores # 显示工资小于2500员工的升
1) "xing"
2) "500"
3) "xiaohong"
4) "2500"
127.0.0.1:6379>
##########################################################################
# 移除rem中的元素
127.0.0.1:6379> zrange salary 0 -1
1) "xing"
2) "xiaohong"
3) "zhangsan"
127.0.0.1:6379> zrem salary xiaohong # 移除有序集合中的指定元素
(integer) 1
127.0.0.1:6379> zrange salary 0 -1
1) "xing"
2) "zhangsan"
127.0.0.1:6379> zcard salary # 获取有序集合中的个数
(integer) 2
##########################################################################
127.0.0.1:6379> flushdb
OK
127.0.0.1:6379> zadd myset 1 hello
(integer) 1
127.0.0.1:6379> zadd myset 2 world 3 xing
(integer) 2
127.0.0.1:6379> zcount myset 1 3 # 获取指定区间的成员数量！
(integer) 3
127.0.0.1:6379> zcount myset 1 2
(integer) 2
```
其与的一些API，通过我们的学习，剩下的如果工作中有需要，这个时候你可以去查查看官方文档！  
案例思路：set 排序 存储班级成绩表，工资表排序！  
普通消息，1、重要消息 2、带权重进行判断！  
做排行榜应用实现，取Top N 测试！

## 4、三种特殊数据类型

### 4.1、Geospatial 地理位置

朋友的定位，附近的人，打车距离计算？

Redis 的 Geo 在Redis3.2 版本就推出了！这个功能可以推算地理位置的信息，两地之间的距离，方圆几里的人！

可以查询一些测试数据：http://www.jsons.cn/lngcode

只有 六个命令：
![](D:\2021\Redis\redis-study\img\23.png)

官方文档：https://www.redis.net.cn/order/3685.html

#### 4.1.1 getadd

```shell
# getadd 添加地理位置
# 规则：两级无法直接添加，我们一般会下载城市数据，直接通过java程序一次性导入！
# 有效的经度从-180度到180度。
# 有效的纬度从-85.05112878度到85.05112878度。

# 当坐标位置超出上述指定范围时，该命令将会返回一个错误。
127.0.0.1:6379> geoadd china:city 39.90 116.40 beijin
(error) ERR invalid longitude,latitude pair 39.900000,116.400000  # 超出有效范围

# 参数 key 值（）
127.0.0.1:6379> geoadd china:city 116.40 39.90 beijing
(integer) 1
127.0.0.1:6379> geoadd china:city 121.47 31.23 shanghai
(integer) 1
127.0.0.1:6379> geoadd china:city 106.50 29.53 chongqing 114.05 22.52 shengzhen
(integer) 2
127.0.0.1:6379> geoadd china:city 120.16 30.24 hangzhou 108.96 34.26 xian
(integer) 2
127.0.0.1:6379>
```

#### 4.1.2 getpos

获得当前定位：一定是一个坐标值！

```shell
127.0.0.1:6379> geopos china:city beijing # 获取指定的城市的经度和纬度！
1) 1) "116.39999896287918091"
   2) "39.90000009167092543"
127.0.0.1:6379> geopos china:city beijing chongqing
1) 1) "116.39999896287918091"
   2) "39.90000009167092543"
2) 1) "106.49999767541885376"
   2) "29.52999957900659211"
127.0.0.1:6379>
```

#### 4.1.3 geodist

两人之间的距离！

单位：

- m 表示单位为米。
- km 表示单位为千米。
- mi 表示单位为英里。
- ft 表示单位为英尺。

```shell
127.0.0.1:6379> geodist china:city beijing shanghai km  # 查看上海到北京的直线距离
"1067.3788"
127.0.0.1:6379> geodist china:city beijing chongqing km # 查看重庆到北京的直线距离
"1464.0708"
127.0.0.1:6379>
```

#### 4.1.4 georadius 

**以给定的经纬度为中心， 找出某一半径内的元素。**

我附近的人？（获得所有附近的人的地址，定位！）通过半径来查询！

获得指定数量的人，200

所有数据应该都录入：china:city，才会让结果更加请求！

```shell
127.0.0.1:6379> georadius china:city 110 30 1000 km # 以110，30 这个经纬度为中心，寻找方圆1000km内的城市 北京上海就没有在里面
1) "chongqi"
2) "xian"
3) "shengzhen"
4) "hangzhou"
127.0.0.1:6379> georadius china:city 110 30 500 km # 以110，30 这个经纬度为中心，寻找方圆500km内的城市
1) "chongqing"
2) "xian"
127.0.0.1:6379> georadius china:city 110 30 500 km withdist # 显示到中间距离的位置
1) 1) "chongqing"
   2) "341.9374"
2) 1) "xian"
   2) "483.8340"
127.0.0.1:6379> georadius china:city 110 30 500 km withcoord # 显示他人的定位信息
1) 1) "chongqing"
   2) 1) "106.49999767541885376"
      2) "29.52999957900659211"
2) 1) "xian"
   2) 1) "108.96000176668167114"
      2) "34.25999964418929977"
127.0.0.1:6379> georadius china:city 110 30 500 km withdist withcoord count 1 # 筛选出指定的结果！
1) 1) "chongqing"
   2) "341.9374"
   3) 1) "106.49999767541885376"
      2) "29.52999957900659211"
127.0.0.1:6379> georadius china:city 110 30 500 km withdist withcoord count 2
1) 1) "chongqing"
   2) "341.9374"
   3) 1) "106.49999767541885376"
      2) "29.52999957900659211"
2) 1) "xian"
   2) "483.8340"
   3) 1) "108.96000176668167114"
      2) "34.25999964418929977"
127.0.0.1:6379>
```

#### 4.1.5 georadiusbymember

```shell
# 找出位于指定元素周围的其他元素！
127.0.0.1:6379> georadiusbymember china:city beijing 1000 km # 以北京为中心，寻找方圆1000km内的城市
1) "beijing"
2) "xian"
127.0.0.1:6379> georadiusbymember china:city shanghai 400 km # 以上海为中心，寻找方圆400km内的城市
1) "hangzhou"
2) "shanghai"
```

#### 4.1.6 geohash

**命令 - 返回一个或多个位置元素的 Geohash 表示。**

该命令将返回11个字符的Geohash字符串!

```shell
# 将二维的经纬度转换为一维的字符串，如果两个字符串越接近，那么则距离越近！
127.0.0.1:6379> geohash china:city beijing chongqing
1) "wx4fbxxfke0"
2) "wm5xzrybty0"
```

#### 4.1.7 geo底层

**GEO 底层的实现原理其实就是 Zset！我们可以使用Zset命令来操作geo！**

```bash
127.0.0.1:6379> zrange china:city 0 -1 # 查看地图中全部的元素
1) "chongqi"
2) "xian"
3) "shengzhen"
4) "hangzhou"
5) "shanghai"
6) "beijing"
127.0.0.1:6379> zrem china:city beijing # 移除指定元素！
(integer) 1
127.0.0.1:6379> ZRANGE china:city 0 -1
1) "chongqi"
2) "xian"
3) "shengzhen"
4) "hangzhou"
5) "shanghai"
```

### 4.2、Hyperloglog

#### 4.2.1 什么是基数？

A {1,3,5,7,8,7}

B{1，3,5,7,8}

基数（不重复的元素） = 5个，可以接受误差！

#### 4.2.2 简介

HyperLogLog 是一种概率数据结构，用于估计集合中不同元素的数量（也称为基数）。

它是用来解决大规模数据集去重计数问题的一种高效、空间占用小的算法。

Redis 从版本 2.8.9 开始支持 HyperLogLog 数据结构。

**优点：**

1. 低内存消耗：使用固定大小的内存来存储估计值，即使输入数据集非常大。
2. 近似计数：HyperLogLog 提供的是一个估算值，而不是精确值，但它在标准误差范围内通常非常准确。
3. 合并操作：多个 HyperLogLog 结构可以被合并成一个，这使得它们非常适合分布式环境中的计数任务。

占用的内存是固定，2^64 不同的元素的基数，只需要废 12KB内存！如果要从内存角度来比较的话 Hyperloglog 首选！

**网页的 UV （一个人访问一个网站多次，但是还是算作一个人！）**

传统的方式， set 保存用户的id，然后就可以统计 set 中的元素数量作为标准判断 !

这个方式如果保存大量的用户id，就会比较麻烦！我们的目的是为了计数，而不是保存用户id；

0.81% 错误率！ 统计UV任务，可以忽略不计的！

#### 4.2.3 测试使用

```shell
127.0.0.1:6379> pfadd mykey a b c d e f g h i j # 创建第一组元素 mykey
(integer) 1
127.0.0.1:6379> pfcount mykey # 统计 mykey 元素的基数数量
(integer) 10
127.0.0.1:6379> pfadd mykey2 i j z x c v b n m  # 创建第二组元素 mykey2
(integer) 1
127.0.0.1:6379> pfcount mykey2
(integer) 9
127.0.0.1:6379> pfmerge mykey3 mykey mykey2     # 合并两组 mykey mykey2 => mykey3 并集
OK
127.0.0.1:6379> pfcount mykey3                  # 看并集的数量！
(integer) 15
```

如果允许容错，那么一定可以使用 Hyperloglog ！

如果不允许容错，就使用 set 或者自己的数据类型即可！

### 4.3、Bitmap

#### 4.3.1 什么是bitmap

Bitmap，也称为位图（Bit Map）或位数组（Bit Array），是一种数据结构，它使用一个二进制位（bit）来表示特定元素的存在与否。在位图中，每一位对应一个元素或者一组元素的状态，通常用0和1来表示元素的两种可能状态。

为什么其他教程都不喜欢讲这些？这些在生活中或者开发中，都有十分多的应用场景，学习了，就是就是多一个思路！技多不压身！

位图的主要优点是节省存储空间，特别是当需要表示大量数据时，比如判断一个整数是否在一个非常大的集合中。例如，如果有一个包含40亿个不重复的32位整数的集合，使用普通的数组存储每个整数将需要大约16GB的空间（40亿 * 4字节）。而如果使用位图，只需要512MB的空间（40亿 / 8字节/字），因为每位只需要1个比特来表示。

**位图的应用场景包括**：

- 快速查找：通过计算索引值可以立即定位到对应的比特位。
- 去重：对于大量的元素，可以快速检测某个元素是否已经存在于集合中。
- 排序：位图可以用来加速排序过程，特别是在进行分桶排序等算法时。
- 数据压缩：位图可以对大量数据进行压缩，减少存储需求。

#### 4.3.2 位存储

统计用户信息，活跃，不活跃！ 登录 、 未登录！ 打卡，365打卡！ 两个状态的，都可以使用Bitmaps！

Bitmap 位图，数据结构！ 都是操作二进制位来进行记录，就只有0 和 1 两个状态！

365 天 = 365 bit 1字节 = 8bit 46 个字节左右

#### 4.3.3 测试

![在这里插入图片描述](D:\2021\Redis\redis-study\img\24.png)

使用bitmap 来记录 周一到周日的打卡！

周一：1  周二：0  周三：0  周四：1 …
```shell
127.0.0.1:6379> flushdb
OK
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> setbit sign 0 1
(integer) 0
127.0.0.1:6379> setbit sign 1 0
(integer) 0
127.0.0.1:6379> setbit sign 2 0
(integer) 0
127.0.0.1:6379> setbit sign 3 1
(integer) 0
127.0.0.1:6379> setbit sign 4 1
(integer) 0
127.0.0.1:6379> setbit sign 5 0
(integer) 0
127.0.0.1:6379> setbit sign 6 0
(integer) 0
127.0.0.1:6379>
```

查看某一天是否有打卡！

```shell
127.0.0.1:6379> getbit sign 3
(integer) 1
127.0.0.1:6379> getbit sign 6
(integer) 0
```

统计操作，统计 打卡的天数！

```shell
127.0.0.1:6379> bitcount sign # 统计这周的打卡记录，就可以看到是否有全勤！
(integer) 3
```

## 5、事务

常称为ACID原则：原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）和持久性（Durability）。

Redis 事务本质：一组命令的集合！ 一个事务中的所有命令都会被**序列化**，在事务执行过程的中，会按照顺序执行！

一次性、顺序性、排他性！执行一些列的命令！

```shell
------ 队列 set set set 执行------
```

**Redis事务没有没有隔离级别的概念！**

所有的命令在事务中，并没有直接被执行！只有发起执行命令的时候才会执行！ExecRedis单条命令式保存原子性的，但是事务不保证原子性！

redis的事务：

- 开启事务（multi）
- 命令入队（…）
- 执行事务（exec）

### 5.1事务测试

正常执行事务！

```shell
127.0.0.1:6379> flushdb  # 清空当前选定数据库中的所有键（keys）
OK
127.0.0.1:6379> multi   # 开启事务
OK
# 命令入队
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> get k2
QUEUED
127.0.0.1:6379(TX)> set k3 v3
QUEUED
127.0.0.1:6379(TX)> exec # 执行事务
1) OK
2) OK
3) "v2"
4) OK
127.0.0.1:6379> 
```

放弃事务！

```shell
127.0.0.1:6379> multi # 开启事务
OK
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> set k4 v4
QUEUED
127.0.0.1:6379(TX)> discard # 取消事务
OK
127.0.0.1:6379> get k4      # 事务队列中命令都不会被执行！
(nil)
```

编译型异常（代码有问题！ 命令有错！） ，事务中所有的命令都不会被执行！

```shell
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> set k3 v3
QUEUED
127.0.0.1:6379(TX)> getset k3 # 错误的命令
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379(TX)> set k4 v4
QUEUED
127.0.0.1:6379(TX)> set k5 v5
QUEUED
127.0.0.1:6379(TX)> exec   # 执行事务报错！
(error) EXECABORT Transaction discarded because of previous errors.
127.0.0.1:6379> get k5 # 所有的命令都不会被执行！
(nil)
```

运行时异常（1/0）， 如果事务队列中存在语法性，那么执行命令的时候，其他命令是可以正常执行的，错误命令抛出异常！

```shell
127.0.0.1:6379> set k1 "v1"
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> incr k1 # 会执行的时候失败！
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> set k3 v3
QUEUED
127.0.0.1:6379(TX)> get k3
QUEUED
127.0.0.1:6379(TX)> exec
1) (error) ERR value is not an integer or out of range # 虽然第一条命令报错了，但是依旧正常执行成功了！
2) OK
3) OK
4) "v3"
127.0.0.1:6379> get k2
"v2"
127.0.0.1:6379> get k3
"v3"
```

监控！ Watch （面试常问！）

**悲观锁：**

- 很悲观，认为什么时候都会出问题，无论做什么都会加锁！

**乐观锁**：

- 很乐观，认为什么时候都不会出问题，所以不会上锁！ 更新数据的时候去判断一下，在此期间是否有人修改过这个数据；
- 获取version；
- 更新的时候比较 version。

### 5.2 Redis测监视测试

正常执行成功！

```shell
127.0.0.1:6379> set money 100
OK
127.0.0.1:6379> set out 0
OK
127.0.0.1:6379> watch money # 监视 money 对象
OK
127.0.0.1:6379> multi       # 事务正常结束，数据期间没有发生变动，这个时候就正常执行成功！
OK
127.0.0.1:6379(TX)> decrby money 20
QUEUED
127.0.0.1:6379(TX)> incrby out 20
QUEUED
127.0.0.1:6379(TX)> exec
1) (integer) 80
2) (integer) 20
127.0.0.1:6379> 
```

测试多线程修改值 , 使用watch 可以当做redis的乐观锁操作！

```shell
127.0.0.1:6379> watch money # 监视 money
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> decrby money 10
QUEUED
127.0.0.1:6379(TX)> incrby out 10
QUEUED
# 等窗口2 执行再运行
127.0.0.1:6379(TX)> exec # 执行之前，另外一个线程，修改了我们的值，这个时候，就会导致事务执行失败！
(nil)

# 窗口2 多线程执行
127.0.0.1:6379> get money
"80"
127.0.0.1:6379> set money 1000
OK
127.0.0.1:6379> 
```

如果修改失败，获取最新的值就好

```shell
127.0.0.1:6379> unwatch     # 1、如果发现事务执行失败，就先解锁
OK
127.0.0.1:6379> watch money # 2、获取最新的值，再次监视，select version
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> decrby money 1
QUEUED
127.0.0.1:6379(TX)> incrby money 1
QUEUED
127.0.0.1:6379(TX)> exec     # 3、比对监视的值是否发生了变化，如果没有变化，那么可以执行成功，如果变量就执行失败!
1) (integer) 999
2) (integer) 1000
127.0.0.1:6379> 
```

## 6、Jedis

Jedis 是一个开源的 Java 客户端库，用于连接和操作 Redis 数据库。它是 Redis 官方推荐的 Java 客户端实现之一，提供了丰富的 API 来执行各种 Redis 命令，包括字符串操作、哈希操作、列表操作、集合操作、有序集合操作、事务处理、发布/订阅等功能。

以下是一些 Jedis 的主要特点：

- 简单易用：Jedis 提供了一个直观的 Java API，使得开发者能够轻松地在 Java 应用程序中使用 Redis。
- 功能全面：支持 Redis 的大部分命令和数据结构。
- 性能优化：Jedis 设计时考虑了性能，通过高效的网络通信和协议解析来提高数据访问速度。
- 连接管理：提供连接池功能，可以复用和管理 Redis 连接，提高应用程序的效率和稳定性。
- 分布式支持：Jedis 可以用于分布式环境，支持 Redis Sentinel 和 Redis Cluster 集群模式。
- 事务处理：支持 Redis 的多命令事务执行，通过 `multi`、`exec`、`discard` 和 `watch` 等命令进行操作。
- 错误处理和重试策略：Jedis 能够处理与 Redis 服务器的通信错误，并可以根据配置进行重试。

开发者可以通过 Jedis 类实例化 Redis 连接，然后调用相应的方法来进行数据的读写和其他操作。

什么是Jedis ？ Redis 官方推荐的 java连接开发工具！ 使用Java 操作Redis 中间件！如果你要使用 java操作redis，那么一定要对Jedis 十分的熟悉！

### 6.1 测试

1、导入对应的依赖

```xml
<!--导入jedis的包-->
<dependencies>
   <dependency>
       <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>5.1.0</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
            <version>2.0.43</version>
    </dependency>
</dependencies>
```

2、编码测试：

- 连接数据库
- 操作命令
- 断开连接！

```java
    // 1、连接数据库测试
    @Test
    void contextLoads() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping()); //输出返回值为PONG 即为成功！
        jedis.close(); //关闭连接
    }

    // 2、清空数据库测试
    @Test
    void testFlush(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll(); //清空数据库，如果不加参数，则清空当前数据库。
        jedis.close(); //关闭连接
        System.out.println("清空数据库成功！"); //输出返回值为PONG 即为成功！
        System.out.println(jedis.ping()); //输出返回值为PONG 即为成功！
        jedis.close(); //关闭连接
    }
```

输出：PONG 就说明连接成功！

### 6.2 常用的API

String、List、Set、Hash、Zset

所有的api命令，就是我们对应的上面学习的指令，一个都没有变化！

事务

```java
    // 3、事务
    @Test
    void testRedis(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //jedis.select(1);    //选择第1号数据库,如果不选择指定数据库、默认就是db0数据库。
        jedis.flushDB();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","xing");
        jsonObject.put("age","18");
        //开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();
        //jedis.watch(result);
        try {
            multi.set("user1",result);
            multi.set("user2",result);
            //int i = 1/0;     //代码抛出异常事务，执行失败！2测
            multi.exec();      //执行事务
        } catch (Exception e) {
            multi.discard();   //放弃事务，执行失败！
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close(); //关闭连接
        }
    }
```

## 7、SpringBoot整合

### 7.1 Redis概述

Redis是一种开源的、支持网络的、可基于内存亦可持久化的日志型、Key-Value数据库。它使用ANSI C语言编写，提供多种语言的API，可以用于存储和检索数据，以及实现缓存和队列等功能。 

Redis的数据存储在内存中，因此存取速度非常快。同时，Redis还支持持久化操作，可以将数据保存到磁盘上，以防止断电等意外情况导致数据丢失。 

Redis支持多种数据类型，包括字符串、哈希表、列表、集合和有序集合等。它还支持事务、Lua脚本、发布/订阅机制等高级功能。 

Redis广泛应用于各种场景，如缓存、队列、分布式计算、实时数据处理等。它可以与其他数据库系统（如MySQL）结合使用，以提高系统的性能和可靠性。

#### 7.1.1 SpringData

SpringBoot操作数据：spring-data jpa jdbc mongodb redis ! 

SpringData也是和SpringBoot齐名的项目!  

SpringBoot 操作数据都是使用 ——SpringData，以下是 Spring 官网中描述的 SpringData 可以整合的数据源

**官网地址**：https://spring.io/projects/spring-data

可以看到 Spring Data Redis。

#### 7.1.2 lettuce

在 SpringBoot 2.x 之后，原来的 Jedis 被替换为了 lettuce

可点击pom中spring-boot-starter-data-redis查看到 lettuce-core。

**Jedis 和 lettuce 区别**：

**Jedis** ：采用的是直连的服务，如果有多个线程操作的话是不安全的，就需要使用 Jedis Pool 连接池取解决。问题就会比较多。

**lettuce** ：底层采用 Netty ，实例可以在多个线程中共享，不存在线程不安全的情况。可以减少线程数据了，性能更高。

**源码分析**：RedisAutoConfiguration

```java
 @Bean
    @ConditionalOnMissingBean(name = {"redisTemplate"}) //我们可以自定义一个redisTemplate来替换这个默认的！
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //默认的RedisTemplate没有过多的设置，redis对象都是需要序列化!
        //两个泛型都是 object，object的类型，我们后使用需要强制转换<String,Object>
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean  //由于String是redis中最常使用的类型，所以说单独提出来了一个bean！
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
```

### 7.2 查看源码

#### 7.2.1 自动配置

**1**、点进 RedisAutoConfiguration；

可以得出配置 Redis，只需要配置 RedisAutoConfiguration 即可

@EnableConfigurationProperties({RedisProperties.class})

**2**、在 RedisAutoConfiguration点进 RedisProperties；
![在这里插入图片描述](D:\2021\Redis\redis-study\img\25.png)

**3**、回到 RedisAutoConfiguration，观察它做了什么；
![在这里插入图片描述](D:\2021\Redis\redis-study\img\26.png)

#### 7.2.2 Jedis.pool 不生效

**1**、在 RedisAutoConfiguration 类中的 RedisTemplate 方法需要传递一个 点进RedisConnectionFactory 参数。点进这个参数，这是一个结构，查看实现类

**2**、查看 Jedis 的实现类，下载源码
JedisConnectionFactory，会发现 ，这个类中很多没有实现的地方。所以 Jedis Pool 不可用。

**3**、查看 Lettuce 的实现类
LettuceConnectionFactory类，没有问题。

- 这也说明 SpringBoot 更推荐使用 Lettuce。

### 7.3 测试使用

#### 7.3.1 新建项目

新建一个 SpringBoot 项目，勾选上以下
![在这里插入图片描述](D:\2021\Redis\redis-study\img\27.png)

#### 7.3.2 引入依赖

```xml
<!-- 操作redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

#### 7.3.3 配置连接application.yml

```yaml
# SpringBoot所有的配置类，都有一个自动配置类 RedisAutoConfiguration
# 自动配置类都会绑定一个properties 配置文件  RedisProperties
# Redis配置 配置 Redis
spring:
  data:
    redis:
      host: 127.0.0.1  # 官方推荐localhost
      port: 6379
```

#### 7.3.4 测试类

```java
@SpringBootTest
class Springboot10RedisApplicationTests {

    // 这就是之前 RedisAutoConfiguration 源码中的 Bean
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    //3测
    @Test
    public void test3() {
        // 使用 RedisUtil 工具类
        redisUtil.set("mykey", "小李");
        System.out.println(redisUtil.get("mykey"));
    }

    //1测
    @Test
    void contextLoads() {
        /** 在企业开发中，我们80%的情况下，都不会使用这个原生的方式去编写代码！ 自己编写：RedisUtils 工具类
         * redisTemplate 操作不同的数据类型，API 和 Redis 中的是一样的
         * opsForValue 类似于 Redis 中的 String
         * opsForList 类似于 Redis 中的 List
         * opsForSet 类似于 Redis 中的 Set
         * opsForHash 类似于 Redis 中的 Hash
         * opsForZSet 类似于 Redis 中的 ZSet
         * opsForGeo 类似于 Redis 中的 Geospatial
         * opsForHyperLogLog 类似于 Redis 中的 HyperLogLog
         */
        // 除了基本的操作，常用的命令都可以直接通过redisTemplate操作，比如事务……
        // 和数据库相关的操作都需要通过连接操作，获取redis的连接对象
        //RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //connection.flushDb();
        redisTemplate.opsForValue().set("mykey","\n小明");
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }

    //2测
    @Test
    public void test2() throws JsonProcessingException {
        // 真实的开发一般都使用json来传递对象
        User user = new User("亚索", "18");
        // 使用 JSON 序列化
        //String jsonUser = new ObjectMapper().writeValueAsString(user);
        // 这里直接传入一个对象
        redisTemplate.opsForValue().set("key",user); //jsonUser
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}
```

### 7.4 Serializable  序列化

为什么要序列化。

#### 7.4.1 新建一个User实体类

```java
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
// 实体类序列化在后面加上 implements Serializable
public class User {
    private String name;
    private String age;
}
```

#### 7.4.2 编写测试类，先不序列化

```java
    //2测
    @Test
    public void test2() throws JsonProcessingException {
        // 真实的开发一般都使用json来传递对象
        User user = new User("亚索", "18");
        // 使用 JSON 序列化
        //String jsonUser = new ObjectMapper().writeValueAsString(user);
        // 这里直接传入一个对象
        redisTemplate.opsForValue().set("user",user); //jsonUser
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
```

#### 7.4.3 执行结果

org.springframework.data.redis.serializer.SerializationException: Cannot serialize...  
DefaultSerializer requires a Serializable payload but received an object of type [com.xing.domain.User]  
实体类没有实现 Serializable ，自然会报序列化的错误。  
如果序列化就不会报错，所以一般实体类都要序列化。

#### 7.4.4  为什么要自定义序列化

使用测试类，向数据库中插入了一个中文字符串，虽然在 Java 端可以看到返回了中文，但是在 Redis 中查看是一串乱码。  
C:\Program Files\Redis redis-cli.exe #管理员身份运行  
keys *  # 查看  
flushdb # 清除当前数据库  
![在这里插入图片描述](D:\2021\Redis\redis-study\img\28.png)
解决这个问题就需要修改默认的序列化规则。  
实体类实现 Serializable 或 使用 JSON 序列化。
![在这里插入图片描述](D:\2021\Redis\redis-study\img\29.png)

#### 7.4.5 使用自定义配置类

JSON 序列化，需要自定义一个配置类。RedisConfig 模板；

```java
@Configuration
public class RedisConfig {
    //编写我们自己的redisTemplate,这是一个比较固定的模板
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        // 为了开发方便，直接使用<String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        // Json 配置序列化
        // 使用 jackson 解析任意的对象
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 使用 objectMapper 进行转义
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key 采用 String 的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // Hash 的 key 采用 String 的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value 采用 jackson 的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash 的 value 采用 jackson 的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 把所有的配置 set 进 template
        template.afterPropertiesSet();

        return template;
    }
}
```

清空一下数据库，再次执行之前那个插入 User 对象的测试；  
发现执行成功，没有报错，并且在 Redis 中也没有转义字符了。  
C:\Program Files\Redis redis-cli.exe：运行 get key 再 get user

#### 7.4.6 工具类

```java
package com.xing.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//在我们真实的公司工作中，一般都可以看到一个公司自己封装：RedisUtils 工具类
@Component
public final class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================
    /**
     * 指定缓存失效时间
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================
    /**
     * HashGet
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */

    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
```

#### 7.4.7 工具类测试

```java
    //3测
    @Test
    public void test3() {
        // 使用 RedisUtil 工具类
        redisUtil.set("mykey", "小李");
        System.out.println(redisUtil.get("mykey"));
    }
```

发现工具类可正常使用。
![在这里插入图片描述](D:\2021\Redis\redis-study\img\30.png)

所有的redis操作，其实对于java开发人员来说，十分的简单，更重要是要去理解redis的思想和每一种数据结构的用处和作用场景！

## 8、Redis.conf 详解

启动的时候，就通过配置文件来启动！

工作中，一些小小的配置，可以让你脱颖而出！

/usr/local/bin/xconfig 中 **vi redis.conf**

### 8.1 单位（Unit）

查看配置文件。

```shell
# Redis configuration file example.
#
# Note that in order to read the configuration file, Redis must be
# started with the file path as first argument:
#
# ./redis-server /path/to/redis.conf

# Note on units: when memory size is needed, it is possible to specify
# it in the usual form of 1k 5GB 4M and so forth:
#
# 1k => 1000 bytes
# 1kb => 1024 bytes
# 1m => 1000000 bytes
# 1mb => 1024*1024 bytes5
# 1g => 1000000000 bytes
# 1gb => 1024*1024*1024 bytes
#
# units are case insensitive so 1GB 1Gb 1gB are all the same.

################################## INCLUDES ###################################
```

配置文件 unit单位 对大小写不敏感！

### 8.2 包含（INCLUDES）

就是好比我们学习Spring、Improt， include。

```shell
################################## INCLUDES ###################################
...略
# include /path/to/local.conf
# include /path/to/other.conf
# include /path/to/fragments/*.conf
#
```

### 8.3 网络（NETWORK）

网络和端口配置。

```shell
bind 127.0.0.1 -::1 # 绑定的ip
protected-mode yes  # 保护模式
port 6379		   # 端口设置
```

### 8.4 通用（GENERAL）

pid 和 日志。

```shell
daemonize yes			       # 以守护进程的方式运行，默认是no，我们需要开启为yes
piffile /var/run/redis_6379.pid	# 如果以后台的方式运行，我们就需要指定一个pid文件

loglevel notice		 # 日志级别
logfile ""			# 日志的文件位置名
database 16			# 数据库的数量，默认是16个数据库
always-show-logo yes # 是否总是显示logo
```

### 8.5 快照（SNAPSHOT）

持久化，在规定的时间内，执行了多少次操作，则会持久化到文件 .rdb. aof

redis 是内存数据库，如果没有持久化，那么数据断电及失！

```shell
# save 3600 1 300 100 60 10000
3600 1：如果至少有一个键（key）在过去的3600秒（即1小时）内被修改，那么执行一次数据保存操作。
300 100：如果至少有100个键在过去的300秒（即5分钟）内被修改，那么执行一次数据保存操作。
60 10000：如果至少有10000个键在过去的60秒内被修改，那么执行一次数据保存操作。
stop-writes-on-bgsave-error yes	#持久化如果出错，是否继续工作
rdbcompression yes				#是否压缩rdb文件，需要消耗一些CPU资源
rdbchecksum yes					#保存rdb文件时，进行错误的检查校验
dir ./							#rdb文件保存的目录
```

REPLICATION 复制，我们后面讲解主从复制的，时候再进行讲解。

### 8.6 安全（SECURITY ）

可以在这里设置redis的密码，默认是没有密码！

```shell
################################## SECURITY ###################################
SECURITY下面。

# cd /usr/local/bin
# redis-server xconfig/redis.conf
# redis-cli -p 6379
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> config get requirepass # 获取redis的密码
1) "requirepass"
2) ""
127.0.0.1:6379> config set requirepass "123456" # 设置redis的密码
OK
127.0.0.1:6379> auth 123456 # 使用密码进行登录！
OK
127.0.0.1:6379> config get requirepass
1) "requirepass"
2) "123456"
```

### 8.7 限制 （CLIENTS）

```shell
################################### CLIENTS ####################################
maxclients 10000            # 设置能连接上redis的最大客户端的数量
maxmemory <bytes>           # redis 配置最大的内存容量
maxmemory-policy noeviction # 内存到达上限之后的处理策略
    1、volatile-lru：只对设置了过期时间的key进行LRU（默认值）
    2、allkeys-lru ： 删除lru算法的key
    3、volatile-random：随机删除即将过期key
    4、allkeys-random：随机删除
    5、volatile-ttl ： 删除即将过期的
    6、noeviction ： 永不过期，返回错误
```

### 8.8 AOF文件（APPEND ONLY MODE）

APPEND ONLY 模式  aof 配置

```shell
############################## APPEND ONLY MODE ###############################
appendonly no			       # 默认不开启aof模式，默认开启rdb方式持久化
appendfilename "appendonly.aof"	# 持久化的文件名字

#appendfsync always	 # 每次修改都会sync 消耗性能
appendfsync everysec # 每秒执行一次 可能会丢失这一秒的数据
#appendfsync no		 # 不执行sync 这个时候操作系统自己同步数据 速度最快
```

具体的配置，我们在 Redis持久化 中去给大家详细详解！

## 9、Redis持久化

面试和工作，持久化都是重点！

Redis 是内存数据库。  
Redis持久化是指将Redis内存数据库中的数据保存到磁盘上，以防止因服务器重启、进程关闭、系统崩溃等原因导致的数据丢失。由于Redis默认是将所有数据存储在内存中，如果不进行持久化，这些数据就可能无法恢复。所以 Redis 提供了持久化功能！

Redis提供了两种主要的持久化机制：

1、**RDB (Redis Database) 快照：**

- RDB是一种周期性地将Redis数据集转储到磁盘上的策略。在预设的时间间隔或者满足特定条件（如指定数量的写操作）时，Redis会创建数据集的一个快照，并将其保存为一个名为`dump.rdb`的二进制文件。
- RDB文件是经过压缩的，因此通常占用的空间较小，且恢复速度相对较快。
- RDB持久化适合于那些能够容忍在一定时间窗口内数据丢失的应用场景。

2、**AOF (Append-Only File) 日志：**

- AOF持久化是将所有的写操作命令记录到一个日志文件（通常是`.aof`文件）中。每次写操作都会被追加到这个文件中。
- AOF文件可以配置不同的同步策略（如`always`、`everysec`和`no`），以平衡数据安全性与性能。
- AOF持久化具有更高的数据安全性，因为它是增量记录所有写操作，理论上可以提供秒级别的数据丢失恢复。
- Redis还提供了AOF重写功能，用于压缩AOF文件，减少不必要的命令并优化存储空间。

从Redis 4.0开始，还支持一种混合持久化模式，即同时使用RDB和AOF。在这种模式下，Redis在启动时既可以快速加载RDB文件，又可以利用AOF文件来恢复最近的写操作。

通过选择合适的持久化策略，Redis能够在保证数据可靠性的同时，兼顾系统的性能和资源利用率。应用程序可以根据其对数据安全性和性能的要求来选择最适合的持久化方法。

### 9.1、RDB（Redis DataBase）

#### 9.1.1 什么是RDB？

RDB (Redis Database) 是 Redis 的一种持久化方式。它通过创建数据集的快照（snapshot）来实现将内存中的数据保存到磁盘上。

以下是对 RDB 持久化的关键特性概述：

1. **快照生成：** 在预设的时间间隔或者满足特定条件时（如指定时间内发生一定数量的写操作），Redis 会自动或手动触发一次快照生成过程。这个过程通常通过执行 `SAVE` 或 `BGSAVE` 命令来启动。
2. **子进程操作：** 为了不影响主线程处理客户端请求，Redis 使用一个子进程来执行快照生成操作。子进程会创建当前内存中数据的一个副本，并将其序列化为一个二进制文件，通常命名为 `dump.rdb`。
3. **数据恢复：** 当 Redis 重启时，如果找到了 RDB 文件，它会自动加载该文件并将其中的数据恢复到内存中。这个过程相对快速，因为它是直接读取和解码二进制文件。
4. **性能与空间效率：** RDB 文件是经过压缩的，因此通常占用的空间较小。此外，由于只在预设时间点或满足特定条件时才进行快照，所以 RDB 持久化对 Redis 性能的影响相对较小。
5. **数据丢失风险：** RDB 持久化的缺点是如果在快照生成之间发生系统崩溃或 Redis 进程终止，那么在这段时间内未被持久化的数据可能会丢失。
6. **配置选项：** Redis 提供了多种配置选项来控制 RDB 持久化的行为，包括何时生成快照、快照文件的存储位置等。

RDB 持久化适用于那些能够容忍在一定时间窗口内数据丢失，并且希望在恢复时有较快速度的应用场景。对于需要更高数据安全性和一致性保证的应用，可以考虑使用 Redis 的另一种持久化机制 AOF（Append-Only File）。

在主从复制中，rdb就是备用的！在从机上面！![在这里插入图片描述](D:\2021\Redis\redis-study\img\31.png)

在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里。

Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的。这就确保了极高的性能。如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失。我们默认的就是RDB，一般情况下不需要修改这个配置！

有时候在生产环境我们会将这个文件进行备份！

rdb保存的文件是dump.rdb 都是在我们的配置文件中快照中进行配置的！

```shell
################################ SNAPSHOTTING  ################################
dbfilename dump.rdb
# save 3600 1 300 100 60 10000
save 60 5 # 测试:只要60s内修改5次key，就会触发rdb操作

# 1、切换到/usr/local/bin目录下
root@wzx:~# cd /usr/local/bin
root@wzx:/usr/local/bin# ls
cloud-id    cloud-init-per  jsonpatch    jsonschema        mcrypt    normalizer       redis-check-aof  redis-cli       redis-server
cloud-init  jsondiff        jsonpointer  libmcrypt-config  mdecrypt  redis-benchmark  redis-check-rdb  redis-sentinel  xconfig

# 2、修改配置
root@wzx:/usr/local/bin# cd xconfig
root@wzx:/usr/local/bin/xconfig# vi redis.conf
在 ### SNAPSHOTTING  ### 下添加save 60 5 只要60s内修改5次key，就会触发rdb操作
# save 3600 1 300 100 60 10000
save 60 5

# 3、重启redis-server服务
root@wzx:/usr/local/bin# service redis stop
root@wzx:/usr/local/bin# redis-server xconfig/redis.conf

# 4、打开另外一个窗口做测试
# 连接到运行在默认 Redis 端口（6379）上的 Redis 服务器。
root@wzx:/usr/local/bin# redis-cli -p 6379
127.0.0.1:6379> flushall
OK
127.0.0.1:6379> set k1 v1
OK
127.0.0.1:6379> set k2 v2
OK
127.0.0.1:6379> set k3 v3
OK
127.0.0.1:6379> set k4 v4
OK
127.0.0.1:6379> set k5 v5
OK
127.0.0.1:6379> 

# 5、最好在bin目录下查看测试触发生成的.rdb文件
root@wzx:/usr/local/bin# ls
cloud-id    cloud-init-per  jsondiff   jsonpointer  libmcrypt-config  mdecrypt    redis-benchmark  redis-check-rdb  redis-sentinel  xconfig
cloud-init  dump.rdb        jsonpatch  jsonschema   mcrypt            normalizer  redis-check-aof  redis-cli        redis-server
root@wzx:/usr/local/bin# 
```

#### 9.1.2 RDB触发机制

1、save的规则满足的情况下，会自动触发rdb规i则
2、执行 flushall 命令，也会触发我们的rdb规则！
3、退出redis，也会产生 rdb 文件！

备份就自动生成一个 dump.rdb文件。

#### 9.1.3 如何恢复rdb文件！

1、只需要将rdb文件放在我们redis启动目录就可以，redis启动的时候会自动检查dump.rdb 恢复其中的数据！

2、查看需要存在的位置。

```shell
127.0.0.1:6379> config get dir
1) "dir"
2) "/usr/local/bin" # 如果在这个目录下存在 dump.rdb 文件，启动就会自动恢复其中的数据
```

几乎就他自己默认的配置就够用了，但是我们还是需要去学习！

**优点：**
1、适合大规模的数据恢复！

2、对数据的完整性要不高！

**缺点：**

1、需要一定的时间间隔进程操作！如果redis意外宕机了，这个最后一次修改数据就没有了！

2、fork进程的时候，会占用一定的内容空间！！

### 9.2、AOF（Append Only File）

将我们的所有命令都记录下来，history，恢复的时候就把这个文件全部在执行一遍！

#### 9.2.1 AOF是什么？

AOF（Append-Only File）是Redis的一种持久化方式。在AOF持久化模式下，Redis会将所有的写操作命令以文本格式追加到一个日志文件中。每当有写操作发生时， Redis会将执行的命令记录到AOF文件中。

以下是一些关于AOF持久化的关键特性：

1. **命令追加：** 每个写命令都会被追加到AOF文件的末尾，这样就可以通过重新执行这些命令来恢复数据。

2. **同步策略：** AOF提供了不同的同步选项，包括`always`、`everysec`和`no`。这些选项控制了命令何时被确保写入磁盘，以平衡数据安全性与性能。
   - `always`：每次写操作后都会立即同步到磁盘，最安全但性能影响最大。
   - `everysec`（默认）：每秒同步一次，即使在这期间服务器宕机，最多只会丢失一秒的数据。
   - `no`：依赖于操作系统的缓存策略，最快但风险最大，可能在系统崩溃时丢失较多数据。
   
3. **重写机制：** 随着AOF文件的增长，Redis提供了一个名为AOF重写的机制。该机制可以周期性地创建一个新的AOF文件，其中只包含重建当前数据集所需的最小命令集，从而减小AOF文件的大小并提高后续恢复的速度。

4. **故障恢复：** 当Redis重启时，如果启用了AOF持久化，它会自动加载并执行AOF文件中的命令，以重建内存中的数据集。

相比Redis的另一种持久化方式RDB（Redis Database），AOF提供了更高的数据安全性，因为它记录了所有写操作，理论上可以提供秒级别的数据丢失恢复。然而，由于需要不断地追加和可能的重写操作，AOF在写入密集型工作负载下可能会对性能产生更大的影响。根据应用程序的具体需求，可以选择使用AOF、RDB或两者的混合持久化策略。

![在这里插入图片描述](D:\2021\Redis\redis-study\img\32.png)

以日志的形式来记录每个写操作，将Redis执行过的所有指令记录下来（读操作不记录），只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作

**7.0 以前 Aof保存的是 appendonly.aof 文件**

```shell
############################## APPEND ONLY MODE ###############################
...略
appendonly yes # 默认是no关闭的。
appendfilename "appendonly.aof"
appenddirname "appendonlydir"

redis-check-aof --fix appendonly.aof

# 修改配置文件：
vim /etc/redis/redis.conf
appendonly yes # 把 no 改为 yes

# 确定存储文件名是否正确
appendfilename "appendonly.aof"
```

默认是不开启的，我们需要手动进行配置！我们只需要将 appendonly 改为yes就开启了 aof。

重启，redis 就可以生效了！

如果这个 aof 文件有错位，这时候 redis 是启动不起来的吗，我们需要修复这个aof文件。

redis 给我们提供了一个工具 `redis-check-aof --fix appendonly.aof` + ENTER y

如果文件正常，重启就可以直接恢复了！

检查Redis服务器是否正在运行或者查看其运行状态：`ps -df|grep redis`

**7.0 以后的 Aof 升级改版内容**

**MP-AOF实现**

Redis 7.0 对 AOF（Append-Only File）持久化机制进行了一些升级和改版，以下是一些主要的改动内容：

1. **MPAOF (Multi-Purpose AOF) 机制**：
   - Redis 7.0 引入了 MPAOF（Multi-Purpose AOF）机制，这允许 AOF 文件包含多种不同的内容格式，而不仅仅是命令日志。
   - MPAOF 可以将 RDB 快照和 AOF 命令日志混合存储在一个文件中，这样在重启时可以更快地加载数据。
2. **aof-use-rdb-preamble 参数变更**：
   - 在 Redis 6 及以前版本中，`aof-use-rdb-preamble` 参数默认值为 `yes`，这意味着 AOF 文件的前缀部分使用 RDB 格式，而后为 AOF 格式。
   - 在 Redis 7.0 中，由于引入了 MPAOF，`aof-use-rdb-preamble` 参数不再用于控制 AOF 文件的前缀部分是否使用 RDB 格式，而是用于表示 baseAOF 的采用的格式（RDB 或 AOF）。
3. **AOF 重写优化**：
   - Redis 7.0 可能对 AOF 重写过程进行了优化，以提高效率和减少磁盘空间占用。
   - 这可能包括改进的命令压缩算法、更智能的增量重写策略等。
4. **AOF 工作文件存放目录**：
   - 虽然具体的变更细节没有明确提到，但通常 Redis 的新版本可能会更新或优化 AOF 文件的工作目录配置选项。
5. **其他底层原理变更**：
   - 可能还包括对 AOF 编码、解码、同步策略等方面的改进，以提高性能、稳定性并减少数据丢失的风险。

需要注意的是，以上信息是基于之前提供的资料摘要，具体改版内容和细节可能会有所不同。要获取最准确和详细的信息，建议查阅官方的 Redis 7.0 发布文档或相关的技术文章。

顾名思义，MP-AOF就是将原来的单个AOF文件拆分成多个AOF文件。在MP-AOF中，我们将AOF分为三种类型，分别为:

- BASE：表示基础AOF，它一般由子进程通过重写产生，该文件最多只有一个。
- INCR：表示增量AOF，1它一般会在AOFRW开始执行时被创建，该文件可能存在多个。
- HISTORY：表示历史AOF，它由BASE和|NCR AOF变化而来，每次AOFRW成功完成时，本次AOFRW之前对应的BASE和INCR AOF都将变为HISTORY，HISTORY类型的AOF会被Redis自动删除。

为了管理这些AOF文件，我们引入了一个manifest(清单）文件来跟踪、管理这些AOF。同时，为了便于AOF备份和拷贝，我们将所有的AOF文件和manifest文件放入一个单独的文件目录中，目录名由appenddirname配置（Redis 7.0新增配置项）决定。

**Aof缓冲区三种写回策略**
![在这里插入图片描述](D:\2021\Redis\redis-study\img\33.png)

```shell
// 几种类型文件的前缀,后跟有关序列和类型的附加信息
appendfilename "appendonly.aof"

//新版本增加的目录配詈项目
appenddirname "appendon1ydir"

//如有下的aof文件存在
1．基本文件
   appendonly.aof.1.base.rdb
2．增量文件
   appendonly.aof.1.incr.aof
   appendonly.aof.2.incr.aof
3．清单文件
   appendonly.aof.manifest
```

#### 9.2.2 测试AOF

```shell
# 1、先删除bin目录下的 appendonlydir 文件
root@wzx:/usr/local/bin# ll
appendonlydir  redis-cli redis-server xconfig ...略
root@wzx:/usr/local/bin# rm -rf appendonlydir
root@wzx:/usr/local/bin# ll
redis-cli redis-server xconfig ...略
root@wzx:/usr/local/bin# rm -rf appendonlydir

# 2、在redis.conf配置文件中 开启 appendonly yes。
root@wzx:/usr/local/bin# cd xconfig
root@wzx:/usr/local/bin/xconfig# ls
redis.conf
root@wzx:/usr/local/bin/xconfig# vi redis.conf
############################## APPEND ONLY MODE ###############################
...略
appendonly yes # 默认是no关闭的。

# 3、启动redis-server 或 重启redis-server服务 查看进程ID 杀死ID再启动
# 启动Redis
[root@localhost bin]# redis-server xconfig/redis.conf
# 重启Redis
root@wzx:/usr/local/bin# pgrep redis
54801
root@wzx:/usr/local/bin# kill -9 54801

# 4、查看bin目录下的 appendonlydir 文件是否已生成
root@wzx:/usr/local/bin# ls
appendonlydir xconfig ...略
root@wzx:/usr/local/bin# 

# 5、连接Redis
root@wzx:/usr/local/bin# redis-cli -p 6379
127.0.0.1:6379> set k1 v1 # 创建一个key value做测试
OK
127.0.0.1:6379> keys *
1) "k1"

# 6、查看appendonlydir文件下appendonly.aof.1.incr.aof
root@wzx:/usr/local/bin# cd appendonlydir
root@wzx:/usr/local/bin/appendonlydir# ll
total 20
drwxr-xr-x 2 root root 4096 Dec 26 11:03 ./
drwxr-xr-x 4 root root 4096 Dec 26 10:56 ../
-rw-r--r-- 1 root root   88 Dec 26 10:56 appendonly.aof.1.base.rdb
-rw-r--r-- 1 root root   52 Dec 26 11:03 appendonly.aof.1.incr.aof
-rw-r--r-- 1 root root   88 Dec 26 10:56 appendonly.aof.manifest
root@wzx:/usr/local/bin/appendonlydir# vim appendonly.aof.1.incr.aof
root@wzx:/usr/local/bin/appendonlydir# 
   # 就可以看到记录每个写操作，将Redis执行过的所有指令记录下来（读操作不记录），只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作。
```

#### 9.2.3 重写规则说明

aof 默认就是文件的无限追加，文件会越来越大！
![](D:\2021\Redis\redis-study\img\34.png)

如果 aof 文件大于 64m，太大了！ fork一个新的进程来将我们的文件进行重写！

```shell
appendonly no # 默认是不开启aof模式的，默认是使用rdb方式持久化的，在大部分所有的情况下，
rdb完全够用！
appendfilename "appendonly.aof" # 持久化的文件的名字
# appendfsync always # 每次修改都会 sync。消耗性能
appendfsync everysec # 每秒执行一次 sync，可能会丢失这1s的数据！
# appendfsync no # 不执行 sync，这个时候操作系统自己同步数据，速度最快！
# rewrite 重写，
```

#### 9.2.4 Aof 优点和缺点！

优点：

1、每一次修改都同步，文件的完整会更加好；

2、每秒同步一次，可能会丢失一秒的数据；

3、从不同步，效率最高的！

缺点：

1、相对于数据文件来说，aof远远大于 rdb，修复的速度也比 rdb慢；

2、Aof 运行效率也要比 rdb 慢，所以我们redis默认的配置就是rdb持久化！

#### 9.2.5 扩展

1、RDB 持久化方式能够在指定的时间间隔内对你的数据进行快照存储；

2、AOF 持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以Redis 协议追加保存每次写的操作到文件末尾，Redis还能对AOF文件进行后台重写，使得AOF文件的体积不至于过大；

3、只做缓存，如果你只希望你的数据在服务器运行的时候存在，你也可以不使用任何持久化；

4、同时开启两种持久化方式

- 在这种情况下，当redis重启的时候会优先载入AOF文件来恢复原始的数据，因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整。
- RDB 的数据不实时，同时使用两者时服务器重启也只会找AOF文件，那要不要只使用AOF呢？作者建议不要，因为RDB更适合用于备份数据库（AOF在不断变化不好备份），快速重启，而且不会有AOF可能潜在的Bug，留着作为一个万一的手段。

5、性能建议

- 因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够了，只保留 save 900 1 这条规则。
- 如果Enable AOF ，好处是在最恶劣情况下也只会丢失不超过两秒数据，启动脚本较简单只load自己的AOF文件就可以了，代价一是带来了持续的IO，二是AOF rewrite 的最后将 rewrite 过程中产生的新数据写到新文件造成的阻塞几乎是不可避免的。只要硬盘许可，应该尽量减少AOF rewrite的频率，AOF重写的基础大小默认值64M太小了，可以设到5G以上，默认超过原大小100%大小重
  写可以改到适当的数值。
- 如果不Enable AOF ，仅靠 Master-Slave Repllcation 实现高可用性也可以，能省掉一大笔IO，也减少了rewrite时带来的系统波动。代价是如果Master/Slave 同时倒掉，会丢失十几分钟的数据，启动脚本也要比较两个 Master/Slave 中的 RDB文件，载入较新的那个，微博就是这种架构。

## 10、Redis发布订阅

Redis 发布订阅(pub/sub)是一种**消息通信模式**：发送者(pub)发送消息，订阅者(sub)接收消息。微信、微博、关注等系统！

Redis 客户端可以订阅任意数量的频道。

### 10.1、订阅/发布消息图

第一个：消息发送者；第二个：频道；第三个：消息订阅者！
![在这里插入图片描述](D:\2021\Redis\redis-study\img\35.png)

下图展示了频道 channel1，以及订阅这个频道的三个客户端 —— client2 、 client5 和 client1 之间的关系：
![在这里插入图片描述](D:\2021\Redis\redis-study\img\36.png)

当有新消息通过 PUBLISH 命令发送给频道 channel1 时， 这个消息就会被发送给订阅它的三个客户端：
![在这里插入图片描述](D:\2021\Redis\redis-study\img\37.png)

### 10.2、命令

这些命令被广泛用于构建即时通信应用，比如网络聊天室(chatroom)和实时广播、实时提醒等。
![在这里插入图片描述](D:\2021\Redis\redis-study\img\38.png)

### 10.3、测试

1、订阅端：

```shell
127.0.0.1:6379> subscribe xing # 先订阅一个频道 xing
1) "subscribe"
2) "xing"
3) (integer) 1
# 等待读取 发布端 推送的信息
1) "message"     # 消息
2) "xing"        # xing频道的消息
3) "hello,xing!" # 消息的具体内容
```

2、发送端：

```bash
127.0.0.1:6379> publish xing hello,xing! # 发布者发布消息 hello,xing! 到 xing 频道！
(integer) 1
127.0.0.1:6379> 
```

### 10.4、原理

Redis是使用C实现的，通过分析 Redis 源码里的 pubsub.c 文件，了解发布和订阅机制的底层实现，籍此加深对 Redis 的理解。

Redis 通过 PUBLISH 、SUBSCRIBE 和 PSUBSCRIBE 等命令实现发布和订阅功能。

微信：

通过 SUBSCRIBE 命令订阅某频道后，redis-server 里维护了一个字典，字典的键就是一个个 频道！，而字典的值则是一个链表，链表中保存了所有订阅这个 channel 的客户端。SUBSCRIBE 命令的关键，就是将客户端添加到给定 channel 的订阅链表中。

推送流程：A、后台编辑文章发布 → B、xing公众账号 → C、订阅我的账号的所有用户 。

通过 PUBLISH 命令向订阅者发送消息，redis-server 会使用给定的频道作为键，在它所维护的 channel字典中查找记录了订阅这个频道的所有客户端的链表，遍历这个链表，将消息发布给所有订阅者。

Pub/Sub 从字面上理解就是发布（Publish）与订阅（Subscribe），在Redis中，你可以设定对某一个key值进行消息发布及消息订阅，当一个key值上进行了消息发布后，所有订阅它的客户端都会收到相应的消息。这一功能最明显的用法就是用作实时消息系统，比如普通的即时聊天，群聊等功能。

使用场景：
1、实时消息系统！
2、事实聊天！（频道当做聊天室，将信息回显给所有人即可！）
3、订阅，关注系统都是可以的！

稍微复杂的场景我们就会使用 消息中间件 MQ 。

## 11、Redis主从复制

### 11.1、概念

主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称为主节点(master/leader)，后者称为从节点(slave/follower)；数据的复制是单向的，只能由主节点到从节点。Master以写为主，Slave 以读为主。

默认情况下，每台Redis服务器都是主节点；且一个主节点可以有多个从节点(或没有从节点)，但一个从节点只能有一个主节点。

**主从复制的作用主要包括：**

1、数据冗余：主从复制实现了数据的热备份，是持久化之外的一种数据冗余方式。

2、故障恢复：当主节点出现问题时，可以由从节点提供服务，实现快速的故障恢复；实际上是一种服务的冗余。

3、负载均衡：在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务（即写Redis数据时应用连接主节点，读Redis数据时应用连接从节点），分担服务器负载；尤其是在写少读多的场景下，通过多个从节点分担读负载，可以大大提高Redis服务器的并发量。

4、高可用（集群）基石：除了上述作用以外，主从复制还是哨兵和集群能够实施的基础，因此说主从复制是Redis高可用的基础。

一般来说，要将Redis运用于工程项目中，只使用一台Redis是万万不能的（宕机），原因如下：

1、从结构上，单个Redis服务器会发生单点故障，并且一台服务器需要处理所有的请求负载，压力较大；

2、从容量上，单个Redis服务器内存容量有限，就算一台Redis服务器内存容量为256G，也不能将所有内存用作Redis存储内存，一般来说，**单台Redis最大**使用内存不应该超过**20G**。

电商网站上的商品，一般都是一次上传，无数次浏览的，说专业点也就是"多读少写"。

对于这种场景，我们可以使如下这种架构：
![在这里插入图片描述](D:\2021\Redis\redis-study\img\39.png)

主从复制，读写分离！ 80% 的情况下都是在进行读操作！减缓服务器的压力！架构中经常使用！ 一主二从！

只要在公司中，主从复制就是必须要使用的，因为在真实的项目中不可能单机使用Redis！

### 11.2、环境配置及启动

1、只配置从库，不用配置主库！

```bash
127.0.0.1:6379> info replication # 查看当前库的信息
role:master         # 角色 master （主机）
connected_slaves:0  # 从机 当前为0
master_failover_state:no-failover
master_replid:23e5d1189d1fd92a10229706b957ab4636283111
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:2
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
127.0.0.1:6379> 
```

2、操作步骤：

```shell
# 1、复制3个配置文件，然后修改对应的信息
root@wzx:/usr/local/bin/xconfig# ls
redis.conf
root@wzx:/usr/local/bin/xconfig# cp redis.conf redis79.conf
root@wzx:/usr/local/bin/xconfig# cp redis.conf redis80.conf
root@wzx:/usr/local/bin/xconfig# cp redis.conf redis81.conf
root@wzx:/usr/local/bin/xconfig# ls
redis79.conf  redis80.conf  redis81.conf  redis.conf
root@wzx:/usr/local/bin/xconfig# 

# 2、编辑 (redis79.conf  redis80.conf  redis81.conf) 各个配置文件
root@wzx:/usr/local/bin/xconfig# vi redis79.conf
root@wzx:/usr/local/bin/xconfig# vi redis80.conf
root@wzx:/usr/local/bin/xconfig# vi redis81.conf
1、port 端口                 # port 6379
2、daemonize yes 后台运行打开 # daemonize yes
3、pid 名字                  # pidfile /var/run/redis_6379.pid
4、log 文件名字               # logfile "6379.log"
5、dbfilename dump.rdb 名字  # dbfilename dump6379.rdb

# 3、启动(redis79.conf  redis80.conf  redis81.conf) 3个redis服务
root@wzx:/usr/local/bin# redis-server xconfig/redis79.conf
root@wzx:/usr/local/bin# redis-server xconfig/redis80.conf
root@wzx:/usr/local/bin# redis-server xconfig/redis81.conf

# 4、查看bin目录下已经生成三个log文件
root@wzx:/usr/local/bin# ls
6379.log  appendonlydir  cloud-init-per  jsonpatch    libmcrypt-config  normalizer       redis-check-rdb  redis-server
6380.log  cloud-id       dump.rdb        jsonpointer  mcrypt            redis-benchmark  redis-cli        xconfig
6381.log  cloud-init     jsondiff        jsonschema   mdecrypt          redis-check-aof  redis-sentinel

# 5、通过进程信息查看三个redis服务！ 命令：ps -ef|grep redis
root@wzx:/usr/local/bin# ps -ef|grep redis
root      298678       1  0  2023 ?        00:17:46 redis-server 127.0.0.1:6379
root      810690       1  0 09:43 ?        00:00:00 redis-server 127.0.0.1:6380
root      810696       1  0 09:43 ?        00:00:00 redis-server 127.0.0.1:6381
root      810723  808353  0 09:44 pts/0    00:00:00 grep --color=auto redis
root@wzx:/usr/local/bin# 
```

### 11.3、一主二从

当前，默认情况下，**每台Redis服务器都是主节点**； 我们一般情况下**只用配置从机**就好了！

认老大！**主机** （79）；**从机**（80，81）

```shell
# 在从机 6380 中认主机（老大）
127.0.0.1:6380> slaveof 127.0.0.1 6379 # slaveof host 6379 找谁当自己的老大！ 第二台也一样。
OK
127.0.0.1:6380> info replication
role:slave             # 当前角色是从机
master_host:127.0.0.1  # 可以的看到主机的ip
master_port:6379       # 可以的看到主机的端口号
master_link_status:down
master_last_io_seconds_ago:-1
master_sync_in_progress:0
slave_read_repl_offset:0
slave_repl_offset:0
master_link_down_since_seconds:-1
slave_priority:100
slave_read_only:1
replica_announced:1
connected_slaves:0
master_failover_state:no-failover
master_replid:85d81d3b9fc82cdbc90719f0f25f8d249a876d43
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
127.0.0.1:6380> 

# 在主机 6379 中查看！
127.0.0.1:6379> info replication
role:master
connected_slaves:1 # 多了从机的配置
slave0:ip=127.0.0.1,port=6380,state=online,offset=142,lag=1 # 多了从机的配置
master_failover_state:no-failover
master_replid:3df3ab4cf83f3ef762508de05cdb07bff36205bd
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:142
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:3
repl_backlog_histlen:140
127.0.0.1:6379> 

# 如果两个都配置完了，就是有两个从机
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:2 # 两个从机
slave0:ip=127.0.0.1,port=6380,state=online,offset=394,lag=1
slave1:ip=127.0.0.1,port=6381,state=online,offset=394,lag=1
master_failover_state:no-failover
master_replid:3df3ab4cf83f3ef762508de05cdb07bff36205bd
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:394
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:3
repl_backlog_histlen:392
127.0.0.1:6379> 
```

真实的从主配置应该在配置文件中配置，这样的话是永久的，我们这里使用的是命令，暂时的！

```shell
# 在配置文件中配置方法
replicaof <masterip> <masterport>
# If the master is password protected (using the "requirepass" configuration
# directive below) it is possible to tell the replica to authenticate before
# starting the replication synchronization process, otherwise the master will
# refuse the replica request.
#
masterauth <master-password>
#
# However this is not enough if you are using Redis ACLs (for Redis version
# 6 or greater), and the default user is not capable of running the PSYNC
# command and/or other commands needed for replication. In this case it's
# better to configure a special user to use with replication, and specify the
# masteruser configuration as such:
#
masteruser <username>
↑↑↑ 配置以上三个（主机ip 端口号），（主机密码），（主机名称）。
```

### 11.4、细节

主机可以写，**从机不能写只能读**！主机中的所有信息和数据，都会自动被从机保存！

主机写：

```shell
# 主机可以 读写
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> set k1 v1
OK
127.0.0.1:6379> get k1
"v1"

# 从机只能读 不能写
127.0.0.1:6380> get k1
"v1"
127.0.0.1:6380> set k2 v2
(error) READONLY You can't write against a read only replica.
127.0.0.1:6380> 
```

测试：主机断开连接，从机依旧连接到主机的，但是没有写操作，这个时候，主机如果回来了，从机依旧可以直接获取到主机写的信息！

如果是使用命令行，来配置的主从，这个时候如果从机重启了，就会变回主机！只要**变为从机，立马就会从主机中获取值**！

### 11.5、复制原理

slave 启动成功连接到 master 后会发送一个sync同步命令。

Master 接到命令，启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕之后，**master将传送整个数据文件到slave，并完成一次完全同步。**

**全量复制：**slave服务在接收到数据库文件数据后，将其存盘并加载到内存中。

**增量复制：**Master 继续将新的所有收集到的修改命令依次传给slave，完成同步。

但是只要是重新连接master，一次完全同步（全量复制）将被自动执行！ 我们的数据一定可以在从机中看到！

**Redis的主从复制机制**中，数据同步方式包括全量复制（Full Resynchronization）和增量复制（Partial Resynchronization），这两种复制方式在不同场景下用于保持主从节点间的数据一致性。

1. **全量复制**：
   - 当一个新的从节点首次连接到主节点时，或者从节点与主节点之间的复制连接断开时间过长，导致复制偏移量、运行ID等信息不再匹配时，需要进行全量复制。
   - 在全量复制过程中，主节点会创建一个RDB快照（即Redis数据库的二进制形式），并将这个包含所有键值对的快照文件发送给从节点。
   - 从节点接收并载入这个RDB文件，从而快速建立起与主节点一致的数据状态。
   - 此过程可能会消耗较多的网络带宽和CPU资源，尤其是当数据量非常大时。
2. **增量复制**（也称为部分复制）：
   - 在全量复制之后或者从节点与主节点始终保持连接的情况下，从节点通常采用增量复制的方式来获取后续的所有写操作命令。
   - 主节点维护了一个复制积压缓冲区（Replication Backlog），其中存储了一段时间内执行过的写命令序列。
   - 当从节点断线重连后，如果条件满足（如复制偏移量在复制积压缓冲区的有效范围内，并且主节点的运行ID未发生变化），从节点可以请求部分复制，主节点只需将从节点断线期间错过的新命令发送给从节点执行即可。
   - 增量复制极大地提高了主从节点之间数据同步的效率和实时性，减少了不必要的数据传输。

层层链路，上一个M链接下一个 依旧是S！

测试：79为主机（不变），80为从机（不变），81（把81改为连接80）。
![在这里插入图片描述](D:\2021\Redis\redis-study\img\40.png)

81连接80，80连接79；此时80是79的从机，81是80的从机。  
这时候，其实80还是从机，80和81还是没有写权限、只有读权限。  
这时候也可以完成我们的主从复制。

假设6379断开了，shutdown了。这样就没有老大了，这个时候能不能选择一个老大出来呢？ 手动！谋朝篡位。

如果主机断开了连接，我们可以使用 slaveof no one 让自己变成主机！其他的节点就可以手动连接到最新的这个主节点（手动）！

127.0.0.1:6380>`slaveof no one ` # 让6380自己变成主机

如果这个时候 6379 老大修复了，那就重新连接！

## 12、哨兵模式

（自动选举老大的模式）

### 12.1、概述

主从切换技术的方法是：当主服务器宕机后，需要手动把一台从服务器切换为主服务器，这就需要人工干预，费事费力，还会造成一段时间内服务不可用。这不是一种推荐的方式，更多时候，我们优先考虑哨兵模式。Redis从2.8开始正式提供了Sentinel（哨兵） 架构来解决这个问题。

谋朝篡位的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库。

哨兵模式是一种特殊的模式，首先Redis提供了哨兵的命令，哨兵是一个独立的进程，作为进程，它会独立运行。其原理是哨兵通过发送命令，等待Redis服务器响应，从而监控运行的多个Redis实例。
![在这里插入图片描述](D:\2021\Redis\redis-study\img\41.png)

这里的哨兵有两个作用

- 通过发送命令，让Redis服务器返回监控其运行状态，包括主服务器和从服务器。
- 当哨兵监测到master宕机，会自动将slave切换成master，然后通过发布订阅模式通知其他的从服务器，修改配置文件，让它们切换主机。

然而一个哨兵进程对Redis服务器进行监控，可能会出现问题，为此，我们可以使用多个哨兵进行监控。各个哨兵之间还会进行监控，这样就形成了多哨兵模式。
![在这里插入图片描述](D:\2021\Redis\redis-study\img\42.png)

假设主服务器宕机，哨兵1先检测到这个结果，系统并不会马上进行failover过程，仅仅是哨兵1主观的认为主服务器不可用，这个现象称为**主观下线**。当后面的哨兵也检测到主服务器不可用，并且数量达到一定值时，那么哨兵之间就会进行一次投票，投票的结果由一个哨兵发起，进行failover[故障转移]操作。切换成功后，就会通过发布订阅模式，让各个哨兵把自己监控的从服务器实现切换主机，这个过程称为**客观下线**。

### 12.2、哨兵测试！

我们目前的状态是 一主二从！

1、创建一个sentinel.conf

```shell
root@wzx:/usr/local/bin# cd xconfig
root@wzx:/usr/local/bin/xconfig# ls
redis79.conf  redis80.conf  redis81.conf  redis.conf
root@wzx:/usr/local/bin/xconfig# 
root@wzx:/usr/local/bin/xconfig# vi sentinel.conf
配置哨兵文件↓↓↓
```

2、配置哨兵配置文件 sentinel.conf

```shell
# sentinel monitor 被监控的名称 host port 1
sentinel monitor myredis 127.0.0.1 6379 1

用于监控一个名为“myredis”的主服务器实例。这句话的具体含义和作用如下：
myredis: 这是为被监控的Redis主服务器定义的一个名称或别名。Sentinel使用这个名称来管理对应的主从集群。
127.0.0.1: 指定Redis主服务器的IP地址，这里表示本地回环地址，即 sentinel 在同一台机器上监控Redis主节点。
6379: 指定Redis主服务器的服务端口。
1: 表示当有至少1个Sentinel节点认为主服务器失效时，该主服务器就被认为下线。也就是说，只要有一个Sentinel同意主服务器不可达，就会触发故障转移流程（自动将从节点提升为主节点，并通知其他从节点和客户端新的主节点位置）。
这条命令的作用是配置Sentinel去监视指定地址和端口上的Redis服务，确保其高可用性。当主服务器出现故障时，Sentinel系统能够自动完成故障发现、故障转移以及后续的从服务器重新配置等工作。

后面的这个数字1，代表至少有一个哨兵认为主机宕机时，该主机才被判定为宕机，slave投票看让谁接替成为主机，票数最多的，就会成为主机！
```

3、启动哨兵服务

```shell
root@wzx:/usr/local/bin# redis-sentinel xconfig/sentinel.conf
823604:X 05 Jan 2024 16:14:43.392 * oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
823604:X 05 Jan 2024 16:14:43.392 * Redis version=7.2.3, bits=64, commit=00000000, modified=0, pid=823604, just started
823604:X 05 Jan 2024 16:14:43.392 * Configuration loaded
823604:X 05 Jan 2024 16:14:43.392 * monotonic clock: POSIX clock_gettime
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 7.2.3 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                  
 (    '      ,       .-`  | `,    )     Running in sentinel mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 26379
 |    `-._   `._    /     _.-'    |     PID: 823604
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           https://redis.io       
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                                  
  `-._    `-._`-.__.-'_.-'    _.-'                                   
      `-._    `-.__.-'    _.-'                                       
          `-._        _.-'                                           
              `-.__.-'                                               
823604:X 05 Jan 2024 16:14:43.398 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:14:43.398 * Sentinel ID is 1e3e34812b6badf4da5ed51ef64a27667c48b5ff
823604:X 05 Jan 2024 16:14:43.398 # +monitor master myredis 127.0.0.1 6379 quorum 1
823604:X 05 Jan 2024 16:14:43.399 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:14:43.403 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:14:43.403 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:14:43.406 * Sentinel new configuration saved on disk
```

4、如果Master 节点断开了，这个时候就会从从机中随机选择一个服务器！ （这里面有一个投票算法！）

```shell
# 断开6379
127.0.0.1:6379> shutdown
not connected> exit
root@wzx:/usr/local/bin# 
```

5、查看80和81谁被选举为主机。

```shell
# 80
127.0.0.1:6380> info replication
# Replication
role:slave
master_host:127.0.0.1
master_port:6381
master_link_status:up
master_last_io_seconds_ago:0
master_sync_in_progress:0
slave_read_repl_offset:52369
slave_repl_offset:52369
slave_priority:100
slave_read_only:1
replica_announced:1
connected_slaves:0
master_failover_state:no-failover
master_replid:cef8ef91f4eccccc833d3049bee274c182aed23f
master_replid2:3df3ab4cf83f3ef762508de05cdb07bff36205bd
master_repl_offset:52369
second_repl_offset:37255
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:17
repl_backlog_histlen:52353
127.0.0.1:6380> 

# 81
127.0.0.1:6381> info replication
# Replication
role:master
connected_slaves:1
slave0:ip=127.0.0.1,port=6380,state=online,offset=42537,lag=0
master_failover_state:no-failover
master_replid:cef8ef91f4eccccc833d3049bee274c182aed23f
master_replid2:3df3ab4cf83f3ef762508de05cdb07bff36205bd
master_repl_offset:42537
second_repl_offset:37255
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:381
repl_backlog_histlen:42157
127.0.0.1:6381> 

# 现在可以发现 81被选举为主机 role:master ↑↑↑
# 80从机也自动连接到了 81 了。
```

6、查看哨兵日志！

```shell
root@wzx:/usr/local/bin# redis-sentinel xconfig/sentinel.conf
823604:X 05 Jan 2024 16:14:43.392 * oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
823604:X 05 Jan 2024 16:14:43.392 * Redis version=7.2.3, bits=64, commit=00000000, modified=0, pid=823604, just started
823604:X 05 Jan 2024 16:14:43.392 * Configuration loaded
823604:X 05 Jan 2024 16:14:43.392 * monotonic clock: POSIX clock_gettime
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 7.2.3 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                  
 (    '      ,       .-`  | `,    )     Running in sentinel mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 26379
 |    `-._   `._    /     _.-'    |     PID: 823604
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           https://redis.io       
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                                  
  `-._    `-._`-.__.-'_.-'    _.-'                                   
      `-._    `-.__.-'    _.-'                                       
          `-._        _.-'                                           
              `-.__.-'                                               
823604:X 05 Jan 2024 16:14:43.398 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:14:43.398 * Sentinel ID is 1e3e34812b6badf4da5ed51ef64a27667c48b5ff
823604:X 05 Jan 2024 16:14:43.398 # +monitor master myredis 127.0.0.1 6379 quorum 1
823604:X 05 Jan 2024 16:14:43.399 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:14:43.403 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:14:43.403 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:14:43.406 * Sentinel new configuration saved on disk
# 手动关闭6379主机后 哨兵日志就发出了以下信息 ↓↓↓↓↓
823604:X 05 Jan 2024 16:18:05.453 # +sdown master myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.453 # +odown master myredis 127.0.0.1 6379 #quorum 1/1
823604:X 05 Jan 2024 16:18:05.453 # +new-epoch 1
823604:X 05 Jan 2024 16:18:05.453 # +try-failover master myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.456 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:18:05.456 # +vote-for-leader 1e3e34812b6badf4da5ed51ef64a27667c48b5ff 1
823604:X 05 Jan 2024 16:18:05.456 # +elected-leader master myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.456 # +failover-state-select-slave master myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.515 # +selected-slave slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.515 * +failover-state-send-slaveof-noone slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.586 * +failover-state-wait-promotion slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.709 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:18:05.709 # +promoted-slave slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.709 # +failover-state-reconf-slaves master myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:05.778 * +slave-reconf-sent slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:06.748 * +slave-reconf-inprog slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:06.748 * +slave-reconf-done slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:06.831 # +failover-end master myredis 127.0.0.1 6379
823604:X 05 Jan 2024 16:18:06.831 # +switch-master myredis 127.0.0.1 6379 127.0.0.1 6381                # ↓↓↓↓↓
823604:X 05 Jan 2024 16:18:06.831 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6381
823604:X 05 Jan 2024 16:18:06.831 * +slave slave 127.0.0.1:6379 127.0.0.1 6379 @ myredis 127.0.0.1 6381
823604:X 05 Jan 2024 16:18:06.835 * Sentinel new configuration saved on disk
823604:X 05 Jan 2024 16:18:36.884 # +sdown slave 127.0.0.1:6379 127.0.0.1 6379 @ myredis 127.0.0.1 6381

# +switch-master myredis 127.0.0.1 6379 127.0.0.1 6381 是Redis Sentinel（哨兵）在执行故障转移操作后发出的通知消息。这条消息表示：↑↑↑↑↑
myredis: 这是被监控的Redis主从集群的逻辑名称，与配置Sentinel时使用的master-name一致。
127.0.0.1 6379: 表示原Redis主服务器的IP地址和端口，在此之前这个实例是集群中的主节点。
127.0.0.1 6381: 新的Redis主服务器的IP地址和端口，在发生故障转移后， Sentinel已经决定将原来的从节点（端口为6381）提升为主节点。
这条消息意味着，在Sentinel系统检测到名为“myredis”的Redis主节点（运行于127.0.0.1:6379）不可用后，它自动执行了故障转移流程，并成功地将从节点（127.0.0.1:6381）切换为主节点。同时，Sentinel会通知其他从节点开始复制新的主节点，并更新客户端连接到新的主节点以继续提供服务。
```

7、如果主机此时回来了，（再启动6379），只能归并到新的主机下，当做从机，这就是哨兵模式的规则！

```shell
root@wzx:/usr/local/bin# redis-server xconfig/redis79.conf
root@wzx:/usr/local/bin# redis-cli -p 6379
127.0.0.1:6379> info replication
# Replication
role:slave                   # ↓↓↓↓↓
master_host:127.0.0.1
master_port:6381
master_link_status:up
master_last_io_seconds_ago:1
master_sync_in_progress:0
slave_read_repl_offset:116978
slave_repl_offset:116978
slave_priority:100
slave_read_only:1
replica_announced:1
connected_slaves:0
master_failover_state:no-failover
master_replid:cef8ef91f4eccccc833d3049bee274c182aed23f
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:116978
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:107110
repl_backlog_histlen:9869
127.0.0.1:6379> 
# 现在可以发现 81被选举为主机后 6379再启动回来就只能当81的弟弟了 role:slave ↑↑↑↑↑
```

优点：

1、哨兵集群，基于主从复制模式，所有的主从配置优点，它全有；

2、 主从可以切换，故障可以转移，系统的可用性就会更好；

3、哨兵模式就是主从模式的升级，手动到自动，更加健壮！

缺点：

1、Redis 不好在线扩容的，集群容量一旦到达上限，在线扩容就十分麻烦！

2、实现哨兵模式的配置其实是很麻烦的，里面有很多选择！

### 12.3、哨兵模式的全部配置！

```shell
# Example sentinel.conf

# 哨兵sentinel实例运行的端口 默认26379
port 26379

# 哨兵sentinel的工作目录
dir /tmp

# 哨兵sentinel监控的redis主节点的 ip port
# master-name 可以自己命名的主节点名字 只能由字母A-z、数字0-9 、这三个字符".-_"组成。
# quorum 配置多少个sentinel哨兵统一认为master主节点失联 那么这时客观上认为主节点失联了
# sentinel monitor <master-name> <ip> <redis-port> <quorum>
sentinel monitor mymaster 127.0.0.1 6379 2

# 当在Redis实例中开启了requirepass foobared 授权密码 这样所有连接Redis实例的客户端都要提供密码
# 设置哨兵sentinel 连接主从的密码 注意必须为主从设置一样的验证密码
# sentinel auth-pass <master-name> <password>
sentinel auth-pass mymaster MySUPER--secret-0123passw0rd

# 指定多少毫秒之后 主节点没有应答哨兵sentinel 此时 哨兵主观上认为主节点下线 默认30秒
# sentinel down-after-milliseconds <master-name> <milliseconds>
sentinel down-after-milliseconds mymaster 30000

# 这个配置项指定了在发生failover主备切换时最多可以有多少个slave同时对新的master进行 同步，
这个数字越小，完成failover所需的时间就越长，
但是如果这个数字越大，就意味着越 多的slave因为replication而不可用。
可以通过将这个值设为 1 来保证每次只有一个slave 处于不能处理命令请求的状态。
# sentinel parallel-syncs <master-name> <numslaves>
sentinel parallel-syncs mymaster 1

# 故障转移的超时时间 failover-timeout 可以用在以下这些方面：
#1. 同一个sentinel对同一个master两次failover之间的间隔时间。
#2. 当一个slave从一个错误的master那里同步数据开始计算时间。直到slave被纠正为向正确的master那里同步数据时。
#3.当想要取消一个正在进行的failover所需要的时间。
#4.当进行failover时，配置所有slaves指向新的master所需的最大时间。不过，即使过了这个超时，slaves依然会被正确配置为指向master，但是就不按parallel-syncs所配置的规则来了
# 默认三分钟
# sentinel failover-timeout <master-name> <milliseconds>
sentinel failover-timeout mymaster 180000

# SCRIPTS EXECUTION
#配置当某一事件发生时所需要执行的脚本，可以通过脚本来通知管理员，例如当系统运行不正常时发邮件通知相关人员。
#对于脚本的运行结果有以下规则：
#若脚本执行后返回1，那么该脚本稍后将会被再次执行，重复次数目前默认为10
#若脚本执行后返回2，或者比2更高的一个返回值，脚本将不会重复执行。
#如果脚本在执行过程中由于收到系统中断信号被终止了，则同返回值为1时的行为相同。
#一个脚本的最大执行时间为60s，如果超过这个时间，脚本将会被一个SIGKILL信号终止，之后重新执行。

#通知型脚本:当sentinel有任何警告级别的事件发生时（比如说redis实例的主观失效和客观失效等等），将会去调用这个脚本，这时这个脚本应该通过邮件，SMS等方式去通知系统管理员关于系统不正常运行的信息。调用该脚本时，将传给脚本两个参数，一个是事件的类型，一个是事件的描述。如果sentinel.conf配置文件中配置了这个脚本路径，那么必须保证这个脚本存在于这个路径，并且是可执行的，否则sentinel无法正常启动成功。
#通知脚本
# shell编程
# sentinel notification-script <master-name> <script-path>
sentinel notification-script mymaster /var/redis/notify.sh

# 客户端重新配置主节点参数脚本
# 当一个master由于failover而发生改变时，这个脚本将会被调用，通知相关的客户端关于master地址已经发生改变的信息。
# 以下参数将会在调用脚本时传给脚本:
# <master-name> <role> <state> <from-ip> <from-port> <to-ip> <to-port>
# 目前<state>总是“failover”,
# <role>是“leader”或者“observer”中的一个。
# 参数 from-ip, from-port, to-ip, to-port是用来和旧的master和新的master(即旧的slave)通信的
# 这个脚本应该是通用的，能被多次调用，不是针对性的。
# sentinel client-reconfig-script <master-name> <script-path>
sentinel client-reconfig-script mymaster /var/redis/reconfig.sh # 一般都是由运维来配置！
```

## 13、Redis缓存穿透和雪崩

服务的高可用问题！在这里不会详细的去分析解决方案的底层！

Redis缓存的使用，极大的提升了应用程序的性能和效率，特别是数据查询方面。但同时，它也带来了一些问题。其中，最要害的问题，就是数据的一致性问题，从严格意义上讲，这个问题无解。如果对数据的一致性要求很高，那么就不能使用缓存。另外的一些典型问题就是，缓存穿透、缓存雪崩和缓存击穿。目前，业界也都有比较流行的解决方案。
![](D:\2021\Redis\redis-study\img\43.png)

### 13.1、缓存穿透（查不到数据）

#### 13.1.1 概念

缓存穿透的概念很简单，用户想要查询一个数据，发现redis内存数据库没有，也就是缓存没有命中，于是向持久层数据库查询。发现也没有，于是本次查询失败。当用户很多的时候，缓存都没有命中（秒杀！），于是都去请求了持久层数据库。这会给持久层数据库造成很大的压力，这时候就相当于出现了缓存穿透。

#### 13.1.2 解决方案

**布隆过滤器**

布隆过滤器是一种数据结构，对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则丢弃，从而避免了对底层存储系统的查询压力；![](D:\2021\Redis\redis-study\img\44.png)

**缓存空对象**

当存储层不命中后，即使返回的空对象也将其缓存起来，同时会设置一个过期时间，之后再访问这个数据将会从缓存中获取，保护了后端数据源；
![](D:\2021\Redis\redis-study\img\45.png)

但是这种方法会存在两个问题：

1、如果空值能够被缓存起来，这就意味着缓存需要更多的空间存储更多的键，因为这当中可能会有很多的空值的键；

2、即使对空值设置了过期时间，还是会存在缓存层和存储层的数据会有一段时间窗口的不一致，这对于需要保持一致性的业务会有影响。

### 13.2、缓存击穿（量太大，缓存过期！）

#### 13.2.1 概述

这里需要注意和缓存击穿的区别，缓存击穿，是指一个key非常热点，在不停的扛着大并发，大并发集中对这一个点进行访问，当这个key在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库，就像在一个屏障上凿开了一个洞。

当某个key在过期的瞬间，有大量的请求并发访问，这类数据一般是热点数据，由于缓存过期，会同时访问数据库来查询最新数据，并且回写缓存，会导使数据库瞬间压力过大。

#### 13.2.2 解决方案

**设置热点数据永不过期**

从缓存层面来看，没有设置过期时间，所以不会出现热点 key 过期后产生的问题。

**加互斥锁**

分布式锁：使用分布式锁，保证对于每个key同时只有一个线程去查询后端服务，其他线程没有获得分布式锁的权限，因此只需要等待即可。这种方式将高并发的压力转移到了分布式锁，因此对分布式锁的考验很大。
![](D:\2021\Redis\redis-study\img\46.png)

### 13.3、缓存雪崩

#### 13.3.1 概念

缓存雪崩，是指在某一个时间段，缓存集中过期失效。Redis 宕机！

产生雪崩的原因之一，比如在写本文的时候，马上就要到双十二零点，很快就会迎来一波抢购，这波商品时间比较集中的放入了缓存，假设缓存一个小时。那么到了凌晨一点钟的时候，这批商品的缓存就都过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言，就会产生周期性的压力波峰。于是所有的请求都会达到存储层，存储层的调用量会暴增，造成存储层也会挂掉的情况。
![](D:\2021\Redis\redis-study\img\47.png)

其实集中过期，倒不是非常致命，比较致命的缓存雪崩，是缓存服务器某个节点宕机或断网。因为自然形成的缓存雪崩，一定是在某个时间段集中创建缓存，这个时候，数据库也是可以顶住压力的。无非就是对数据库产生周期性的压力而已。而缓存服务节点的宕机，对数据库服务器造成的压力是不可预知的，很有可能瞬间就把数据库压垮。

#### 13.3.2 解决方案

**redis高可用**

这个思想的含义是，既然redis有可能挂掉，那我多增设几台redis，这样一台挂掉之后其他的还可以继续工作，其实就是搭建的集群。（异地多活！）

**限流降级**（在SpringCloud讲解过！）

这个解决方案的思想是，在缓存失效后，通过加锁或者队列来控制读数据库写缓存的线程数量。比如对某个key只允许一个线程查询数据和写缓存，其他线程等待。

**数据预热**

数据加热的含义就是在正式部署之前，我先把可能的数据先预先访问一遍，这样部分可能大量访问的数据就会加载到缓存中。在即将发生大并发访问前手动触发加载缓存不同的key，设置不同的过期时间，让缓存失效的时间点尽量均匀。

YYDS：https://www.bilibili.com/video/BV1S54y1R7SB
