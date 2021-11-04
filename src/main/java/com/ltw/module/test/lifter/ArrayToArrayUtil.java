package com.ltw.module.test.lifter;


public class ArrayToArrayUtil {
	
	/**扩充数组*/
	public static byte[] addArrayLength(byte[] data, int length){
		byte[] newArray = new byte [data.length+length];
		//将array数组从0位置至array.length位置，复制到newArray数组0位置到array.length位置。
		System.arraycopy(data,0,newArray,0,data.length);
		return newArray;
	}
	/**
	 * 根据传入的开始位置和结束位置，截取传入的byte数组
	 * Intercept：截取
	 */
	public static byte[] getArrayToIntercept(byte[] data, int start, int end) throws RuntimeException {
		if (start>end||start<0||end>data.length) {throw new RuntimeException();}
		byte[] bs = new byte[end-start];
		for (int i = start; i < end; i++) {bs[i-start] = data[i];}
		return bs;
	}
	
	/**
	 * 根据传入的开始位置和数组大小，截取传入的byte数组 方法2
	 * 未测试
	 */
	public static byte[] getArrayToIntercept2(byte[] data, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(data, begin, bs, 0, count);
		return bs;
	}
	
	/**
	 * 根据传入的发送数组和接收数组，发送数组和接收数组 的 开始位置和结束位置，将发送数组中的数据转义到接收数组
	 * length是待分配的数据长度
	 * startSend 发送数据的数组的开始遍历位置
	 * startReceive 接收数据的数组的开始遍历位置
	 * Assignment：分配
	 */
	public static byte[] getArrayToAssignment(byte[] dataSend, byte[] dataReceive, int startSend, int startReceive, int length) throws Exception {
		if (startSend<0||length>dataSend.length) {throw new Exception();}
		for (int i = 0; i < length; i++) {
			dataSend[i+startSend] = dataReceive[i+startReceive];
		}
		return dataReceive;
	}
	
}