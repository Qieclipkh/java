package com.cly.netty.echo.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        Integer port = 10011;
        initClient(host, port, null);
    }

    private static void initClient(String host, Integer port, NettyClientHandler nettyClientHandler) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                //将缓冲区小封包自动相连，组成较大的封包，阻止大量小封包的发送阻塞网络，提高网络应用效率；时延敏感的应用场景需要关闭该优化算法
                //.option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //业务逻辑 = new NettyClientHandler();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new NettyClientHandler());
                    }
                })

            ;
            try { ChannelFuture cf = bootstrap.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
