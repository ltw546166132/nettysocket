package com.ltw.device;

import com.ltw.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YCServer {
    @Autowired
    private NettyServerConfig nettyConfig;

    public YCServer(){}

    public void run() {
        int port = this.nettyConfig.getPort();
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new ChannelHandler[]{new LineBasedFrameDecoder(2147483647)});
                    pipeline.addLast(new ChannelHandler[]{new YCHandler()});
                }
            }).option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
            System.out.println("服务关闭...");
        }
    }
}
