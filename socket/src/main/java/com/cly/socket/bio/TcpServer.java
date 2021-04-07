package com.cly.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpServer {
    /**
     * 监听端口
     */
    private Integer prot;

    public TcpServer(Integer prot) {
        this.prot = prot;


        this.pool = new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    }

    /**
     * 服务端处理一个单个客户端请求。
     * 一旦接收到一个客户端连接，此时不能接收其他客户端连接请求，只能等待当前连接的客户端的操作执行完成
     */
    public void startServer1() {
        try {
            ServerSocket serverSocket = new ServerSocket(prot);
            log.info("启动服务器....");
            /**
             * 监听客户端连接，该方法会同步阻塞，直到和客户端建立连接
             */
            Socket socket = serverSocket.accept();
            ServerSocketHandler.handlemessage(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过使用多线程，服务端处理多个客户端请求。在接收到一个客户端连接请求之后为每一个客户端创建一个新线程进行处理。
     * <p>
     * 接收请求独立在主线程，处理请求独立出来避免阻塞
     * <p>
     * 如果并发访问量增加，会导致线程数急剧膨胀
     */
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(prot);
            log.info("启动服务器....");
            while (true) {
                try {
                    /**
                     * 监听客户端连接，该方法会阻塞，直到和客户端建立连接
                     */
                    Socket socket = serverSocket.accept();
                    new Thread(new ServerSocketHandler(socket)).start();
                } catch (IOException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ExecutorService pool;
    private Integer poolSize = 10;

    /**
     * 使用多线程，服务端处理多个客户端请求
     */
    public void startServer2() {
        try {
            ServerSocket serverSocket = new ServerSocket(prot);
            log.info("启动服务器....");
            while (true) {
                try {
                    /**
                     * 监听客户端连接，该方法会阻塞，直到和客户端建立连接
                     */
                    Socket socket = serverSocket.accept();
                    pool.execute(new ServerSocketHandler(socket));
                } catch (IOException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Integer port = 8888;
        TcpServer tcpServer = new TcpServer(port);
        //tcpServer.startServer1();
        //tcpServer.startServer();
        tcpServer.startServer2();

    }


}
