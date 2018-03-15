package com.example.util;

/**
 * 创建日期：2018-03-14 on 14:19
 * 作者：ls
 */

public class NameUtil {

    private static char[] initials = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};

    /**
     * 根据名字返回首字母的大写
     * @param name 名字
     * @return 首字母的大写
     */
    public static String getCatalog(String name){
        if(name == null || name.isEmpty()){
            return "";
        }
        char first = name.toUpperCase().charAt(0);
        for(char c : initials){
            if(c == first){
                return String.valueOf(c);
            }
        }
        return "";
    }

    public static char[] getInitials() {
        return initials;
    }
}
