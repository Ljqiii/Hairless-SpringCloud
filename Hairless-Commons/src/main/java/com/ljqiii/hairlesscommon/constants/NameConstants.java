package com.ljqiii.hairlesscommon.constants;

import java.text.MessageFormat;

public class NameConstants {
    public static String Docker_CacheDir_Key = "dockerdirCache_{0}_{1}";//0:problemid,1:cachedir
    public static String Log_Handler_Thread_Name = "Container[{0}]-LogsStreamThread";//处理日志的线程名字,0:containerid
    public static String Container_Name = "{0}_{1}_{2}";//生成的container名字，0:imageName, 1：username, 2：submitid
    public static String WorkingDir = "/code_{0}_{1}_{2}";//0:userid，1:problemid, 2:submitid
    public static String CodeDir = "/code_{0}_{1}_{2}";//0:userid，1:problemid, 2:submitid


    //MessageFormat.format("oh, {0} is 'a' pig", "ZhangSan")

    public static void main(String[] args) {
        String format = MessageFormat.format(NameConstants.Docker_CacheDir_Key, "2", "/");
        int a = 1;
    }

}
