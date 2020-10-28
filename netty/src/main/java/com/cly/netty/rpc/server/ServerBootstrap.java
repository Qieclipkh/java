package com.cly.netty.rpc.server;

/**
 * @Author changleying
 * @Date 2020/6/19 10:09
 **/
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("localhost", 10012);
    }
}
