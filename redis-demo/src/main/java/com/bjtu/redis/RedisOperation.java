package com.bjtu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/*
** Function: Redis连接+对Reids数据库的操作封装
** Author:   王磊 18301137
** Date:     2020年12月5日
*/

public class RedisOperation {

    private final JedisPool jedisPool;
    private final Jedis jedis;

    //构造器 得到连接池并返回一个jedis资源
    public RedisOperation(){
        jedisPool = JedisInstance.getInstance();
        jedis = jedisPool.getResource();
    }

    //得到key的值
    public String get(String key){
        return jedis.get(key);
    }

    //设定key的值
    public void set(String key,String value){
        jedis.set(key,value);
    }

    //对key增加1
    public void incr(String key){
        jedis.incr(key);
    }

    //对key增加任意值
    public void incrBy(String key,long value){
        jedis.incrBy(key,value);
    }

    //对key减少1
    public void decr(String key){
        jedis.decr(key);
    }

    //对key减少任意值
    public void decrBy(String key,long value){
        jedis.decrBy(key,value);
    }

    //删除一个key
    public void del(String key){
        jedis.del(key);
    }

    //关闭Redis连接池
    public void closeJedis(){
        //jedis.close();
        //jedisPool.close();
    }

    //设置key过期时间
    public void setExpire(String key,int seconds){
        jedis.expire(key,seconds);
    }

    //向list头部中添加元素
    public void lpush(String key,String value){
        jedis.lpush(key,value);
    }

    //按索引取list中元素
    public String lindex(String key,long value){
        return jedis.lindex(key,value);
    }

    //判断Redis数据库中是否存在key，-2为不存在，-1为存在但未设置过期时间，否则是过期时间（s）
    public long ttl(String key){
        return jedis.ttl(key);
    }

    //返回list的长度
    public long getLen(String key){
        return jedis.llen(key);
    }

    //返回字符串的长度
    public long getStrLen(String key){
        return jedis.strlen(key);
    }

    //字符串追加
    public void append(String key,String value){
        jedis.append(key,value);
    }

    //返回子字符串
    public String getSubStr(String key,long start,long end){
        return jedis.getrange(key,start,end);
    }

    //set中添加值
    public void sAdd(String key,String value){
        jedis.sadd(key,value);
    }

    //获取set成员数
    public long scard(String key){
        return jedis.scard(key);
    }

    //删除set中一个成员
    public void srem(String key){
        jedis.srem(key);
    }

    //判断一个值是不是在set中
    public boolean sismember(String key,String value){
        return jedis.sismember(key,value);
    }

    //zset中添加
    public void zadd(String key,double score,String value){
        jedis.zadd(key,score,value);
    }

    //zset中删除
    public void zrem(String key,String value){
        jedis.zrem(key,value);
    }

    //zrange
    public Set<String> zrange(String key, long start, long end){
        return jedis.zrange(key,start,end);
    }

    /*
    //测试本类函数
    public static void main(String[] args){
        RedisOperation ro = new RedisOperation();
        ro.set("home2","1");
        ro.incrBy("home2",10);
        System.out.println("RedisOperation测试：home2的值为：" + ro.get("home2"));
        ro.decr("home2");
        System.out.println("RedisOperation测试：home2的值为：" + ro.get("home2"));
        ro.decrBy("home2",10);
        System.out.println("RedisOperation测试：home2的值为：" + ro.get("home2"));
        ro.closeJedis();
        //ro.del("home2");
    }
     */


}

