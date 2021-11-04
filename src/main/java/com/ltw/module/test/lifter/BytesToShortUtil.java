package com.ltw.module.test.lifter;


public class BytesToShortUtil {
    /**
     * 第一个参数是高字节，第二个参数是低字节
     * java是采用补码的形式
     *
     * @param high
     * @param low
     * @return
     */
    public static short getShortHL(byte high, byte low) {
        return (short) ((high << 8) | (low & 0xFF));
    }

}