# 1	NoSQL
## 1.1	����

* �߲�����д

* �������ݵĸ�Ч�ʴ洢�����

* �߿���չ�Ժ͸߿�����

## 1.2	��Ʒ����
|����|�����Ʒ|����Ӧ�ó���|����ģ��|�ŵ�|ȱ��|
|---|---|---|---|---|---|
|��ֵ��key-value��|Tokyo Cabinet/Tyrant, Redis, Voldemort, Oracle BDB|���ݻ��棬��Ҫ���ڴ���������ݵĸ߷��ʸ��أ�Ҳ����һЩ��־ϵͳ�ȵ�|Key ָ�� Value �ļ�ֵ�ԣ�ͨ����HashTable��ʵ��|�����ٶȿ�|�����޽ṹ����ͨ��ֻ�������ַ������߶���������|
|�д洢���ݿ�|	Cassandra, HBase, Riak|�ֲ�ʽ���ļ�ϵͳ|���д�ʽ�洢����ͬһ�����ݴ���һ��|�����ٶȿ죬����չ��ǿ�������׽��зֲ�ʽ��չ|������Ծ���|
|�ĵ������ݿ�|	CouchDB, MongoDb|WebӦ�ã���Key-Value���ƣ�Value�ǽṹ���ģ���ͬ�������ݿ��ܹ��˽�Value�����ݣ�|Key-Value��Ӧ�ļ�ֵ�ԣ�ValueΪ�ṹ������|���ݽṹҪ���ϸ񣬱�ṹ�ɱ䣬����Ҫ���ϵ�����ݿ�һ����ҪԤ�ȶ����ṹ|��ѯ���ܲ��ߣ�����ȱ��ͳһ�Ĳ�ѯ�﷨��|
|ͼ��(Graph)���ݿ�|Neo4J, InfoGrid, Infinite Graph|�罻���磬�Ƽ�ϵͳ�ȡ�רע�ڹ�����ϵͼ��|ͼ�ṹ|����ͼ�ṹ����㷨���������·��Ѱַ��N�ȹ�ϵ���ҵ�|�ܶ�ʱ����Ҫ������ͼ��������ܵó���Ҫ����Ϣ���������ֽṹ��̫�����ֲ�ʽ�ļ�Ⱥ������|

# 2	����
## 2.1	Redis���
Remote Dictionary Server(Redis) ��ʹ��ANSI C���Ա�д��key-value�洢ϵͳ��֧�����硢�ɻ����ڴ���ɳ־û�����־�͡�Key-Value���ݿ⣬���ṩ�������Ե�API��

��ͨ������Ϊ���ݽṹ����������Ϊֵ��value�������� �ַ���(String)�� ��ϣ(Map)���б�(list)������(sets) �� ���򼯺�(sorted sets)�����͡�

Ӧ�ó��������桢������С����а���վ����ͳ�ơ����ݹ��ڴ����ֲ�ʽ��Ⱥ�ܹ��е�session����

## 2.2	��װ
### 2.2.1	����

https://github.com/MSOpenTech/redis/releases

### 2.2.2	����

1. �����

```
redis-server.exe redis.windows.conf
```

2. �ͻ���

```
redis-cli.exe -h 127.0.0.1 -p 6379
```

# 3	Jedis

Redis��Java�ͻ��˿�������

https://github.com/xetorthio/jedis

## 3.1	����ʹ��

``` java
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("name", "rain");
        String val = jedis.get("name");
        System.out.println(val);
        jedis.close();
```

## 3.2	���ӳ�ʹ��

``` java
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);

        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("date", "5/18");
        String val = jedis.get("name");
        System.out.println(val);
        jedis.close();
        jedisPool.close();
```

# 4 Key
|����	|����|
|---|---|
|�鿴|KEYS pattern֧��*��?|
|ɾ��|	DEL key1 \[key2\]|
�Ƿ����|	EXISTS key|
|������|	RENAME key newkey <br/> RENAMENX key newkey<br/> ���� newkey ������ʱ���� key ����Ϊ newkey
|���ù���|	EXPIRE key seconds|
|��ȡ����|	TTL key<br/> ��λ�룬-1δ���ù���<br/> PTTL key<br/> ��λ����|
|ֵ����	|TYPE key|
# 5 Value���ݽṹ
## 5.1	String
�����ƴ洢���512M��

|����	|����|
|---|---|
��ֵ	|SET key value <br/> GETSET keye value
ȡֵ|	GET key
ɾ��	|DEL key
��ֵ|	INCR key <br>INCRBY key increment<br>������ʱ��Ĭ��Ϊ0
��ֵ|	DECR key<br>DECRBY key increment <br>������ʱ��Ĭ��Ϊ0
ƴ��|	APPEND key value<br>������ʱ�ᴴ��
## 5.2	Hash
String���͵�key��value��ӳ����ʺ����ڴ洢����

ÿ�� hash ���Դ洢 232 - 1 ��ֵ�ԣ�40���ڣ���

�������洢һ���û���Ϣ�������ݡ�

|����	|����|
|---|---|
��ֵ|	HSET key field value<br>HMSET key field1 value1 \[field2 value2\]
ȡֵ|	HGET key field<br>HMGET key field1 field2<br>HGETALL key
ɾ��|	HDEL key field1 \[field2\] <br> DEL key
��ֵ|	HINCRBY key field increment
�Ƿ����|	HEXISTS key field
��������	|HLEN key
�����б�|	HKEYS key
ֵ�б�	|HVALS key

## 5.3	List
�򵥵��ַ����б����ղ���˳�����򡣿����Ԫ�ص�ͷ����β����

���232 - 1 ��Ԫ�� (40����)��

�ײ����ݽṹ�ǿ����б��ѹ���б������б�����ѹ���б�Ϊ�ڵ��˫������

���������ɵ�ʵ��������Ϣ���С�

|����	|����|
|---|---|
��ֵ|	LPUSH key value1 \[value2\]<br>RPUSH key value1 \[value2\]<br>LPUSHX key value<br>list�������<br>RPUSHX key value<br>list�������
ȡֵ|	LRANGE key start stop<br>������ʾ�ӷ�����ʼ<br>LPOP key<br>RPOP key
ȡֵ����ֵ|	RPOPLPUSH source destination
����|	LLEN
�Ƴ�|	LREM key count value<br>(count��=0��ȫ����>0����<0����)
�޸�|	LSET key index value
����|	LINSERT key BEFORE|AFTER pivot value

## 5.4	Set
String ���͵����򼯺ϣ����ظ����ݡ�

ͨ����ϣ��ʵ�ֵģ���ӡ�ɾ�������ҵĸ��Ӷȶ��� O(1)��

���232 - 1 ��Ԫ�� (40����)��

��������Ʒ�����߽���

|����	|����|
|---|---|
���|	SADD key member1 \[member2\]
ɾ��|	SREM key member1 \[member2\]
�鿴|	SMEMBERS key <br> SRANDMEMBER key \[count\] <br>����鿴��Ĭ��1��
�Ƿ����|	SISMEMBER key member
�|	SDIFF key1 \[key2\]<br>key1��Ԫ�س�ȥkey2��Ԫ��<br>SDIFFSTORE destination key1 \[key2\]<br>�������destination
����|	SINTER key1 \[key2\]<br>SINTERSTORE destination key1 \[key2\]<br>����������destination
����|	SUNION key1 \[key2\]<br>SUNIONSTORE destination key1 \[key2\]<br>����������destination
����|	SCARD key

## 5.5	SortedSet(ZSet)
ÿ��Ԫ�ع���һ��double���͵ķ��������ݷ�����С��������򣬷��������ظ���

����������Setһ�¡�

�������������а񣬹���������

|����	|����|
|---|---|
���|	ZADD key score1 member1 \[score2 member2\]
��ȡ|	ZSCORE key member
����|	ZRANGE key start stop \[WITHSCORES\]<br>С����<br>ZREVRANGE key start stop \[WITHSCORES\]<br>��С<br>ZRANGEBYSCORE key min max \[WITHSCORES\] \[LIMIT\]
ɾ��|	ZREM key member \[member ...\]<br>ZREMRANGEBYRANK key start stop<br>����������Χɾ��<br>ZREMRANGEBYSCORE key min max<br>���ݷ���ɾ��
����|	ZCARD key<br>ȫ��<br>ZCOUNT key min max<br>ָ����������ĳ�Ա��
�޸�|	ZINCRBY key increment member<br>����

# 6	����

## 6.1	�����ݿ�
����ʵ�����֧��16�����ݿ⣬�±��0��ʼ��Ĭ��0��

SELECT num��ѡ�����ݿ⡣

MOVE key num���ƶ�key��ָ�����ݿ⡣

## 6.2	����
### 6.2.1	����

�����е�����л���˳��ִ�У�ִ���ڼ䲻Ϊ�����ͻ����ṩ�κη��񣬱�֤�������ԭ�ӻ�ִ�С�

������ĳ����ִ��ʧ�ܣ�������������ִ�С�

### 6.2.2	����

|����|����|
|---|---|
|��ʼ����	|MULTI|
|ִ������	|EXEC|
|ȡ������	|DISCARD|
# 7	�־û�

## 7.1	��ʽ
RDB��AOF���޳־û���RDB��AOF���

## 7.2	RDB

### 7.2.1	����

Ĭ�Ϸ�������ָ����ʱ�����ڣ�ִ��ָ��������д��������Ὣ�ڴ��е�����д�뵽�����С�����ָ��Ŀ¼������һ��dump.rdb�ļ���Redis ������ͨ������dump.rdb�ļ��ָ����ݡ�

### 7.2.2	����

�ŵ㣺�ʺϴ��ģ�����ݻָ������ҵ������������Ժ�һ����Ҫ�󲻸ߣ�RDB�Ǻܺõ�ѡ��

ȱ�㣺���ݵ������Ժ�һ���Բ��ߣ���ΪRDB���������һ�α���ʱ崻��ˣ�����ʱռ���ڴ棬��ΪRedis �ڱ���ʱ���������һ���ӽ��̣�������д�뵽һ����ʱ�ļ�����ʱ�ڴ��е�������ԭ����������������ٽ���ʱ�ļ��滻֮ǰ�ı����ļ���

### 7.2.3	����
redis.conf �ļ���SNAPSHOTTING����

*	��������

```
save <seconds> <changes>
# save ""
save 900 1
save 300 10
save 60 10000
```

*	�ļ�����

```
dbfilename dump.rdb
```

*	Ŀ¼����

```
dir ./
```

*	����ѹ��

```
rdbcompression yes
```


## 7.3	AOF

### 7.3.1	����

Ĭ�ϲ����������ĳ�����Ϊ���ֲ�RDB�Ĳ��㣨���ݵĲ�һ���ԣ���������������־����ʽ����¼ÿ��д��������׷�ӵ��ļ��С�Redis �����������־�ļ������ݽ�дָ���ǰ����ִ��һ����������ݵĻָ�������

### 7.3.2	����
�ŵ㣺���ݵ������Ժ�һ���Ը���

ȱ�㣺��ΪAOF��¼�����ݶ࣬�ļ���Խ��Խ�����ݻָ�Ҳ��Խ��Խ����

### 7.3.3	����
*	����

```
appendonly yes
```

*	�ļ�����

```
appendfilename "appendonly.aof"
```

*	�����������

```
# appendfsync always
appendfsync everysec
# appendfsync no
```

*	��д��������

```
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 3gb
```

# 8 �ο�����
1.	��Redis�ο����ϡ� redis���� https://redis.io/documentation
2.	��Redis ��� redis���� https://redis.io/commands
3.	��Redis�̡̳� ����̲� http://www.runoob.com/redis/redis-tutorial.html
4.	��Redis���Ž̡̳� Ľ���� https://www.imooc.com/view/839
5.	��Redis �־û�֮RDB��AOF�� ����԰ https://www.cnblogs.com/itdragon/p/7906481.html