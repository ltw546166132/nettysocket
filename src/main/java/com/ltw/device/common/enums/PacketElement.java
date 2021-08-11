package com.ltw.device.common.enums;

public enum PacketElement {
    HEADER(2),
    DATA_LEN(4),
    DATA(0),
    DATA_CRC(4),
    FOOTER(2);

    private int len;

    private PacketElement(int len) {
        this.len = len;
    }

    public int getLen() {
        return this.len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
