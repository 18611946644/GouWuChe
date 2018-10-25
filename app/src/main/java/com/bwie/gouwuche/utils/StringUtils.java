package com.bwie.gouwuche.utils;

/**
 * 将Https  全部都替换为  Http的一个工具类
 * Created by DELL on 2018/10/23.
 */

public class StringUtils {

    public static String httpstoHttp(String url){
         return url.replace("https","http");
    }

}
