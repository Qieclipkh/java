package com.cly.netty.echo.server;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端" + ctx.channel().remoteAddress() + "已连接");
    }

    /**
     * 读取客户端发送数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //System.out.println("Ctx:"+ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("接收到客户端发送的信息:"+byteBuf.toString(Charset.defaultCharset()));
    }

    /**
     * 读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello,"+ctx.channel().remoteAddress());
    }

    /**
     * 出错处理，关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
