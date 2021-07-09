package com.ltw;

import cn.hutool.core.thread.GlobalThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class bio {
    static ExecutorService executor = GlobalThreadPool.getExecutor();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new  ServerSocket(6000);

        while (Boolean.TRUE){
            Socket accept = serverSocket.accept();
        }



    }
}
