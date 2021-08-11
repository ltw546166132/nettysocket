package com.ltw.device.utils;

import com.ltw.device.config.Configurator;
import com.ltw.device.config.Configured;
import com.ltw.device.common.enums.PacketElement;
import com.ltw.device.common.enums.ParserFeature;
import com.ltw.device.exception.T212FormatException;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class T212Parser implements Configured<T212Parser>, Closeable {
    public static char[] HEADER = new char[]{'#', '#'};
    public static char[] FOODER = new char[]{'\r', '\n'};
    protected Reader reader;
    private int parserFeature;
    private int count;

    public T212Parser(Reader reader) {
        this.reader = reader;
    }

    public void setParserFeature(int parserFeature) {
        this.parserFeature = parserFeature;
    }

    public char[] readHeader() throws T212FormatException, IOException {
        char[] header = new char[2];
        this.count = this.reader.read(header);
        VerifyUtil.verifyLen(this.count, 2, PacketElement.HEADER);
        if (ParserFeature.HEADER_CONSTANT.enabledIn(this.parserFeature)) {
            VerifyUtil.verifyChar(header, HEADER, PacketElement.HEADER);
        }

        return header;
    }

    public char[] readDataLen() throws T212FormatException, IOException {
        char[] len = new char[4];
        this.count = this.reader.read(len);
        VerifyUtil.verifyLen(this.count, len.length, PacketElement.DATA_LEN);
        return len;
    }

    public int readInt32(int radix) throws IOException {
        char[] intChars = new char[4];
        this.count = this.reader.read(intChars);
        return this.count != 4 ? -1 : Integer.parseInt(new String(intChars), radix);
    }

    public char[] readData(int segmentLen) throws T212FormatException, IOException {
        char[] segment = new char[segmentLen];
        this.count = this.reader.read(segment);
        VerifyUtil.verifyLen(this.count, segmentLen, PacketElement.DATA);
        return segment;
    }

    public char[] readCrc() throws T212FormatException, IOException {
        char[] crc = new char[4];
        this.count = this.reader.read(crc);
        VerifyUtil.verifyLen(this.count, crc.length, PacketElement.DATA_CRC);
        return crc;
    }

    public char[] readFooter() throws T212FormatException, IOException {
        char[] footer = new char[2];
        this.count = this.reader.read(footer);
        VerifyUtil.verifyLen(this.count, 2, PacketElement.FOOTER);
        if (ParserFeature.FOOTER_CONSTANT.enabledIn(this.parserFeature)) {
            VerifyUtil.verifyChar(footer, FOODER, PacketElement.FOOTER);
        }

        return footer;
    }

    public char[] readDataAndCrc(int dataLen) throws IOException, T212FormatException {
        this.reader.mark(0);
        char[] data = new char[dataLen];
        this.count = this.reader.read(data);
        VerifyUtil.verifyLen(this.count, dataLen, PacketElement.DATA);
        int crc = this.readInt32(16);
        if (crc != -1 && crc16Checkout(data, dataLen) == crc) {
            return data;
        } else {
            this.reader.reset();
            return null;
        }
    }

    public static int crc16Checkout(char[] msg, int length) {
        int crc_reg = 65535;

        for(int i = 0; i < length; ++i) {
            crc_reg = crc_reg >> 8 ^ msg[i];

            for(int j = 0; j < 8; ++j) {
                int check = crc_reg & 1;
                crc_reg >>= 1;
                if (check == 1) {
                    crc_reg ^= 40961;
                }
            }
        }

        return crc_reg;
    }

    @Override
    public void configured(Configurator<T212Parser> configurator) {
        configurator.config(this);
    }

    @Override
    public void close() throws IOException {
        try {
            this.reader.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
