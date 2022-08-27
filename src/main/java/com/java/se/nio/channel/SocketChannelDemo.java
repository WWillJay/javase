package com.java.se.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannelDemo.create1();
        System.out.println("socketChannel.isOpen=" + socketChannel.isOpen());
        System.out.println("socketChannel.isConnected=" + socketChannel.isConnected());
        // 设置IO模式，非堵塞
        socketChannel.configureBlocking(false);
        // 读操作
        SocketChannelDemo.read(socketChannel);

    }

    public static SocketChannel create1() throws Exception {
        return SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
    }

    public static SocketChannel create2() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        return socketChannel;
    }

    public static void read(SocketChannel socketChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel.read(buffer);
        socketChannel.close();
        System.out.println("read over");
    }

}
