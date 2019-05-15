package com.github.ruanshengwei.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class KeysTest {

    public Jedis jedis = JedisPoolUtil.getJedis();

    /**
     * keys *
     * 虽然其模糊匹配功能使用非常方便也很强大，在小数据量情况下使用没什么问题，
     * 数据量大会导致 Redis 锁住及 CPU 飙升，在生产环境建议禁用或者重命名！
     */
    @Test
    public void fun(){
        Set<String> keys = jedis.keys("1*");

        for(String key:keys){
            System.out.println("key:"+key);
        }
    }

    /**
     * 塞数据
     */
    @Test

    public void fun1(){

        for (int i=0;i<1000000;i++){
            jedis.set(String.valueOf(i),String.valueOf(i));
        }

    }

}
