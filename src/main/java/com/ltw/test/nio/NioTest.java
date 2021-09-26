package com.ltw.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel inputchannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel outputchannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for(;;){
            byteBuffer.clear();
            long position = inputchannel.position();
            System.out.println(position);
            int read = inputchannel.read(byteBuffer);
            if(read==-1){
                break;
            }
            byteBuffer.flip();
            outputchannel.write(byteBuffer);
        }
        inputchannel.close();
        outputchannel.close();
        fileOutputStream.close();
        fileInputStream.close();
    }
}
