package com.yzc.concurrency.excutor;

import java.util.concurrent.*;

public class Demo {
    private  final static ExecutorService executor = null;

    public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException {
        Future<?> task = executor.submit(r);
        try {
            task.get(timeout, unit);
        } catch (ExecutionException e) {
            // 在执行过程中，抛出了异常，重新抛出
            e.printStackTrace();
            throw e;
        } catch (TimeoutException e) {
            // 在规定时间没返回结果，任务将取消
            e.printStackTrace();
        } finally {
            // 如果任务已经结束，那么执行取消操作不会带来任何影响
            // 若果任务正在执行，那么将取消任务
            task.cancel(true);
        }
    }
}
