package com.ljqiii.hairlesscommon.constants;

import java.text.MessageFormat;

public class NameConstants {
    public static String Docker_CacheDir_Key = "dockerdirCache_{0}_{1}";
    public static String Log_Handler_Thread_Name = "Container[{0}]-LogsStreamThread";//处理日志的线程名字


    //MessageFormat.format("oh, {0} is 'a' pig", "ZhangSan")

    public static void main(String[] args) {
        String format = MessageFormat.format(NameConstants.Docker_CacheDir_Key, "2", "/");
        int a=1;
    }

}
