package com.dgut.medicalsystem.util;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期格式转换
 * 将LocalDate转换成”yyyy-MM-dd HH:mm:ss“形式的字符串
 */
@Component
public final class DateFormatUtil {

    public static  String dateFormat(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
