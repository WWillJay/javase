package com.java.se.nio.selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SelectorClientAndServerDemo {

    public static void main(String[] args) throws IOException {
        // 1.获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        // 2.切换成非阻塞模式
        socketChannel.configureBlocking(false);
        // 3.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 4.写入Buffer数据
            buffer.put((new Date() + "------>" + message).getBytes());
            // 5.读写模式切换
            buffer.flip();
            // 6.写入通道
            socketChannel.write(buffer);
            // 7.清除
            buffer.clear();
        }

    }

    @Test
    public void clientDemo() throws IOException {
        // 1.获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        // 2.切换成非阻塞模式
        socketChannel.configureBlocking(false);
        // 3.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4.写入Buffer数据
        buffer.put(new Date().toString().getBytes());
        // 5.读写模式切换
        buffer.flip();
        // 6.写入通道
        socketChannel.write(buffer);
        // 7.清除
        buffer.clear();

    }

    @Test
    public void serverDemo() throws IOException {
        // 1.获取服务通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 3.创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4.绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 5.获取Selector选择器
        Selector selector = Selector.open();
        // 6.通道注册到选择器，进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 7.选择器轮询
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if (iterator.hasNext()) {
                // 获取就绪操作
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    // 获取连接
                    SocketChannel accept = serverSocketChannel.accept();
                    // 切换成非阻塞
                    accept.configureBlocking(false);
                    // 注册
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int num;
                    while ((num = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, num));
                        byteBuffer.clear();
                    }
                } else if (selectionKey.isConnectable()){

                } else if (selectionKey.isWritable()){

                }

            }
            iterator.remove();
        }
    }

}
