package com.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 时间工具类
 * @author app
 *
 */
public class DateUtil {
	
	/**
	 * 获取当前时间(年月日时分秒)
	 * @return YYYY-MM-dd HH:mm:ss 时间
	 */
	public static String getDateTime(){
		Date date = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间(年月日)
	 * @return YYYY-MM-dd 时间
	 */
	public static String getDate(){
		Date date = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间(时分秒)
	 * @return HH:mm:ss 时间
	 */
	public static String getTime(){
		Date date = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 获取星期几
	 * @return 获取星期几
	 */
	public static String getWeekOfDate() {
		Date dt = new Date();
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){w = 0;}
        return weekDays[w];
    }
	
	/**
	 * 获取几分钟后的时间
	 * @param size 分钟数long类型可为负数
	 * @return 计算后的时间
	 */
	public static String getTimePlus(long size){
		long curren = System.currentTimeMillis();
		curren += size * 60 * 1000;
		Date da = new Date(curren); SimpleDateFormat dateFormat = new
		SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(da).toString();
	}
	
	/**
	 * 获得指定日期的前后天
	 * @param specifiedDay
	 * @return String
	 */
	public static String getSpecifiedDayBefore(String specifiedDay,int days) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + days);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}
	
	/**
	 * 获取当前毫秒数
	 * @return MS
	 */
	public static long getMS(){
		return System.currentTimeMillis();
	}
	
	/**
     * 时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return long 
     */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	/**
	 * 将时间格式化 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String toDateTimeFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 将时间格式化 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String toDateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 格式化标准年月日(2017-1-1)to(2017-01-01)
	 * @param nianyueri (2017-1-1)
	 * @return (2017-01-01)
	 */
	public static String toDate(String nianyueri) {
		String nian = nianyueri.split("-")[0];
		int yue = Integer.parseInt(nianyueri.split("-")[1]);
		int ri = Integer.parseInt(nianyueri.split("-")[2]);
		if(yue<10){
			nian = nian+"-0"+yue;
		}else{
			nian = nian+"-"+yue;
		}
		if (ri<10) {
			nian = nian+"-0"+ri;
		}else{
			nian = nian+"-"+ri;
		}
		return nian;
	}
	
	/**
	 * 将字符串时间转成时间对象
	 * @param dateTime
	 * @return
	 */
	public static Date toDateObject(String dateTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
	    Date parse = new Date();
		try {
			parse = simpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			try {
				parse = dates.parse(dateTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	    return parse;
	}
	
	public static void main(String[] args) {
		System.out.println(getDateTime());
		System.out.println(getTime());
		System.out.println(getDate());
		System.out.println(getWeekOfDate());
		System.out.println(getTimePlus(60*24*345));
	}
}
