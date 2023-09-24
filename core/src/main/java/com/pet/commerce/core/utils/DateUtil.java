package com.pet.commerce.core.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author hary
 * @since 2018-12-4 18:19:26
 */
@Slf4j
public class DateUtil {

    /**
     * 日期格式: yyyy-MM-dd
     */
    public static final String DATE = "yyyy-MM-dd";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static Date now() {
        return new Date();
    }

    private static Pattern pattern = Pattern.compile(
            "^[0-9]{4}[/-][0-9]{1,2}[/-][0-9]{1,2}( +[0-9]{2}(:[0-9]{2}(:[0-9]{2}\\.?[0-9]{0,3})?)?)?$");

    public static final Map<String, String> DATE_FORMAT = Maps.newHashMap();
    public static final Map<String, String> TIME_FORMAT = Maps.newHashMap();

    static {
        DATE_FORMAT.put(null, "MM/dd/yyyy");
        DATE_FORMAT.put("", "MM/dd/yyyy");
        DATE_FORMAT.put("en_US", "MM/dd/yyyy");
        DATE_FORMAT.put("zh_CN", "yyyy-MM-dd");

        TIME_FORMAT.put(null, "MM/dd/yyyy HH:mm:ss");
        TIME_FORMAT.put("", "MM/dd/yyyy HH:mm:ss");
        TIME_FORMAT.put("en_US", "MM/dd/yyyy HH:mm:ss");
        TIME_FORMAT.put("zh_CN", "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据日期文本获取日期对象
     *
     * @param date 日期文本
     */
    public static Date toDate(String date) {
        Date d = null;
        if (StringUtils.isNotBlank(date)) {
            try {
                if (!pattern.matcher(date).matches()) {
                    throw new IllegalArgumentException(date + " is not a date format.");
                }
                StringBuilder sb = new StringBuilder(date.replaceAll("[/ :.-]", ""));
                if (sb.length() < 8) {
                    throw new IllegalArgumentException("Invalid date string: " + sb.toString());
                } else {
                    while (sb.length() < 17) {
                        sb.append("0");
                    }
                }
                d = new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(sb.toString());
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                log.error("toDate parse error: {}", date);
            }
        }
        return d;
    }

    public static Date toDate(String dateString, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(dateString).toDate();
    }

    /**
     * 把日期转换为字符串
     *
     * @param value 日期
     * @return
     * @author huanghaiping
     */
    public static String dateToString(Date value) {
        return toString(value, DATE);
    }

    public static String timeToString(Date value) {
        return toString(value, DATE_TIME);
    }

    public static String toString(Date value, String pattern) {
        return new DateTime(value).toString(pattern);
    }

    public static Date getNextMonth() {
        return new DateTime().plusMonths(1).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static Date getNextDays(int dates) {
        return new DateTime().plusDays(dates).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static Date getNextDays(Date date, int dates) {
        return new DateTime(date).plusDays(dates).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static Date getNextYear() {
        return new DateTime().plusYears(1).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static Date plusMonth(int monthAmount) {
        return new DateTime().plusMonths(monthAmount).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static Date getStartDate(Date date) {
        return new DateTime(date).millisOfDay().withMinimumValue().toDate();
    }

    public static Date getStartDate() {
        return new DateTime().millisOfDay().withMinimumValue().toDate();
    }

    public static Date getEndDate(Date date) {
        return new DateTime(date).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static Date getEndDate() {
        return new DateTime().millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
    }

    public static String toUTCString(Date date) {
        return new DateTime(date).withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ssZ");
    }

    public static String toUTCString() {
        return new DateTime().withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ssZ");
    }

    /**
     * @param date 指定时间
     * @return 本地时间
     */
    public static LocalDate toLocaleDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static long between(Date begin, Date end) {
        return toLocaleDate(end).toEpochDay() - toLocaleDate(begin).toEpochDay();
    }


    /**
     * begin离end还有多少毫秒
     *
     * @param begin 起始日期
     * @param end   结束日期
     * @return 毫秒数
     */
    public static long betweenMillis(Date begin, Date end) {
        return ChronoUnit.MILLIS.between(begin.toInstant(), end.toInstant());
    }

    /**
     * 指定日期的在该 月/周 开始时间和结束时间
     *
     * @param date 指定日期
     * @param type 计算区间
     *             W - 周
     *             M - 月
     * @return 指定日期在计算区间内最早开始时间和最晚结束时间
     */
    public static Object[] getStartAndEndDate(Date date, String type) {
        Date startDate;
        Date endDate;
        switch (type) {
            // WEEKLY
            case "W":
                startDate = new DateTime(date).dayOfWeek().withMinimumValue().plusWeeks(0).withTimeAtStartOfDay()
                        .toDate();
                endDate = new DateTime(date).dayOfWeek().withMaximumValue().millisOfDay().withMaximumValue()
                        .toDate();
                break;
            // MONTHLY
            case "M":
                startDate = new DateTime(date).dayOfMonth().withMinimumValue().plusMonths(0).withTimeAtStartOfDay()
                        .toDate();
                endDate = new DateTime(date).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
                break;
            // YEARLY
            case "Y":
                startDate = new DateTime(date).dayOfYear().withMinimumValue().plusMonths(0).withTimeAtStartOfDay()
                        .toDate();
                endDate = new DateTime(date).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
                break;
            default:
                startDate = new DateTime(date).withDate(1970, 1, 1).withTimeAtStartOfDay().toDate();
                endDate = new DateTime().millisOfDay().withMaximumValue().toDate();
        }
        return new Object[]{startDate, endDate};
    }

    /**
     * 基于当前日期的近 一年/月/周 开始时间和结束时间
     *
     * @param type 计算区间
     *             W - 周
     *             M - 月
     * @return 当前日期在计算区间内最早开始时间和最晚结束时间
     */
    public static Object[] getStartAndEndDate(String type) {
        Date startDate;
        Date endDate = new DateTime().millisOfDay().withMaximumValue().toDate();
        switch (type) {
            case "W": // WEEKLY
                startDate = new DateTime().plusWeeks(-1).withTimeAtStartOfDay().toDate();
                break;
            case "M": // MONTHLY
                startDate = new DateTime().plusMonths(-1).withTimeAtStartOfDay().toDate();
                break;
            case "Y": // YEARLY
                startDate = new DateTime().plusYears(-1).withTimeAtStartOfDay().toDate();
                break;
            default:
                startDate = new DateTime().withDate(1970, 1, 1).withTimeAtStartOfDay().toDate();
        }
        return new Object[]{startDate, endDate};
    }

    /**
     * 获取月内的天数
     *
     * @param date 指定日期
     * @return 指定日期该月的天数
     */
    public static Integer getDay(Date date) {
        return new DateTime(date).dayOfMonth().get();
    }

    /**
     * 返回当天日期（年月日）
     *
     * @return 当前日期
     */
    public static Date toLocalDateTimeToUdate() {
        LocalDate localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * @param date 指定日期
     * @param sub  天数
     * @return 指定日期增加指定天数
     */
    public static Date addDay(Date date, int sub) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, sub);
        return calendar.getTime();
    }

    /**
     * @param startTime 时间范围开始
     * @param endTime   时间范围结束
     * @return 1 > endTime; 0 startTime < & < endTime; -1 < startTime
     * @method 判断当前时间是否位于两时间之间，如：2019-01-01 08:00:00 ~ 2021-01-01 18:00:00
     */
    public static int currentTimeBetweenLimitsJudget(Date startTime, Date endTime) {
        Date currentDate = now();
        if (endTime.getTime() < currentDate.getTime()) {
            return 1;
        }
        if ((endTime.getTime() >= currentDate.getTime()) && (startTime.getTime() <= currentDate.getTime())) {
            return 0;
        }
        return -1;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(String dateStr, String pattern, String zoneId) {
        return DateTimeFormat.forPattern(pattern)
                .withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(zoneId)))
                .parseDateTime(dateStr)
                .toDate();
    }

    /**
     * 指定时区的时间转为系统默认时区的时间
     *
     * @param date   时间格式符合 DATE_TIME
     * @param zoneId dateStr时间所在时区
     * @return 目标时区时间
     */
    public static Date asDate(Date date, String zoneId) {
        return asDate(timeToString(date), DATE_TIME, zoneId);
    }

    public static Date asDate(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    /**
     * 获取季度的开始时间，相对于当前季度
     * 0 - 表示当前季度的开始时间
     * 1 - 表示下一季度的开始时间
     */
    public static Date getStartDateOfQuarterlyForCurrent(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());
        calendar.add(Calendar.MONTH, num * 3);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) / 3 * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取季度的结束时间，相对于当前季度
     * 0 - 表示当前季度的结束时间
     * 1 - 表示下一季度的结束时间
     */
    public static Date getEndDateOfQuarterlyForCurrent(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());
        calendar.add(Calendar.MONTH, num * 3);
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) / 3 + 1) * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) - 1);
        return calendar.getTime();
    }

    /**
     * 获取当天的开始时间
     *
     * @return
     */
    public static java.util.Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     *
     * @return
     */
    public static java.util.Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
