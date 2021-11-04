package com.ltw.module.test.lifter;

public class ByteArrayToStringUtil {

	/**
	 * byte数组转换成十六进制字符串输出："abcd" => "61626364"
	 * @param data
	 */
	public static String getHex(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length);
		String hex;
		for (int i = 0; i < data.length; i++) {
			hex = Integer.toHexString(0xFF & data[i]);
			if (hex.length() < 2) {sb.append(0);}
			sb.append(hex);
		}
		data=null;
		return sb.toString();
	}
	
	/**十六进制字符串转为byte数组*/
	public static byte[] getBytes(String inHex){
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1){
			//奇数  
			hexlen++;
			result = new byte[(hexlen/2)];
			inHex="0"+inHex;
		}else {
			//偶数
			result = new byte[(hexlen/2)];
		}
		int j=0;
		for (int i = 0; i < hexlen; i+=2){
			result[j]=getByte(inHex.substring(i,i+2));
			j++;
		}
		
		inHex=null;
		return result;
	}
	
	/**十六进制字符串转为byte
	 * PS:Hex的字符串必须为十六进制的字符，否则会抛出异常。Hex的范围为0x00到0xFF
	 */ 
	public static byte getByte(String inHex){
		return (byte)Integer.parseInt(inHex,16);
	}
	
	/**根据传入的开始位置和结束位置，截取传入的byte数组
	 * byte数组转字符串，同时修剪去除首尾空格
	 */
	public static String getStringTrim(byte[] data, int start, int end, String encoder) throws Exception{
		byte[] intercept = ArrayToArrayUtil.getArrayToIntercept(data, start, end);
		return new String(intercept, encoder).trim();
	}
}
