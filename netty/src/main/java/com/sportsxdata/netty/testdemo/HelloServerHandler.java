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
     * 服务端处理客户端的核心方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
       logger.info(ctx.channel().remoteAddress() + " Say : " + msg);

        FileOutputStream fileOutputStream = new FileOutputStream(file,true);
        byte[] aByte = msg.getBytes();
        fileOutputStream.write(aByte);
        String lineSeparator = System.getProperty("line.separator", "\n");
        fileOutputStream.write(lineSeparator.getBytes());
        fileOutputStream.close();

        // 返回客户端消息 - 我已经接收到了你的消息
//        ctx.writeAndFlush("Received your message !\n");
    }


    protected HelloServerHandler() {
        super();
    }

    /**
     * 客户端与服务端创建链接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
        count++;
        logger.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        logger.info("当前连接数：{}",count);

//        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");


    }


    /**
     * 客户端与服务端断开链接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        count--;
        logger.info("当前连接数：{}",count);
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 工程出现异常的时候调用
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