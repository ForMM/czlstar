//package com.example.demo;
//
//import com.example.demo.util.RedisUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//
//
////@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class ReidsTest {
//
//    private static Logger logger = LoggerFactory.getLogger(SM3test.class);
//
//    private RedisUtil redisUtil;
//
//
//    @Test
//    public void set() {
//        redisUtil.set("redis_key", "redis_vale");
//    }
//
//
//    @Test
//    public void get() {
//        Object value = redisUtil.get("redis_key");
//        System.out.println(value.toString());
//    }
//
//
//}
