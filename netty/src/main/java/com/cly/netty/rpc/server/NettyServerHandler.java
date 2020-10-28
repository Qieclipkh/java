package com.cly.netty.rpc.server;

import com.cly.netty.rpc.IHelloService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author changleying
 * @Date 2020/6/19 9:58
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.startsWith(IHelloService.PROVIDERNAME)) {
            String result = new HelloServiceImpl().hello(msg.substring(IHelloService.PROVIDERNAME.length()));
            System.out.println("服务器发送结果"+result);
            ctx.writeAndFlush(result);
        } else {
            System.out.println("无法处理的业务");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getCause());
        ctx.close();
    }
}
