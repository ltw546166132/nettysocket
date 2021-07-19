package com.ltw.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ltw
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置两个线程组
            serverBootstrap.group(bossGroup, workerGroup)
            //使用NioSocketChannel 作为服务器通道实现;
            .channel(NioServerSocketChannel.class)
            //设置线程队列得到的连接个数
            .option(ChannelOption.SO_BACKLOG, 1024)
            //设置保持活动链接状态
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            //创建一个通道测试对象
            .childHandler(new ChannelInitializer<SocketChannel>() {
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
