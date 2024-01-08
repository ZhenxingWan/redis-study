package com.xing;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@SpringBootTest
class RedisStudyApplicationTests {

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

    // 3、事务
    @Test
    void testRedis(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //jedis.select();//选择第1号数据库,如果不选择指定数据库、默认就是db0数据库。
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
            //int i = 1/0; //代码抛出异常事务，执行失败！
            multi.exec(); //执行事务
        } catch (Exception e) {
            multi.discard(); //放弃事务，执行失败！
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close(); //关闭连接
        }
    }

}
