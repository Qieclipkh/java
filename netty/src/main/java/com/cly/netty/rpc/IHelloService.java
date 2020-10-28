package com.cly.netty.rpc;

/**
 * 公有接口-服务端和客户端都需要
 */
public interface IHelloService {
    static final String PROVIDERNAME = "helloserver#hello#";

    public String hello(String str);
}
