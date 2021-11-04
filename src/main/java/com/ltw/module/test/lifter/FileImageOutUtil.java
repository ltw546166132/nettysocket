package com.ltw.module.test.lifter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class FileImageOutUtil extends FileOutUtil{
	
	/**
	 * byte数组到图片 已测试
	 * @param data
	 * @param path
	 * @param imageName
	 */
	public static void fileImageOutputStream(byte[] data, String path, String imageName) {
		if(data.length<3||path==null||imageName==null||imageName.equals("")) {throw new RuntimeException();};
		FileImageOutputStream imageOutput = null;
		try{
			existsFolder(path);
			imageOutput = new FileImageOutputStream(new File(path+imageName));
			imageOutput.write(data, 0, data.length);
		} catch(Exception e) {
		}finally {
			try {
				if (imageOutput!=null) {imageOutput.close();}
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * byte数组到图片 已测试 可用
	 * 此方法会压缩图片(已经压缩过一次的不会再压缩)，不建议使用，仅供参考
	 * @param data
	 * @param image_type
	 * @param path
	 * @param imageName
	 */
	public static void bufferedImage(byte[] data, String image_type, String path, String imageName) {
		if(data.length<3||path==null||imageName==null||imageName.equals("")) {throw new RuntimeException();};
		ByteArrayInputStream bais = null;
		BufferedImage bi = null;
		try {
			existsFolder(path);
			bais = new ByteArrayInputStream(data);//将byte数组写到内存
			bi = ImageIO.read(bais);
			ImageIO.write(bi, image_type, new File(path+imageName));//这里会对图片进行压缩
		} catch (IOException e) {
		}finally {
			try {
				if (bais!=null) {bais.close();}
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 生成缩略图 已测试 可用
	 */
	public static void byteToThumbnail(byte[] data, String path, String fileName) {
		try {
			//缩略图存放的文件夹
			String outputFolder =path + "thu/";
			existsFolder(outputFolder);
			//4个参数	(图片byte，生成缩略图地址，缩放比例，后缀格式)
			ImageCompressUtil.ByteToScaleImage(data,outputFolder+fileName,0.2,"jpg");
		} catch (Exception e) {
		}
	}
	
}