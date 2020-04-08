package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlesscommon.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MySQLFileService implements FileUtils {
    @Override
    public String saveFile(String fileName, byte[] content) {
        return null;
    }

    @Override
    public byte[] getFile(String fileName) {
        return new byte[0];
    }
}
