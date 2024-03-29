package org.blog.knowyourtrade.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtils {

    public static String formatPostDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
    }
}