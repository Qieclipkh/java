package com.cly.netty.rpc.server;

import com.cly.netty.rpc.IHelloService;

/**
 * @Author changleying
 * @Date 2020/6/19 10:00
 **/
public class HelloServiceImpl implements IHelloService {
    @Override
    public String hello(String str) {
        return "hello," + str;
    }
}
