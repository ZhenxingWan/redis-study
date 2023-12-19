package com.xing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xing.domain.User;
import com.xing.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringbootRedisApplicationTests {

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
        /** 在企业开发中，我们80%的情况下，都不会使用这个原生的方式去编写代码！
         * 自己编写：RedisUtils 工具类
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
        redisTemplate.opsForValue().set("mykey","Hello");
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
        System.out.println(redisTemplate.opsForValue().get("key"));
    }
}
