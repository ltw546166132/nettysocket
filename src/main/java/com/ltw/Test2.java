package com.ltw;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Test2 {
    public static void main(String[] args) {
//        String imgData = "data:image/jpg;base64," +
               String imgData =  "/9j/6gBqSElKS3gAAABmMWU4OTFjMGZhZmNlOTU0ZGQ5OGE2MTEzYjI3YzY5NHqlNldKAAAAAAAA1OhTp1OwpWIBAAAAAAAAAAAAAAAAAAAAAAAAAAAkA";
        base64StringToImage(imgData);
    }

    static void base64StringToImage(String base64String) {
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File f1 = new File("E://out.jpg");
            ImageIO.write(bi1, "jpg", f1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
