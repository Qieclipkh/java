package com.cly.webservice.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.cly.webservice.service.CxfFileWrapper;
import com.cly.webservice.service.FileWS;

/**
 * Created by zhengweiyfw on 2018/4/9.
 */
@WebService
@Service
@Component("fileWS")
public class FileWSImpl implements FileWS {
    @Override
    @WebMethod
    public boolean upload(String fileName, String fileExtension, DataHandler dataHandler) {
        boolean result = true;
        OutputStream os = null;
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = dataHandler.getInputStream();
            File dest = new File("d:\\temp\\" + fileName);
            os = new FileOutputStream(dest);
            bos = new BufferedOutputStream(os);
            byte[] buffer = new byte[1024 * 1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    @Override
    @WebMethod
    public DataHandler download() {
        //下载文件的路径
        String filePath = "D:\\2.txt";
        CxfFileWrapper fileWrapper = new CxfFileWrapper();
        fileWrapper.setFileName("2.txt");
        fileWrapper.setFileExtension("txt");
        DataSource source = new FileDataSource(new File(filePath));
        final DataHandler dataHandler = new DataHandler(source);
        return dataHandler;
    }
}
