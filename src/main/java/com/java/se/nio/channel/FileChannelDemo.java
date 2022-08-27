package com.java.se.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelDemo {
    public static void main(String[] args) throws Exception {
        // 读操作
        FileChannelDemo.channel2Buffer();
        // 写操作
        FileChannelDemo.buffer2Channel();
        // 通道间传输
        FileChannelDemo.transferFrom();
        // 通道间传输
        FileChannelDemo.transferTo();
    }

    /**
     * FileChannel读取数据到Buffer中
     */
    public static void channel2Buffer() throws Exception {
        // 1.创建FileChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("f:\\nio\\channel\\demo.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        // 2.创建Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 3.读取数据到Buffer,channel不直接读写，通过buffer对象
        int byteRead = channel.read(buf);
        // 读到最后
        while (byteRead != -1){
            System.out.println("读取了：" + byteRead);
            // 读写转换
            buf.flip();
            // buffer还有值时
            while (buf.hasRemaining()){
                System.out.println((char) buf.get());
            }
            // 清除buffer
            buf.clear();
            // 继续读取channel中数据
            byteRead = channel.read(buf);
        }
        channel.close();
        randomAccessFile.close();
        System.out.println("读操作结束");
    }

    /**
     * FileChannel读取数据到Buffer中
     */
    public static void buffer2Channel() throws Exception {
        // 1.创建FileChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("f:\\nio\\channel\\demo.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        channel.position(58);
        // 2.创建Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 3.写操作
        String content = "FileChannel write!";
        buf.put(content.getBytes(StandardCharsets.UTF_8));
        buf.flip();
        while (buf.hasRemaining()){
            channel.write(buf);
        }
        channel.close();
        randomAccessFile.close();
        System.out.println("写操作结束");
    }

    /**
     * 通道之间传输
     * transferFrom()
     */
    public static void transferFrom() throws Exception {

        RandomAccessFile fromRandomAccessFile = new RandomAccessFile("f:\\nio\\channel\\transfer\\FromFile.txt", "rw");
        FileChannel fromChannel = fromRandomAccessFile.getChannel();

        RandomAccessFile toRandomAccessFile = new RandomAccessFile("f:\\nio\\channel\\transfer\\ToFile.txt", "rw");
        FileChannel toChannel = toRandomAccessFile.getChannel();

        toChannel.transferFrom(fromChannel, 0, fromChannel.size());
        fromRandomAccessFile.close();
        toRandomAccessFile.close();
    }

    /**
     * 通道之间传输
     * transferTo()
     */
    public static void transferTo() throws Exception {
        RandomAccessFile fromRandomAccessFile = new RandomAccessFile("f:\\nio\\channel\\transfer\\FromFile.txt", "rw");
        FileChannel fromChannel = fromRandomAccessFile.getChannel();

        RandomAccessFile toRandomAccessFile = new RandomAccessFile("f:\\nio\\channel\\transfer\\ToFile.txt", "rw");
        FileChannel toChannel = toRandomAccessFile.getChannel();
        fromChannel.transferTo(0, fromChannel.size(), toChannel);
        fromRandomAccessFile.close();
        toRandomAccessFile.close();
    }
}
