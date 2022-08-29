package com.java.se.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {

    public static void main(String[] args) throws IOException {
        // 1.创建Selector
        Selector selector = Selector.open();
        // 创建通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 非阻塞
        serverSocketChannel.configureBlocking(false);
        // 绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 2.注册Channel到Selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 查询就绪通道操作
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        // 遍历集合
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            // 判断key状态
            if (key.isReadable()) {

            } else if (key.isWritable()) {

            } else if (key.isConnectable()) {

            } else if (key.isAcceptable()) {

            }
            iterator.remove();
        }
    }


}
