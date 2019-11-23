package com.api.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class DateUtils {
	private static Locale LOCALE_JAPAN = new Locale("ja", "JP", "JP");
	public static final String YYYYMMDD = "yyyyMMdd";

	public static Date now() {
		return new Date();
	}

	public static Date currentDate() {
		return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date offsetYear(Date date, int years) {
		LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return Date.from(localDate.plusYears((long) years).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date offsetMonth(Date date, int months) {
		LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return Date.from(localDate.plusMonths((long) months).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date offsetDay(Date date, int days) {
		LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return Date.from(localDate.plusDays((long) days).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date offsetHour(Date date, int hours) {
		LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return Date.from(localDate.plusHours((long) hours).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date offsetSecond(Date date, int second) {
		LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return Date.from(localDate.plusSeconds((long) second).atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static String toYYYYMMDD(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	public static String toWarekiYYMMDD(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("GyyMMdd", LOCALE_JAPAN);
		return df.format(date);
	}

	public static Date parseDate(String pattern, String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException arg3) {
			throw arg3;
		}
	}

	public static Date tryParseDate(String pattern, String str) {
		if (!StringUtils.hasText(str)) {
			return null;

		} else {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false);
			try {
				return df.parse(str);
			} catch (ParseException arg3) {
				return null;
			}
		}
	}
}
