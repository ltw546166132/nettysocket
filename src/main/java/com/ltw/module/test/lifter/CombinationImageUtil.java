package com.ltw.module.test.lifter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CombinationImageUtil {

    public static Map<String, List<DevicePicture>> byteMap = new ConcurrentHashMap<>();//每台设备对应的图片

    public static String getImageName(String deviceId) {
        //生成图片文件
        return DateFormatUtil.getStringTimeByDate(new Date()) + " " + deviceId;
    }

    /**
     * 组合图片		type1为塔机	2位升降机
     * 塔机/升降机发来的是标准JPEG格式 (标准JPEG和顺序式编码的概念似乎是相同的？)
     */
    public static byte[] combination(byte[] data, String deviceId) {
        DevicePicture devicePicture = new DevicePicture();
        short id = ByteArrayToShortUtil.getShortLH(data, 7);
//        log.info("设备号:"+deviceId +"图片:"+id+"数据-"+ByteArrayToStringUtil.getHex(data));
        devicePicture.setID(id);//包号
        devicePicture.setP(ByteArrayToShortUtil.getShortLH(data, 9));//包长
        devicePicture.setPicture(ArrayToArrayUtil.getArrayToIntercept(data, 11, data.length - 8));//信息体(图片的某部分)
        devicePicture.setAmount(data[data.length - 7]);//减去和校验与尾部,获得总包数

        //保存图片包前的准备
        if (byteMap.get(deviceId) == null) {
            byteMap.put(deviceId, new ArrayList<DevicePicture>());
        }
        //如果收到的包号是0,舍弃原来的不完整的包，重新开始。
        if (id == 0) {
            byteMap.get(deviceId).clear();
        }

        //保存图片包
        byteMap.get(deviceId).add(devicePicture);
        
        //当包号(从0开始)等于总包数(从1开始)，说明图片接收完成，可以往下运行。
        if (id + 1 != (devicePicture.getAmount() & 0xFF)) {
        	//保存图片包
        	return null;
        }
        //接收了所有包后，将此图片list的引用移交给当前方法，避免新的图片包影响到当前图片包
        //而且用devicePictures可读性比较高
        List<DevicePicture> devicePictures = byteMap.get(deviceId);
//        byteMap.put(deviceId, null);
        //给图片包排序
        Collections.sort(devicePictures, new Comparator<DevicePicture>() {
            @Override
            public int compare(DevicePicture o1, DevicePicture o2) {
                //升序
                return Short.valueOf(o1.getID()).compareTo(Short.valueOf(o2.getID()));
                //把o1 o2反过来就是降序
            }
        });

        //计算这张图片长度,建立完整图片数组
        int byteImageLength = 0;
        for (int i = 0; i < devicePictures.size(); i++) {
            byteImageLength += devicePictures.get(i).getPicture().length;
        }
        byte[] byteImage = new byte[byteImageLength];

        //将list中的图片数据移动的完整数组中
        int byteCount = 0;//放置的起始位置
        for (DevicePicture picture : devicePictures) {
            System.arraycopy(picture.getPicture(), 0, byteImage, byteCount, picture.getPicture().length);
            byteCount += picture.getPicture().length;
        }
        byteMap.get(deviceId).clear();
        data = null;
        devicePictures = null;
        return byteImage;
    }

    /**
     * 组合图片数组		type1为塔机	2位升降机
     * 塔机/升降机发来的是标准JPEG格式 (标准JPEG和顺序式编码的概念似乎是相同的？)
     */
    public static byte[] combination(byte[] data, String imageKey, int index) {
        DevicePicture devicePicture = new DevicePicture();
        short id = ByteArrayUtil.getShortLH(data, index);
//        log.info("设备号:"+imageKey+index +"图片:"+id+"数据-"+ByteArrayToStringUtil.getHex(data));
        index++;
        index++;
        devicePicture.setID(id);//包号
        devicePicture.setP(ByteArrayUtil.getShortLH(data, index));
        index++;
        index++;//包长
        devicePicture.setPicture(ArrayToArrayUtil.getArrayToIntercept(data, index, data.length - 8));//信息体(图片的某部分)
        devicePicture.setAmount(data[data.length - 7]);//减去和校验与尾部,获得总包数
        //保存图片包前的准备
        if (byteMap.get(imageKey) == null) {
            byteMap.put(imageKey, new ArrayList<DevicePicture>());
        }
        if (id == 0) {
        	byteMap.get(imageKey).clear();
        }
        //保存图片包
        byteMap.get(imageKey).add(devicePicture);
        //说明图片接收完成，可以往下运行。
        if (id + 1 != (devicePicture.getAmount() & 0xFF)) {
        	//保存图片包
        	return null;
        }
        //接收了所有包后，将此图片list的引用移交给当前方法，避免新的图片包影响到当前图片包
        //而且用devicePictures可读性比较高
        List<DevicePicture> devicePictures = byteMap.get(imageKey);
        //给图片包排序
        Collections.sort(devicePictures, new Comparator<DevicePicture>() {
            @Override
            public int compare(DevicePicture o1, DevicePicture o2) {
                //升序
                return Short.valueOf(o1.getID()).compareTo(Short.valueOf(o2.getID()));
                //把o1 o2反过来就是降序
            }
        });
//        byteMap.put(imageKey, devicePictures);
        //计算这张图片长度,建立完整图片数组
        int byteImageLength = 0;
        for (int i = 0; i < devicePictures.size(); i++) {
            byteImageLength += devicePictures.get(i).getPicture().length;
        }
        byte[] byteImage = new byte[byteImageLength];

        //将list中的图片数据移动的完整数组中
        int byteCount = 0;//放置的起始位置
        for (DevicePicture picture : devicePictures) {
            System.arraycopy(picture.getPicture(), 0, byteImage, byteCount, picture.getPicture().length);
            byteCount += picture.getPicture().length;
        }
        byteMap.get(imageKey).clear();
        data = null;
        devicePictures = null;
        return byteImage;
    }

}
