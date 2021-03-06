package com.hlj.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author HealerJean
 * @Date 2018/3/30  下午4:55.
 */
@RestController
public class OptsForValuesController {

    @Resource(name="stringRedisTemplate")
    private StringRedisTemplate  stringRedisTemplate;


    @Resource(name = "redisTemplate")
    private  RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private RedisTemplate<String,Long>  redisTemplateLong;


    private  String  KEY = "stringRedisTemplate";

    private  String LongKey = "LongKey";
    @GetMapping("set")
    public String set(){
        stringRedisTemplate.opsForValue().set(KEY," stringRedisTemplate.opsForValue().set");
        redisTemplate.opsForValue().set("redisTemplate","redisTemplate.opsForValue().set");


        return   stringRedisTemplate.opsForValue().get(KEY).toString();
    }

    @GetMapping("stringRedisTemplateDelete")
    public String delete(){
        stringRedisTemplate.delete(KEY);
        return   "delete ok";
    }




    @GetMapping("boundValueOps")
    public String boundValueOps(){
        stringRedisTemplate.boundValueOps(KEY).set("NewValue");

        System.out.println(stringRedisTemplate.boundValueOps(KEY).get());
        return  "boundValueOps";
    }

    @GetMapping("hasKey")
    public String hasKey(){

        System.out.println(stringRedisTemplate.hasKey(KEY));
        return  "boundValueOps";
    }

    @GetMapping("LongKey")
    public String set(Long id){
        redisTemplate.opsForValue().set(LongKey,id);


        return   stringRedisTemplate.opsForValue().get(LongKey).toString();
    }




}
