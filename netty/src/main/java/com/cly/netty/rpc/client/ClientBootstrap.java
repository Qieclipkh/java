package com.cly.netty.rpc.client;

import com.cly.netty.rpc.IHelloService;

/**
 * @Author changleying
 * @Date 2020/6/19 10:50
 **/
public class ClientBootstrap {

    /**
     * 1.
     * @param args
     */
    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        for (int i=0;i<10;i++){
            IHelloService helloService = client.getBean(IHelloService.class, IHelloService.PROVIDERNAME);
            String result = helloService.hello("World"+i);
            System.out.println("调用远程得到结果 " + result);
        }
    }
}
