package com.cly.webservice.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.cly.webservice.service.DemoService;

/**
 * @Author changleying
 * @Date 2020/5/6 11:25
 **/

@WebService(serviceName = "DemoService", // 与接口中指定的name一致
        targetNamespace = "http://service.webservice.cly.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.cly.webservice.service.DemoService"// 接口地址
)
/*@MTOM*/
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String user) {
        return "hello:"+user;
    }

    @Override
    public String saySorry(String user) {
        return "sorry:"+user;
    }

    @Override
    public void noSayHello(String user) {
        System.out.println("我久不告诉你");
        //return "sorry，"+user + "，现在时间：" + "(" + new SimpleDateFormat("HH:mm:ss").format(new Date())  + ")";
    }

    @Override
    public DataHandler sendServerImage() {
        File file = new File("E:\\server\\jQuery1.4 API-20100204.chm");

        DataSource dataSource = new FileDataSource(file);
        DataHandler dataHandler = new DataHandler(dataSource);
        return dataHandler;
    }

    @Override
    public void receiveClientImage(DataHandler dataHandler, String filename) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("E:\\server\\" + filename + "");
            dataHandler.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }


}
