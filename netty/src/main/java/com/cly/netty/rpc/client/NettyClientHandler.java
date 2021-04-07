package com.cly.netty.rpc.client;

import java.util.concurrent.Callable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author changleying
 * @Date 2020/6/19 10:24
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<String> implements Callable<String> {


    private ChannelHandlerContext ctx;


    private String param;

    private String result;

    /**
     * 第1步调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    /**
     * 第4步调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //System.out.println(ctx.pipeline().hashCode());
        //System.out.println("客户端收到结果"+msg);
        result = msg;
        notify();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getCause());
        ctx.close();
    }

    /**
     *
     * 发送数据给服务器，然后等待服务器发送返回结果
     *
     * 第3步调用
     * 等待第4步返回结果
     * 第5步调用
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized String call() throws Exception {
        ctx.writeAndFlush(param);
        wait();
        return result;
    }

    /**
     * 第2步调用
     * @param param
     */
    public void setParam(String param) {
        this.param = param;
    }


    public synchronized String  execute() throws Exception {
        ctx.writeAndFlush(param);
        wait();
        return result;
    }

}
