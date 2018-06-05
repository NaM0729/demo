package com.sportsxdata.netty.testdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

    /**
     * ����˼����Ķ˿ڵ�ַ  IP��47.95.112.189���˿ڣ�9001
     */
    private static final int portNumber = 9001;

    public void start() throws InterruptedException {
        //���ڴ���������˽��տͻ�������
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //��������ͨ�ţ���д��
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //���������࣬���ڷ�����ͨ����һϵ������
            ServerBootstrap b = new ServerBootstrap();
            //�������߳���
            b.group(bossGroup, workerGroup);
            //ָ��NIO��ģʽ
            b.channel(NioServerSocketChannel.class);
            //���þ�������ݴ���ʽ
            b.childHandler(new HelloServerInitializer());
            //

//          b.option(ChannelOption.SO_BACKLOG, 128) //����TCP������
//          b.option(ChannelOption.SO_SNDBUF, 32 * 1024) //���÷������ݻ����С
//          b.option(ChannelOption.SO_RCVBUF, 32 * 1024) //���ý������ݻ����С
//          b.childOption(ChannelOption.SO_KEEPALIVE, true); //��������

            // �������󶨶˿ڼ���
            ChannelFuture f = b.bind(portNumber).sync();
            // �����������رռ���
            f.channel().closeFuture().sync();

            // ���Լ�дΪ
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}