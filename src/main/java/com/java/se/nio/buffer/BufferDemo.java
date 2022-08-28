package com.java.se.nio.buffer;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {

    @Test
    public void  readBuffer() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("f:\\nio\\buffer\\demo.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int num = channel.read(buffer);
        while (num != -1){
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            num = channel.read(buffer);
        }
        channel.close();
        randomAccessFile.close();
    }


    @Test
    public void writeAndReadIntBuffer(){
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i * 2 + 2);
        }
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

    /**
     * 缓存区分片
     */
    @Test
    public void sliceBuffer(){
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        // 设置position和limit
        buffer.position(3);
        buffer.limit(7);
        // 从设置的position和limit之间设置分片
        ByteBuffer slice = buffer.slice();
        // buffer相当于数组
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            slice.put(i, (byte)(b * 10));
        }
        // 重置position和limit
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

    /**
     * 只读缓冲区
     */
    @Test
    public void readOnlyBuffer(){
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        // 创建只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        // 原缓冲区修改
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            buffer.put(i, (byte)(b * 10));
        }
        // 只读缓冲区设置position和limit
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());
        // 查看原缓冲区修改后，只读缓冲区是否可以修改
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }

    /**
     * 直接缓冲区
     */
    @Test
    public void directBuffer() throws Exception {

        FileInputStream fis = new FileInputStream("F:\\nio\\buffer\\direct\\direct_in.txt");
        FileChannel fisChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("F:\\nio\\buffer\\direct\\direct_out.txt");
        FileChannel fosChannel = fos.getChannel();

        // 创建直接缓冲区
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024);
        while (true){
            directBuffer.clear();
            int num = fisChannel.read(directBuffer);
            if (num == -1) {
                break;
            }
            directBuffer.flip();
            fosChannel.write(directBuffer);
        }
        fisChannel.close();
        fosChannel.close();
    }

    /**
     * 内存映射文件I/O
     */
    @Test
    public void mappingIO() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\nio\\buffer\\mappingIO\\mappingIO.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        map.put(0, (byte) 97);
        map.put(1023, (byte) 122);
        randomAccessFile.close();

    }
}
