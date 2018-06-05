package com.sportsxdata.netty.testdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

    /**
     * 服务端监听的端口地址  IP：47.95.112.189，端口：9001
     */
    private static final int portNumber = 9001;

    public void start() throws InterruptedException {
        //用于处理服务器端接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //进行网络通信（读写）
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap b = new ServerBootstrap();
            //绑定两个线程组
            b.group(bossGroup, workerGroup);
            //指定NIO的模式
            b.channel(NioServerSocketChannel.class);
            //配置具体的数据处理方式
            b.childHandler(new HelloServerInitializer());
            //

//          b.option(ChannelOption.SO_BACKLOG, 128) //设置TCP缓冲区
//          b.option(ChannelOption.SO_SNDBUF, 32 * 1024) //设置发送数据缓冲大小
//          b.option(ChannelOption.SO_RCVBUF, 32 * 1024) //设置接受数据缓冲大小
//          b.childOption(ChannelOption.SO_KEEPALIVE, true); //保持连接

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}