package com.ltw.module.test.lifter;

public class ByteArrayUtil {
	
	/**
	 * byte数组中取int数值 
	 * 因为一个byte是八位，int有四个八位，所以将这个byte[0]左移24位，就将这个byte[0]放在了int的最高一个八位上。
	 * b1是最高位，b2是次高位，b3是次低位，b4是最低位
	 */
	public static int getInt(byte high, byte second_high, byte second_low, byte low) {
		return ((high & 0xFF)<<24) | ((second_high & 0xFF)<<16) | ((second_low & 0xFF)<<8) | (low & 0xFF);
	}
	
	/**
	 * 通过byte数组取到short
	 * 
	 * @param select1
	 * @param index
	 *		第几位开始取
	 *高字节在前，低字节在后
	 * @return
	 */
	public static short getShortHL(byte[] bs,int index) {
		return getShortHL(bs[index+0],bs[index+1]);
	}
	
	/**
	 * 第一个参数是高字节，第二个参数是低字节
	 * java是采用补码的形式
	 * @param high
	 * @param low
	 * @return
	 */
	public static short getShortHL(byte high, byte low) {
		return (short)((high<<8)|(low&0xFF));
	}
	
	/**
	 * 通过byte数组取到short
	 * @param select1
	 * @param index
	 *		第几位开始取
	 *低字节在前，高字节在后
	 * @return
	 */
	public static short getShortLH(byte[] bs, int index) {
		return getShortHL( bs[index+1], bs[index+0] );
	}
	
	/**
	 * 将 负数的 符号位 变成0
	 */
	public static byte signBitTo0(byte b) {
		if (b<0) {
			byte[] array = BitToByteUtil.getBitArray(b);
			array[0]=0;
			b=(byte)BitToByteUtil.getByteByArray(array);
		}
		return b;
	}
	
	/**
	 * 转换short为byte
	 * 需要转换的short
	 * @param index
	 * 低位在前，高位在后
	 */
	public static void getBytesLH(byte[] bs, short s, int index) {
		bs[index + 0] = (byte) (s >> 0);
		bs[index + 1] = (byte) (s >> 8);
	}
	
	/**
	 * 转换short为byte
	 * 需要转换的short
	 * @param index
	 * 高位在前，低位在后
	 */
	public static void getBytesHL(byte[] bs, short s, int index) {
		bs[index + 0] = (byte) (s >> 8);
		bs[index + 1] = (byte) (s >> 0);
	}
}
