package com.cly.netty.rpc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author changleying
 * @Date 2020/6/19 9:57
 **/
public class NettServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 出站 编码
        pipeline.addLast("encoder", new StringEncoder());
        // 入站 解码
        pipeline.addLast("decoder", new StringDecoder());
        //业务 处理
        pipeline.addLast(new NettyServerHandler());
    }
}
