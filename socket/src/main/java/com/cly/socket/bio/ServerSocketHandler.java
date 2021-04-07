package com.cly.socket.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerSocketHandler implements Runnable {

    private final Socket socket;

    public ServerSocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        handlemessage(this.socket);
    }

    public static void handlemessage(Socket socket) {
        try {
            final int port = socket.getPort();
            log.info("客户端:" + port + "已连接到服务器");
            //客户端 > 服务端  读
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            //读取客户端发送来的消息
            String mess = br.readLine();
            log.info("客户端：" + mess);


            //服务端 > 客户端  写
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(  "你好客户端"+port+"，我是服务端\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
