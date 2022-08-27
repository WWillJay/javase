package com.java.se.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
    public static void main(String[] args) throws Exception {
        // port
        int port = 8888;
        // buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello ServerSocketChannel".getBytes());
        // ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);

        while (true){
            System.out.println("waiting for connections");
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                System.out.println("null");
                Thread.sleep(5000);
            } else {
                System.out.println("Incoming connection from:" + socketChannel.socket().getRemoteSocketAddress());
               // 指针指向0
                buffer.rewind();
                // 写操作
                socketChannel.write(buffer);
                socketChannel.close();
            }
        }
    }
}
