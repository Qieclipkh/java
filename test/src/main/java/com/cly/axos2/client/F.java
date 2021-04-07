package com.cly.axos2.client;

import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;

public class F {
    public static void main(String[] args) throws Exception {
        DemoServiceStub demoServiceStub = new DemoServiceStub();
        DemoServiceStub.SayHelloE sd = new DemoServiceStub.SayHelloE();
        DemoServiceStub.SayHello param = new DemoServiceStub.SayHello();
        param.setUser("asdasd");
        sd.setSayHello(param);
        final DemoServiceStub.SayHelloResponseE sayHelloResponseE = demoServiceStub.sayHello(sd);
        final DemoServiceStub.SayHelloResponse sayHelloResponse = sayHelloResponseE.getSayHelloResponse();
        final String string = sayHelloResponse.getString();
        System.out.println(string);
    }
}
