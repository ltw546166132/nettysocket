package com.ltw.module.test.lifter;

public class ByteArrayToIntUtil {
	
	/**
	* 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 
	* @param value
	*	要转换的int值 
	* @return byte数组 
	*/	
	public static void getBytesLH(byte[] bytes, int value, int index) {
		bytes[index+3] = (byte) ((value>>24)&0xFF);
		bytes[index+2] = (byte) ((value>>16)&0xFF);
		bytes[index+1] = (byte) ((value>>8)&0xFF);
		bytes[index+0] = (byte) (value&0xFF);
	}
	
	/**
	* 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。 
	*/	
	public static void getBytesHL(byte[] bytes, int value, int index) {
		bytes[index+0] = (byte) ((value>>24)&0xFF);
		bytes[index+1] = (byte) ((value>>16)&0xFF);
		bytes[index+2] = (byte) ((value>>8)&0xFF);
		bytes[index+3] = (byte) (value&0xFF);
	}
	
	/**
	* byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序 
	* @param src
	*	byte数组
	* @return int数值
	*/	
	public static int getIntLH(byte[] src, int index) {
		return getInt(src[index+3], src[index+2], src[index+1], src[index]);
	}
	
	/**
	 * byte数组中取int数值，本方法适用于(高位在前，低位在后)的顺序。 
	 * 因为一个byte是八位，int有四个八位，所以将这个byte[0]左移24位，就将这个byte[0]放在了int的最高一个八位上。
	 */
	public static int getIntHL(byte[] src, int index) {
		return getInt(src[index], src[index+1], src[index+2], src[index+3]);
	}
	
	/**
	 * byte数组中取int数值 
	 * 因为一个byte是八位，int有四个八位，所以将这个byte[0]左移24位，就将这个byte[0]放在了int的最高一个八位上。
	 * b1是最高位，b2是次高位，b3是次低位，b4是最低位
	 */
	public static int getInt(byte high, byte second_high, byte second_low, byte low) {
		return ((high & 0xFF)<<24) | ((second_high & 0xFF)<<16) | ((second_low & 0xFF)<<8) | (low & 0xFF);
	}
	
	public static int toInt(byte[] bytes){
        int number = 0;
        for(int i = 0; i < 4 ; i++){
            number += bytes[i] << i*8;
        }
        return number;
    }
	 
	public static byte[] toBytes(int number){
        byte[] bytes = new byte[4];
        bytes[0] = (byte)number;
        bytes[1] = (byte) (number >> 8);
        bytes[2] = (byte) (number >> 16);
        bytes[3] = (byte) (number >> 24);
        return bytes;
    }
}
