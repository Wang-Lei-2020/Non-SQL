

/*
 ** Function: Redis连接测试（有/无连接池）
 ** Author:   王磊 18301137
 ** Date:     2020年12月4日
 */

public class RedisConnection {

   // public static void main(String[] args){

        //Jedis不用连接池实例
        /*
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        // 如果 Redis 服务设置来密码，需要下面这行，没有就不需要
        // jedis.auth("123456");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println("返回Redis中数据: "+jedis.get("home"));
        */

        /*
        //使用Jedis连接池
        JedisPool jedisPool = JedisInstance.getInstance();
        Jedis jedis = jedisPool.getResource();
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println("返回Redis中数据: "+jedis.get("home"));
        jedis.close();
        jedisPool.close();
        */

    //}
}
