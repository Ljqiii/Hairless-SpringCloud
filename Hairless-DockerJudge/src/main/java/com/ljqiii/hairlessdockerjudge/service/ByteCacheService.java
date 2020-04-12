package com.ljqiii.hairlessdockerjudge.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ByteCacheService {

    @Autowired
    private RedisTemplate<String, String> template;

//    public String set(String key,)


}
