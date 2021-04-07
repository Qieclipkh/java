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
public class TcpClient implements Runnable {

    private String host;
    private Integer port;


    public TcpClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    private String message;

    private Integer localPort;
    private Socket socket;

    @Override
    public void run() {
        try {
            // 连接
            Socket socket = new Socket(host, port);
            this.socket = socket;
            Integer localPort = socket.getLocalPort();
            this.localPort = localPort;


            //发送消息
            sendMessage();
            //接收消息
            receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送消息
     */
    private void sendMessage(){
        OutputStream os;
        try {
            os = socket.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            //向服务器端发送一条消息
            bw.write(this.message+this.localPort+"\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收服务器消息
     */
    private void receiveMessage() {
        InputStream is = null;
        try {
            is = socket.getInputStream();
            //读取服务器返回的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //因为服务器发送消息以换行符结尾，所以直接使用readLine
            String mess = br.readLine();
            log.info(mess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8888;
        //启动客户端数量
        int n = 20;
        for (int i = 0; i < n; i++) {
            final TcpClient tcpClient = new TcpClient(host, port);
            tcpClient.setMessage("你好服务端，我是客户端");
            new Thread(tcpClient).start();
        }
    }
}
