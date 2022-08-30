package com.java.se.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {

    public static void main(String[] args) throws Exception {
        Pipe pipe = Pipe.open();
        Thread threadA = new Thread(new ThreadA(pipe));
        Thread threadB = new Thread(new ThreadB(pipe));
        threadA.start();
        threadB.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


class ThreadA implements Runnable {

    private Pipe pipe;

    public ThreadA(Pipe pipe){
        this.pipe = pipe;
    }

    @Override
    public void run() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(("this is " + Thread.currentThread().getName()).getBytes());
        buffer.flip();
        try {
            Pipe.SinkChannel sinkChannel = pipe.sink();
            sinkChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadB implements Runnable {

    private Pipe pipe;

    public ThreadB(Pipe pipe){
        this.pipe = pipe;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            Pipe.SourceChannel sourceChannel = pipe.source();
            sourceChannel.read(buffer);
            System.out.println(Thread.currentThread().getName() + ":" + new String(buffer.array()));
            sourceChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}