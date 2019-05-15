package com.github.ruanshengwei.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
和set极为的类似，他们是字符串的集合，没有重复的数据
差别是sortedset每个成员中都会有一个分数（score）与之关联
，redis正是通过分数来为集合中的成员进行从小到大的排序
sortedset中数据必须单一但是他的score可以是重复的
 */
public class SortedsetTest {
    public Jedis jedis = JedisPoolUtil.getJedis();
    //    添加元素
    @Test
    public void fun(){
        jedis.zadd("mysort",100.0, "qingxu");
        jedis.zadd("mysort",200.0,"lisi");
        jedis.zadd("mysort",50.0,"wangwu");
        Map<String ,Double> map = new HashMap<String ,Double>();
        map.put("mutouliu",70.0);

        jedis.zadd("mysort",map);
        Set<String> mysort = jedis.zrange("mysort", 0, -1);
        System.out.println(mysort);
        Set<String> mysort1 = jedis.zrange("mysort", 1, 2);
        System.out.println(mysort1);
    }
}
