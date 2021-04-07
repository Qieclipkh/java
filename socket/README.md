# 1.BIO
## 1.1 传统BIO
## 1.1 伪异步IO

为了解决同步阻塞I/0面临一个链路需要一个线程处理的问题，服务端通过一个线程池来处理多个客户端的请求，


[服务端](src/main/java/com/cly/socket/bio/TcpServer.java)

[客户端](src/main/java/com/cly/socket/bio/TcpClient.java)

# NIO