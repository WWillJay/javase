package com.java.se.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatterAndGatherDemo {

    public void scatter() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("f:\\nio\\channel\\demo.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buf1 = ByteBuffer.allocate(128);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArr = {buf1, buf2};
        channel.read(bufferArr);
    }

    public void gather() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("f:\\nio\\channel\\demo.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buf1 = ByteBuffer.allocate(128);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArr = {buf1, buf2};
        channel.write(bufferArr);
    }

}
