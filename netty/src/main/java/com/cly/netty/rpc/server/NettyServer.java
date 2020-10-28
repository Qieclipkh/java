package com.cly.netty.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author changleying
 * @Date 2020/6/19 9:49
 **/
public class NettyServer {


    public static void startServer(String host, int port) {
        startServer0(host, port);
    }

    private static void startServer0(String host, int port) {
        /**
         * 创建2个线程组 bossGroup workerGroup
         * <p>1.bossGroup 只是处理连接请求，真正和客户端业务处理，会交给workerGroup</p>
         * <p>2.两个都是无限循环</p>
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)// 设置线程队里得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)// 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 出站 编码
                            pipeline.addLast("encoder", new StringEncoder());
                            // 入站 解码
                            pipeline.addLast("decoder", new StringDecoder());
                            //业务逻辑
                            pipeline.addLast(new NettyServerHandler());
                        }
                    })// 给workerGroup的eventLoop对应的管道设置处理器
            ;
            // 绑定一个端口并且同步，生成一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(host, port).sync();
            /**
             * 对关闭通道进行监听
             * 如果没有，main方法所在线程（即主线程）在执行bind().sync()方法后，会进入finally，关闭服务
             */
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
