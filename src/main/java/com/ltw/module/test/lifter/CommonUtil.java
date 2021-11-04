package com.ltw.module.test.lifter;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommonUtil {

    /* 时间格式 */
    public static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /* 时间格式 */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    
	public static boolean isNullOrEmtyString(String str) {
		if(str == null || str.length() == 0) {
			return true;
		}
		str = str.trim();
		if("".equals(str) || "null".equals(str.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 生成响应帧
	 * 2019版本响应帧
	 * @param type 帧类型
	 * @param box_id 设备编号
	 * @param message_segment 信息段
	 * @return
	 */
	public static byte[] getDataReleased(byte type, byte crane_id, byte[] box_id,
			byte[] message_segment) {
		int messageLength = message_segment.length;
		byte[] byteResult = new byte[7+messageLength+6];
		//帧头 a55a
		byteResult[0] = -91;
		byteResult[1] = 90;
		//帧类型
		byteResult[2] = type;
		//塔吊编号
		byteResult[3] = crane_id;
		//设备编号
		for (byte i = 0;i < box_id.length; i++) {byteResult[4+i] = box_id[i];}
		//信息体
		for (byte j = 0; j < messageLength; j++) {byteResult[7+j] = message_segment[j];}
		//计算校验和
		//校验和由计算得来，值为从帧第一个字节开始到该字节之前所有字节和的低字节。
		byte checksum = countChecksum(byteResult, 0);//用bytes计算校验和
		byteResult[7+messageLength] = checksum;
		//计算长度，值从帧头开始，到校验和前(不算校验和)的值。计算格式：信息体前部长度+信息体
		//长度是从1开始计算
		byteResult[7+messageLength+1] = (byte) (7+messageLength);
		//帧尾 cc33c33c
		byteResult[7+messageLength+2] = -52;
		byteResult[7+messageLength+3] = 51;
		byteResult[7+messageLength+4] = -61;
		byteResult[7+messageLength+5] = 60;
		return byteResult;
	}

	/**
	 * LocalDate转Date
	 * @param localDate
	 * @return
	 */
	public static Date localDate2Date(LocalDate localDate) {
		if(null == localDate) {
			return null;
		}
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTime.toInstant());
	}
	
	/**
	 * 计算校验和
	 * 累加和校验【每字节相加（16进制）取后末两位】
	 * @param bytes
	 * @return
	 */
	public static byte countChecksum(byte[] data, int start) {
		int result = 0;
		for(; start < data.length; start++){result += data[start];}
		return (byte)(result%256);
	}
	
	public static final BigDecimal b1 = new BigDecimal(1);
	public static final BigDecimal b10 = new BigDecimal(10);
	public static final BigDecimal b100 = new BigDecimal(100);
	public static final BigDecimal b150 = new BigDecimal(150);
	public static final BigDecimal b1000 = new BigDecimal(1000);
	public static final BigDecimal b1500 = new BigDecimal(1500);
	
	//单字节编码，在ASCII编码之上增加了西欧语言、希腊语、泰语、阿拉伯语、希伯来语对应的文字符号，向下兼容ASCII编码。
	public final static String ISO_8859_1 = "ISO-8859-1";
	
	//信息交换用汉字编码字符集，中国于1980年发布，主要收录了6763个汉字、682个符号。
	public final static String GB2312 = "GB2312";
	
	//全称为Chinese Internal Code Specification，即汉字内码扩展规范，于1995年制定。主要扩展了GB2312，增加了更多的汉字，一共收录了21003个汉字。向下兼容GB2312编码。
	public final static String GBK = "GBK";
	
	//UNICODE的一种可变长度字符编码的实现，可以使用1～6个定长字节来编码UNICODE字符。
	//UTF-8对ASCII字符使用单字节存储，单个字符损坏也不会影响后面的字符。UTF-8非常适合在网络上面传输，也是现在使用最广泛的编码之一。
	//如果要表示中文，UTF-8编码效率要大于GBK，小于UTF-16。
	public final static String UTF_8 = "UTF-8";
	
	//UTF-16是UNICODE的具体实现，16即16位。UTF-16使用了两个字节来表示任何字符，这样使得操作字符串非常高效，这也是java把UTF-16作为字符在内存中存储的格式的重要原因。
	//UTF-16适合在磁盘与内存之间使用，字符和字节的相互转换会更加简单和高效，但不适合在网络上传输，因为网络传输可能会损坏字节流。
	//UTF-16单字节字符一定要占两个字节，存储空间放大了一倍，明显消耗了资源。
	public final static String UTF_16 = "UTF-16";
	
	/**
     * @param temp 8位
     * @return 倒序字符串
     */
    public static String stringOrder(String temp) {
        if (temp.length() == 8) {
            String s1 = temp.substring(0, 2);
            String s2 = temp.substring(2, 4);
            String s3 = temp.substring(4, 6);
            String s4 = temp.substring(6, 8);
            temp = s4 + s3 + s2 + s1;
            return temp;
        }
        return null;
    }
    
    /**
     * @param content
     * @return 十六进制转十进制
     */
    public static int hexToDecimal(String content) {
        int number = 0;
        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }
        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            number += map.get(str[i]) * Math.pow(16, str.length - 1 - i);
        }
        return number;
    }
    
    /**
     * @param s
     * @return 16进制转换成为string类型字符串
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    
    /**
     * @param second 秒
     * @return long时间戳转date
     */
    public static LocalDateTime secondToTime(long second) {
        return LocalDateTime.ofEpochSecond(second, 0, ZoneOffset.ofHours(8));
    }
    
    /**
     * int到byte[] 由低位到高位
     *
     * @param i 需要转换为byte数组的整行值。
     * @return byte数组
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) (i & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[3] = (byte) ((i >> 24) & 0xFF);
        return result;
    }
    
    /*
     * 	字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }
        return r;
    }
    
    /**
     * @param millisecond 毫秒
     * @return long时间戳转date
     */
    public static LocalDateTime millisecondToTime(long millisecond) {
        return LocalDateTime.ofEpochSecond(millisecond / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * @param plainText 待加密文本
     * @return 使用md5加密
     */
    public static String getMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");/*获取MD5实例*/
            /*此处传入要加密的byte类型值*/
            md.update(plainText.getBytes());
            /*此处得到的是md5加密后的byte类型值*/
            byte[] digest = md.digest();
            int i;
            StringBuilder sb = new StringBuilder();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append(0);
                /*通过Integer.toHexString方法把值变为16进制*/
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();/*从下标0开始，length目的是截取多少长度的值*/
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static byte[] LongToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * @param time              时间字符串
     * @param dateTimeFormatter 格式
     * @return 字符串转换成日期
     */
    public static LocalDateTime timeToDate(String time, DateTimeFormatter dateTimeFormatter) {
        if (!isNullOrEmtyString(time)) {
            return LocalDateTime.parse(time, dateTimeFormatter);
        }
        return null;
    }

    /**
     * @param time              时间
     * @param dateTimeFormatter 时间格式
     * @return 时间转字符串
     */
    public static String dateToTime(LocalDateTime time, DateTimeFormatter dateTimeFormatter) {
        if (null != time) {
            return dateTimeFormatter.format(time);
        }
        return "";
    }
    
    /**
     * @param temp 字符串
     * @return 字符串转换成十六进制字符串
     */
    public static String stringToHex(String temp) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = temp.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }
    
}
