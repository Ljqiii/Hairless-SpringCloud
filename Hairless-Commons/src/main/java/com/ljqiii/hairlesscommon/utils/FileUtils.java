package com.ljqiii.hairlesscommon.utils;

public interface FileUtils {

    /**
     * @param fileName 文件路径和名称
     * @param content  文件内容
     * @return 存储后的文件名
     */
    String saveFile(String fileName, byte[] content);


    /**
     * @param fileName 文件路径和文件名
     * @return
     */
    byte[] getFile(String fileName);
}
