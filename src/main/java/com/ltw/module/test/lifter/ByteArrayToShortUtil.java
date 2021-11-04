package com.ltw.module.test.lifter;

/**
 * 16进制String与int的转换
 *
 * @author wx
 */
public class ByteArrayToShortUtil {

    /**
     * 转换short为byte
     * 需要转换的short
     *
     * @param index 低位在前，高位在后
     */
    public static void getBytesLH(byte[] bs, short s, int index) {
        bs[index + 0] = (byte) (s >> 0);
        bs[index + 1] = (byte) (s >> 8);
    }

    /**
     * 转换short为byte
     * 需要转换的short
     *
     * @param index 高位在前，低位在后
     */
    public static void getBytesHL(byte[] bs, short s, int index) {
        bs[index + 0] = (byte) (s >> 8);
        bs[index + 1] = (byte) (s >> 0);
    }

    /**
     * 通过byte数组取到short
     *
     * @param select1
     * @param index   第几位开始取
     *                低字节在前，高字节在后
     * @return
     */
    public static short getShortLH(byte[] bs, int index) {
        return BytesToShortUtil.getShortHL(bs[index + 1], bs[index + 0]);
    }

    /**
     * 通过byte数组取到short
     *
     * @param select1
     * @param index   第几位开始取
     *                高字节在前，低字节在后
     * @return
     */
    public static short getShortHL(byte[] bs, int index) {
        return BytesToShortUtil.getShortHL(bs[index + 0], bs[index + 1]);
    }

}