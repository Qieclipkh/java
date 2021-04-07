package com.cly.netty.rpc;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class BytebufTest {
    private static final String charsetName = "UTF-8";

    public static void main(String[] args) throws Exception {
        String msg1 = "aaaa";
        byte[] msg1Byte = msg1.getBytes(charsetName);
        String msg2 = "bbbb";
        byte[] msg2Byte = msg2.getBytes(charsetName);
        byte[] dest = new byte[4];

        /**
         *
         * 参考 https://www.imooc.com/article/39788?block_id=tuijian_wz
         * 初始化 ByteBuf
         * 1.ByteBufAllocator （实现了池化，有效的降低了分配和释放内存的开销）
         *  1.1 PooledByteBufAllocator 池化了ByteBuf的实例以提高性能并最大限度地减少内存碎片
         *  1.2 UnpooledByteBufAllocator 不池化ByteBuf实例， 并且在每次它被调用时都会返回一个新的实例
         *
         * 2.Unpooled （Netty 提供的工具类来创建未池化的ByteBuf 实例）。
         *  创建一个未池化的基于堆内存存储的 ByteBuf 实例
         */

        ByteBuf byteBuf = Unpooled.buffer(8);
        /**
         * ===========writableBytes(8)=================capacity
         *
         * 0=readerIndex=writerIndex
         */

        // 写入4个字节数据
        byteBuf.writeBytes(msg1Byte);
        /**
         * =====readableBytes(4)======writableBytes(4)=======Capacity
         *
         * 0=readerIndex...........writerIndex=4.............
         */


        //读取4个字节
        byteBuf.readBytes(dest);
        System.out.println(new String(dest, charsetName));
        /**
         * =====readableBytes(0)======writableBytes(4)==========Capacity
         *
         * 0..........................readerIndex=writerIndex=4...
         */

        //byteBuf.discardReadBytes(); //释放已读空间
        //byteBuf.readableBytes();
        //byteBuf.writableBytes();
        //byteBuf.readerIndex();
        //byteBuf.writerIndex();

        byteBuf.writeBytes(msg2Byte);
        byteBuf.readBytes(dest);
        System.out.println(new String(dest, charsetName));
        //动态扩容
        byteBuf.writeBytes(msg1Byte);


        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put(msg1Byte);
        //复位，否则无法读取
        byteBuffer.flip();
        byteBuffer.mark();

        byteBuffer.get(dest);
        System.out.println(new String(dest, charsetName));
    }
}
