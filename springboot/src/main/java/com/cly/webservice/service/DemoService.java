package com.cly.webservice.service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

/**
 * @Author changleying
 * @Date 2020/5/6 11:23
 **/
@WebService(name = "DemoService",
        targetNamespace = "http://service.webservice.cly.com")
public interface DemoService {

    @WebResult(name = "String")
    public String sayHello(@WebParam(name="user") String user);


    @WebResult(name = "String")
    public String saySorry(@WebParam(name="user") String user);

    @WebResult(name = "String")
    @WebMethod(action="http://dasd.com")
    public void noSayHello(@WebParam(name="user") String user);

    @WebResult(name = "sendServerImageResult")
    @XmlMimeType(value = "application/octet-stream")
    public DataHandler sendServerImage();

    public void receiveClientImage(@WebParam(name = "dataHandler")
                                   @XmlMimeType(value = "application/octet-stream")
                                           DataHandler dataHandler, @WebParam(name = "filename")
                                           String filename);

}
