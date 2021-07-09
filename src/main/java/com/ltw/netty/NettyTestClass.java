package com.ltw.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTestClass {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) //设置两个线程组
            .channel(NioServerSocketChannel.class) //使用NioSocketChannel 作为服务器通道实现;
            .option(ChannelOption.SO_BACKLOG, 128)  //设置线程队列得到的连接个数
            .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动链接状态
            .childHandler(new ChannelInitializer<SocketChannel>() {  //创建一个通道测试对象

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyServerHandler());
                }
            });  //给我们的workerGroup的EventLoop对应的管道设置处理器
            //绑定一个端口并同步,生成一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture sync = serverBootstrap.bind(6668).sync();
            //对关闭通道进行监听
            sync.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
