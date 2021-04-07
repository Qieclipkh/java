package com.cly.netty.echo.client;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * SimpleChannelInboundHandler 集成自 ChannelInboundHandlerAdapter
 * SimpleChannelInboundHandler 会自己释放 ReferenceCountUtil.release(msg);
 */

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 启动时候就会触发执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "我是"+ctx.channel().localAddress();
        msg="hello";
        ByteBuf byteBuf = ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), Charset.defaultCharset());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到服务器"+ctx.channel().remoteAddress()+"消息:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
