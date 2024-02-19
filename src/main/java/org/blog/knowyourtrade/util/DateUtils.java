package org.blog.knowyourtrade.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateUtils {

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    public static final String KSQLDB_FORMAT = "yyyyMM";

    public static final String OLD_FORMAT = "dd/MM/yyyy";
    public static final String NEW_FORMAT = "yyyy/MM/dd";

    public static boolean isDateValid(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_FORMAT);
        try {
            formatter.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            log.error("Date Time Error: {}", e.getMessage());
            return false;
        }
    }

    public static boolean isCurrentMonth(String dateTimeStr) {
        Instant instant = Instant.parse(dateTimeStr);
        ZonedDateTime inputDateTime = instant.atZone(ZoneId.of("UTC")); // Use UTC for consistency
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("UTC")); // Use UTC here too
        return inputDateTime.getMonth() == currentDateTime.getMonth() &&
                inputDateTime.getYear() == currentDateTime.getYear(); // Check the year as well
    }

    public static boolean checkTransactionMonth(String dateStart, String dateEnd) {
        String dateStartMonth = convertDateToTransactionMonth(dateStart);
        String dateEndMonth = convertDateToTransactionMonth(dateEnd);

        return dateStartMonth.equalsIgnoreCase(dateEndMonth);
    }

    public static String formatPostDate(LocalDateTime date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        log.debug("Formatted Date: " + formattedDate);
        return formattedDate;
    }

    public static String convertDateToTransactionMonth(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(ISO_8601_FORMAT));
        return dateTime.format(DateTimeFormatter.ofPattern(KSQLDB_FORMAT));
    }

    public static String getYearFromDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(ISO_8601_FORMAT));
        return String.valueOf(dateTime.getYear());
    }

    public static String getMonthFromDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(ISO_8601_FORMAT));
        return dateTime.format(DateTimeFormatter.ofPattern("MM"));
    }
}