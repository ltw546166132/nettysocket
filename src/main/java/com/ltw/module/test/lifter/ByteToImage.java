package com.ltw.module.test.lifter;

import java.util.HashMap;
import java.util.Map;


public class ByteToImage {
	
	public static Map<String,String> map=new HashMap<String, String>();
	
	public static void ByteToFile(String deviceId,byte[] byteImage, String path,
			String imageName, String suffix,int type) {
		//byte数组转照片文件
		try {
			//保存图片
			FileImageOutUtil.bufferedOutputStream(byteImage, path, imageName+"."+suffix);
			//保存缩略图
			FileImageOutUtil.byteToThumbnail(byteImage, path, imageName+"."+suffix);
		} catch (Exception e) {
		}
	}
	
	public static Byte ByteToFiles(String deviceId,byte[] byteImage, String path,
			String imageName, String suffix,int type) {
		//byte数组转照片文件
		try {
			//保存图片
			FileImageOutUtil.bufferedOutputStream(byteImage, path, imageName+"."+suffix);
			//保存缩略图
			FileImageOutUtil.byteToThumbnail(byteImage, path, imageName+"."+suffix);
		} catch (Exception e) {
		}
		return 0;
	}
}
