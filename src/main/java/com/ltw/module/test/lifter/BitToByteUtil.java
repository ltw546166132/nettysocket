package com.ltw.module.test.lifter;

public class BitToByteUtil {
	
	/**
	 * 将byte转为长度8的字符串，字符串每一位代表1bit
	 * 得到的是原码
	 */
	public static String getBitString(byte b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 7; i >= 0; i--) {
			sb.append((b>>i)&0x1);
		}
		return sb.toString();
	}
	
	/**
	 * 将byte转为长度8的bit数组，数组每个值代表1bit 
	 */
	public static byte[] getBitArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte)(b&1);
			b = (byte) (b>>1);
		}
		return array;
	}
	
	public static byte[] getBitArray2(byte b) {
		byte[] array = new byte[8];
		array[0]=(byte) ((b>>7)&0x1);
		array[1]=(byte) ((b>>6)&0x1);
		array[2]=(byte) ((b>>5)&0x1);
		array[3]=(byte) ((b>>4)&0x1);
		array[4]=(byte) ((b>>3)&0x1);
		array[5]=(byte) ((b>>2)&0x1);
		array[6]=(byte) ((b>>1)&0x1);
		array[7]=(byte) ((b>>0)&0x1);
		return array;
	}
	
	/**
	 * 二进制转10进制
	 */
	public static byte getByteByArray(byte[] bit) {
		byte b = 0;
		int maxIndex = bit.length-1;//最大索引
		for (int i = maxIndex; i >= 0; i--) {
			//log.debug("maxIndex-i---:{}", maxIndex-i);
			b += bit[i]*Math.pow(2, maxIndex-i);
		}
		return b;
	}
	
	/**
	 * java原生 将8bits转为byte
	 */
	public static byte getByteBy8Bit(String bits) {
		if (bits==null) {return 0;}
		return (byte)Short.parseShort(bits, 2);
	}
	
	/**
	 * 将int格式bit转为byte
	 * @param bits
	 * @return
	 */
	public static byte getByteBy1To7IntBit(int bits) {
		return getByteBy1To7StringBit(String.valueOf(bits));
	}
	/**
	 * java原生 将字符串格式1-7bits转为byte
	 */
	public static byte getByteBy1To7StringBit(String bits) {
		return Byte.parseByte(bits, 2);
	}
	//Byte.valueOf(bits, 2) 或者 Byte.parseByte("",2) 都可以使用。
			//valueOf是转换为Byte封装类型，而parseByte是转换为byte基本数据类型。
	
	/**
	 * bits转byte
	 * 本方法不够完善，只能转换4bit和8bit
	 * 本方法未经验证，未使用
	 */
	public byte getByte2(String bits) {
		int re, len;
		if (null == bits) {
			return 0;
		}
		len = bits.length();
		if (len != 4 && len != 8) {
			return 0;
		}
		if (len == 8) {// 8 bit处理
			if (bits.charAt(0) == '0') {// 正数
				re = Integer.parseInt(bits, 2);
			} else {// 负数
				re = Integer.parseInt(bits, 2) - 256;
			}
		} else {//4 bit处理
			re = Integer.parseInt(bits, 2);
		}
		return (byte) re;
	}
	
}
