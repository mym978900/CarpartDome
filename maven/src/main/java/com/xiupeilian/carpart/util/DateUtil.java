package com.xiupeilian.carpart.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * @Description <时间处理工具>
 * @author 何家明
 * @date 2017年3月20日 上午9:14:44
 */
public class DateUtil {

	private static Logger log = Logger.getLogger(DateUtil.class);

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_CHINA = "yyyy年MM月dd日";
	public static final String DATE_FORMAT_BAR = "yyyy-MM-dd";
	public static final String DATE_FORMAT_SPRIT = "yyyy/MM/dd";
	public static final String DATE_FORMAT_MS = "yyyy-MM-dd HH:mm:ss,SSS";
	public static final String DATE_FORMAT_FILE_SUFFIX = "yyyyMMddHHmmss";

	/**
	 * 将两个日期相减得到间隔的天数
	 * 
	 * @param startdate
	 * @param enddate
	 * @return int days
	 */
	public static int dateTointerval(Date startdate, Date enddate) {
		long timeNow = enddate.getTime();
		long timeOld = startdate.getTime();
		int days = (int) ((timeNow - timeOld) / (1000 * 60 * 60 * 24));
		return days;

	}
	/**
	 * 将两个日期相减得到间隔的分钟数
	 * 
	 * @param startdate
	 * @param enddate
	 * @return int minutes
	 */
	public static int dateTointervalMin(Date startdate, Date enddate) {
		long timeNow = enddate.getTime();
		long timeOld = startdate.getTime();
		int mins = (int) ((timeNow - timeOld) / (1000 * 60));
		return mins;

	}

	/**
	 * 将日期转化为字符串 格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return String date
	 */
	public static String dateToFullString(Date date) {
		return dateToString(date, DATE_FORMAT);

	}

	/**
	 * 将日期转化为“yyyy年MM月dd日”格式字符串
	 * 
	 * @param date
	 * @return String date
	 */
	public static String dateToChinaString(Date date) {
		return dateToString(date, DATE_CHINA);
	}

	/**
	 * 将日期转化为“yyyy/MM/dd”格式字符串
	 * 
	 * @param date
	 * @return String date
	 */
	public static String dateToSpritString(Date date) {
		return dateToString(date, DATE_FORMAT_SPRIT);
	}

	/**
	 * 将日期转化为“yyyy-MM-dd”格式字符串
	 * 
	 * @param date
	 * @return String date
	 */
	public static String dateToBarString(Date date) {
		return dateToString(date, DATE_FORMAT_BAR);
	}

	/**
	 * 将日期转化为用户指定格式字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		String datetime = null;
		if (date == null) {
			return datetime;
		}
		try {
			SimpleDateFormat dformate = new SimpleDateFormat(pattern);
			datetime = dformate.format(date);
		} catch (Exception e) {
			log.error("将日期转化为用户指定格式字符串异常:" + e);
			e.printStackTrace();
		}
		return datetime;
	}

	/**
	 * 将"yyyy-MM-dd HH:mm:ss"格式字符传转换化为日期
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static Date dateFullStrToDate(String dateStr) {
		return strToDate(dateStr, DATE_FORMAT);
	}

	/**
	 * 将指定日期格式字符串转换成日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date strToDate(String dateStr, String pattern) {
		Date da = null;
		SimpleDateFormat dformate = new SimpleDateFormat(pattern);
		try {
			da = dformate.parse(dateStr);
		} catch (ParseException e) {
			log.error("把字符串转换成日期对象异常:" + e);
			e.printStackTrace();
		}
		return da;
	}

	/**
	 * 得到当前标准的格式化时间字符串 格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return String date
	 */
	public static String getCurrentDate() {
		SimpleDateFormat dformate = new SimpleDateFormat(DATE_FORMAT);
		return dformate.format(new Date());
	}

	/**
	 * 得到当前标准的格式化时间字符串 格式为"yyyy年MM月dd日"
	 * 
	 * @return String date
	 */
	public static String getChiinaDate() {
		SimpleDateFormat dformate = new SimpleDateFormat(DATE_CHINA);
		return dformate.format(new Date());
	}

	/**
	 * 得到指定格式的当前字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat dformate = new SimpleDateFormat(format);
		return dformate.format(new Date());
	}

	/**
	 * 将一个字符传转化为一个标准的时间 格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		SimpleDateFormat dformate = new SimpleDateFormat(DATE_FORMAT);
		Date currentDate = null;
		try {
			currentDate = dformate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentDate;
	}

	/**
	 * 将一个字符传转化为一个标准的时间 格式为"yyyy-MM-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate2(String date) {
		SimpleDateFormat dformate = new SimpleDateFormat(DATE_FORMAT_BAR);
		Date currentDate = null;
		try {
			currentDate = dformate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentDate;
	}

	public static int getDaysOfTheMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 在给定的时间上添加指定的天数
	 * @param date 时间
	 * @param day 天数
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}
	
	/**
	   * 得到现在时间
	 * @param currentTime2 
	   * 
	   * @return 字符串 yyyyMMdd HHmmss
	   */
	
	public static String getStringToday(Date currentTime2) {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	
	/**
	 * 
	 * 
	 * @author:  xingyou823  
	 * @time: 2017年5月18日 下午3:04:05
	 * @Description :将两个日期相减得到间隔的秒数
	 *
	 */
	public static int dateTimeTointerval(Date startdate, Date enddate) {
		long timeNow = enddate.getTime();
		long timeOld = startdate.getTime();
		int days = (int)((timeNow - timeOld)/1000);
		return days;

	}
}