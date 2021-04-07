package com.cly.netty.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author changleying
 * @Date 2020/6/19 9:59
 **/
public class NettyClient {


    private static ExecutorService executorService = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    private NettyClientHandler clientHandler;

    public <T> T getBean(Class<T> cls, String providerName) {
        final T t = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(clientHandler == null){
                    initClient();
                }
                clientHandler.setParam(providerName + args[0]);
                return executorService.submit(clientHandler).get();
            }
        });
        return t;
    }

    public void initClient() {
        clientHandler = new NettyClientHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                //将缓冲区小封包自动相连，组成较大的封包，阻止大量小封包的发送阻塞网络，提高网络应用效率；时延敏感的应用场景需要关闭该优化算法
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new NettyClientInitializer().setNettyClientHandler(clientHandler))
        ;
        try {
            final ChannelFuture cf = bootstrap.connect("127.0.0.1", 8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public NettyClientHandler getClientHandler() {
        return clientHandler;
    }
}
