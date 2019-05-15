package com.github.ruanshengwei.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashTest {
    public Jedis jedis = JedisPoolUtil.getJedis();

    //    hash 操作的是map对象
//    适合存储键值对象的信息
    @Test
    //存值 参数第一个变量的名称， map键名(key)， map键值(value)
//    调用hset
    public void fun() {
        Long num = jedis.hset("hash1", "username", "ruanshengwei");
        System.out.println(num);
        String hget = jedis.hget("hash1", "username");
        System.out.println(hget);
    }

    @Test
    //也可以存多个key
//    调用hmset
    public void fun1() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "ruanshengwei");
        map.put("age", "21");
        map.put("sex", "男");
        String res = jedis.hmset("hash2", map);
        System.out.println(res);//ok
    }

    @Test
    //获取hash中所有的值
    public void fun2() {
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
//    删除hash中的键 可以删除一个也可以删除多个，返回的是删除的个数
    public void fun3() {
        Long num = jedis.hdel("hash2", "username", "age");
        System.out.println(num);
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    //增加hash中的键值对
    public void fun4() {
        Map<String, String> map2 = new HashMap<String, String>();
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
        jedis.hincrBy("hash2", "age", 10);
        map2 = jedis.hgetAll("hash2");
        System.out.println(map2);
    }

    @Test
    //判断hash是否存在某个值
    public void fun5() {
        System.out.println(jedis.hexists("hash2", "username"));
        System.out.println(jedis.hexists("hash2", "age"));
    }

    @Test
    //获取hash中键值对的个数
    public void fun6() {
        System.out.println(jedis.hlen("hash2"));
    }

    //    获取一个hash中所有的key值
    @Test
    public void fun7() {
        Set<String> hash2 = jedis.hkeys("hash2");
        System.out.println(hash2);
    }

    //    获取所有的value值
    @Test
    public void fun8() {
        List<String> hash2 = jedis.hvals("hash2");
        System.out.println(hash2);
    }
}
