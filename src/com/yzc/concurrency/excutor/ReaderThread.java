package com.yzc.concurrency.excutor;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

// 改写interrupt方法将非标准的取消操作封装在Thread中
public class ReaderThread extends Thread{
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int count = in.read(buf);
                if(count<0){
                    break;
                } else {
                    // 读取数据
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
