package com.yzc.concurrency.moudle;

import java.util.concurrent.TimeUnit;

public class InterruptDemo2 {
    // 设置了中断，并响应了中断状态
    public static void main(String[] args) {
        Thread t = new MyThread();
        t.start();
        t.interrupt();
        System.out.println("已调用线程的interrupt方法");
    }
    static class MyThread extends Thread {
        public void run() {
            int num = -1;
            try {
                num = longTimeRunningInterruptMethod(2, 0);
            } catch (InterruptedException e) {
                System.out.println("线程被中断");
                throw new RuntimeException(e);
            }
            System.out.println("长时间任务运行结束,num=" + num);
            System.out.println("线程的中断状态:" + Thread.interrupted());
        }
        private static int longTimeRunningInterruptMethod(int count, int initNum)
                throws InterruptedException {
            for (int i = 0; i < count; i++) {
                // 该方法会响应中断
                TimeUnit.SECONDS.sleep(5);
            }
            return initNum;
        }
    }
}
