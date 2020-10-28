package com.cly.webservice.config;


import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

import com.cly.webservice.service.DemoService;
import com.cly.webservice.service.FileWS;
import com.cly.webservice.service.impl.DemoServiceImpl;

/**
 * @Author changleying
 * @Date 2020/5/6 11:27
 **/
@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        return new ServletRegistrationBean(cxfServlet, "/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Autowired
    private DemoService demoService;
    @Autowired
    private FileWS fileWS;
    @Bean
    public Endpoint endpoint() {
        SpringBus bus = springBus();
        EndpointImpl endpointD = new EndpointImpl(bus, demoService);
        endpointD.publish("/str");
        EndpointImpl endpoint = new EndpointImpl(springBus(), fileWS);
        endpoint.publish("/file");
        return endpoint;
    }

}
