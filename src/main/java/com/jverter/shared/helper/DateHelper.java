package com.jverter.shared.helper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import com.jverter.shared.service.ApplicationPropertiesUtil;

public class DateHelper {

	private final static SimpleDateFormat formatter = new SimpleDateFormat(ApplicationPropertiesUtil.getProperty("date.format"));
	
	@Value("${date.format}")
	private String dateFormat;
	
	public String getDateFormat() {
        return dateFormat;
    }


	public static String convertDateToShortTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String shortTime = sdf.format(date);
		return shortTime;
	}

	public static double getHoursDiff(Date startWorkTime, Date endWorkTime) {
		long duration  = endWorkTime.getTime() - startWorkTime.getTime();
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		double differenceInHours = diffInMinutes / 60d;
		return differenceInHours;
	}

	public static boolean ifWeekEnd(Date date) {
		if(date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}

	public static boolean isFutureDate(Calendar date) {
	    if (date == null) {
	        throw new IllegalArgumentException("date must not be null");
	    }
	    Calendar now = Calendar.getInstance();
	    return date.compareTo(now) > 0;
	}

	public static String format(Calendar calendar) {
		if(calendar == null) {
			return null;
		}
		return DateHelper.format(calendar.getTime());
	}
	
	public static String format(Instant instant) {
		if(instant == null) {
			return null;
		}
		return DateHelper.format(Date.from(instant));
	}

	public static String format(Date date) {
		if(date == null) {
			return null;
		}
		return DateHelper.formatter.format(date);
	}

	public static Calendar clearToYearAndMonth(Calendar firstDay) {
		Calendar day = Calendar.getInstance();
		day.clear();
		day.set(Calendar.YEAR, firstDay.get(Calendar.YEAR));
		day.set(Calendar.MONTH, firstDay.get(Calendar.MONTH));
		return day;
	}

	public static long getNumberOfDaysBetween(Date first, Date last) {
		LocalDate dateFirst = convertFromDateToLocaleDate(first);
		LocalDate dateLast = convertFromDateToLocaleDate(last);
		return ChronoUnit.DAYS.between(dateFirst, dateLast);
	}
	
	public static boolean isBetweenOrEqual(Date fromDate, Date toDate, Date dateBetween) {
		return fromDate.compareTo(dateBetween) <= 0 && toDate.compareTo(dateBetween) >= 0;
	}
	
	public static boolean isBetween(Date fromDate, Date toDate, Date dateBetween) {
		return fromDate.compareTo(dateBetween) < 0 && toDate.compareTo(dateBetween) > 0;
	}

	public static LocalDate convertFromDateToLocaleDate(Date date) {
		return new java.sql.Date(date.getTime()).toLocalDate();
	}

	public static String convertToString(Date month, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(month);
	}

	public static Instant toInstant(Date date) {
		 return new java.util.Date(date.getTime()).toInstant();
	}
	
	public static java.sql.Date convertToSqlDate(Calendar calendar) {
		return new java.sql.Date(calendar.getTime().getTime());
	}
	
	public static java.sql.Date convertToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	public static String getCurrentTimestampIso() {
        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormatter.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

        return isoFormatter.format(Date.from(Instant.now()));
    }

	public static Date getCurrentTimestamp() {
		return new Date();
	}
}
