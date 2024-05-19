package com.example.tierpark.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates using java.sql.Date.
 *
 * @author Marco Jakob
 */
public class DateUtil {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";

    /** The date formatter. */
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_PATTERN);

    private static final DateTimeFormatter DATE_TIME_PATTERN_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static String format(Timestamp timestamp){
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime != null ? localDateTime.format(DATE_TIME_PATTERN_FORMATTER) : "";
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
     * to a {@link java.sql.Date} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static Date parse(String dateString) {
        try {
            java.util.Date utilDate = DATE_FORMATTER.parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp parseDatetime(String datetimeString){
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(datetimeString, DATE_TIME_PATTERN_FORMATTER);
            return Timestamp.valueOf(localDateTime);
        }
        catch (DateTimeParseException e){
            return null;
        }
    }



    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }

    public static boolean validDatetime(String datetimeString){
        return parseDatetime(datetimeString) != null;
    }
}