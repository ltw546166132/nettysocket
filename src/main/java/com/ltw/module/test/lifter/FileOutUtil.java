package com.ltw.module.test.lifter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutUtil {
	
	//判断文件夹是否存在,不存在就创建文件夹
	public static void existsFolder(String path) {
		File file = null;
		file = new File(path);
		file.mkdirs();
		file=null;
	}
	
	public static File existsFile(String path) {
		File file = null;
		file = new File(path);
		if (!file.exists()) {throw new RuntimeException("文件不存在");}
		return file;
	}
	
	/**
	 * byte数组到文件 文件类型不限
	 * 目前只测试过图片类型
	 * @param data
	 * @param folderName
	 * @param fileName
	 */
	public static void fileOutputStream(byte[] data, String folderName, String fileName) {
		if(data.length<=0||folderName==null||fileName==null||fileName.equals("")) {throw new RuntimeException();};
		existsFolder(folderName);
		try( OutputStream output = new FileOutputStream(folderName+fileName) ){
			output.write(data);
		} catch(Exception e) {
		}
	}
	
	/**
	 * byte数组到文件 文件类型不限
	 * 据说效率高于直接用FileOutputStream
	 * 目前只测试过图片类型
	 * @param data
	 * @param folderName
	 * @param fileName
	 */
	public static void bufferedOutputStream(byte[] data, String folderName, String fileName) {
		if(data.length<=0||folderName==null||fileName==null||fileName.equals("")) {throw new RuntimeException();};
		existsFolder(folderName);
		try( OutputStream output = new FileOutputStream(new File(folderName+fileName));
			OutputStream bufferedOutput = new BufferedOutputStream(output) ){
			
			bufferedOutput.write(data);
			bufferedOutput.flush();
		} catch(Exception e) {
		}
	}
	
}
