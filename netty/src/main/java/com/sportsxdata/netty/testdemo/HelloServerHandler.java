package com.sportsxdata.netty.testdemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    private File file =  new File("data.txt");
    private Logger logger = LoggerFactory.getLogger(HelloServer.class);
    private Integer count = 0;
    /**
     * ����˴���ͻ��˵ĺ��ķ���
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // �յ���Ϣֱ�Ӵ�ӡ���
       logger.info(ctx.channel().remoteAddress() + " Say : " + msg);

        FileOutputStream fileOutputStream = new FileOutputStream(file,true);
        byte[] aByte = msg.getBytes();
        fileOutputStream.write(aByte);
        String lineSeparator = System.getProperty("line.separator", "\n");
        fileOutputStream.write(lineSeparator.getBytes());
        fileOutputStream.close();

        // ���ؿͻ�����Ϣ - ���Ѿ����յ��������Ϣ
//        ctx.writeAndFlush("Received your message !\n");
    }


    protected HelloServerHandler() {
        super();
    }

    /**
     * �ͻ��������˴������ӵ�ʱ�����
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
        count++;
        logger.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        logger.info("��ǰ��������{}",count);

//        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");


    }


    /**
     * �ͻ��������˶Ͽ����ӵ�ʱ�����
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        count--;
        logger.info("��ǰ��������{}",count);
    }

    /**
     * ����˽��տͻ��˷��͹��������ݽ���֮�����
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * ���̳����쳣��ʱ�����
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}