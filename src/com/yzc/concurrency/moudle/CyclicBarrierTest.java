package com.yzc.concurrency.moudle;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CyclicBarrierTest() {
        int count = Runtime.getRuntime().availableProcessors();
        // 将一个栅栏操作传递给构造函数，这是一个runnable，当成功通过栅栏时会在一个子任务线程中执行它，
        // 但在阻塞线程被释放之前是不能执行的
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有线程均达到栅栏位置，开始下一轮计算");
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(i);
        }
    }

    private class Worker implements Runnable{
        int i;

        public Worker(int i){
            this.i = i;
        }

        @Override
        public void run() {
            for (int index = 1; index <= 3; index++){
                System.out.println("线程"+i+"第"+index+"次到达栅栏位置，等待其他线程到达");
                try{
                    barrier.await();
                } catch (InterruptedException e){
                    e.getStackTrace();
                    return;
                } catch (BrokenBarrierException e){
                    e.getStackTrace();
                    return;
                }
            }
        }
    }

    public void start(){
        for (int i = 0;i < workers.length; i++){
            new Thread(workers[i]).start();
        }
    }

    public static void main(String[] args) {
        new CyclicBarrierTest().start();
    }
}
