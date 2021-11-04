package com.ltw.module.test.lifter;

public class BitToShortUtil {

    public static String getBitString(short b) {
        StringBuilder sb = new StringBuilder();
        sb.append((b >> 15) & 0x1).append((b >> 14) & 0x1).append((b >> 13) & 0x1).append((b >> 12) & 0x1)
                .append((b >> 11) & 0x1).append((b >> 10) & 0x1).append((b >> 9) & 0x1).append((b >> 8) & 0x1)
                .append((b >> 7) & 0x1).append((b >> 6) & 0x1).append((b >> 5) & 0x1).append((b >> 4) & 0x1)
                .append((b >> 3) & 0x1).append((b >> 2) & 0x1).append((b >> 1) & 0x1).append((b >> 0) & 0x1);
        return sb.toString();
    }

}