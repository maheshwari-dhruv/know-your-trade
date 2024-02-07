package org.blog.knowyourtrade.util;

import java.text.MessageFormat;

public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String value(String value, String defaultVal){
        return isNotBlank(value) ? value : defaultVal;
    }

    public static String format(String pattern, Object ... arguments){
       return MessageFormat.format(pattern, arguments);
    }

    public static boolean checkIsEmpty(String value) {
        return isEmpty(value);
    }

    public static boolean checkIsNull(String value) {
        return value.equalsIgnoreCase("null");
    }
}