package com.du.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: duzhibin
 * @description:
 * @date: create in 23:13 2021/12/9
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector=Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置未阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        while (true){
            if (selector.select(1000)==0){
                System.out.println("服务器阻塞了一秒，无连接");
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();
                // 若有客户端连接，则直接连接
                if (selectionKey.isAcceptable()){
                    // 得到客户端的连接socket
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个socketChannel："+socketChannel.hashCode());
                    //设置为未阻塞
                    socketChannel.configureBlocking(false);
                    // 同样进行注册到selector,并绑定buffer
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }
                if (selectionKey.isReadable()){
                    // 若为读状态，从selectionKey反向获取socketChannel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 获取注册时绑定的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("来自客户端的消息："+new String(byteBuffer.array()));

                }
                selectionKeyIterator.remove();
            }
        }

    }
}
