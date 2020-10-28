package com.cly;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

public class TestWevservice {

    @Test
    public void testSayHello() throws Exception {
        String wsdlUrl = "http://172.16.15.199:8181/drsp-ebsm-server/api/services/9e0016c41f04419c81c460c556b2270c?wsdl";
        String method="sayHello";//sayHello  saySorry
        Object[] params = new Object[] { "2222" };

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlUrl);
        Object[] invoke = client.invoke(method, params);
        System.out.println(invoke[0]);
    }
}
