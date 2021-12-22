package com.du.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author: duzhibin
 * @description:
 * @date: create in 23:45 2021/12/9
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);


        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()){
                //若未完成连接
                System.out.println("未完成连接，不会阻塞，继续其他工作");
            }
        }
        //成功则发送数据
        String str="你好，NIO";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
