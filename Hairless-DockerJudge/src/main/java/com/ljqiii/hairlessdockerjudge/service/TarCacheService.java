package com.ljqiii.hairlessdockerjudge.service;


import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Service
public class TarCacheService {

    @Autowired
    private RedisTemplate<String, String> template;

    public boolean set(String key, InputStream tarStream) {
        try {
            byte[] tarByte = IOUtils.toByteArray(tarStream);

            byte[] base64Encode = Base64.getEncoder().encode(tarByte);
            ValueOperations ops = template.opsForValue();
            ops.set(key, new String(base64Encode));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ByteArrayInputStream get(String key) {
        ValueOperations ops = template.opsForValue();
        String encodedFile = String.valueOf(ops.get(key));
        byte[] tarByte = Base64.getDecoder().decode(encodedFile.getBytes());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(tarByte);
        return inputStream;
    }

    public boolean hasCache(String key) {
        return template.hasKey(key);
    }

}
