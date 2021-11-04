package com.ltw.module.test.lifter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式转换工具类
 * 主要参数含义：
 * StringTime:yyyy-MM-dd HH:mm:ss 日期格式
 * StringFigure:yyyyMMddHHmmss 数字格式
 * StringTimeMS:(yyyy-MM-dd HH:mm:ssSSS 带毫秒的日期格式
 * StringFigureMS:yyyyMMddHHmmssSSS 带毫秒的数字格式
 * @author wx
 */
public class DateFormatUtil {
	
//获取数字格式String
	/**
	 * StringTime转StringFigure
	 * 将StringTime转为Date，然后将Date转为StringFigure
	 * @param date
	 * @return
	 */
	public static String getStringFigureByStringTime(String time) throws ParseException {
		return getStringFigureByDate( getDateByStringTime(time) );
	}
	
	/**
	 * Date转String 数字格式
	 * @param date
	 * @return
	 */
	public static String getStringFigureByDate(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	/**
	 * Date转String 数字格式 精确到毫秒
	 * @param date
	 * @return
	 */
	public static String getStringFigureMSByDate(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}
	
//获取日期格式String
	/**
	 * String转String 日期格式
	 * @param time
	 * @return
	 */
	
	public static String getStringTimeByStringFigure(String time) {
		//将日期字符串格式化为yyyy-MM-dd HH:mm:ss的形式
		StringBuilder sb = new StringBuilder(time);//构造一个StringBuilder对象
		sb.insert(4, "-");sb.insert(7, "-");sb.insert(10, " ");
		sb.insert(13, ":");sb.insert(16, ":");
		return sb.toString();
	}
	
	/**
	 * String转String 日期格式
	 * @param time
	 * @return
	 */
	public static String getStringTimeByStringYear(String time) {
		//将日期字符串格式化为yyyy-MM-dd的形式
		StringBuilder sb = new StringBuilder(time);//构造一个StringBuilder对象
		sb.insert(4, "-");sb.insert(7, "-");
		return sb.toString();
	}
	
	/**
	 * Date转String Date格式
	 * @param date
	 * @return
	 */
	public static String getStringTimeByDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * Date转毫秒+地区格式
	 * @param date
	 * @return
	 */
	public static String getRegionMSByDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(date);
	}
	
	/**
	 * Date转String Date格式 精确到毫秒
	 * @param date
	 * @return
	 */
	public static String getStringTimeMSByDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS").format(date);
	}
	
	/**
	 * 将 yyMMddHHmmss格式的bytes转换成yyyy-MM-dd HH:mm:ss格式的String
	 * 主要用于 解析塔机 升降机发送的时间
	 * @return
	 */
	public static String getStringTimeBy6ByteArray(byte[] bytesTime) {
		StringBuilder sb = new StringBuilder();
		sb.append("20");
		if (bytesTime[0]<10) {sb.append("0");}
		sb.append(bytesTime[0]);
		sb.append("-");
		if (bytesTime[1]<10) {sb.append("0");}
		sb.append(bytesTime[1]);
		sb.append("-");
		if (bytesTime[2]<10) {sb.append("0");}
		sb.append(bytesTime[2]);
		sb.append(" ");
		if (bytesTime[3]<10) {sb.append("0");}
		sb.append(bytesTime[3]);
		if (bytesTime[4]<10) {sb.append("0");}
		sb.append(":");
		sb.append(bytesTime[4]);
		if (bytesTime[5]<10) {sb.append("0");}
		sb.append(":");
		sb.append(bytesTime[5]);
		return sb.toString();
	}
	
	/**
	 * Timestamp转String（未测试，未投入使用）
	 * @param timeStamp
	 * @return
	 */
	public static String getStringTimeByTimestamp(Timestamp timeStamp) {
		return String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeStamp));
	}
	
//获取date格式	
	/**String转Date Date格式
	 * HH是24小时制
	 * hh是12小时制
	 */
	public static Date getDateByStringTime(String time) throws ParseException {
		//SimpleDateFormat是一个线程不安全的类
		//log.warn("time---{}s", time);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);//24小时制。如果用hh则是12小时制
	}
	
	public static Date getDateByStringTimeYear(String time) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(time);//24小时制。如果用hh则是12小时制
	}
	
	/**String转Date	Date格式 精确到毫秒
	 * HH是24小时制
	 * hh是12小时制
	 */
	public static Date getDateByStringTimeMS(String time) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS").parse(time);
	}
	
	
//获取时间戳格式
	/**
	 * String转Timestamp
	 * @param time
	 * @return
	 */
	public static Timestamp getTimestampByStringTime(String time) {
		//log.debug("DateFormatUtil---transferFormat---{}", time);
		return Timestamp.valueOf(time);//String转Timetamp,可以用 .valueOf直接转
	}
	
	/**
	 * String转时间戳 
	 * 不是我写的，是方向正写的。
	 * 我没测过，不知道效率怎么样，也不知道返回的是什么。
	 */
	public static String millisecondByStringTime(String str) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str));
		return String.valueOf(calendar.getTimeInMillis());
	}
	
	/**
	 * 20151212T121212Z格式日期转Timestamp
	 * 主要用于 解析电信iot发送的时间
	 */
	public static Timestamp getTimestampByStringFigureTZ(String timeTZ) throws ParseException {
		//将yyyyMMddTHHmmssZ格式转为yyyy-MM-dd HH:mm:ss格式
		StringBuilder sb = new StringBuilder();//构造一个StringBuilder对象
		sb.append(timeTZ.substring(0,4));sb.append("-");
		sb.append(timeTZ.substring(4,6));sb.append("-");
		sb.append(timeTZ.substring(6,8));sb.append(" ");
		sb.append(timeTZ.substring(9,11));sb.append(":");
		sb.append(timeTZ.substring(11,13));sb.append(":");
		sb.append(timeTZ.substring(13,15));
		//转换为Date格式
		Date dateTemp = getDateByStringTime(sb.toString());
		//时间增加8小时
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTemp);
		cal.add(Calendar.HOUR, 8);// 24小时制   
		dateTemp = cal.getTime();
		//调用方法将Date转换为StringTime，
		//然后将StringTime转为Timestamp
		return getTimestampByStringTime( getStringTimeByDate(dateTemp) );
	}
	
	/**
	 * Date转Timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestamp(Date date) {
		//设置格式为StringTime格式，同时将Date转换为String类型，然后将String转换为Timestamp
		return getTimestampByStringTime( getStringTimeByDate(date) );
	}
	
	public static Timestamp getTimestamp(byte[] bytesTime, int index) {
		return getTimestamp(bytesTime[index], bytesTime[index+1], bytesTime[index+2], bytesTime[index+3], bytesTime[index+4], bytesTime[index+5]);
	}
	
	//bytes先转换成string，然后转换成需要的类型
	public static Timestamp getTimestamp(byte year, byte month, byte day, byte hour, byte minute, byte second) {
		StringBuilder sb = new StringBuilder();
		sb.append("20");
		if (year<10) {sb.append("0");}sb.append(year);sb.append("-");
		if (month<10) {sb.append("0");}sb.append(month);sb.append("-");
		if (day<10) {sb.append("0");}sb.append(day);sb.append(" ");
		if (hour<10) {sb.append("0");}sb.append(hour);sb.append(":");
		if (minute<10) {sb.append("0");}sb.append(minute);sb.append(":");
		if (second<10) {sb.append("0");}sb.append(second);
		return getTimestampByStringTime(sb.toString());
	}
	
	/**
	 * 将 yyMMddHHmmss格式的bytes转换成yyyy-MM-dd HH:mm:ss格式的timeStamp
	 * @return
	 */
	public static Timestamp getTimeStampBy6ByteArrayTime(byte[] bytesTime) {
		StringBuilder sb = new StringBuilder();
		sb.append("20");
		if (bytesTime[0]<10) {sb.append("0");}
		sb.append(bytesTime[0]);
		sb.append("-");
		if (bytesTime[1]<10) {sb.append("0");}
		sb.append(bytesTime[1]);
		sb.append("-");
		if (bytesTime[2]<10) {sb.append("0");}
		sb.append(bytesTime[2]);
		sb.append(" ");
		if (bytesTime[3]<10) {sb.append("0");}
		sb.append(bytesTime[3]);
		sb.append(":");
		if (bytesTime[4]<10) {sb.append("0");}
		sb.append(bytesTime[4]);
		sb.append(":");
		if (bytesTime[5]<10) {sb.append("0");}
		sb.append(bytesTime[5]);
		return getTimestampByStringTime(sb.toString());
	}
	
	/**
	 * Date转ByteArray
	 * 时间 所需格式为 yyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static void get6ByteArrayByDate(Date date, byte[] bytesTime, int index) {
		String time = getStringFigureByDate(date);
		bytesTime[index+0] = (byte) Integer.parseInt(time.substring(2,4));
		bytesTime[index+1] = (byte) Integer.parseInt(time.substring(4,6));
		bytesTime[index+2] = (byte) Integer.parseInt(time.substring(6,8));
		bytesTime[index+3] = (byte) Integer.parseInt(time.substring(8,10));
		bytesTime[index+4] = (byte) Integer.parseInt(time.substring(10,12));
		bytesTime[index+5] = (byte) Integer.parseInt(time.substring(12,14));
	}
	
	/**
	 * 两个字符串时间计算时间差（秒）
	 * @return
	 */
	public static Integer getSecondsByDate(String startTime, String endTime) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date startDate = null;
		Date endDate = null;
		try {
			startDate=inputFormat.parse(startTime);
			endDate=inputFormat.parse(endTime);
		} catch (ParseException e) {
			return null;
		}
		//得到两个日期对象的总毫秒数，计算两者之差
		long firstMinusSecond = getMinusSecond(startDate, endDate);
		//得到秒
		int totalSeconds=(int)(firstMinusSecond/1000);
		return totalSeconds;
	}
	
	//结束时间和开始时间的秒数之差
	public static long getSecondByDate(Date startDate, Date endDate) {
		return (getMinusSecond(startDate, endDate)/1000);
	}
	
	//结束时间和开始时间的毫秒数之差
	public static long getMinusSecond(Date startDate, Date endDate) {
		return endDate.getTime()-startDate.getTime();
	}
	
	/**秒转HH：mm：ss*/
	public static String secondsToHours(int seconds) {
		int temp=0;
		StringBuffer sb=new StringBuffer();
		temp = seconds/3600;
		if(temp < 0) {
			temp = Integer.valueOf(String.valueOf(temp).substring(1, String.valueOf(temp).length()));
		}
		sb.append((temp<10)?"0"+temp+":":""+temp+":");
		
		temp=seconds%3600/60;
		if(temp < 0) {
			temp = Integer.valueOf(String.valueOf(temp).substring(1, String.valueOf(temp).length()));
		}
		sb.append((temp<10)?"0"+temp+":":""+temp+":");
		
		temp=seconds%3600%60;
		if(temp < 0) {
			temp = Integer.valueOf(String.valueOf(temp).substring(1, String.valueOf(temp).length()));
		}
		sb.append((temp<10)?"0"+temp:""+temp);
		
		return sb.toString();
	}
	
	/**
	 * HH：mm：ss转秒
	 * @param seconds
	 * @return
	 */
	public static int getSecond(String time){
		int s = 0;
		if(time.length()==8){ //时分秒格式00:00:00
			int index1=time.indexOf(":");
			int index2=time.indexOf(":",index1+1);
			s = Integer.parseInt(time.substring(0,index1))*3600;//小时   
			s+=Integer.parseInt(time.substring(index1+1,index2))*60;//分钟 
			s+=Integer.parseInt(time.substring(index2+1));//秒
		}
		if(time.length()==5){//分秒格式00:00
			s = Integer.parseInt(time.substring(time.length()-2)); //秒  后两位肯定是秒
			s+=Integer.parseInt(time.substring(0,2))*60;//分钟
		}
		return s;
	}
	
	/**
	 * HH：mm：ss转秒(时间大于100小时的)
	 * @param seconds
	 * @return
	 */
	public static int getSecondSuper(String time){
		String[] arr = time.split(":");
		Integer hour = Integer.valueOf(arr[0])*60*60;
		Integer min = Integer.valueOf(arr[1])*60;
		Integer sec = Integer.valueOf(arr[2]);
		return hour + min + sec;
	}
	
	/**
	 * 小时    转天     加小时
	 * @param seconds
	 * @return
	 */
	public static String getDayHour(String time){
		if(time == null) {
			return "0 00:00:00";
		}
		String[] arr = time.split(":");
		String hour = arr[0];
		String min = arr[1];
		String sec = arr[2];
		Integer day = Integer.valueOf(hour)/24;
		Integer hours = Integer.valueOf(hour)%24;
		if(hours == 0) {
			hour = "00";
		} else {
			hour = String.valueOf(hours);
		}
		if(Integer.valueOf(min) == 0) {
			min = "00";
		}
		if(Integer.valueOf(sec) == 0) {
			sec = "00";
		}
		return day + " " +hour +":"+ min +":"+sec;
	}
	
	/**
	 * 小时    转天     加小时
	 * @param seconds
	 * @return
	 */
	public static String getDayHourSum(String beforeTime,String afterTime){
		if(beforeTime == null && afterTime != null) {
			return afterTime;
		}
		if(afterTime == null && beforeTime != null) {
			return beforeTime;
		}
		if(afterTime == null && beforeTime == null) {
			return "0 00:00:00";
		}
		//将时间的天数和时间分开
		String[] arrbefore = beforeTime.split(" ");
		String[] arrafter = afterTime.split(" ");
		
		//将时间分为时分秒
		String[] hmsbefore = arrbefore[1].split(":");
		String[] hmsafter = arrafter[1].split(":");
		
		//计算时分秒和
		Integer sec = Integer.valueOf(hmsbefore[2]) + Integer.valueOf(hmsafter[2]);
		Integer min = Integer.valueOf(hmsbefore[1]) + Integer.valueOf(hmsafter[1]);
		Integer hour = Integer.valueOf(hmsbefore[0]) + Integer.valueOf(hmsafter[0]);
		//计算天数和
		Integer day = Integer.valueOf(arrbefore[0]) + Integer.valueOf(arrafter[0]);
		//如果时分秒和超过逻辑进1
		if(sec > 59) {
			min += 1;
			sec -= 60;
		}
		if(min > 59) {
			hour += 1;
			min -= 60;
		}
		if(hour > 23) {
			day += 1;
			hour -= 24;
		}
		String dayStr = "0";
		String hourStr = "00";
		String minStr = "00";
		String secStr = "00";
		if(sec != 0) {
			secStr = String.valueOf(sec);
		}
		if(min != 0) {
			minStr = String.valueOf(min);
		}
		if(hour != 0) {
			hourStr = String.valueOf(hour);
		}
		if(day != 0) {
			dayStr = String.valueOf(day);
		}
		return dayStr + " " + hourStr + ":" + minStr + ":" + secStr;
	}
	
	/**
	 *    天  加小时 转小时
	 * @param seconds
	 * @return
	 */
	public static String DayHourToTime(String time){
		String[] arr = time.split(" ");
		Integer hour = Integer.valueOf(arr[0])*24;
		
		String[] arr1 = arr[1].split(":");
		String hour1 = arr1[0];
		String min1 = arr1[1];
		String sec1 = arr1[2];
		hour1 = String.valueOf(Integer.valueOf(hour1) + hour);
		return hour1 + ":" + min1 + ":" + sec1;
	}
	
}
