package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlessdockerjudge.utils.TarUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

@SpringBootTest

public class TarCacheServiceTest {

    @Autowired
    TarCacheService tarCacheService;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void set() throws IOException {

        ByteArrayInputStream inputStream = tarCacheService.get("dockerdir_cache_2_/root/.m2/repository/");
        FileUtils.copyInputStreamToFile(inputStream, new File("/tmp/testdir.tar"));

    }
}
