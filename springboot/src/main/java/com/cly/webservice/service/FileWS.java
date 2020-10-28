package com.cly.webservice.service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

@WebService(name = "FileWS", serviceName = "FileWS")
public interface FileWS {
    /**
     * 文件上传
     */
    @WebMethod
    boolean upload(@WebParam(name = "filename") String fileName, @WebParam(name = "fileExtension") String fileExtension, @XmlMimeType("application/octet-stream") DataHandler dataHandler);

    /**
     * 文件下载
     *
     * @return 文件
     */
    @WebMethod
    DataHandler download();
}
