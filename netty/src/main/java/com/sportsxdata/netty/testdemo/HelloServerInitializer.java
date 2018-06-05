package com.sportsxdata.netty.testdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // ��("\n")Ϊ��β�ָ�� ������
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // �ַ������� �� ����
//        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
//        pipeline.addLast("encoder", new StringEncoder(Charset.forName("GBK")));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        // �Լ����߼�Handler
        pipeline.addLast("handler", new HelloServerHandler());
    }
}