package com.qyk.douban.utils;

/**
 * Created by Administrator on 2015/12/17.
 */
public class StringNotNull {
    public static String NotNullString(String object){
        if (object==null||object.equals(""))
            return "";
        return object.toString();
    }
}
