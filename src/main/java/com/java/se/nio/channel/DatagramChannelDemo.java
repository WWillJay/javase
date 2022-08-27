package com.java.se.nio.channel;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramChannelDemo {

    // 发送
    @Test
    public void sendDatagram() throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap("发送message".getBytes());
            sendChannel.send(buffer, sendAddress);
            System.out.println("已发送");
            Thread.sleep(5000);
        }
    }

    // 接收
    @Test
    public void receiveDatagram() throws Exception {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);
        receiveChannel.bind(receiveAddress);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(buffer);
            buffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(StandardCharsets.UTF_8.decode(buffer));
        }
    }

    // 连接
    @Test
    public void testConnect() throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();
        // 绑定
        datagramChannel.bind(new InetSocketAddress(9999));
        // 连接
        datagramChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        // write
        datagramChannel.write(ByteBuffer.wrap("发送connect message".getBytes()));

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            buffer.clear();
            datagramChannel.read(buffer);
            buffer.flip();
            System.out.println(StandardCharsets.UTF_8.decode(buffer));
        }
    }
}
