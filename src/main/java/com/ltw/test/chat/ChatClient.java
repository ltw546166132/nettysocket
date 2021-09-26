package com.ltw.test.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast(new ChatClientHandler());
                }
            });
            System.out.println("客户端  is  ok");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8807).sync();
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String s = scanner.nextLine();
                channel.writeAndFlush(s);
            }

            //给关闭通道增加一个监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}