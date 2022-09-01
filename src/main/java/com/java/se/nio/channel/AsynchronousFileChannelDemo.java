package com.java.se.nio.channel;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannelDemo {

    @Test
    public void readByFuture() throws Exception {
        // 创建
        Path path = Paths.get("f:\\nio\\channel\\async\\demo.txt");
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 调用channel的read得到Future
        Future<Integer> future = asynchronousFileChannel.read(buffer, 0);
        // 判断Future是否完成，isDone()，知道结束时进行下一步
        while (!future.isDone());
        // 读取数据到buffer里面
        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }

    @Test
    public void writeByFuture() throws Exception {
        // 创建
        Path path = Paths.get("f:\\nio\\channel\\async\\demo.txt");
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Future write".getBytes());
        buffer.flip();
        Future<Integer> future = asynchronousFileChannel.write(buffer, 0);
        buffer.clear();
        // 判断Future是否完成，isDone()，知道结束时进行下一步
        while (!future.isDone());
        this.readByFuture();
    }

    @Test
    public void readByCompletionHandler() throws Exception {
        // 创建
        Path path = Paths.get("f:\\nio\\channel\\async\\demo.txt");
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asynchronousFileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
            }
        });
    }

    @Test
    public void writeByCompletionHandler() throws Exception {
        // 创建
        Path path = Paths.get("f:\\nio\\channel\\async\\demo.txt");
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("CompletionHandler write".getBytes());
        buffer.flip();
        asynchronousFileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
            }
        });
    }
}
