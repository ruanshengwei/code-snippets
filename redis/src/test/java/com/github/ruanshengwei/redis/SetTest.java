package com.github.ruanshengwei.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Set集合，和List类的区别就是
 * set中不会出现重复的数据
 * 他可以进行聚合操作效率比较高
 * 其余的操作基本上和list相同
 */
public class SetTest {
    public Jedis jedis = JedisPoolUtil.getJedis();

    @Test
    /*添加元素删除元素*/
    public void fun(){
        Long num = jedis.sadd("myset", "a", "a", "b","abc");
        System.out.println(num);

    }
    @Test
    /*获得元素*/
    public void fun1(){
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);
    }
    @Test
    /*移除元素*/
    public void fun2(){
        jedis.srem("myset","a","b");
        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);
    }
    @Test
    //判断是否这个set中存在某个值
    public void fun3(){
        Boolean sismember = jedis.sismember("myset", "a");
        System.out.println(sismember);
    }
    @Test
    //获得A-B 获得差集合
    public void fun4(){
        jedis.sadd("myset1","123","32","abc","def","123456","sdfasd");
        jedis.sadd("myset2","abc","345","123","fda");
        Set<String> sdiff = jedis.sdiff("myset1", "myset2");
        System.out.println(sdiff);
    }
    @Test
    //获得交集
    public void fun5(){
        Set<String> sinter = jedis.sinter("myset1", "myset2");
        System.out.println(sinter);

    }
    @Test
//    获得并集合
    public void fun6(){
        Set<String> sunion = jedis.sunion("myset1", "myset2");
        System.out.println(sunion);
    }
    @Test
//    成员数量
    public void fun7(){
        System.out.println(jedis.scard("myset1"));
    }
    @Test
//    获得随机的一个成员
    public void fun8(){
        System.out.println(jedis.srandmember("myset1"));
    }
    @Test
//    将相差的成员放到一个新的set中同理交集和并集都可以后面均
//    加上一个store即可
//    并返回新的长度
    public void fun9(){
        System.out.println(jedis.sdiffstore("myset3","myset1","myset2"));
        System.out.println(jedis.smembers("myset3"));
    }

}
